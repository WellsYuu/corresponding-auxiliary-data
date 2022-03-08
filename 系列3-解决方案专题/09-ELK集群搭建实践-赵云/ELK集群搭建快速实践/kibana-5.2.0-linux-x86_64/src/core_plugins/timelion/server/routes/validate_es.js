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

module.exports = function (server) {
  server.route({
    method: 'GET',
    path: '/api/timelion/validate/es',
    handler: function handler(request, reply) {
      return server.uiSettings().getAll(request).then(function (uiSettings) {
        var _server$plugins$elasticsearch$getCluster = server.plugins.elasticsearch.getCluster('data');

        var callWithRequest = _server$plugins$elasticsearch$getCluster.callWithRequest;

        var timefield = uiSettings['timelion:es.timefield'];

        var body = {
          index: uiSettings['es.default_index'],
          fields: timefield
        };

        callWithRequest(request, 'fieldStats', body).then(function (resp) {
          reply({
            ok: true,
            field: timefield,
            min: resp.indices._all.fields[timefield].min_value,
            max: resp.indices._all.fields[timefield].max_value
          });
        })['catch'](function (resp) {
          reply({
            ok: false,
            resp: resp
          });
        });
      });
    }
  });
};
