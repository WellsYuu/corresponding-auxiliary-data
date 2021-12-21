'use strict';

Object.defineProperty(exports, '__esModule', {
  value: true
});

var _boom = require('boom');

exports['default'] = function (kbnServer, server, config) {
  var versionHeader = 'kbn-version';
  var actualVersion = config.get('pkg.version');

  server.ext('onPostAuth', function (req, reply) {
    var versionRequested = req.headers[versionHeader];

    if (versionRequested && versionRequested !== actualVersion) {
      return reply((0, _boom.badRequest)('Browser client is out of date, please refresh the page', {
        expected: actualVersion,
        got: versionRequested
      }));
    }

    return reply['continue']();
  });
};

module.exports = exports['default'];
