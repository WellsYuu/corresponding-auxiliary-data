typedef struct { int dummy; } ngx_str_t;
typedef int64_t ngx_int_t;
typedef uint64_t ngx_uint_t;
typedef ngx_uint_t ngx_msec_t;
typedef struct { int dummy; } ngx_module_t;
typedef struct { int dummy; } ngx_http_module_t;
typedef struct { int dummy; } ngx_table_elt_t;
typedef struct { int dummy; } ngx_event_t;
typedef struct { int dummy; } ngx_pool_t;
typedef char unsigned u_char;


provider nginx {
    /* probes for subrequests */
    probe http__subrequest__cycle(void *pr, ngx_str_t *uri, ngx_str_t *args);
    probe http__subrequest__start(void *r);
    probe http__subrequest__finalize_writing(void *r);
    probe http__subrequest__finalize_nonactive(void *r);
    probe http__subrequest__wake__parent(void *r);
    probe http__subrequest__done(void *r);
    probe http__subrequest__post__start(void *r, ngx_int_t rc);
    probe http__subrequest__post__done(void *r, ngx_int_t rc);
    probe http__module__post__config(ngx_module_t *m);
    probe http__read__body__done(void *r);
    probe http__read__req__line__done(void *r);
    probe http__read__req__header__done(void *r, ngx_table_elt_t *h);
    probe timer__add(ngx_event_t *ev, ngx_msec_t timer);
    probe timer__del(ngx_event_t *ev);
    probe timer__expire(ngx_event_t *ev);
    probe create__pool__done(ngx_pool_t *pool, size_t size);
};


#pragma D attributes Evolving/Evolving/Common      provider nginx provider
#pragma D attributes Private/Private/Unknown       provider nginx module
#pragma D attributes Private/Private/Unknown       provider nginx function
#pragma D attributes Private/Private/Common        provider nginx name
#pragma D attributes Evolving/Evolving/Common      provider nginx args

