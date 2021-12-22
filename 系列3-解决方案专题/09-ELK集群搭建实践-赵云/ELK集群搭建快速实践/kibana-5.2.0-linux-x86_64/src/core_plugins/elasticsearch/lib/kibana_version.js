'use strict';

Object.defineProperty(exports, '__esModule', {
  value: true
});

var _packageJson = require('../../../../package.json');

exports['default'] = {
  // Make the version stubbable to improve testability.
  get: function get() {
    return _packageJson.version;
  }
};
module.exports = exports['default'];
