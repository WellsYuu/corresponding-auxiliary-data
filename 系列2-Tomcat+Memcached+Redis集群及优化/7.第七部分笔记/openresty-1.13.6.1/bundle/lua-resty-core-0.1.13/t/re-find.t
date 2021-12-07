# vim:set ft= ts=4 sw=4 et fdm=marker:
use lib 'lib';
use Test::Nginx::Socket::Lua;
use Cwd qw(cwd);

#worker_connections(1014);
#master_process_enabled(1);
#log_level('warn');

repeat_each(2);

plan tests => repeat_each() * (blocks() * 5);

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

#no_diff();
no_long_string();
check_accum_error_log();
run_tests();

__DATA__

=== TEST 1: matched, no submatch, no jit compile, no regex cache
--- http_config eval: $::HttpConfig
--- config
    location = /re {
        access_log off;
        content_by_lua_block {
            local from, to, err
            local find = ngx.re.find
            local s = "a"
            for i = 1, 100 do
                from, to, err = find(s, "a")
            end
            if err then
                ngx.log(ngx.ERR, "failed: ", err)
                return
            end
            if not from then
                ngx.log(ngx.ERR, "no match")
                return
            end
            ngx.say("from: ", from)
            ngx.say("to: ", to)
            ngx.say("matched: ", string.sub(s, from, to))
        }
    }
--- request
GET /re
--- response_body
from: 1
to: 1
matched: a
--- error_log eval
qr/\[TRACE   \d+ content_by_lua\(nginx\.conf:\d+\):5 loop\]/
--- no_error_log
[error]
bad argument type



=== TEST 2: matched, no submatch, jit compile, regex cache
--- http_config eval: $::HttpConfig
--- config
    location = /re {
        access_log off;
        content_by_lua_block {
            local from, to, err
            local find = ngx.re.find
            local s = "a"
            for i = 1, 200 do
                from, to, err = find(s, "a", "jo")
            end
            if err then
                ngx.log(ngx.ERR, "failed: ", err)
                return
            end
            if not from then
                ngx.log(ngx.ERR, "no match")
                return
            end
            ngx.say("from: ", from)
            ngx.say("to: ", to)
            ngx.say("matched: ", string.sub(s, from, to))
        }
    }
--- request
GET /re
--- response_body
from: 1
to: 1
matched: a
--- error_log eval
qr/\[TRACE   \d+ content_by_lua\(nginx\.conf:\d+\):5 loop\]/
--- no_error_log
[error]
NYI



=== TEST 3: not matched, no submatch, jit compile, regex cache
--- http_config eval: $::HttpConfig
--- config
    location = /re {
        access_log off;
        content_by_lua_block {
            local from, to, err
            local find = ngx.re.find
            local s = "b"
            for i = 1, 200 do
                from, to, err = find(s, "a", "jo")
            end
            if err then
                ngx.log(ngx.ERR, "failed: ", err)
                return
            end
            if not from then
                ngx.say("no match")
                return
            end
            ngx.say("from: ", from)
            ngx.say("to: ", to)
            ngx.say("matched: ", string.sub(s, from, to))
        }
    }
--- request
GET /re
--- response_body
no match
--- error_log eval
qr/\[TRACE   \d+ content_by_lua\(nginx\.conf:\d+\):5 loop\]/
--- no_error_log
[error]
NYI



=== TEST 4: nil submatch (2nd)
--- http_config eval: $::HttpConfig
--- config
    location /re {
        content_by_lua_block {
            local s = "hello, 1234"
            local from, to, err
            for i = 1, 100 do
                from, to, err = ngx.re.find(s, "([0-9])|(hello world)", "jo", nil, 2)
            end
            if from or to then
                ngx.say("from: ", from)
                ngx.say("to: ", to)
                ngx.say("matched: ", string.sub(s, from, to))
            else
                if err then
                    ngx.say("error: ", err)
                    return
                end
                ngx.say("not matched!")
            end
        }
    }
--- request
    GET /re
--- response_body
not matched!
--- no_error_log
[error]
NYI
--- error_log eval
qr/\[TRACE   \d+ content_by_lua\(nginx\.conf:\d+\):4 loop\]/



=== TEST 5: nil submatch (1st)
--- http_config eval: $::HttpConfig
--- config
    location /re {
        content_by_lua_block {
            local s = "hello, 1234"
            local from, to, err
            for i = 1, 400 do
                from, to, err = ngx.re.find(s, "(hello world)|([0-9])", "jo", nil, 1)
            end
            if from or to then
                ngx.say("from: ", from)
                ngx.say("to: ", to)
                ngx.say("matched: ", string.sub(s, from, to))
            else
                if err then
                    ngx.say("error: ", err)
                    return
                end
                ngx.say("not matched!")
            end
        }
    }
--- request
    GET /re
--- response_body
not matched!
--- no_error_log
[error]
NYI
--- error_log eval
qr/\[TRACE   \d+ content_by_lua\(nginx\.conf:\d+\):4 loop\]/



=== TEST 6: specify the group (2)
--- http_config eval: $::HttpConfig
--- config
    location /re {
        content_by_lua_block {
            local s = "hello, 1234"
            local from, to, err
            for i = 1, 100 do
                from, to, err = ngx.re.find(s, "([0-9])([0-9]+)", "jo", nil, 2)
            end
            if from then
                ngx.say("from: ", from)
                ngx.say("to: ", to)
                ngx.say("matched: ", string.sub(s, from, to))
            else
                if err then
                    ngx.say("error: ", err)
                end
                ngx.say("not matched!")
            end
        }
    }
--- request
    GET /re
--- response_body
from: 9
to: 11
matched: 234
--- no_error_log
[error]
NYI
--- error_log eval
qr/\[TRACE   \d+ content_by_lua\(nginx\.conf:\d+\):4 loop\]/
