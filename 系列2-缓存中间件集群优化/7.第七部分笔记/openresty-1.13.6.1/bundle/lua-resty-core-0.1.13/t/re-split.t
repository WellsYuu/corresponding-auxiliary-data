# vim:set ft= ts=4 sw=4 et fdm=marker:
use lib 'lib';
use Test::Nginx::Socket::Lua;
use Cwd qw(cwd);

#worker_connections(1014);
#master_process_enabled(1);
#log_level('warn');

repeat_each(2);

plan tests => repeat_each() * blocks() * 4 + (2 * repeat_each());

my $pwd = cwd();

our $HttpConfig = <<_EOC_;
    lua_package_path "$pwd/lib/?.lua;../lua-resty-lrucache/lib/?.lua;;";
    init_by_lua_block {
        -- local verbose = true
        local verbose = false
        local outfile = "$Test::Nginx::Util::ErrLogFile"
        -- local outfile = "/tmp/v.log"
        if verbose then
            local dump = require "jit.dump"
            dump.on(nil, outfile)
        else
            local v = require "jit.v"
            v.on(outfile)
        end

        require "resty.core"
        -- jit.opt.start("hotloop=1")
        -- jit.opt.start("loopunroll=1000000")
        -- jit.off()
    }
_EOC_

no_diff();
no_long_string();
check_accum_error_log();
run_tests();

__DATA__

=== TEST 1: split matches, no submatch, no jit compile, no regex cache
--- http_config eval: $::HttpConfig
--- config
    location = /re {
        content_by_lua_block {
            local ngx_re = require "ngx.re"

            local res, err = ngx_re.split("a,b,c,d", ",")
            if err then
                ngx.log(ngx.ERR, "failed: ", err)
                return
            end

            for i = 1, #res do
                ngx.say(res[i])
            end
        }
    }
--- request
GET /re
--- response_body
a
b
c
d
--- error_log eval
qr/\[TRACE   \d+/
--- no_error_log
[error]



=== TEST 2: split matches, no submatch, no jit compile, no regex cache
--- http_config eval: $::HttpConfig
--- config
    location = /re {
        content_by_lua_block {
            local ngx_re = require "ngx.re"

            local res, err = ngx_re.split("a;,b;,c;,d;e", ";,")
            if err then
                ngx.log(ngx.ERR, "failed: ", err)
                return
            end

            for i = 1, #res do
                ngx.say(res[i])
            end
        }
    }
--- request
GET /re
--- response_body
a
b
c
d;e
--- error_log eval
qr/\[TRACE   \d+/
--- no_error_log
[error]



=== TEST 3: split matches, no submatch, jit compile, regex cache
--- http_config eval: $::HttpConfig
--- config
    location = /re {
        content_by_lua_block {
            local ngx_re = require "ngx.re"

            local res, err = ngx_re.split("a,b,c,d", ",", "jo")
            if err then
                ngx.log(ngx.ERR, "failed: ", err)
                return
            end

            for i = 1, #res do
                ngx.say(res[i])
            end
        }
    }
--- request
GET /re
--- response_body
a
b
c
d
--- error_log eval
qr/\[TRACE   \d+/
--- no_error_log
[error]



=== TEST 4: split matches + submatch (matching)
--- http_config eval: $::HttpConfig
--- config
    location = /re {
        content_by_lua_block {
            local ngx_re = require "ngx.re"

            local res, err = ngx_re.split("a;,b;,c;,d,e", "(;),")
            if err then
                ngx.log(ngx.ERR, "failed: ", err)
                return
            end

            for i = 1, #res do
                ngx.say(res[i])
            end
        }
    }
--- request
GET /re
--- response_body
a
;
b
;
c
;
d,e
--- error_log eval
qr/\[TRACE   \d+/
--- no_error_log
[error]



=== TEST 5: split matches + submatch (not matching)
--- http_config eval: $::HttpConfig
--- config
    location = /re {
        content_by_lua_block {
            local ngx_re = require "ngx.re"

            local res, err = ngx_re.split("a,b,c,d,e", "(;)|,")
            if err then
                ngx.log(ngx.ERR, "failed: ", err)
                return
            end

            for i = 1, #res do
                ngx.say(res[i])
            end
        }
    }
--- request
GET /re
--- response_body
a
b
c
d
e
--- error_log eval
qr/\[TRACE   \d+/
--- no_error_log
[error]



=== TEST 6: split matches + max limiter
--- http_config eval: $::HttpConfig
--- config
    location = /re {
        content_by_lua_block {
            local ngx_re = require "ngx.re"

            local res, err = ngx_re.split("a,b,c,d,e", ",", nil, nil, 3)
            if err then
                ngx.log(ngx.ERR, "failed: ", err)
                return
            end

            for i = 1, #res do
                ngx.say(res[i])
            end
        }
    }
--- request
GET /re
--- response_body
a
b
c,d,e
--- error_log eval
qr/\[TRACE   \d+/
--- no_error_log
[error]



=== TEST 7: split matches + submatch + max limiter
--- http_config eval: $::HttpConfig
--- config
    location = /re {
        content_by_lua_block {
            local ngx_re = require "ngx.re"

            local res, err = ngx_re.split("a,b,c,d,e", "(,)", nil, nil, 3)
            if err then
                ngx.log(ngx.ERR, "failed: ", err)
                return
            end

            for i = 1, #res do
                ngx.say(res[i])
            end
        }
    }
--- request
GET /re
--- response_body
a
,
b
,
c,d,e
--- error_log eval
qr/\[TRACE   \d+/
--- no_error_log
[error]



=== TEST 8: split matches + max limiter set to 0
--- http_config eval: $::HttpConfig
--- config
    location = /re {
        content_by_lua_block {
            local ngx_re = require "ngx.re"

            local res, err = ngx_re.split("a,b,c,d,e", ",", nil, nil, 0)
            if err then
                ngx.log(ngx.ERR, "failed: ", err)
                return
            end

            for i = 1, #res do
                ngx.say(res[i])
            end
        }
    }
--- request
GET /re
--- response_body
a
b
c
d
e
--- error_log eval
qr/\[TRACE   \d+/
--- no_error_log
[error]



=== TEST 9: split matches + max limiter set to a negative value
--- http_config eval: $::HttpConfig
--- config
    location = /re {
        content_by_lua_block {
            local ngx_re = require "ngx.re"

            local res, err = ngx_re.split("a,b,c,d,e", ",", nil, nil, -1)
            if err then
                ngx.log(ngx.ERR, "failed: ", err)
                return
            end

            for i = 1, #res do
                ngx.say(res[i])
            end
        }
    }
--- request
GET /re
--- response_body
a
b
c
d
e
--- error_log eval
qr/\[TRACE   \d+/
--- no_error_log
[error]



=== TEST 10: split matches + max limiter set to 1
--- http_config eval: $::HttpConfig
--- config
    location = /re {
        content_by_lua_block {
            local ngx_re = require "ngx.re"

            local res, err = ngx_re.split("a,b,c,d,e", ",", nil, nil, 1)
            if err then
                ngx.log(ngx.ERR, "failed: ", err)
                return
            end

            for i = 1, #res do
                ngx.say(res[i])
            end
        }
    }
--- request
GET /re
--- response_body
a,b,c,d,e
--- error_log eval
qr/\[TRACE   \d+/
--- no_error_log
[error]



=== TEST 11: split matches, provided res table
--- http_config eval: $::HttpConfig
--- config
    location = /re {
        content_by_lua_block {
            local ngx_re = require "ngx.re"

            local my_table = {}

            local res, err = ngx_re.split("a,b,c,d,e", ",", nil, nil, nil, my_table)
            if err then
                ngx.log(ngx.ERR, "failed: ", err)
                return
            end

            for i = 1, #res do
                ngx.say(res[i])
            end
        }
    }
--- request
GET /re
--- response_body
a
b
c
d
e
--- error_log eval
qr/\[TRACE   \d+/
--- no_error_log
[error]



=== TEST 12: split matches, provided res table (non-cleared)
--- http_config eval: $::HttpConfig
--- config
    location = /re {
        content_by_lua_block {
            local ngx_re = require "ngx.re"

            local my_table = {}

            for i = 1, 10 do
                my_table[i] = i.." hello world"
            end

            local res, err = ngx_re.split("a,b,c,d,e", ",", nil, nil, nil, my_table)
            if err then
                ngx.log(ngx.ERR, "failed: ", err)
                return
            end

            for i in ipairs(my_table) do
                ngx.say(res[i])
            end
        }
    }
--- request
GET /re
--- response_body
a
b
c
d
e
--- error_log eval
qr/\[TRACE   \d+/
--- no_error_log
[error]



=== TEST 13: split matches, provided res table + max limiter
--- http_config eval: $::HttpConfig
--- config
    location = /re {
        content_by_lua_block {
            local ngx_re = require "ngx.re"

            local my_table = {"hello, world"}

            local res, err = ngx_re.split("a,b,c,d,e", ",", nil, nil, 3, my_table)
            if err then
                ngx.log(ngx.ERR, "failed: ", err)
                return
            end

            for i = 1, #my_table do
                ngx.say(res[i])
            end
        }
    }
--- request
GET /re
--- response_body
a
b
c,d,e
--- error_log eval
qr/\[TRACE   \d+/
--- no_error_log
[error]



=== TEST 14: split matches, provided res table (non-cleared) + max limiter
--- http_config eval: $::HttpConfig
--- config
    location = /re {
        content_by_lua_block {
            local ngx_re = require "ngx.re"

            local my_table = {}

            for i = 1, 10 do
                my_table[i] = i.." hello world"
            end

            local res, err = ngx_re.split("a,b,c,d,e", ",", nil, nil, 3, my_table)
            if err then
                ngx.log(ngx.ERR, "failed: ", err)
                return
            end

            for i in ipairs(my_table) do
                ngx.say(res[i])
            end
        }
    }
--- request
GET /re
--- response_body
a
b
c,d,e
--- error_log eval
qr/\[TRACE   \d+/
--- no_error_log
[error]



=== TEST 15: split matches, provided res table + max limiter + sub-match capturing group
--- http_config eval: $::HttpConfig
--- config
    location = /re {
        content_by_lua_block {
            local ngx_re = require "ngx.re"

            local my_table = {"hello, world"}

            local res, err = ngx_re.split("a,b,c,d,e", "(,)", nil, nil, 3, my_table)
            if err then
                ngx.log(ngx.ERR, "failed: ", err)
                return
            end

            for i = 1, #my_table do
                ngx.say(res[i])
            end
        }
    }
--- request
GET /re
--- response_body
a
,
b
,
c,d,e
--- error_log eval
qr/\[TRACE   \d+/
--- no_error_log
[error]



=== TEST 16: split matches, ctx arg
--- http_config eval: $::HttpConfig
--- config
    location = /re {
        content_by_lua_block {
            local ngx_re = require "ngx.re"

            local res, err = ngx_re.split("a,b,c,d,e", ",", nil, { pos = 5 })
            if err then
                ngx.log(ngx.ERR, "failed: ", err)
                return
            end

            for i = 1, #res do
                ngx.say(res[i])
            end
        }
    }
--- request
GET /re
--- response_body
c
d
e
--- error_log eval
qr/\[TRACE   \d+/
--- no_error_log
[error]



=== TEST 17: split matches, trailing subjects
--- http_config eval: $::HttpConfig
--- config
    location /re {
        content_by_lua_block {
            local ngx_re = require "ngx.re"

            local res, err = ngx_re.split(",a,b,c,d,", ",")
            if err then
                ngx.log(ngx.ERR, "failed: ", err)
                return
            end

            for i = 1, #res do
                if res[i] == "" then
                    ngx.say("_blank_")
                else
                    ngx.say(res[i])
                end
            end
        }
    }
--- request
GET /re
--- response_body
_blank_
a
b
c
d
_blank_
--- error_log eval
qr/\[TRACE   \d+/
--- no_error_log
[error]
attempt to get length of local 'regex' (a number value)



=== TEST 18: split matches, real use-case
--- http_config eval: $::HttpConfig
--- config
    location /re {
        content_by_lua_block {
            local ngx_re = require "ngx.re"

            local res, err = ngx_re.split("abcd,erfg,ghij;hello world;aaa", ",|;")
            if err then
                ngx.log(ngx.ERR, "failed: ", err)
                return
            end

            for i = 1, #res do
                ngx.say(res[i])
            end
        }
    }
--- request
GET /re
--- response_body
abcd
erfg
ghij
hello world
aaa
--- error_log eval
qr/\[TRACE   \d+/
--- no_error_log
[error]



=== TEST 19: split no matches
--- http_config eval: $::HttpConfig
--- config
    location /re {
        content_by_lua_block {
            local ngx_re = require "ngx.re"

            local res, err = ngx_re.split("abcd", ",")
            if err then
                ngx.log(ngx.ERR, "failed: ", err)
                return
            end

            for i = 1, #res do
                ngx.say(res[i])
            end
        }
    }
--- request
GET /re
--- response_body
abcd
--- error_log eval
qr/\[TRACE   \d+/
--- no_error_log
[error]



=== TEST 20: subject is not a string type
--- http_config eval: $::HttpConfig
--- config
    location /re {
        content_by_lua_block {
            local ngx_re = require "ngx.re"

            local res, err = ngx_re.split(1234512345, "23", "jo")
            if err then
                ngx.log(ngx.ERR, "failed: ", err)
                return
            end

            for i = 1, #res do
                ngx.say(res[i])
            end
        }
    }
--- request
GET /re
--- response_body
1
451
45
--- error_log eval
qr/\[TRACE   \d+/
--- no_error_log
[error]
attempt to get length of local 'subj' (a number value)



=== TEST 21: split matches, pos is larger than subject length
--- http_config eval: $::HttpConfig
--- config
    location = /re {
        content_by_lua_block {
            local ngx_re = require "ngx.re"

            local res, err = ngx_re.split("a,b,c,d,e", ",", nil, { pos = 10 })
            if err then
                ngx.log(ngx.ERR, "failed: ", err)
                return
            end

            for i = 1, #res do
                ngx.say(res[i])
            end
            ngx.say("len: ", #res)
        }
    }
--- request
GET /re
--- response_body
len: 0
--- no_error_log
[error]
[TRACE



=== TEST 22: regex is ""
--- http_config eval: $::HttpConfig
--- config
    location /re {
        content_by_lua_block {
            local ngx_re = require "ngx.re"

            local res, err = ngx_re.split("12345", "", "jo")
            if err then
                ngx.log(ngx.ERR, "failed: ", err)
                return
            end

            for i = 1, #res do
                ngx.say(res[i])
            end

            ngx.say("len: ", #res)
        }
    }
--- request
GET /re
--- response_body
1
2
3
4
5
len: 5
--- error_log eval
qr/\[TRACE   \d+/
--- no_error_log
[error]



=== TEST 23: regex is "" with max
--- http_config eval: $::HttpConfig
--- config
    location /re {
        content_by_lua_block {
            local ngx_re = require "ngx.re"

            local res, err = ngx_re.split("12345", "", "jo", nil, 3)
            if err then
                ngx.log(ngx.ERR, "failed: ", err)
                return
            end

            for i = 1, #res do
                ngx.say(res[i])
            end

            ngx.say("len: ", #res)
        }
    }
--- request
GET /re
--- response_body
1
2
345
len: 3
--- error_log eval
qr/\[TRACE   \d+/
--- no_error_log
[error]



=== TEST 24: regex is "" with pos
--- http_config eval: $::HttpConfig
--- config
    location /re {
        content_by_lua_block {
            local ngx_re = require "ngx.re"

            local res, err = ngx_re.split("12345", "", "jo", { pos = 2 })
            if err then
                ngx.log(ngx.ERR, "failed: ", err)
                return
            end

            for i = 1, #res do
                ngx.say(res[i])
            end

            ngx.say("len: ", #res)
        }
    }
--- request
GET /re
--- response_body
2
3
4
5
len: 4
--- error_log eval
qr/\[TRACE   \d+/
--- no_error_log
[error]



=== TEST 25: regex is "" with pos larger than subject length
--- http_config eval: $::HttpConfig
--- config
    location /re {
        content_by_lua_block {
            local ngx_re = require "ngx.re"

            local res, err = ngx_re.split("12345", "", "jo", { pos = 10 })
            if err then
                ngx.log(ngx.ERR, "failed: ", err)
                return
            end

            for i = 1, #res do
                ngx.say(res[i])
            end

            ngx.say("len: ", #res)
        }
    }
--- request
GET /re
--- response_body
len: 0
--- no_error_log
[error]
[TRACE



=== TEST 26: regex is "" with pos & max
--- http_config eval: $::HttpConfig
--- config
    location /re {
        content_by_lua_block {
            local ngx_re = require "ngx.re"

            local res, err = ngx_re.split("12345", "", "jo", { pos = 2 }, 2)
            if err then
                ngx.log(ngx.ERR, "failed: ", err)
                return
            end

            for i = 1, #res do
                ngx.say(res[i])
            end

            ngx.say("len: ", #res)
        }
    }
--- request
GET /re
--- response_body
2
345
len: 2
--- error_log eval
qr/\[TRACE   \d+/
--- no_error_log
[error]



=== TEST 27: no match separator (github issue #104)
--- http_config eval: $::HttpConfig
--- config
    location /re {
        content_by_lua_block {
            local ngx_re = require "ngx.re"

            local res, err = ngx_re.split("abcd", "|")
            if err then
                ngx.log(ngx.ERR, "failed: ", err)
                return
            end

            ngx.say(table.concat(res, ":"))
            ngx.say("len: ", #res)
        }
    }
--- request
GET /re
--- response_body
a:b:c:d
len: 4
--- error_log eval
qr/\[TRACE   \d+/
--- no_error_log
[error]



=== TEST 28: no match separator (github issue #104) & max
--- http_config eval: $::HttpConfig
--- config
    location /re {
        content_by_lua_block {
            local ngx_re = require "ngx.re"

            local res, err = ngx_re.split("abcd", "|", nil, nil, 2)
            if err then
                ngx.log(ngx.ERR, "failed: ", err)
                return
            end

            ngx.say(table.concat(res, ":"))
            ngx.say("len: ", #res)
        }
    }
--- request
GET /re
--- response_body
a:bcd
len: 2
--- error_log eval
qr/\[TRACE   \d+/
--- no_error_log
[error]



=== TEST 29: no match separator bis (github issue #104)
--- http_config eval: $::HttpConfig
--- config
    location /re {
        content_by_lua_block {
            local ngx_re = require "ngx.re"

            local res, err = ngx_re.split("abcd", "()")
            if err then
                ngx.log(ngx.ERR, "failed: ", err)
                return
            end

            ngx.say(table.concat(res, ":"))
            ngx.say("len: ", #res)
        }
    }
--- request
GET /re
--- response_body
a::b::c::d
len: 7
--- error_log eval
qr/\[TRACE   \d+/
--- no_error_log
[error]



=== TEST 30: behavior with /^/ differs from Perl's split
--- http_config eval: $::HttpConfig
--- config
    location /re {
        content_by_lua_block {
            local ngx_re = require "ngx.re"

            local res, err = ngx_re.split("ab\ncd\nef", "^")
            if err then
                ngx.log(ngx.ERR, "failed: ", err)
                return
            end

            ngx.say(table.concat(res, ":"))

            ngx.say("len: ", #res)
        }
    }
--- request
GET /re
--- response_body
ab
cd
ef
len: 1
--- error_log eval
qr/\[TRACE   \d+/
--- no_error_log
[error]



=== TEST 31: behavior with /^/m
--- http_config eval: $::HttpConfig
--- config
    location /re {
        content_by_lua_block {
            local ngx_re = require "ngx.re"

            local res, err = ngx_re.split("ab\ncd\nef", "^", "m")
            if err then
                ngx.log(ngx.ERR, "failed: ", err)
                return
            end

            ngx.say(table.concat(res, ":"))

            ngx.say("len: ", #res)
        }
    }
--- request
GET /re
--- response_body
ab
:cd
:ef
len: 3
--- error_log eval
qr/\[TRACE   \d+/
--- no_error_log
[error]



=== TEST 32: behavior with /^()/m (capture)
--- http_config eval: $::HttpConfig
--- config
    location /re {
        content_by_lua_block {
            local ngx_re = require "ngx.re"

            local res, err = ngx_re.split("ab\ncd\nef", "^()", "m")
            if err then
                ngx.log(ngx.ERR, "failed: ", err)
                return
            end

            ngx.say(table.concat(res, ":"))

            ngx.say("len: ", #res)
        }
    }
--- request
GET /re
--- response_body
ab
::cd
::ef
len: 5
--- error_log eval
qr/\[TRACE   \d+/
--- no_error_log
[error]



=== TEST 33: behavior with /^/m & max
--- http_config eval: $::HttpConfig
--- config
    location /re {
        content_by_lua_block {
            local ngx_re = require "ngx.re"

            local res, err = ngx_re.split("ab\ncd\nef", "^", "m", nil, 2)
            if err then
                ngx.log(ngx.ERR, "failed: ", err)
                return
            end

            ngx.say(table.concat(res, ":"))

            ngx.say("len: ", #res)
        }
    }
--- request
GET /re
--- response_body
ab
:cd
ef
len: 2
--- error_log eval
qr/\[TRACE   \d+/
--- no_error_log
[error]



=== TEST 34: behavior with /^\d/m
--- http_config eval: $::HttpConfig
--- config
    location /re {
        content_by_lua_block {
            local ngx_re = require "ngx.re"

            local res, err = ngx_re.split("ab\n1cdefg\n2hij", "^\\d", "m")
            if err then
                ngx.log(ngx.ERR, "failed: ", err)
                return
            end

            ngx.say(table.concat(res, ":"))

            ngx.say("len: ", #res)
        }
    }
--- request
GET /re
--- response_body
ab
:cdefg
:hij
len: 3
--- error_log eval
qr/\[TRACE   \d+/
--- no_error_log
[error]



=== TEST 35: behavior with /^(\d)/m (capture)
--- http_config eval: $::HttpConfig
--- config
    location /re {
        content_by_lua_block {
            local ngx_re = require "ngx.re"

            local res, err = ngx_re.split("ab\n1cdefg\n2hij", "^(\\d)", "m")
            if err then
                ngx.log(ngx.ERR, "failed: ", err)
                return
            end

            ngx.say(table.concat(res, ":"))

            ngx.say("len: ", #res)
        }
    }
--- request
GET /re
--- response_body
ab
:1:cdefg
:2:hij
len: 5
--- error_log eval
qr/\[TRACE   \d+/
--- no_error_log
[error]
