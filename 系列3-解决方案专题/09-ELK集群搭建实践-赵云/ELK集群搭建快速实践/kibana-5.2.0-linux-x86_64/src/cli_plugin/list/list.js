/*
 * Copyright 2021-2022 the original author or authors
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

Object.defineProperty(exports, '__esModule', {
  value: true
});
exports['default'] = list;

var _fs = require('fs');

var _path = require('path');

function list(settings, logger) {
  (0, _fs.readdirSync)(settings.pluginDir).forEach(function (filename) {
    var stat = (0, _fs.statSync)((0, _path.join)(settings.pluginDir, filename));

    if (stat.isDirectory() && filename[0] !== '.') {
      try {
        var packagePath = (0, _path.join)(settings.pluginDir, filename, 'package.json');

        var _JSON$parse = JSON.parse((0, _fs.readFileSync)(packagePath, 'utf8'));

        var version = _JSON$parse.version;

        logger.log(filename + '@' + version);
      } catch (e) {
        throw new Error('Unable to read package.json file for plugin ' + filename);
      }
    }
  });
  logger.log(''); //intentional blank line for aesthetics
}

module.exports = exports['default'];
