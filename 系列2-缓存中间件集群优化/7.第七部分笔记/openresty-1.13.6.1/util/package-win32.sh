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

name=`pwd|perl -e '$d=<>;$d=~s{.*?/}{}g;$d=~s/$//g;print $d'`
name="$name-win32"
echo $name
if [ -d $name ]; then
    rm -rf $name
fi
mkdir $name || exit 1
cp -r resty restydoc restydoc-index nginx.exe luajit.exe lua51.dll lua include lualib html conf logs pod $name/ || exit 1
cp COPYRIGHT $name/ || exit 1
cp /c/MinGW/bin/libgcc_s_dw2-1.dll $name/ || exit 1
cd $name || exit 1
PATH=/c/Strawberry/perl/bin:$PATH cmd /c 'pl2bat.bat resty' || exit 1
PATH=/c/Strawberry/perl/bin:$PATH cmd /c 'pl2bat.bat restydoc' || exit 1
PATH=/c/Strawberry/perl/bin:$PATH cmd /c 'pl2bat.bat restydoc-index' || exit 1
cp ../README-win32.txt README.txt
unix2dos conf/* html/*.html resty || exit 1
cd .. || exit 1
zip -r $name.zip $name || exit 1
echo $name.zip
