# vim:set ft= ts=4 sw=4 et fdm=marker:
use lib 'lib';
use Test::Nginx::Socket::Lua;
use Cwd qw(cwd);

#worker_connections(1014);
#master_process_enabled(1);
#log_level('warn');

repeat_each(2);

plan tests => repeat_each() * (blocks() * 5 + 2);

my $pwd = cwd();

our $HttpConfig = <<_EOC_;
    lua_shared_dict dogs 1m;
    lua_shared_dict cats 16k;
    lua_shared_dict birds 100k;
    lua_package_path "$pwd/lib/?.lua;../lua-resty-lrucache/lib/?.lua;;";
    init_by_lua_block {
        local verbose = false
        if verbose then
            local dump = require "jit.dump"
            dump.on(nil, "$Test::Nginx::Util::ErrLogFile")
        else
            local v = require "jit.v"
            v.on("$Test::Nginx::Util::ErrLogFile")
        end

        require "resty.core"
        -- jit.off()
    }
_EOC_

#no_diff();
no_long_string();
check_accum_error_log();
run_tests();

__DATA__

=== TEST 1: get a string value
--- http_config eval: $::HttpConfig
--- config
    location = /t {
        content_by_lua_block {
            local ffi = require "ffi"
            local val, flags
            local dogs = ngx.shared.dogs
            -- local cd = ffi.cast("void *", dogs)
            local ok, err, forcible = dogs:set("foo", "bar", 0, 72)
            if not ok then
                ngx.say("failed to set: ", err)
                return
            end
            for i = 1, 100 do
                val, flags = dogs:get("foo")
            end
            ngx.say("value type: ", type(val))
            ngx.say("value: ", val)
            ngx.say("flags: ", flags)
        }
    }
--- request
GET /t
--- response_body
value type: string
value: bar
flags: 72
--- error_log eval
qr/\[TRACE   \d+ content_by_lua\(nginx\.conf:\d+\):11 loop\]/
--- no_error_log
[error]
 -- NYI:



=== TEST 2: get an nonexistent key
--- http_config eval: $::HttpConfig
--- config
    location = /t {
        content_by_lua_block {
            local ffi = require "ffi"
            local val, flags
            local dogs = ngx.shared.dogs
            -- local cd = ffi.cast("void *", dogs)
            -- dogs:set("foo", "bar")
            for i = 1, 100 do
                val, flags = dogs:get("foo")
            end
            ngx.say("value type: ", type(val))
            ngx.say("value: ", val)
            ngx.say("flags: ", flags)
        }
    }
--- request
GET /t
--- response_body
value type: nil
value: nil
flags: nil
--- error_log eval
qr/\[TRACE   \d+ content_by_lua\(nginx\.conf:\d+\):7 loop\]/
--- no_error_log
[error]
 -- NYI:



=== TEST 3: get a boolean value (true)
--- http_config eval: $::HttpConfig
--- config
    location = /t {
        content_by_lua_block {
            local ffi = require "ffi"
            local val, flags
            local dogs = ngx.shared.dogs
            -- local cd = ffi.cast("void *", dogs)
            dogs:set("foo", true, 0, 5678)
            for i = 1, 100 do
                val, flags = dogs:get("foo")
            end
            ngx.say("value type: ", type(val))
            ngx.say("value: ", val)
            ngx.say("flags: ", flags)
        }
    }
--- request
GET /t
--- response_body
value type: boolean
value: true
flags: 5678
--- error_log eval
qr/\[TRACE   \d+ content_by_lua\(nginx\.conf:\d+\):7 loop\]/
--- no_error_log
[error]
 -- NYI:



=== TEST 4: get a boolean value (false)
--- http_config eval: $::HttpConfig
--- config
    location = /t {
        content_by_lua_block {
            local ffi = require "ffi"
            local val, flags
            local dogs = ngx.shared.dogs
            -- local cd = ffi.cast("void *", dogs)
            dogs:set("foo", false, 0, 777)
            for i = 1, 100 do
                val, flags = dogs:get("foo")
            end
            ngx.say("value type: ", type(val))
            ngx.say("value: ", val)
            ngx.say("flags: ", flags)
        }
    }
--- request
GET /t
--- response_body
value type: boolean
value: false
flags: 777
--- error_log eval
qr/\[TRACE   \d+ content_by_lua\(nginx\.conf:\d+\):7 loop\]/
--- no_error_log
[error]
 -- NYI:



=== TEST 5: get a number value (int)
--- http_config eval: $::HttpConfig
--- config
    location = /t {
        content_by_lua_block {
            local ffi = require "ffi"
            local val, flags
            local dogs = ngx.shared.dogs
            -- local cd = ffi.cast("void *", dogs)
            dogs:set("foo", 51203)
            for i = 1, 100 do
                val, flags = dogs:get("foo")
            end
            ngx.say("value type: ", type(val))
            ngx.say("value: ", val)
            ngx.say("flags: ", flags)
        }
    }
--- request
GET /t
--- response_body
value type: number
value: 51203
flags: nil
--- error_log eval
qr/\[TRACE   \d+ content_by_lua\(nginx\.conf:\d+\):7 loop\]/
--- no_error_log
[error]
 -- NYI:



=== TEST 6: get a number value (double)
--- http_config eval: $::HttpConfig
--- config
    location = /t {
        content_by_lua_block {
            local ffi = require "ffi"
            local val, flags
            local dogs = ngx.shared.dogs
            -- local cd = ffi.cast("void *", dogs)
            dogs:set("foo", 3.1415926, 0, 78)
            for i = 1, 100 do
                val, flags = dogs:get("foo")
            end
            ngx.say("value type: ", type(val))
            ngx.say("value: ", val)
            ngx.say("flags: ", flags)
        }
    }
--- request
GET /t
--- response_body
value type: number
value: 3.1415926
flags: 78
--- error_log eval
qr/\[TRACE   \d+ content_by_lua\(nginx\.conf:\d+\):7 loop\]/
--- no_error_log
[error]
 -- NYI:



=== TEST 7: get a large string value
--- http_config eval: $::HttpConfig
--- config
    location = /t {
        content_by_lua_block {
            local ffi = require "ffi"
            local val, flags
            local dogs = ngx.shared.dogs
            -- local cd = ffi.cast("void *", dogs)
            dogs:set("foo", string.rep("bbbb", 1024) .. "a", 0, 912)
            for i = 1, 100 do
                val, flags = dogs:get("foo")
            end
            ngx.say("value type: ", type(val))
            ngx.say("value: ", val)
            ngx.say("flags: ", flags)
        }
    }
--- request
GET /t
--- response_body eval
"value type: string
value: " . ("bbbb" x 1024) . "a
flags: 912
"
--- error_log eval
qr/\[TRACE   \d+ content_by_lua\(nginx\.conf:\d+\):7 loop\]/
--- no_error_log
[error]
 -- NYI:



=== TEST 8: get_stale (false)
--- http_config eval: $::HttpConfig
--- config
    location = /t {
        content_by_lua_block {
            local ffi = require "ffi"
            local val, flags, stale
            local dogs = ngx.shared.dogs
            -- local cd = ffi.cast("void *", dogs)
            dogs:set("foo", "bar", 0, 72)
            for i = 1, 100 do
                val, flags, stale = dogs:get_stale("foo")
            end
            ngx.say("value type: ", type(val))
            ngx.say("value: ", val)
            ngx.say("flags: ", flags)
            ngx.say("stale: ", stale)
        }
    }
--- request
GET /t
--- response_body
value type: string
value: bar
flags: 72
stale: false
--- error_log eval
qr/\[TRACE   \d+ content_by_lua\(nginx\.conf:\d+\):7 loop\]/
--- no_error_log
[error]
 -- NYI:



=== TEST 9: get_stale (true)
--- http_config eval: $::HttpConfig
--- config
    location = /t {
        content_by_lua_block {
            local ffi = require "ffi"
            local val, flags, stale
            local dogs = ngx.shared.dogs
            -- local cd = ffi.cast("void *", dogs)
            local ok, err, forcible = dogs:set("foo", "bar", 0.001, 72)
            if not ok then
                ngx.say("failed to set: ", err)
                return
            end
            ngx.sleep(0.002)
            for i = 1, 100 do
                val, flags, stale = dogs:get_stale("foo")
            end
            ngx.say("value type: ", type(val))
            ngx.say("value: ", val)
            ngx.say("flags: ", flags)
            ngx.say("stale: ", stale)
        }
    }
--- request
GET /t
--- response_body
value type: string
value: bar
flags: 72
stale: true
--- error_log eval
qr/\[TRACE   \d+ content_by_lua\(nginx\.conf:\d+\):12 loop\]/
--- no_error_log
[error]
 -- NYI:



=== TEST 10: incr int
--- http_config eval: $::HttpConfig
--- config
    location = /t {
        content_by_lua_block {
            local ffi = require "ffi"
            local val
            local dogs = ngx.shared.dogs
            -- local cd = ffi.cast("void *", dogs)
            local ok, err, forcible = dogs:set("foo", 56)
            if not ok then
                ngx.say("failed to set: ", err)
                return
            end
            for i = 1, 100 do
                val, err = dogs:incr("foo", 2)
            end
            ngx.say("value: ", val)
            ngx.say("err: ", err)
        }
    }
--- request
GET /t
--- response_body
value: 256
err: nil
--- error_log eval
qr/\[TRACE   \d+ content_by_lua\(nginx\.conf:\d+\):11 loop\]/
--- no_error_log
[error]
 -- NYI:



=== TEST 11: incr double
--- http_config eval: $::HttpConfig
--- config
    location = /t {
        content_by_lua_block {
            local ffi = require "ffi"
            local val
            local dogs = ngx.shared.dogs
            -- local cd = ffi.cast("void *", dogs)
            dogs:set("foo", 56)
            for i = 1, 150 do
                val, err = dogs:incr("foo", 2.1)
            end
            ngx.say("value: ", val)
            ngx.say("err: ", err)
        }
    }
--- request
GET /t
--- response_body
value: 371
err: nil
--- error_log eval
qr/\[TRACE   \d+ content_by_lua\(nginx\.conf:\d+\):7 loop\]/
--- no_error_log
[error]
 -- NYI:



=== TEST 12: set a string value
--- http_config eval: $::HttpConfig
--- config
    location = /t {
        content_by_lua_block {
            local ffi = require "ffi"
            local val, flags
            local dogs = ngx.shared.dogs
            -- local cd = ffi.cast("void *", dogs)
            local ok, err, forcible
            for i = 1, 100 do
                ok, err, forcible = dogs:set("foo", "bar", 0, 72)
            end
            if not ok then
                ngx.say("failed to set: ", err)
                return
            end
            val, flags = dogs:get("foo")
            ngx.say("value type: ", type(val))
            ngx.say("value: ", val)
            ngx.say("flags: ", flags)
        }
    }
--- request
GET /t
--- response_body
value type: string
value: bar
flags: 72
--- error_log eval
qr/\[TRACE   \d+ content_by_lua\(nginx\.conf:\d+\):7 loop\]/
--- no_error_log
[error]
 -- NYI:



=== TEST 13: set a boolean value (true)
--- http_config eval: $::HttpConfig
--- config
    location = /t {
        content_by_lua_block {
            local ffi = require "ffi"
            local val, flags
            local dogs = ngx.shared.dogs
            -- local cd = ffi.cast("void *", dogs)
            local ok, err, forcible
            for i = 1, 100 do
                ok, err, forcible = dogs:set("foo", true, 0, 5678)
            end
            if not ok then
                ngx.say("failed to set: ", err)
                return
            end
            val, flags = dogs:get("foo")
            ngx.say("value type: ", type(val))
            ngx.say("value: ", val)
            ngx.say("flags: ", flags)
        }
    }
--- request
GET /t
--- response_body
value type: boolean
value: true
flags: 5678
--- error_log eval
qr/\[TRACE   \d+ content_by_lua\(nginx\.conf:\d+\):7 loop\]/
--- no_error_log
[error]
 -- NYI:



=== TEST 14: set a boolean value (false)
--- http_config eval: $::HttpConfig
--- config
    location = /t {
        content_by_lua_block {
            local ffi = require "ffi"
            local val, flags
            local dogs = ngx.shared.dogs
            -- local cd = ffi.cast("void *", dogs)
            for i = 1, 100 do
                dogs:set("foo", false, 0, 777)
            end
            val, flags = dogs:get("foo")
            ngx.say("value type: ", type(val))
            ngx.say("value: ", val)
            ngx.say("flags: ", flags)
        }
    }
--- request
GET /t
--- response_body
value type: boolean
value: false
flags: 777
--- error_log eval
qr/\[TRACE   \d+ content_by_lua\(nginx\.conf:\d+\):6 loop\]/
--- no_error_log
[error]
 -- NYI:



=== TEST 15: set a number value (int)
--- http_config eval: $::HttpConfig
--- config
    location = /t {
        content_by_lua_block {
            local ffi = require "ffi"
            local val, flags
            local dogs = ngx.shared.dogs
            -- local cd = ffi.cast("void *", dogs)
            for i = 1, 100 do
                dogs:set("foo", 51203)
            end
            val, flags = dogs:get("foo")
            ngx.say("value type: ", type(val))
            ngx.say("value: ", val)
            ngx.say("flags: ", flags)
        }
    }
--- request
GET /t
--- response_body
value type: number
value: 51203
flags: nil
--- error_log eval
qr/\[TRACE   \d+ content_by_lua\(nginx\.conf:\d+\):6 loop\]/
--- no_error_log
[error]
 -- NYI:



=== TEST 16: set a number value (double)
--- http_config eval: $::HttpConfig
--- config
    location = /t {
        content_by_lua_block {
            local ffi = require "ffi"
            local val, flags
            local dogs = ngx.shared.dogs
            -- local cd = ffi.cast("void *", dogs)
            for i = 1, 100 do
                dogs:set("foo", 3.1415926, 0, 78)
            end
            val, flags = dogs:get("foo")
            ngx.say("value type: ", type(val))
            ngx.say("value: ", val)
            ngx.say("flags: ", flags)
        }
    }
--- request
GET /t
--- response_body
value type: number
value: 3.1415926
flags: 78
--- error_log eval
qr/\[TRACE   \d+ content_by_lua\(nginx\.conf:\d+\):6 loop\]/
--- no_error_log
[error]
 -- NYI:



=== TEST 17: set a number value and a nil
--- http_config eval: $::HttpConfig
--- config
    location = /t {
        content_by_lua_block {
            local ffi = require "ffi"
            local val, flags
            local dogs = ngx.shared.dogs
            -- local cd = ffi.cast("void *", dogs)
            for i = 1, 150 do
                dogs:set("foo", 3.1415926, 0, 78)
                dogs:set("foo", nil)
            end
            val, flags = dogs:get("foo")
            ngx.say("value type: ", type(val))
            ngx.say("value: ", val)
            ngx.say("flags: ", flags)
        }
    }
--- request
GET /t
--- response_body
value type: nil
value: nil
flags: nil
--- error_log eval
qr/\[TRACE   \d+ content_by_lua\(nginx\.conf:\d+\):6 loop\]/
--- no_error_log
[error]
 -- NYI:



=== TEST 18: safe set a number value
--- http_config eval: $::HttpConfig
--- config
    location = /t {
        content_by_lua_block {
            local ffi = require "ffi"
            local val, flags
            local dogs = ngx.shared.dogs
            -- local cd = ffi.cast("void *", dogs)
            for i = 1, 100 do
                dogs:safe_set("foo", 3.1415926, 0, 78)
            end
            val, flags = dogs:get("foo")
            ngx.say("value type: ", type(val))
            ngx.say("value: ", val)
            ngx.say("flags: ", flags)
        }
    }
--- request
GET /t
--- response_body
value type: number
value: 3.1415926
flags: 78
--- error_log eval
qr/\[TRACE   \d+ content_by_lua\(nginx\.conf:\d+\):6 loop\]/
--- no_error_log
[error]
 -- NYI:



=== TEST 19: add a string value
--- http_config eval: $::HttpConfig
--- config
    location = /t {
        content_by_lua_block {
            local ffi = require "ffi"
            local val, flags
            local dogs = ngx.shared.dogs
            -- local cd = ffi.cast("void *", dogs)
            dogs:flush_all()
            local ok, err, forcible
            for i = 1, 100 do
                ok, err, forcible = dogs:add("foo" .. i, "bar", 0, 72)
            end
            if not ok then
                ngx.say("failed to set: ", err)
                return
            end
            val, flags = dogs:get("foo100")
            ngx.say("value type: ", type(val))
            ngx.say("value: ", val)
            ngx.say("flags: ", flags)
        }
    }
--- request
GET /t
--- response_body
value type: string
value: bar
flags: 72
--- error_log eval
qr/\[TRACE   \d+ content_by_lua\(nginx\.conf:\d+\):8 loop\]/
--- no_error_log
[error]
 -- NYI:



=== TEST 20: safe add a string value
--- http_config eval: $::HttpConfig
--- config
    location = /t {
        content_by_lua_block {
            local ffi = require "ffi"
            local val, flags
            local dogs = ngx.shared.dogs
            -- local cd = ffi.cast("void *", dogs)
            dogs:flush_all()
            local ok, err, forcible
            for i = 1, 100 do
                ok, err, forcible = dogs:safe_add("foo" .. i, "bar", 0, 72)
            end
            if not ok then
                ngx.say("failed to set: ", err)
                return
            end
            val, flags = dogs:get("foo100")
            ngx.say("value type: ", type(val))
            ngx.say("value: ", val)
            ngx.say("flags: ", flags)
        }
    }
--- request
GET /t
--- response_body
value type: string
value: bar
flags: 72
--- error_log eval
qr/\[TRACE   \d+ content_by_lua\(nginx\.conf:\d+\):8 loop\]/
--- no_error_log
[error]
 -- NYI:



=== TEST 21: replace a string value
--- http_config eval: $::HttpConfig
--- config
    location = /t {
        content_by_lua_block {
            local ffi = require "ffi"
            local val, flags
            local dogs = ngx.shared.dogs
            -- local cd = ffi.cast("void *", dogs)
            dogs:set("foo", "hello")
            local ok, err, forcible
            for i = 1, 100 do
                ok, err, forcible = dogs:replace("foo", "bar" .. i, 0, 72)
            end
            if not ok then
                ngx.say("failed to set: ", err)
                return
            end
            val, flags = dogs:get("foo")
            ngx.say("value type: ", type(val))
            ngx.say("value: ", val)
            ngx.say("flags: ", flags)
        }
    }
--- request
GET /t
--- response_body
value type: string
value: bar100
flags: 72
--- error_log eval
qr/\[TRACE   \d+ content_by_lua\(nginx\.conf:\d+\):8 loop\]/
--- no_error_log
[error]
 -- NYI:



=== TEST 22: set a number value and delete
--- http_config eval: $::HttpConfig
--- config
    location = /t {
        content_by_lua_block {
            local ffi = require "ffi"
            local val, flags
            local dogs = ngx.shared.dogs
            -- local cd = ffi.cast("void *", dogs)
            for i = 1, 150 do
                dogs:set("foo", 3.1415926, 0, 78)
                dogs:delete("foo")
            end
            val, flags = dogs:get("foo")
            ngx.say("value type: ", type(val))
            ngx.say("value: ", val)
            ngx.say("flags: ", flags)
        }
    }
--- request
GET /t
--- response_body
value type: nil
value: nil
flags: nil
--- error_log eval
qr/\[TRACE   \d+ content_by_lua\(nginx\.conf:\d+\):6 loop\]/
--- no_error_log
[error]
 -- NYI:
stitch



=== TEST 23: set nil key
--- http_config eval: $::HttpConfig
--- config
    location = /t {
        content_by_lua_block {
            local val, flags
            local dogs = ngx.shared.dogs
            local ok, err = dogs:set(nil, "bar")
            if not ok then
                ngx.say("failed to set: ", err)
            end
        }
    }
--- request
GET /t
--- response_body
failed to set: nil key
--- no_error_log
[error]
[alert]
[crit]



=== TEST 24: get nil key
--- http_config eval: $::HttpConfig
--- config
    location = /t {
        content_by_lua_block {
            local val, flags
            local dogs = ngx.shared.dogs
            local value, err = dogs:get(nil, "bar")
            if not ok then
                ngx.say("failed to get: ", err)
            end
        }
    }
--- request
GET /t
--- response_body
failed to get: nil key
--- no_error_log
[error]
[alert]
[crit]



=== TEST 25: get stale key
--- http_config eval: $::HttpConfig
--- config
    location = /t {
        content_by_lua_block {
            local val, flags
            local dogs = ngx.shared.dogs
            local value, err = dogs:get_stale(nil, "bar")
            if not ok then
                ngx.say("failed to get stale: ", err)
            end
        }
    }
--- request
GET /t
--- response_body
failed to get stale: nil key
--- no_error_log
[error]
[alert]
[crit]



=== TEST 26: incr key
--- http_config eval: $::HttpConfig
--- config
    location = /t {
        content_by_lua_block {
            local val, flags
            local dogs = ngx.shared.dogs
            local value, err = dogs:incr(nil, 32)
            if not value then
                ngx.say("failed to incr: ", err)
            end
        }
    }
--- request
GET /t
--- response_body
failed to incr: nil key
--- no_error_log
[error]
[alert]
[crit]



=== TEST 27: flush_all
--- http_config eval: $::HttpConfig
--- config
    location = /t {
        content_by_lua_block {
            local ffi = require "ffi"
            local val, flags
            local dogs = ngx.shared.dogs
            dogs:set("foo", "bah")
            -- local cd = ffi.cast("void *", dogs)
            for i = 1, 150 do
                dogs:flush_all()
            end
            val, flags = dogs:get("foo")
            ngx.say("value type: ", type(val))
            ngx.say("value: ", val)
            ngx.say("flags: ", flags)
        }
    }
--- request
GET /t
--- response_body
value type: nil
value: nil
flags: nil
--- error_log eval
qr/\[TRACE   \d+ content_by_lua\(nginx\.conf:\d+\):7 loop\]/
--- no_error_log
[error]
 -- NYI:
stitch



=== TEST 28: incr, value is not number
--- http_config eval: $::HttpConfig
--- config
    location = /t {
        content_by_lua_block {
            local val, flags
            local dogs = ngx.shared.dogs
            local value, err = dogs:incr("foo", "bar")
            if not value then
                ngx.say("failed to incr: ", err)
            end
        }
    }
--- request
GET /t
--- error_code: 500
--- response_body_like: 500
--- error_log
cannot convert 'nil' to 'double'
--- no_error_log
[alert]
[crit]



=== TEST 29: incr with init
--- http_config eval: $::HttpConfig
--- config
    location = /t {
        content_by_lua_block {
            local val, flags
            local dogs = ngx.shared.dogs
            dogs:flush_all()

            local value, err = dogs:incr("foo", 10)
            if not value then
                ngx.say("failed to incr: ", err)
            end

            local value, err, forcible = dogs:incr("foo", 10, 10)
            if not value then
                ngx.say("failed to incr: ", err)
                return
            end

            ngx.say("incr ok, value: ", value, ", forcible: ", forcible)
        }
    }
--- request
GET /t
--- response_body
failed to incr: not found
incr ok, value: 20, forcible: false
--- no_error_log
[error]
[alert]
[crit]



=== TEST 30: incr, init is not number
--- http_config eval: $::HttpConfig
--- config
    location = /t {
        content_by_lua_block {
            local val, flags
            local dogs = ngx.shared.dogs
            local value, err = dogs:incr("foo", 10, "bar")
            if not ok then
                ngx.say("failed to incr: ", err)
            end
        }
    }
--- request
GET /t
--- error_code: 500
--- response_body_like: 500
--- error_log
number expected, got string
--- no_error_log
[alert]
[crit]



=== TEST 31: capacity
--- http_config eval: $::HttpConfig
--- config
    location = /t {
        content_by_lua_block {
            local cats = ngx.shared.cats
            local capacity = cats:capacity()
            ngx.say("capacity type: ", type(capacity))
            ngx.say("capacity: ", capacity)
        }
    }
--- request
GET /t
--- response_body
capacity type: number
capacity: 16384
--- no_error_log
[error]
[alert]
[crit]



=== TEST 32: free_space, empty (16k zone)
--- skip_nginx: 5: < 1.11.7
--- http_config eval: $::HttpConfig
--- config
    location = /t {
        content_by_lua_block {
            local cats = ngx.shared.cats
            cats:flush_all()
            cats:flush_expired()
            local free_page_bytes = cats:free_space()
            ngx.say("free_page_bytes type: ", type(free_page_bytes))
            ngx.say("free_page_bytes: ", free_page_bytes)
        }
    }
--- request
GET /t
--- response_body
free_page_bytes type: number
free_page_bytes: 4096
--- no_error_log
[error]
[alert]
[crit]



=== TEST 33: free_space, empty (100k zone)
--- skip_nginx: 5: < 1.11.7
--- http_config eval: $::HttpConfig
--- config
    location = /t {
        content_by_lua_block {
            local birds = ngx.shared.birds
            birds:flush_all()
            birds:flush_expired()
            local free_page_bytes = birds:free_space()
            ngx.say("free_page_bytes type: ", type(free_page_bytes))
            ngx.say("free_page_bytes: ", free_page_bytes)
        }
    }
--- request
GET /t
--- response_body_like chomp
\Afree_page_bytes type: number
free_page_bytes: (?:90112|94208)
\z
--- no_error_log
[error]
[alert]
[crit]



=== TEST 34: free_space, about half full, one page left
--- skip_nginx: 5: < 1.11.7
--- http_config eval: $::HttpConfig
--- config
    location = /t {
        content_by_lua_block {
            local cats = ngx.shared.cats
            cats:flush_all()
            cats:flush_expired()
            for i = 1, 31 do
                local key = string.format("key%05d", i)
                local val = string.format("val%05d", i)
                local success, err, forcible = cats:set(key, val)
                if err ~= nil then
                    ngx.say(string.format("got error, i=%d, err=%s", i, err))
                end
                if forcible then
                    ngx.say(string.format("got forcible, i=%d", i))
                end
                if not success then
                    ngx.say(string.format("got not success, i=%d", i))
                end
            end
            local free_page_bytes = cats:free_space()
            ngx.say("free_page_bytes type: ", type(free_page_bytes))
            ngx.say("free_page_bytes: ", free_page_bytes)
        }
    }
--- request
GET /t
--- response_body
free_page_bytes type: number
free_page_bytes: 4096
--- no_error_log
[error]
[alert]
[crit]



=== TEST 35: free_space, about half full, no page left
--- skip_nginx: 5: < 1.11.7
--- http_config eval: $::HttpConfig
--- config
    location = /t {
        content_by_lua_block {
            local cats = ngx.shared.cats
            cats:flush_all()
            cats:flush_expired()
            for i = 1, 32 do
                local key = string.format("key%05d", i)
                local val = string.format("val%05d", i)
                local success, err, forcible = cats:set(key, val)
                if err ~= nil then
                    ngx.say(string.format("got error, i=%d, err=%s", i, err))
                end
                if forcible then
                    ngx.say(string.format("got forcible, i=%d", i))
                end
                if not success then
                    ngx.say(string.format("got not success, i=%d", i))
                end
            end
            local free_page_bytes = cats:free_space()
            ngx.say("free_page_bytes type: ", type(free_page_bytes))
            ngx.say("free_page_bytes: ", free_page_bytes)
        }
    }
--- request
GET /t
--- response_body_like chomp
\Afree_page_bytes type: number
free_page_bytes: (?:0|4096)
\z
--- no_error_log
[error]
[alert]
[crit]



=== TEST 36: free_space, full
--- skip_nginx: 5: < 1.11.7
--- http_config eval: $::HttpConfig
--- config
    location = /t {
        content_by_lua_block {
            local cats = ngx.shared.cats
            cats:flush_all()
            cats:flush_expired()
            for i = 1, 63 do
                local key = string.format("key%05d", i)
                local val = string.format("val%05d", i)
                local success, err, forcible = cats:set(key, val)
                if err ~= nil then
                    ngx.say(string.format("got error, i=%d, err=%s", i, err))
                end
                if forcible then
                    ngx.say(string.format("got forcible, i=%d", i))
                end
                if not success then
                    ngx.say(string.format("got not success, i=%d", i))
                end
            end
            local free_page_bytes = cats:free_space()
            ngx.say("free_page_bytes type: ", type(free_page_bytes))
            ngx.say("free_page_bytes: ", free_page_bytes)
        }
    }
--- request
GET /t
--- response_body
free_page_bytes type: number
free_page_bytes: 0
--- no_error_log
[error]
[alert]
[crit]



=== TEST 37: free_space, got forcible
--- skip_nginx: 5: < 1.11.7
--- http_config eval: $::HttpConfig
--- config
    location = /t {
        content_by_lua_block {
            local cats = ngx.shared.cats
            cats:flush_all()
            cats:flush_expired()
            for i = 1, 64 do
                local key = string.format("key%05d", i)
                local val = string.format("val%05d", i)
                local success, err, forcible = cats:set(key, val)
                if err ~= nil then
                    ngx.say(string.format("got error, i=%d, err=%s", i, err))
                end
                if forcible then
                    ngx.say(string.format("got forcible, i=%d", i))
                end
                if not success then
                    ngx.say(string.format("got not success, i=%d", i))
                end
            end
            local free_page_bytes = cats:free_space()
            ngx.say("free_page_bytes type: ", type(free_page_bytes))
            ngx.say("free_page_bytes: ", free_page_bytes)
        }
    }
--- request
GET /t
--- response_body_like chomp
\A(?:got forcible, i=64
)?free_page_bytes type: number
free_page_bytes: 0
\z
--- no_error_log
[error]
[alert]
[crit]



=== TEST 38: free_space, full (100k)
--- skip_nginx: 5: < 1.11.7
--- http_config eval: $::HttpConfig
--- config
    location = /t {
        content_by_lua_block {
            local birds = ngx.shared.birds
            birds:flush_all()
            birds:flush_expired()
            for i = 1, 1000 do
                local key = string.format("key%05d", i)
                local val = string.format("val%05d", i)
                local ok, err, forcible = birds:set(key, val)
                if err ~= nil then
                    ngx.say(string.format("got error, i=%d, err=%s", i, err))
                end
                if forcible then
                    ngx.say(string.format("got forcible, i=%d", i))
                    break
                end
                if not ok then
                    ngx.say(string.format("got not ok, i=%d", i))
                    break
                end
            end
            local free_page_bytes = birds:free_space()
            ngx.say("free_page_bytes type: ", type(free_page_bytes))
            ngx.say("free_page_bytes: ", free_page_bytes)
        }
    }
--- request
GET /t
--- response_body_like chomp
\A(?:got forcible, i=736
)?free_page_bytes type: number
free_page_bytes: (?:0|32768)
\z
--- no_error_log
[error]
[alert]
[crit]
