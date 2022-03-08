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

exports.all = [{
  id: 'red',
  title: 'Red',
  icon: 'danger',
  severity: 1000,
  nicknames: ['Danger Will Robinson! Danger!']
}, {
  id: 'uninitialized',
  title: 'Uninitialized',
  icon: 'spinner',
  severity: 900,
  nicknames: ['Initializing']
}, {
  id: 'yellow',
  title: 'Yellow',
  icon: 'warning',
  severity: 800,
  nicknames: ['S.N.A.F.U', 'I\'ll be back', 'brb']
}, {
  id: 'green',
  title: 'Green',
  icon: 'success',
  severity: 0,
  nicknames: ['Looking good']
}, {
  id: 'disabled',
  title: 'Disabled',
  severity: -1,
  icon: 'toggle-off',
  nicknames: ['Am I even a thing?']
}];

exports.allById = _lodash2['default'].indexBy(exports.all, 'id');

exports.defaults = {
  icon: 'question',
  severity: Infinity
};

exports.get = function (id) {
  return exports.allById[id] || _lodash2['default'].defaults({ id: id }, exports.defaults);
};
