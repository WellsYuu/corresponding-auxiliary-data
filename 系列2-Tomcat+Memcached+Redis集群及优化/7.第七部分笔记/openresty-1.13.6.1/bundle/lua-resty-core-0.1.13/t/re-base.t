# vim:set ft= ts=4 sw=4 et fdm=marker:
use lib 'lib';
use Test::Nginx::Socket::Lua;
use Cwd qw(cwd);

repeat_each(2);

plan tests => repeat_each() * (blocks() * 3);

my $pwd = cwd();

our $HttpConfig = <<_EOC_;
    lua_package_path "$pwd/lib/?.lua;../lua-resty-lrucache/lib/?.lua;;";
    init_by_lua_block {
        require "resty.core"
    }
_EOC_

no_long_string();
check_accum_error_log();
run_tests();

__DATA__

=== TEST 1: bad pattern
--- http_config eval: $::HttpConfig
--- config
    location /re {
        content_by_lua_block {
            local it, err = ngx.re.gmatch("hello\\nworld", "(abc")
            if not err then
                ngx.say("good")
            else
                ngx.say("error: ", err)
            end
        }
    }
--- request
    GET /re
--- response_body
error: pcre_compile() failed: missing ) in "(abc"
--- no_error_log
[error]



=== TEST 2: bad UTF-8
--- http_config eval: $::HttpConfig
--- config
    location = /t {
        content_by_lua_block {
            local target = "你好"
            local regex = "你好"

            -- Note the D here
            local it, err = ngx.re.gmatch(string.sub(target, 1, 4), regex, "u")

            if err then
                ngx.say("error: ", err)
                return
            end

            local m, err = it()
            if err then
                ngx.say("error: ", err)
                return
            end

            if m then
                ngx.say("matched: ", m[0])
            else
                ngx.say("not matched")
            end
        }
    }
--- request
GET /t
--- response_body_like chop
error: pcre_exec\(\) failed: -10

--- no_error_log
[error]



=== TEST 3: UTF-8 mode without UTF-8 sequence checks
--- http_config eval: $::HttpConfig
--- config
    location /re {
        content_by_lua_block {
            local it = ngx.re.gmatch("你好", ".", "U")
            local m = it()
            if m then
                ngx.say(m[0])
            else
                ngx.say("not matched!")
            end
        }
    }
--- stap
probe process("$LIBPCRE_PATH").function("pcre_compile") {
    printf("compile opts: %x\n", $options)
}

probe process("$LIBPCRE_PATH").function("pcre_exec") {
    printf("exec opts: %x\n", $options)
}

--- stap_out
compile opts: 800
exec opts: 2000

--- request
    GET /re
--- response_body
你
--- no_error_log
[error]



=== TEST 4: UTF-8 mode with UTF-8 sequence checks
--- http_config eval: $::HttpConfig
--- config
    location /re {
        content_by_lua_block {
            local it = ngx.re.gmatch("你好", ".", "u")
            local m = it()
            if m then
                ngx.say(m[0])
            else
                ngx.say("not matched!")
            end
        }
    }
--- stap
probe process("$LIBPCRE_PATH").function("pcre_compile") {
    printf("compile opts: %x\n", $options)
}

probe process("$LIBPCRE_PATH").function("pcre_exec") {
    printf("exec opts: %x\n", $options)
}

--- stap_out
compile opts: 800
exec opts: 0

--- request
    GET /re
--- response_body
你
--- no_error_log
[error]



=== TEST 5: just hit match limit
--- http_config eval: "lua_regex_match_limit 5000;" . $::HttpConfig
--- config
    location /re {
        content_by_lua_file html/a.lua;
    }

--- user_files
>>> a.lua
local re = [==[(?i:([\s'\"`´’‘\(\)]*)?([\d\w]+)([\s'\"`´’‘\(\)]*)?(?:=|<=>|r?like|sounds\s+like|regexp)([\s'\"`´’‘\(\)]*)?\2|([\s'\"`´’‘\(\)]*)?([\d\w]+)([\s'\"`´’‘\(\)]*)?(?:!=|<=|>=|<>|<|>|\^|is\s+not|not\s+like|not\s+regexp)([\s'\"`´’‘\(\)]*)?(?!\6)([\d\w]+))]==]

local s = string.rep([[ABCDEFG]], 10)

local it, err = ngx.re.gmatch(s, re, "o")
if not it then
    ngx.say("failed to gen iterator: ", err)
    return
end

local res, err = it()
if not res then
    if err then
        ngx.say("error: ", err)
        return
    end
    ngx.say("failed to match")
    return
end

--- request
    GET /re
--- response_body
error: pcre_exec() failed: -8



=== TEST 6: just not hit match limit
--- http_config eval: "lua_regex_match_limit 5100;" . $::HttpConfig
--- config
    location /re {
        content_by_lua_file html/a.lua;
    }

--- user_files
>>> a.lua
local re = [==[(?i:([\s'\"`´’‘\(\)]*)?([\d\w]+)([\s'\"`´’‘\(\)]*)?(?:=|<=>|r?like|sounds\s+like|regexp)([\s'\"`´’‘\(\)]*)?\2|([\s'\"`´’‘\(\)]*)?([\d\w]+)([\s'\"`´’‘\(\)]*)?(?:!=|<=|>=|<>|<|>|\^|is\s+not|not\s+like|not\s+regexp)([\s'\"`´’‘\(\)]*)?(?!\6)([\d\w]+))]==]

local s = string.rep([[ABCDEFG]], 10)

local it, err = ngx.re.gmatch(s, re, "o")
if not it then
    ngx.say("failed to gen iterator: ", err)
    return
end

local res, err = it()
if not res then
    if err then
        ngx.say("error: ", err)
        return
    end
    ngx.say("failed to match")
    return
end

--- request
    GET /re
--- response_body
failed to match
