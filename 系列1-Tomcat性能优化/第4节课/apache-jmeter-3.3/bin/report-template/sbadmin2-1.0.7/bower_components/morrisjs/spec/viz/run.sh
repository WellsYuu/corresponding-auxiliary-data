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

# visual_specs.js creates output in output/XXX.png
phantomjs visual_specs.js

# clear out old diffs
mkdir -p diff
rm -f diff/*

# generate diffs
PASS=1
for i in exemplary/*.png
do
  FN=`basename $i`
  perceptualdiff $i output/$FN -output diff/$FN
  if [ $? -eq 0 ]
  then
    echo "OK:   $FN"
  else
    echo "FAIL: $FN"
    PASS=0
  fi
done

# pass / fail
if [ $PASS -eq 1 ]
then
  echo "Success."
else
  echo "Failed."
  exit 1
fi
