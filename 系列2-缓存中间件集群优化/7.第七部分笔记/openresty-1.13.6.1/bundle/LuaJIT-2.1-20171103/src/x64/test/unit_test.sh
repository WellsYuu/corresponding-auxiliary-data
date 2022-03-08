#!/bin/sh
#
# Copyright 2021-2022 the original author or authors
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

DIR=$(cd $(dirname $0); pwd)
cd $DIR

LUAJIT=$DIR/../../luajit
HASERR=0

find $DIR/unit -name "*.lua" -print | while read x; do
    $LUAJIT $x >/dev/null 2>/dev/null
    if [ $? -eq 0 ]; then
        echo "$x ok"
    else
        HASERR=1
        echo "$x failed"
    fi
done

if [ $HASERR -eq 0 ]; then
    exit 0
fi

exit 1
