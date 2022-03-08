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

var _boom = require('boom');

exports['default'] = function (kbnServer, server, config) {
  var disabled = config.get('server.xsrf.disableProtection');
  var versionHeader = 'kbn-version';
  var xsrfHeader = 'kbn-xsrf';

  server.ext('onPostAuth', function (req, reply) {
    if (disabled) {
      return reply['continue']();
    }

    var isSafeMethod = req.method === 'get' || req.method === 'head';
    var hasVersionHeader = (versionHeader in req.headers);
    var hasXsrfHeader = (xsrfHeader in req.headers);

    if (!isSafeMethod && !hasVersionHeader && !hasXsrfHeader) {
      return reply((0, _boom.badRequest)('Request must contain an ' + xsrfHeader + ' header'));
    }

    return reply['continue']();
  });
};

module.exports = exports['default'];
