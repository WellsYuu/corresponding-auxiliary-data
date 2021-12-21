'use strict';

Object.defineProperty(exports, '__esModule', {
  value: true
});

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { 'default': obj }; }

var _url = require('url');

var _url2 = _interopRequireDefault(_url);

var _lodash = require('lodash');

var _http = require('http');

var _http2 = _interopRequireDefault(_http);

var _https = require('https');

var _https2 = _interopRequireDefault(_https);

var _parse_config = require('./parse_config');

var readFile = function readFile(file) {
  return require('fs').readFileSync(file, 'utf8');
};

exports['default'] = function (config) {
  var target = _url2['default'].parse((0, _lodash.get)(config, 'url'));

  if (!/^https/.test(target.protocol)) return new _http2['default'].Agent();

  return new _https2['default'].Agent((0, _parse_config.parseConfig)(config).ssl);
};

module.exports = exports['default'];
