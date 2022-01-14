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

function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError('Cannot call a class as a function'); } }

var _lodash = require('lodash');

var _lodash2 = _interopRequireDefault(_lodash);

var _ansicolors = require('ansicolors');

var _ansicolors2 = _interopRequireDefault(_ansicolors);

var _color = require('./color');

var _color2 = _interopRequireDefault(_color);

var log = _lodash2['default'].restParam(function (color, label, rest1) {
  console.log.apply(console, [color(' ' + _lodash2['default'].trim(label) + ' ')].concat(rest1));
});

module.exports = function Log(quiet, silent) {
  _classCallCheck(this, Log);

  this.good = quiet || silent ? _lodash2['default'].noop : _lodash2['default'].partial(log, _color2['default'].green);
  this.warn = quiet || silent ? _lodash2['default'].noop : _lodash2['default'].partial(log, _color2['default'].yellow);
  this.bad = silent ? _lodash2['default'].noop : _lodash2['default'].partial(log, _color2['default'].red);
};
