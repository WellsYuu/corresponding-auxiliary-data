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
exports['default'] = remove;

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { 'default': obj }; }

var _fs = require('fs');

var _rimraf = require('rimraf');

var _rimraf2 = _interopRequireDefault(_rimraf);

function remove(settings, logger) {
  try {
    var stat = undefined;
    try {
      stat = (0, _fs.statSync)(settings.pluginPath);
    } catch (e) {
      throw new Error('Plugin [' + settings.plugin + '] is not installed');
    }

    if (!stat.isDirectory()) {
      throw new Error('[' + settings.plugin + '] is not a plugin');
    }

    logger.log('Removing ' + settings.plugin + '...');
    _rimraf2['default'].sync(settings.pluginPath);
  } catch (err) {
    logger.error('Unable to remove plugin because of error: "' + err.message + '"');
    process.exit(74); // eslint-disable-line no-process-exit
  }
}

module.exports = exports['default'];
