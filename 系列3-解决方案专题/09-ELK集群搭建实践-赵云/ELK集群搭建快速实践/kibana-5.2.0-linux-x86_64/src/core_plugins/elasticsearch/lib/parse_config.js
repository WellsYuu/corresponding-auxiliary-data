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
exports.parseConfig = parseConfig;

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { 'default': obj }; }

var _util = require('util');

var _util2 = _interopRequireDefault(_util);

var _url = require('url');

var _url2 = _interopRequireDefault(_url);

var _lodash = require('lodash');

var _fs = require('fs');

var _bluebird = require('bluebird');

var _bluebird2 = _interopRequireDefault(_bluebird);

var readFile = function readFile(file) {
  return (0, _fs.readFileSync)(file, 'utf8');
};

function parseConfig() {
  var serverConfig = arguments.length <= 0 || arguments[0] === undefined ? {} : arguments[0];

  var config = Object.assign({
    keepAlive: true
  }, (0, _lodash.pick)(serverConfig, ['plugins', 'apiVersion', 'keepAlive', 'pingTimeout', 'requestTimeout', 'log', 'logQueries']));

  var uri = _url2['default'].parse(serverConfig.url);
  config.host = {
    host: uri.hostname,
    port: uri.port,
    protocol: uri.protocol,
    path: uri.pathname,
    query: uri.query,
    headers: serverConfig.customHeaders
  };

  // Auth
  if (serverConfig.auth !== false && serverConfig.username && serverConfig.password) {
    config.host.auth = _util2['default'].format('%s:%s', serverConfig.username, serverConfig.password);
  }

  // SSL
  config.ssl = { rejectUnauthorized: (0, _lodash.get)(serverConfig, 'ssl.verify') };

  if ((0, _lodash.get)(serverConfig, 'ssl.cert') && (0, _lodash.get)(serverConfig, 'ssl.key')) {
    config.ssl.cert = readFile(serverConfig.ssl.cert);
    config.ssl.key = readFile(serverConfig.ssl.key);
  }

  if ((0, _lodash.size)((0, _lodash.get)(serverConfig, 'ssl.ca'))) {
    config.ssl.ca = serverConfig.ssl.ca.map(readFile);
  }

  config.defer = function () {
    return _bluebird2['default'].defer();
  };

  return config;
}
