'use strict';

Object.defineProperty(exports, '__esModule', {
  value: true
});

var _register_field_capabilities = require('./register_field_capabilities');

exports['default'] = function (server) {
  (0, _register_field_capabilities.registerFieldCapabilities)(server);
};

module.exports = exports['default'];
