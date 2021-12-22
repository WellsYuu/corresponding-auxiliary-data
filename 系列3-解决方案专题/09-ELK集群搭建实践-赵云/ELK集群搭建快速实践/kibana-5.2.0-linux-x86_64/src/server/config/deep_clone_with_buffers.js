'use strict';

Object.defineProperty(exports, '__esModule', {
  value: true
});

var _lodash = require('lodash');

function cloneBuffersCustomizer(val) {
  if (Buffer.isBuffer(val)) {
    return new Buffer(val);
  }
}

exports['default'] = function (vals) {
  return (0, _lodash.cloneDeep)(vals, cloneBuffersCustomizer);
};

;
module.exports = exports['default'];
