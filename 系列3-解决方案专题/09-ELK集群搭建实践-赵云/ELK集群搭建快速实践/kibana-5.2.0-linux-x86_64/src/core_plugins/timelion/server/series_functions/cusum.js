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

var alter = require('../lib/alter.js');
var _ = require('lodash');
var Chainable = require('../lib/classes/chainable');
module.exports = new Chainable('cusum', {
  args: [{
    name: 'inputSeries',
    types: ['seriesList']
  }, {
    name: 'base',
    types: ['number'],
    help: 'Number to start at. Basically just adds this to the beginning of the series'
  }],
  help: 'Return the cumulative sum of a series, starting at a base.',
  fn: function cusumFn(args) {
    return alter(args, function (eachSeries, base) {
      var pairs = eachSeries.data;
      var total = base || 0;
      eachSeries.data = _.map(pairs, function (point, i) {
        total += point[1];
        return [point[0], total];
      });

      return eachSeries;
    });
  }
});
