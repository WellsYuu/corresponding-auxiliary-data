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

var _lodash = require('lodash');

var _lodash2 = _interopRequireDefault(_lodash);

function Samples(max) {
  this.vals = {};
  this.max = max || Infinity;
  this.length = 0;
}

Samples.prototype.add = function (sample) {
  var vals = this.vals;
  var length = this.length = Math.min(this.length + 1, this.max);

  _lodash2['default'].forOwn(sample, function (val, name) {
    if (val == null) val = null;

    if (!vals[name]) vals[name] = new Array(length);
    vals[name].unshift([Date.now(), val]);
    vals[name].length = length;
  });
};

Samples.prototype.toJSON = function () {
  return this.vals;
};

module.exports = Samples;
