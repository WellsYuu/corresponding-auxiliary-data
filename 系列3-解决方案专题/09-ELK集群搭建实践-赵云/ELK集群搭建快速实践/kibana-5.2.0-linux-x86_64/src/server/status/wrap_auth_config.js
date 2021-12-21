'use strict';

Object.defineProperty(exports, '__esModule', {
  value: true
});

var _lodash = require('lodash');

exports['default'] = function (allowAnonymous) {
  if (allowAnonymous) return function (options) {
    return (0, _lodash.assign)(options, { config: { auth: false } });
  };
  return _lodash.identity;
};

module.exports = exports['default'];
