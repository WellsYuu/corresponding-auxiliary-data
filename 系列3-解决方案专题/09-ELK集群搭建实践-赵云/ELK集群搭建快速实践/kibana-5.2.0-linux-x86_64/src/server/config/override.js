'use strict';

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { 'default': obj }; }

var _lodash = require('lodash');

var _lodash2 = _interopRequireDefault(_lodash);

var _flatten_with = require('./flatten_with');

var _flatten_with2 = _interopRequireDefault(_flatten_with);

var _explode_by = require('./explode_by');

var _explode_by2 = _interopRequireDefault(_explode_by);

module.exports = function (target, source) {
  var _target = (0, _flatten_with2['default'])('.', target);
  var _source = (0, _flatten_with2['default'])('.', source);
  return (0, _explode_by2['default'])('.', _lodash2['default'].defaults(_source, _target));
};
