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
var argType = require('../handlers/lib/arg_type.js');

module.exports = new Chainable('trim', {
  args: [{
    name: 'inputSeries',
    types: ['seriesList']
  }, {
    name: 'start',
    types: ['number', 'null'],
    help: 'Buckets to trim from the beginning of the series. Default: 1'
  }, {
    name: 'end',
    types: ['number', 'null'],
    help: 'Buckets to trim from the end of the series. Default: 1'
  }],
  help: 'Set N buckets at the start or end of a series to null to fit the "partial bucket issue"',
  fn: function conditionFn(args) {
    var config = args.byName;
    if (config.start == null) config.start = 1;
    if (config.end == null) config.end = 1;

    return alter(args, function (eachSeries) {

      _.times(config.start, function (i) {
        eachSeries.data[i][1] = null;
      });

      _.times(config.end, function (i) {
        eachSeries.data[eachSeries.data.length - 1 - i][1] = null;
      });

      return eachSeries;
    });
  }
});
