'use strict';

var _bluebird = require('bluebird');

var _lodash = require('lodash');

module.exports = function (kbnServer, server, config) {

  server.route({
    path: '/bundles/{path*}',
    method: 'GET',
    handler: {
      proxy: {
        host: config.get('optimize.lazyHost'),
        port: config.get('optimize.lazyPort'),
        passThrough: true,
        xforward: true
      }
    },
    config: { auth: false }
  });

  return (0, _bluebird.fromNode)(function (cb) {
    var timeout = setTimeout(function () {
      cb(new Error('Server timedout waiting for the optimizer to become ready'));
    }, config.get('optimize.lazyProxyTimeout'));

    var waiting = (0, _lodash.once)(function () {
      server.log(['info', 'optimize'], 'Waiting for optimizer completion');
    });

    if (!process.connected) return;

    process.send(['WORKER_BROADCAST', { optimizeReady: '?' }]);
    process.on('message', function (msg) {
      switch ((0, _lodash.get)(msg, 'optimizeReady')) {
        case true:
          clearTimeout(timeout);
          cb();
          break;
        case false:
          waiting();
          break;
      }
    });
  });
};
