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

Object.defineProperty(exports, '__esModule', {
  value: true
});

var _fs = require('fs');

var _lodash = require('lodash');

var _utils = require('../../utils');

var CONFIG_PATHS = [process.env.CONFIG_PATH, (0, _utils.fromRoot)('config/kibana.yml'), '/etc/kibana/kibana.yml'].filter(Boolean);

var DATA_PATHS = [process.env.DATA_PATH, (0, _utils.fromRoot)('data'), '/var/lib/kibana'].filter(Boolean);

function findFile(paths) {
  var availablePath = (0, _lodash.find)(paths, function (configPath) {
    try {
      (0, _fs.accessSync)(configPath, _fs.R_OK);
      return true;
    } catch (e) {
      //Check the next path
    }
  });
  return availablePath || paths[0];
}

exports['default'] = {
  getConfig: function getConfig() {
    return findFile(CONFIG_PATHS);
  },
  getData: function getData() {
    return findFile(DATA_PATHS);
  }
};
module.exports = exports['default'];
