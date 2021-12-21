'use strict';

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { 'default': obj }; }

var _joi = require('joi');

var _joi2 = _interopRequireDefault(_joi);

module.exports = _joi2['default'].object({
  id: _joi2['default'].string().required(),
  title: _joi2['default'].string().required(),
  time_field_name: _joi2['default'].string(),
  interval_name: _joi2['default'].string(),
  not_expandable: _joi2['default'].boolean(),
  source_filters: _joi2['default'].array(),
  fields: _joi2['default'].array().items(_joi2['default'].object({
    name: _joi2['default'].string().required(),
    type: _joi2['default'].string().required(),
    count: _joi2['default'].number().integer(),
    scripted: _joi2['default'].boolean(),
    doc_values: _joi2['default'].boolean(),
    analyzed: _joi2['default'].boolean(),
    indexed: _joi2['default'].boolean(),
    script: _joi2['default'].string(),
    lang: _joi2['default'].string()
  })).required().min(1),
  field_format_map: _joi2['default'].object()
});
