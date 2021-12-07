#!/bin/bash

# this file is mostly meant to be used by the author himself.

root=`pwd`
version=$1
home=~
force=$2

ngx_redis_version=0.3.7
ngx_redis_path=$home/work/nginx/ngx_http_redis-$ngx_redis_version

cd $ngx_redis_path || exit 1
patch --forward -p1 < $root/../ngx_openresty/patches/ngx_http_redis-$ngx_redis_version-variables_in_redis_pass.patch
cd $root || exit 1

            #--without-http_memcached_module \
ngx-build $force $version \
            --with-cc-opt="-O0" \
            --with-ld-opt="-Wl,-rpath,/opt/postgres/lib:/opt/drizzle/lib:/usr/local/lib:/home/lz/lib" \
            --without-mail_pop3_module \
            --without-mail_imap_module \
            --without-mail_smtp_module \
            --without-http_upstream_ip_hash_module \
            --without-http_empty_gif_module \
            --without-http_referer_module \
            --without-http_autoindex_module \
            --without-http_auth_basic_module \
            --without-http_userid_module \
            --add-module=$root/../ndk-nginx-module \
            --add-module=$root/../set-misc-nginx-module \
          --add-module=$ngx_redis_path \
          --add-module=$root/../xss-nginx-module \
          --add-module=$root/../redis2-nginx-module \
          --add-module=$root/../eval-nginx-module \
          --add-module=$root/../echo-nginx-module \
          --add-module=$root/../headers-more-nginx-module \
          --add-module=$root $opts \
          --add-module=$root/../lua-nginx-module \
          --add-module=$root/../rds-json-nginx-module \
          --add-module=$root/../drizzle-nginx-module \
          --add-module=$root/../postgres-nginx-module \
          --add-module=$root/../memc-nginx-module \
            --add-module=$home/work/nginx/ngx_http_upstream_keepalive-0.7 \
          --with-select_module \
          --with-poll_module \
          --without-http_ssi_module \
          --with-debug
          #--add-module=/home/agentz/git/dodo/utils/dodo-hook \
          #--add-module=$home/work/ngx_http_auth_request-0.1 #\
          #--with-rtsig_module
          #--with-cc-opt="-g3 -O0"
          #--add-module=$root/../echo-nginx-module \

