#!/bin/sh

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

OUT_DIR=$1
DEBUG=$2

# Change into script's own dir
cd $(dirname $0)

DT_SRC=$(dirname $(dirname $(pwd)))
DT_BUILT="${DT_SRC}/built/DataTables"
. $DT_SRC/build/include.sh

scss_compile $DT_SRC/extensions/Plugins/integration/jqueryui/dataTables.jqueryui.scss

js_compress $DT_SRC/extensions/Plugins/features/searchHighlight/dataTables.searchHighlight.js
js_compress $DT_SRC/extensions/Plugins/features/alphabetSearch/dataTables.alphabetSearch.js
js_compress $DT_SRC/extensions/Plugins/features/lengthLinks/dataTables.lengthLinks.js

js_compress $DT_SRC/extensions/Plugins/integration/bootstrap/2/dataTables.bootstrap.js
js_compress $DT_SRC/extensions/Plugins/integration/bootstrap/3/dataTables.bootstrap.js
js_compress $DT_SRC/extensions/Plugins/integration/foundation/dataTables.foundation.js
js_compress $DT_SRC/extensions/Plugins/integration/jqueryui/dataTables.jqueryui.js

# Only copying the integration files
rsync -r integration     $OUT_DIR

