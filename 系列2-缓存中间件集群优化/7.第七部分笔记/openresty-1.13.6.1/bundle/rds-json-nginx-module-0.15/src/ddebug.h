#ifndef DDEBUG_H
#define DDEBUG_H

#include <ngx_config.h>
#include <ngx_core.h>

#if defined(DDEBUG) && (DDEBUG)

#   define dd_dump_chain_size() { \
        int              n; \
        ngx_chain_t     *cl; \
            \
        for (n = 0, cl = ctx->out; cl; cl = cl->next, n++) { \
        } \
            \
        dd("chain size: %d", n); \
    }

#   if (NGX_HAVE_VARIADIC_MACROS)

#       define dd(...) fprintf(stderr, "rds-json *** %s: ", __func__); \
            fprintf(stderr, __VA_ARGS__); \
            fprintf(stderr, " at %s line %d.\n", __FILE__, __LINE__)

#   else

#include <stdarg.h>
#include <stdio.h>

#include <stdarg.h>

static ngx_inline void
dd(const char * fmt, ...) {
}

#    endif

#else

#   define dd_dump_chain_size()

#   if (NGX_HAVE_VARIADIC_MACROS)

#       define dd(...)

#   else

#include <stdarg.h>

static ngx_inline void
dd(const char * fmt, ...) {
}

#   endif

#endif

#if defined(DDEBUG) && (DDEBUG)

#define dd_check_read_event_handler(r)   \
    dd("r->read_event_handler = %s", \
        r->read_event_handler == ngx_http_block_reading ? \
            "ngx_http_block_reading" : \
        r->read_event_handler == ngx_http_test_reading ? \
            "ngx_http_test_reading" : \
        r->read_event_handler == ngx_http_request_empty_handler ? \
            "ngx_http_request_empty_handler" : "UNKNOWN")

#define dd_check_write_event_handler(r)   \
    dd("r->write_event_handler = %s", \
        r->write_event_handler == ngx_http_handler ? \
            "ngx_http_handler" : \
        r->write_event_handler == ngx_http_core_run_phases ? \
            "ngx_http_core_run_phases" : \
        r->write_event_handler == ngx_http_request_empty_handler ? \
            "ngx_http_request_empty_handler" : "UNKNOWN")

#else

#define dd_check_read_event_handler(r)
#define dd_check_write_event_handler(r)

#endif

#endif /* DDEBUG_H */

