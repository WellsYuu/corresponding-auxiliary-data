'use strict';

Object.defineProperty(exports, '__esModule', {
  value: true
});
exports.registerFieldCapabilities = registerFieldCapabilities;

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { 'default': obj }; }

var _lodash = require('lodash');

var _lodash2 = _interopRequireDefault(_lodash);

var _libHandle_es_error = require('../../../lib/handle_es_error');

var _libHandle_es_error2 = _interopRequireDefault(_libHandle_es_error);

function registerFieldCapabilities(server) {
  server.route({
    path: '/api/kibana/{indices}/field_capabilities',
    method: ['GET'],
    handler: function handler(req, reply) {
      var _server$plugins$elasticsearch$getCluster = server.plugins.elasticsearch.getCluster('data');

      var callWithRequest = _server$plugins$elasticsearch$getCluster.callWithRequest;

      var indices = req.params.indices || '';

      return callWithRequest(req, 'fieldStats', {
        fields: '*',
        level: 'cluster',
        index: indices,
        allowNoIndices: false
      }).then(function (res) {
        var fields = _lodash2['default'].get(res, 'indices._all.fields', {});
        var fieldsFilteredValues = _lodash2['default'].mapValues(fields, function (value) {
          return _lodash2['default'].pick(value, ['searchable', 'aggregatable']);
        });

        reply({ fields: fieldsFilteredValues });
      }, function (error) {
        reply((0, _libHandle_es_error2['default'])(error));
      });
    }
  });
}
