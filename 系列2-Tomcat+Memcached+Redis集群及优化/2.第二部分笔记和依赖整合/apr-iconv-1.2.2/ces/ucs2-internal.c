/*-
 * Copyright (c) 1999,2000
 *	Konstantin Chuguev.  All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 * 3. All advertising materials mentioning features or use of this software
 *    must display the following acknowledgement:
 *	This product includes software developed by Konstantin Chuguev
 *	and its contributors.
 *
 * THIS SOFTWARE IS PROVIDED BY THE AUTHOR AND CONTRIBUTORS ``AS IS'' AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED.  IN NO EVENT SHALL THE AUTHOR OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS
 * OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
 * LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY
 * OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 *
 *	iconv (Charset Conversion Library) v1.0
 */

#define ICONV_INTERNAL
#include "iconv.h"

static const char * const names[] = {
	"ucs2-internal", NULL
};

static const char * const *
ucs2_names(struct iconv_ces *ces)
{
	return names;
}

static APR_INLINE int
ucs2_nbytes(struct iconv_ces *ces)
{
	return sizeof(ucs2_t);
}

static apr_ssize_t
convert_from_ucs(struct iconv_ces *ces, ucs_t in,
	unsigned char **outbuf, apr_size_t *outbytesleft)
{
	ucs2_t *ptr = (ucs2_t *) *outbuf;
	if (in == UCS_CHAR_NONE)
		return 1;	/* No state reinitialization for table charsets */
	if (iconv_char32bit(in))
		return -1;	/* No corresponding character in UCS-2 */
	if (*outbytesleft < sizeof(ucs2_t))
		return 0;	/* No space in the output buffer */
	*ptr = (ucs2_t) in;
	*outbuf += sizeof(ucs2_t);
	(*outbytesleft) -= sizeof(ucs2_t);
	return 1;
}

static ucs_t
convert_to_ucs(struct iconv_ces *ces,
	const unsigned char **inbuf, apr_size_t *inbytesleft)
{
	const ucs2_t *ret = (const ucs2_t *)*inbuf;
	if (*inbytesleft < sizeof(ucs2_t))
		return UCS_CHAR_NONE;	/* Not enough bytes in the input buffer */
	(*inbytesleft) -= sizeof(ucs2_t);
	*inbuf += sizeof(ucs2_t);
	return *ret;
}

static const struct iconv_ces_desc iconv_ces_desc = {
	apr_iconv_ces_open_zero,
	apr_iconv_ces_zero,
	apr_iconv_ces_no_func,
	ucs2_names,
	apr_iconv_ces_nbits8,
	ucs2_nbytes,
	convert_from_ucs,
	convert_to_ucs,
	NULL
};

struct iconv_module_desc iconv_module = {
	ICMOD_UC_CES,
	apr_iconv_mod_noevent,
	NULL,
	&iconv_ces_desc
};
