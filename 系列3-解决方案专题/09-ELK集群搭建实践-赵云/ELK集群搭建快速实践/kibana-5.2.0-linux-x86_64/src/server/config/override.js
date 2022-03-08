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

var _flatten_with = require('./flatten_with');

var _flatten_with2 = _interopRequireDefault(_flatten_with);

var _explode_by = require('./explode_by');

var _explode_by2 = _interopRequireDefault(_explode_by);

module.exports = function (target, source) {
  var _target = (0, _flatten_with2['default'])('.', target);
  var _source = (0, _flatten_with2['default'])('.', source);
  return (0, _explode_by2['default'])('.', _lodash2['default'].defaults(_source, _target));
};
