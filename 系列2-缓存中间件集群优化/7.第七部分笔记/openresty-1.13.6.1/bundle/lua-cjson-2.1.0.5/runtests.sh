#!/bin/sh

#
# Copyright [$tody.year] [Wales Yu of copyright owner]
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

PLATFORM="`uname -s`"
[ "$1" ] && VERSION="$1" || VERSION="2.1devel"

set -e

# Portable "ggrep -A" replacement.
# Work around Solaris awk record limit of 2559 bytes.
# contextgrep PATTERN POST_MATCH_LINES
contextgrep() {
    cut -c -2500 | awk "/$1/ { count = ($2 + 1) } count > 0 { count--; print }"
}

do_tests() {
    echo
    cd tests
    lua -e 'print("Testing Lua CJSON version " .. require("cjson")._VERSION)'
    ./test.lua | contextgrep 'FAIL|Summary' 3 | grep -v PASS | cut -c -150
    cd ..
}

echo "===== Setting LuaRocks PATH ====="
eval "`luarocks path`"

echo "===== Building UTF-8 test data ====="
( cd tests && ./genutf8.pl; )

echo "===== Cleaning old build data ====="
make clean
rm -f tests/cjson.so

echo "===== Verifying cjson.so is not installed ====="

cd tests
if lua -e 'require "cjson"' 2>/dev/null
then
    cat <<EOT
Please ensure you do not have the Lua CJSON module installed before
running these tests.
EOT
    exit
fi
cd ..

echo "===== Testing LuaRocks build ====="
luarocks make --local
do_tests
luarocks remove --local lua-cjson
make clean

echo "===== Testing Makefile build ====="
make
cp -r lua/cjson cjson.so tests
do_tests
make clean
rm -rf tests/cjson{,.so}

echo "===== Testing Cmake build ====="
mkdir build
cd build
cmake ..
make
cd ..
cp -r lua/cjson build/cjson.so tests
do_tests
rm -rf build tests/cjson{,.so}

# vi:ai et sw=4 ts=4:
