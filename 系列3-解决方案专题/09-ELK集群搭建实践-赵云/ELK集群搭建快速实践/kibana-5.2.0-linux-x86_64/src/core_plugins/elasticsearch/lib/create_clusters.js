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
exports.createClusters = createClusters;

var _cluster = require('./cluster');

var _lodash = require('lodash');

function createClusters(server) {
  var esPlugin = server.plugins.elasticsearch;
  esPlugin._clusters = esPlugin._clusters || new Map();

  return {
    get: function get(name) {
      return esPlugin._clusters.get(name);
    },

    create: function create(name, config) {
      var cluster = new _cluster.Cluster(config);

      if (esPlugin._clusters.has(name)) {
        throw new Error('cluster \'' + name + '\' already exists');
      }

      esPlugin._clusters.set(name, cluster);

      return cluster;
    }
  };
}
