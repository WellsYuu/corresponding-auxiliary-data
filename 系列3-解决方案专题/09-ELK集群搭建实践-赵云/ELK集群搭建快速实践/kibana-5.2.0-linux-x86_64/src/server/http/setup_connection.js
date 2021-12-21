'use strict';

Object.defineProperty(exports, '__esModule', {
  value: true
});

var _extends = Object.assign || function (target) { for (var i = 1; i < arguments.length; i++) { var source = arguments[i]; for (var key in source) { if (Object.prototype.hasOwnProperty.call(source, key)) { target[key] = source[key]; } } } return target; };

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { 'default': obj }; }

var _fs = require('fs');

var _url = require('url');

var _httpolyglot = require('httpolyglot');

var _httpolyglot2 = _interopRequireDefault(_httpolyglot);

var _tls_ciphers = require('./tls_ciphers');

var _tls_ciphers2 = _interopRequireDefault(_tls_ciphers);

exports['default'] = function (kbnServer, server, config) {
  // this mixin is used outside of the kbn server, so it MUST work without a full kbnServer object.
  kbnServer = null;

  var host = config.get('server.host');
  var port = config.get('server.port');

  var connectionOptions = {
    host: host,
    port: port,
    state: {
      strictHeader: false
    },
    routes: {
      cors: config.get('server.cors'),
      payload: {
        maxBytes: config.get('server.maxPayloadBytes')
      }
    }
  };

  // enable tlsOpts if ssl key and cert are defined
  var useSsl = config.get('server.ssl.key') && config.get('server.ssl.cert');

  // not using https? well that's easy!
  if (!useSsl) {
    server.connection(connectionOptions);
    return;
  }

  server.connection(_extends({}, connectionOptions, {
    tls: true,
    listener: _httpolyglot2['default'].createServer({
      key: (0, _fs.readFileSync)(config.get('server.ssl.key')),
      cert: (0, _fs.readFileSync)(config.get('server.ssl.cert')),

      ciphers: _tls_ciphers2['default'],
      // We use the server's cipher order rather than the client's to prevent the BEAST attack
      honorCipherOrder: true
    })
  }));

  server.ext('onRequest', function (req, reply) {
    // A request sent through a HapiJS .inject() doesn't have a socket associated with the request
    // which causes a failure.
    if (!req.raw.req.socket || req.raw.req.socket.encrypted) {
      reply['continue']();
    } else {
      reply.redirect((0, _url.format)({
        port: port,
        protocol: 'https',
        hostname: host,
        pathname: req.url.pathname,
        search: req.url.search
      }));
    }
  });
};

module.exports = exports['default'];
