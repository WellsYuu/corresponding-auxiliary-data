/*
** String handling.
** Copyright (C) 2005-2017 Mike Pall. See Copyright Notice in luajit.h
*/

#define lj_str_c
#define LUA_CORE

#include "lj_obj.h"
#include "lj_gc.h"
#include "lj_err.h"
#include "lj_str.h"
#include "lj_char.h"

/* -- String helpers ------------------------------------------------------ */

/* Ordered compare of strings. Assumes string data is 4-byte aligned. */
int32_t LJ_FASTCALL lj_str_cmp(GCstr *a, GCstr *b)
{
  MSize i, n = a->len > b->len ? b->len : a->len;
#ifdef LUAJIT_USE_VALGRIND
  for (i = 0; i < n; i++) {
    uint8_t va = *(const uint8_t *)(strdata(a)+i);
    uint8_t vb = *(const uint8_t *)(strdata(b)+i);
    if (va != vb) {
        return va < vb ? -1 : 1;
    }
  }
#else
  for (i = 0; i < n; i += 4) {
    /* Note: innocuous access up to end of string + 3. */
    uint32_t va = *(const uint32_t *)(strdata(a)+i);
    uint32_t vb = *(const uint32_t *)(strdata(b)+i);
    if (va != vb) {
#if LJ_LE
      va = lj_bswap(va); vb = lj_bswap(vb);
#endif
      i -= n;
      if ((int32_t)i >= -3) {
	va >>= 32+(i<<3); vb >>= 32+(i<<3);
	if (va == vb) break;
      }
      return va < vb ? -1 : 1;
    }
  }
#endif
  return (int32_t)(a->len - b->len);
}

#ifndef LUAJIT_USE_VALGRIND
/* Fast string data comparison. Caveat: unaligned access to 1st string! */
static LJ_AINLINE int str_fastcmp(const char *a, const char *b, MSize len)
{
  MSize i = 0;
  lua_assert(len > 0);
  lua_assert((((uintptr_t)a+len-1) & (LJ_PAGESIZE-1)) <= LJ_PAGESIZE-4);
  do {  /* Note: innocuous access up to end of string + 3. */
    uint32_t v = lj_getu32(a+i) ^ *(const uint32_t *)(b+i);
    if (v) {
      i -= len;
#if LJ_LE
      return (int32_t)i >= -3 ? (v << (32+(i<<3))) : 1;
#else
      return (int32_t)i >= -3 ? (v >> (32+(i<<3))) : 1;
#endif
    }
    i += 4;
  } while (i < len);
  return 0;
}
#endif

/* Find fixed string p inside string s. */
const char *lj_str_find(const char *s, const char *p, MSize slen, MSize plen)
{
  if (plen <= slen) {
    if (plen == 0) {
      return s;
    } else {
      int c = *(const uint8_t *)p++;
      plen--; slen -= plen;
      while (slen) {
	const char *q = (const char *)memchr(s, c, slen);
	if (!q) break;
	if (memcmp(q+1, p, plen) == 0) return q;
	q++; slen -= (MSize)(q-s); s = q;
      }
    }
  }
  return NULL;
}

/* Check whether a string has a pattern matching character. */
int lj_str_haspattern(GCstr *s)
{
  const char *p = strdata(s), *q = p + s->len;
  while (p < q) {
    int c = *(const uint8_t *)p++;
    if (lj_char_ispunct(c) && strchr("^$*+?.([%-", c))
      return 1;  /* Found a pattern matching char. */
  }
  return 0;  /* No pattern matching chars found. */
}

/* -- String interning ---------------------------------------------------- */

/* Resize the string hash table (grow and shrink). */
void lj_str_resize(lua_State *L, MSize newmask)
{
  global_State *g = G(L);
  GCRef *newhash;
  MSize i;
  if (g->gc.state == GCSsweepstring || newmask >= LJ_MAX_STRTAB-1)
    return;  /* No resizing during GC traversal or if already too big. */
  newhash = lj_mem_newvec(L, newmask+1, GCRef);
  memset(newhash, 0, (newmask+1)*sizeof(GCRef));
  for (i = g->strmask; i != ~(MSize)0; i--) {  /* Rehash old table. */
    GCobj *p = gcref(g->strhash[i]);
    while (p) {  /* Follow each hash chain and reinsert all strings. */
      MSize h = gco2str(p)->hash & newmask;
      GCobj *next = gcnext(p);
      /* NOBARRIER: The string table is a GC root. */
      setgcrefr(p->gch.nextgc, newhash[h]);
      setgcref(newhash[h], p);
      p = next;
    }
  }
  lj_mem_freevec(g, g->strhash, g->strmask+1, GCRef);
  g->strmask = newmask;
  g->strhash = newhash;
}

static MSize
lj_str_original_hash(const char *str, size_t lenx) {
  MSize len = (MSize)lenx;
  MSize a, b, h = len;

  /* Compute string hash. Constants taken from lookup3 hash by Bob Jenkins. */
  if (len >= 4) {  /* Caveat: unaligned access! */
    a = lj_getu32(str);
    h ^= lj_getu32(str+len-4);
    b = lj_getu32(str+(len>>1)-2);
    h ^= b; h -= lj_rol(b, 14);
    b += lj_getu32(str+(len>>2)-1);
  } else if (len > 0) {
    a = *(const uint8_t *)str;
    h ^= *(const uint8_t *)(str+len-1);
    b = *(const uint8_t *)(str+(len>>1));
    h ^= b; h -= lj_rol(b, 14);
  } else {
    return 0;
  }

  a ^= h; a -= lj_rol(h, 11);
  b ^= a; b -= lj_rol(a, 25);
  h ^= b; h -= lj_rol(b, 16);

  return h;
}

MSize
lj_str_indep_hash(GCstr *str) {
  return lj_str_original_hash(strdata(str), str->len);
}

#include "x64/src/lj_str_hash_x64.h"

#if defined(LJ_ARCH_STR_HASH)
#define LJ_STR_HASH LJ_ARCH_STR_HASH
#else
#define LJ_STR_HASH lj_str_original_hash
#endif

/* Intern a string and return string object. */
GCstr *lj_str_new(lua_State *L, const char *str, size_t lenx)
{
  global_State *g;
  GCstr *s;
  GCobj *o;
  MSize len = (MSize)lenx;
  MSize h;

  if (lenx >= LJ_MAX_STR)
    lj_err_msg(L, LJ_ERR_STROV);
  g = G(L);
  if (LJ_UNLIKELY(lenx == 0)) {
    return &g->strempty;
  }

  h = LJ_STR_HASH(str, lenx);

  /* Check if the string has already been interned. */
  o = gcref(g->strhash[h & g->strmask]);
#ifndef LUAJIT_USE_VALGRIND
  if (LJ_LIKELY((((uintptr_t)str+len-1) & (LJ_PAGESIZE-1)) <= LJ_PAGESIZE-4)) {
    while (o != NULL) {
      GCstr *sx = gco2str(o);
      if (sx->len == len && sx->hash == h && str_fastcmp(str, strdata(sx), len) == 0) {
	/* Resurrect if dead. Can only happen with fixstring() (keywords). */
	if (isdead(g, o)) flipwhite(o);
	return sx;  /* Return existing string. */
      }
      o = gcnext(o);
    }
  } else {  /* Slow path: end of string is too close to a page boundary. */
#endif
    while (o != NULL) {
      GCstr *sx = gco2str(o);
      if (sx->len == len && sx->hash == h && memcmp(str, strdata(sx), len) == 0) {
	/* Resurrect if dead. Can only happen with fixstring() (keywords). */
	if (isdead(g, o)) flipwhite(o);
	return sx;  /* Return existing string. */
      }
      o = gcnext(o);
    }
#ifndef LUAJIT_USE_VALGRIND
  }
#endif
  /* Nope, create a new string. */
  s = lj_mem_newt(L, sizeof(GCstr)+len+1, GCstr);
  newwhite(g, s);
  s->gct = ~LJ_TSTR;
  s->len = len;
  s->hash = h;
  s->reserved = 0;
  memcpy(strdatawr(s), str, len);
  strdatawr(s)[len] = '\0';  /* Zero-terminate string. */
  /* Add it to string hash table. */
  h &= g->strmask;
  s->nextgc = g->strhash[h];
  /* NOBARRIER: The string table is a GC root. */
  setgcref(g->strhash[h], obj2gco(s));
  if (g->strnum++ > g->strmask)  /* Allow a 100% load factor. */
    lj_str_resize(L, (g->strmask<<1)+1);  /* Grow string table. */
  return s;  /* Return newly interned string. */
}

void LJ_FASTCALL lj_str_free(global_State *g, GCstr *s)
{
  g->strnum--;
  lj_mem_free(g, s, sizestring(s));
}

