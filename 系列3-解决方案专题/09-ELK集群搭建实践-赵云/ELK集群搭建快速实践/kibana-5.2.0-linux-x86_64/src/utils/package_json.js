/*
 * Copyright 2021-2022 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

'use strict';

var _fs = require('fs');

var _path = require('path');

var packageDir = undefined;
var packagePath = undefined;

while (!packagePath || !(0, _fs.existsSync)(packagePath)) {
  var prev = packageDir;
  packageDir = prev ? (0, _path.join)(prev, '..') : __dirname;
  packagePath = (0, _path.join)(packageDir, 'package.json');

  if (prev === packageDir) {
    // if going up a directory doesn't work, we
    // are already at the root of the filesystem
    throw new Error('unable to find package.json');
  }
}

module.exports = require(packagePath);
module.exports.__filename = packagePath;
module.exports.__dirname = packageDir;
