'use strict';

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { 'default': obj }; }

var _lodash = require('lodash');

var _lodash2 = _interopRequireDefault(_lodash);

module.exports = _lodash2['default'].once(function (kbnServer) {
  var uiExports = kbnServer.uiExports;
  var config = kbnServer.config;

  return '' + config.get('server.basePath') + config.get('server.defaultRoute');
});
