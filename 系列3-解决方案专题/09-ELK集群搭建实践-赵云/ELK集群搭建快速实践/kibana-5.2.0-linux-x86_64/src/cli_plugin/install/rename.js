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
exports.renamePlugin = renamePlugin;

var _fs = require('fs');

var _lodash = require('lodash');

function renamePlugin(workingPath, finalPath) {
  return new Promise(function (resolve, reject) {
    var start = Date.now();
    var retryTime = 3000;
    var retryDelay = 100;
    (0, _fs.rename)(workingPath, finalPath, function retry(err) {
      if (err) {
        // In certain cases on Windows, such as running AV, plugin folders can be locked shortly after extracting
        // Retry for up to retryTime seconds
        var windowsEPERM = process.platform === 'win32' && err.code === 'EPERM';
        var retryAvailable = Date.now() - start < retryTime;
        if (windowsEPERM && retryAvailable) return (0, _lodash.delay)(_fs.rename, retryDelay, workingPath, finalPath, retry);
        reject(err);
      }
      resolve();
    });
  });
}
