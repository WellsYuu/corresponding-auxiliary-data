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

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { 'default': obj }; }

var _bluebird = require('bluebird');

var _evenBetter = require('even-better');

var _evenBetter2 = _interopRequireDefault(_evenBetter);

var _configuration = require('./configuration');

var _configuration2 = _interopRequireDefault(_configuration);

exports['default'] = function (kbnServer, server, config) {
  // prevent relying on kbnServer so this can be used with other hapi servers
  kbnServer = null;

  return (0, _bluebird.fromNode)(function (cb) {
    server.register({
      register: _evenBetter2['default'],
      options: (0, _configuration2['default'])(config)
    }, cb);
  });
};

;
module.exports = exports['default'];
