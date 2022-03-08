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

var _setup_error = require('./setup_error');

var _setup_error2 = _interopRequireDefault(_setup_error);

var _util = require('util');

var _kibana_index_mappings = require('./kibana_index_mappings');

module.exports = function (server) {
  var _server$plugins$elasticsearch$getCluster = server.plugins.elasticsearch.getCluster('admin');

  var callWithInternalUser = _server$plugins$elasticsearch$getCluster.callWithInternalUser;

  var index = server.config().get('kibana.index');

  function handleError(message) {
    return function (err) {
      throw new _setup_error2['default'](server, message, err);
    };
  }

  return callWithInternalUser('indices.create', {
    index: index,
    body: {
      settings: {
        number_of_shards: 1
      },
      mappings: _kibana_index_mappings.mappings
    }
  })['catch'](handleError('Unable to create Kibana index "<%= kibana.index %>"')).then(function () {
    return callWithInternalUser('cluster.health', {
      waitForStatus: 'yellow',
      index: index
    })['catch'](handleError('Waiting for Kibana index "<%= kibana.index %>" to come online failed.'));
  });
};
