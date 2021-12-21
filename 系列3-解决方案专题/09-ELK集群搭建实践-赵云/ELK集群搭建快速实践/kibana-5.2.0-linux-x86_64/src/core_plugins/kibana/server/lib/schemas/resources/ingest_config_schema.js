'use strict';

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { 'default': obj }; }

var _joi = require('joi');

var _joi2 = _interopRequireDefault(_joi);

var _index_pattern_schema = require('./index_pattern_schema');

var _index_pattern_schema2 = _interopRequireDefault(_index_pattern_schema);

module.exports = _joi2['default'].object({
  index_pattern: _index_pattern_schema2['default'].required()
});
