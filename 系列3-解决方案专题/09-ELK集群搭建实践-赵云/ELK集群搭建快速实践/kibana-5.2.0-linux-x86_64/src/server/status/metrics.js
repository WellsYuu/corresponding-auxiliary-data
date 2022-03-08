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

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { 'default': obj }; }

var _lodash = require('lodash');

var _lodash2 = _interopRequireDefault(_lodash);

var _samples = require('./samples');

var _samples2 = _interopRequireDefault(_samples);

module.exports = function (kbnServer, server, config) {
  var lastReport = Date.now();

  kbnServer.metrics = new _samples2['default'](12);

  server.plugins['even-better'].monitor.on('ops', function (event) {
    var now = Date.now();
    var secSinceLast = (now - lastReport) / 1000;
    lastReport = now;

    var port = config.get('server.port');
    var requests = _lodash2['default'].get(event, ['requests', port, 'total'], 0);
    var requestsPerSecond = requests / secSinceLast;

    kbnServer.metrics.add({
      heapTotal: _lodash2['default'].get(event, 'psmem.heapTotal'),
      heapUsed: _lodash2['default'].get(event, 'psmem.heapUsed'),
      load: event.osload,
      responseTimeAvg: _lodash2['default'].get(event, ['responseTimes', port, 'avg']),
      responseTimeMax: _lodash2['default'].get(event, ['responseTimes', port, 'max']),
      requestsPerSecond: requestsPerSecond
    });
  });
};
