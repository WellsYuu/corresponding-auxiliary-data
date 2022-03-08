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
exports['default'] = registerCount;

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { 'default': obj }; }

var _lodash = require('lodash');

var _lodash2 = _interopRequireDefault(_lodash);

var _libHandle_es_error = require('../../../../lib/handle_es_error');

var _libHandle_es_error2 = _interopRequireDefault(_libHandle_es_error);

function registerCount(server) {
  server.route({
    path: '/api/kibana/{id}/_count',
    method: ['POST', 'GET'],
    handler: function handler(req, reply) {
      var _server$plugins$elasticsearch$getCluster = server.plugins.elasticsearch.getCluster('data');

      var callWithRequest = _server$plugins$elasticsearch$getCluster.callWithRequest;

      var boundCallWithRequest = _lodash2['default'].partial(callWithRequest, req);

      boundCallWithRequest('count', {
        allowNoIndices: false,
        index: req.params.id
      }).then(function (res) {
        reply({ count: res.count });
      }, function (error) {
        reply((0, _libHandle_es_error2['default'])(error));
      });
    }
  });
}

module.exports = exports['default'];
