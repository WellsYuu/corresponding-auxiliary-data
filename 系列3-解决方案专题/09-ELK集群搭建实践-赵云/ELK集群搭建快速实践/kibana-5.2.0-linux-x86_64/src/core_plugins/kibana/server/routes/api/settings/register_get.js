'use strict';

Object.defineProperty(exports, '__esModule', {
  value: true
});
exports['default'] = registerGet;

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { 'default': obj }; }

var _boom = require('boom');

var _boom2 = _interopRequireDefault(_boom);

function registerGet(server) {
  server.route({
    path: '/api/kibana/settings',
    method: 'GET',
    handler: function handler(req, reply) {
      server.uiSettings().getUserProvided(req).then(function (settings) {
        return reply({ settings: settings }).type('application/json');
      })['catch'](function (err) {
        return reply(_boom2['default'].wrap(err, err.statusCode));
      });
    }
  });
}

module.exports = exports['default'];
