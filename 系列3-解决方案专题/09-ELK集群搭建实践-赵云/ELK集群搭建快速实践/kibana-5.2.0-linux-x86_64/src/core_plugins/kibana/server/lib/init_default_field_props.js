/*
 * Copyright 2021-2022 the original author or authors
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

var _lodash = require('lodash');

var _lodash2 = _interopRequireDefault(_lodash);

var _mapping_overrides = require('./mapping_overrides');

var _mapping_overrides2 = _interopRequireDefault(_mapping_overrides);

module.exports = function initDefaultFieldProps(fields) {
  if (fields === undefined || !_lodash2['default'].isArray(fields)) {
    throw new Error('requires an array argument');
  }

  var results = [];

  _lodash2['default'].forEach(fields, function (field) {
    var newField = _lodash2['default'].cloneDeep(field);
    results.push(newField);

    if (newField.type === 'string') {
      _lodash2['default'].defaults(newField, {
        indexed: true,
        analyzed: true,
        doc_values: false,
        scripted: false,
        count: 0
      });

      results.push({
        name: newField.name + '.keyword',
        type: 'string',
        indexed: true,
        analyzed: false,
        doc_values: true,
        scripted: false,
        count: 0
      });
    } else {
      _lodash2['default'].defaults(newField, {
        indexed: true,
        analyzed: false,
        doc_values: true,
        scripted: false,
        count: 0
      });
    }

    if (_mapping_overrides2['default'][newField.name]) {
      _lodash2['default'].assign(newField, _mapping_overrides2['default'][newField.name]);
    }
  });

  return results;
};
