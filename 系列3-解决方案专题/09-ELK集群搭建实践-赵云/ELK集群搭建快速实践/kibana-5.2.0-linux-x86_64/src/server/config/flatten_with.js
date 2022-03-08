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

module.exports = function (dot, nestedObj, flattenArrays) {
  var stack = []; // track key stack
  var flatObj = {};
  (function flattenObj(obj) {
    _lodash2['default'].keys(obj).forEach(function (key) {
      stack.push(key);
      if (!flattenArrays && _lodash2['default'].isArray(obj[key])) flatObj[stack.join(dot)] = obj[key];else if (_lodash2['default'].isObject(obj[key])) flattenObj(obj[key]);else flatObj[stack.join(dot)] = obj[key];
      stack.pop();
    });
  })(nestedObj);
  return flatObj;
};
