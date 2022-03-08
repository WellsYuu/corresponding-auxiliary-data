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

var reduce = require('../lib/reduce.js');

var Chainable = require('../lib/classes/chainable');
module.exports = new Chainable('sum', {
  args: [{
    name: 'inputSeries',
    types: ['seriesList']
  }, {
    name: 'term',
    types: ['seriesList', 'number'],
    help: 'Number or series to sum with the input series. If passing a seriesList it must contain exactly 1 series.'

  }],
  help: 'Adds the values of one or more series in a seriesList to each position, in each series, of the input seriesList',
  aliases: ['add', 'plus'],
  fn: function sumFn(args) {
    return reduce(args, function (a, b) {
      return a + b;
    });
  }
});
