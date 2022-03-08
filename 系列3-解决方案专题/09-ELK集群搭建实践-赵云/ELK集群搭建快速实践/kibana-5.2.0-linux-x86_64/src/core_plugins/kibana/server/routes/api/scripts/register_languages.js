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
exports.registerLanguages = registerLanguages;

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { 'default': obj }; }

var _lodash = require('lodash');

var _lodash2 = _interopRequireDefault(_lodash);

var _libHandle_es_error = require('../../../lib/handle_es_error');

var _libHandle_es_error2 = _interopRequireDefault(_libHandle_es_error);

function registerLanguages(server) {
  server.route({
    path: '/api/kibana/scripts/languages',
    method: 'GET',
    handler: function handler(request, reply) {
      var _server$plugins$elasticsearch$getCluster = server.plugins.elasticsearch.getCluster('data');

      var callWithRequest = _server$plugins$elasticsearch$getCluster.callWithRequest;

      return callWithRequest(request, 'cluster.getSettings', {
        include_defaults: true,
        filter_path: '**.script.engine.*.inline'
      }).then(function (esResponse) {
        var langs = _lodash2['default'].get(esResponse, 'defaults.script.engine', {});
        var inlineLangs = _lodash2['default'].pick(langs, function (lang) {
          return lang.inline === 'true';
        });
        var supportedLangs = _lodash2['default'].omit(inlineLangs, 'mustache');
        return _lodash2['default'].keys(supportedLangs);
      }).then(reply)['catch'](function (error) {
        reply((0, _libHandle_es_error2['default'])(error));
      });
    }
  });
}
