'use strict';

Object.defineProperty(exports, '__esModule', {
  value: true
});

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { 'default': obj }; }

var _lodash = require('lodash');

var _lodash2 = _interopRequireDefault(_lodash);

exports['default'] = function (originalHeaders, headersToKeep) {

  var normalizeHeader = function normalizeHeader(header) {
    if (!header) {
      return '';
    }
    header = header.toString();
    return header.trim().toLowerCase();
  };

  // Normalize list of headers we want to allow in upstream request
  var headersToKeepNormalized = headersToKeep.map(normalizeHeader);

  // Normalize original headers in request
  var originalHeadersNormalized = _lodash2['default'].mapKeys(originalHeaders, function (headerValue, headerName) {
    return normalizeHeader(headerName);
  });

  return _lodash2['default'].pick(originalHeaders, headersToKeepNormalized);
};

module.exports = exports['default'];
