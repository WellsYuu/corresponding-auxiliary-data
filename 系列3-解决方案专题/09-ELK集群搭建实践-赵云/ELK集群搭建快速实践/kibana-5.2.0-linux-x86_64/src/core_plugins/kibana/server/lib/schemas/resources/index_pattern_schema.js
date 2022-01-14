/*
 * Copyright 2021-2022 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
