'use strict';

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { 'default': obj }; }

var _lodash = require('lodash');

var _lodash2 = _interopRequireDefault(_lodash);

var _ansicolors = require('ansicolors');

var _ansicolors2 = _interopRequireDefault(_ansicolors);

exports.green = _lodash2['default'].flow(_ansicolors2['default'].black, _ansicolors2['default'].bgGreen);
exports.red = _lodash2['default'].flow(_ansicolors2['default'].white, _ansicolors2['default'].bgRed);
exports.yellow = _lodash2['default'].flow(_ansicolors2['default'].black, _ansicolors2['default'].bgYellow);
