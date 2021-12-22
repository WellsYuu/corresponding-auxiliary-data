'use strict';

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { 'default': obj }; }

var _config = require('./config');

var _config2 = _interopRequireDefault(_config);

module.exports = function (kbnServer) {
  kbnServer.config = _config2['default'].withDefaultSchema(kbnServer.settings);
};
