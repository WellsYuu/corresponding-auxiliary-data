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

var alter = require('../lib/alter.js');
var Chainable = require('../lib/classes/chainable');
module.exports = new Chainable('bars', {
  args: [{
    name: 'inputSeries',
    types: ['seriesList']
  }, {
    name: 'width',
    types: ['number', 'null'],
    help: 'Width of bars in pixels'
  }, {
    name: 'stack',
    types: ['boolean', 'null'],
    help: 'Should bars be stacked, true by default'
  }],
  help: 'Show the seriesList as bars',
  fn: function barsFn(args) {
    return alter(args, function (eachSeries, width, stack) {
      eachSeries.bars = eachSeries.bars || {};
      eachSeries.bars.show = width == null ? 1 : width;
      eachSeries.bars.lineWidth = width == null ? 6 : width;
      eachSeries.stack = stack == null ? true : stack;
      return eachSeries;
    });
  }
});
