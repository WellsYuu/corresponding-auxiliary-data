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

ragel -I src -G2 src/ngx_http_redis2_reply.rl || exit 1
./util/fix-clang-warnings

root=`pwd`
version=$1
home=~
force=$2

          #--with-cc-opt="-O3" \
          #--with-cc-opt="-fast" \
          #--with-cc=gcc46 \

ngx-build $force $version \
            --with-ld-opt="-L$PCRE_LIB -Wl,-rpath,$PCRE_LIB:$LIBDRIZZLE_LIB:/usr/local/lib" \
          --with-cc-opt="-O3 -funsigned-char" \
          --with-http_addition_module \
          --add-module=$root $opts \
          --add-module=$root/../eval-nginx-module \
          --add-module=$root/../echo-nginx-module \
          --add-module=$root/../ndk-nginx-module \
          --add-module=$root/../set-misc-nginx-module \
          --add-module=$root/../lua-nginx-module \
          --with-debug
          #--add-module=$root/../eval-nginx-module \
          #--add-module=$home/work/nginx/nginx_upstream_hash-0.3 \
  #--without-http_ssi_module  # we cannot disable ssi because echo_location_async depends on it (i dunno why?!)

