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
