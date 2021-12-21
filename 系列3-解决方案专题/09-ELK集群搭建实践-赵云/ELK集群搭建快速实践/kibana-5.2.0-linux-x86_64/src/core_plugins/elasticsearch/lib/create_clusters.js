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
