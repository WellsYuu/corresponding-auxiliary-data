#!/bin/bash
#
# Copyright 2022 the original author or authors.
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

script_dir=$(dirname $0)
root=$(readlink -f $script_dir/..)
testfile=${1:-$root/t/*.t $root/t/*/*.t}
cd $root
$script_dir/reindex $testfile
export PATH=$root/work/sbin:$PATH
killall nginx
prove -I$root/../test-nginx/lib $testfile

