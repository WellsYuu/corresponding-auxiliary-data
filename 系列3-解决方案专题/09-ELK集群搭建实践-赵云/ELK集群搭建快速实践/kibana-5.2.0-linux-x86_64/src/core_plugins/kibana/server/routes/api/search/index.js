'use strict';

Object.defineProperty(exports, '__esModule', {
  value: true
});

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { 'default': obj }; }

var _countRegister_count = require('./count/register_count');

var _countRegister_count2 = _interopRequireDefault(_countRegister_count);

exports['default'] = function (server) {
  (0, _countRegister_count2['default'])(server);
};

module.exports = exports['default'];
