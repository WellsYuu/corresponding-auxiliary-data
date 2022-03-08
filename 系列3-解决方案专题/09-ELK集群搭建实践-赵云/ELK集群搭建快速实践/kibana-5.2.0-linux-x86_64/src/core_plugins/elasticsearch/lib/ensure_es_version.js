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

/**
 * ES and Kibana versions are locked, so Kibana should require that ES has the same version as
 * that defined in Kibana's package.json.
 */

'use strict';

Object.defineProperty(exports, '__esModule', {
  value: true
});
exports.ensureEsVersion = ensureEsVersion;

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { 'default': obj }; }

var _lodash = require('lodash');

var _is_es_compatible_with_kibana = require('./is_es_compatible_with_kibana');

var _is_es_compatible_with_kibana2 = _interopRequireDefault(_is_es_compatible_with_kibana);

var _setup_error = require('./setup_error');

var _setup_error2 = _interopRequireDefault(_setup_error);

/**
 * tracks the node descriptions that get logged in warnings so
 * that we don't spam the log with the same message over and over.
 *
 * There are situations, like in testing or multi-tenancy, where
 * the server argument changes, so we must track the previous
 * node warnings per server
 */
var lastWarnedNodesForServer = new WeakMap();

function ensureEsVersion(server, kibanaVersion) {
  var _server$plugins$elasticsearch$getCluster = server.plugins.elasticsearch.getCluster('admin');

  var callWithInternalUser = _server$plugins$elasticsearch$getCluster.callWithInternalUser;

  server.log(['plugin', 'debug'], 'Checking Elasticsearch version');
  return callWithInternalUser('nodes.info', {
    filterPath: ['nodes.*.version', 'nodes.*.http.publish_address', 'nodes.*.ip']
  }).then(function (info) {
    // Aggregate incompatible ES nodes.
    var incompatibleNodes = [];

    // Aggregate ES nodes which should prompt a Kibana upgrade.
    var warningNodes = [];

    (0, _lodash.forEach)(info.nodes, function (esNode) {
      if (!(0, _is_es_compatible_with_kibana2['default'])(esNode.version, kibanaVersion)) {
        // Exit early to avoid collecting ES nodes with newer major versions in the `warningNodes`.
        return incompatibleNodes.push(esNode);
      }

      // It's acceptable if ES and Kibana versions are not the same so long as
      // they are not incompatible, but we should warn about it
      if (esNode.version !== kibanaVersion) {
        warningNodes.push(esNode);
      }
    });

    function getHumanizedNodeNames(nodes) {
      return nodes.map(function (node) {
        var publishAddress = (0, _lodash.get)(node, 'http.publish_address') ? (0, _lodash.get)(node, 'http.publish_address') + ' ' : '';
        return 'v' + node.version + ' @ ' + publishAddress + '(' + node.ip + ')';
      });
    }

    if (warningNodes.length) {
      var simplifiedNodes = warningNodes.map(function (node) {
        return {
          version: node.version,
          http: {
            publish_address: (0, _lodash.get)(node, 'http.publish_address')
          },
          ip: node.ip
        };
      });

      // Don't show the same warning over and over again.
      var warningNodeNames = getHumanizedNodeNames(simplifiedNodes).join(', ');
      if (lastWarnedNodesForServer.get(server) !== warningNodeNames) {
        lastWarnedNodesForServer.set(server, warningNodeNames);
        server.log(['warning'], {
          tmpl: 'You\'re running Kibana ' + kibanaVersion + ' with some different versions of ' + 'Elasticsearch. Update Kibana or Elasticsearch to the same ' + ('version to prevent compatibility issues: ' + warningNodeNames),
          kibanaVersion: kibanaVersion,
          nodes: simplifiedNodes
        });
      }
    }

    if (incompatibleNodes.length) {
      var incompatibleNodeNames = getHumanizedNodeNames(incompatibleNodes);

      var errorMessage = 'This version of Kibana requires Elasticsearch v' + (kibanaVersion + ' on all nodes. I found ') + ('the following incompatible nodes in your cluster: ' + incompatibleNodeNames.join(', '));

      throw new _setup_error2['default'](server, errorMessage);
    }

    return true;
  });
}
