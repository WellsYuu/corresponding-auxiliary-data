#!/bin/bash

#
# Copyright 2021-2022 the original author or authors.
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#

# this file is mostly meant to be used by the author himself.

root=`pwd`
home=~
version=$1
force=$2

    #--with-cc="gcc46" \
    #--with-ld-opt="-rdynamic" \
    #--with-mail \
    #--with-mail_ssl_module \

ngx-build $force $version \
    --with-cc-opt="-I$PCRE_INC -I$OPENSSL_INC" \
    --with-ld-opt="-L$PCRE_LIB -L$OPENSSL_LIB -Wl,-rpath,$PCRE_LIB:$OPENSSL_LIB" \
    --with-http_ssl_module \
    --without-mail_pop3_module \
    --without-mail_imap_module \
    --without-mail_smtp_module \
    --without-http_upstream_ip_hash_module \
    --without-http_empty_gif_module \
    --without-http_memcached_module \
    --without-http_referer_module \
    --without-http_autoindex_module \
    --without-http_auth_basic_module \
    --without-http_userid_module \
    --add-module=$root/../echo-nginx-module \
    --add-module=$root/../ndk-nginx-module \
    --add-module=$root/../iconv-nginx-module \
    --add-module=$root $opts \
    --with-debug \
  || exit 1

