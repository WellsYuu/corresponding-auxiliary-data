'use strict';

Object.defineProperty(exports, '__esModule', {
  value: true
});

var _boom = require('boom');

exports['default'] = function (kbnServer, server, config) {
  var disabled = config.get('server.xsrf.disableProtection');
  var versionHeader = 'kbn-version';
  var xsrfHeader = 'kbn-xsrf';

  server.ext('onPostAuth', function (req, reply) {
    if (disabled) {
      return reply['continue']();
    }

    var isSafeMethod = req.method === 'get' || req.method === 'head';
    var hasVersionHeader = (versionHeader in req.headers);
    var hasXsrfHeader = (xsrfHeader in req.headers);

    if (!isSafeMethod && !hasVersionHeader && !hasXsrfHeader) {
      return reply((0, _boom.badRequest)('Request must contain an ' + xsrfHeader + ' header'));
    }

    return reply['continue']();
  });
};

module.exports = exports['default'];
