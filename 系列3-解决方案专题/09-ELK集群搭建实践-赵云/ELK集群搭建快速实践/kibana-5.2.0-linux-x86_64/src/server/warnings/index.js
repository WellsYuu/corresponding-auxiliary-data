'use strict';

Object.defineProperty(exports, '__esModule', {
  value: true
});

exports['default'] = function (kbnServer, server, config) {
  process.on('warning', function (warning) {
    server.log(['warning', 'process'], warning);
  });
};

module.exports = exports['default'];
