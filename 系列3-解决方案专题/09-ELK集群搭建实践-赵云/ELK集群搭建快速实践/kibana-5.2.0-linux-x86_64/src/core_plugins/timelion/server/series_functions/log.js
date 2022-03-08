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
module.exports = new Chainable('log', {
  args: [{
    name: 'inputSeries',
    types: ['seriesList']
  }, {
    name: 'base',
    types: ['number'],
    help: 'Set logarithmic base, 10 by default'

  }],
  help: 'Return the logarithm value of each value in the series list (default base: 10)',
  fn: function logFn(args) {
    var config = args.byName;
    return alter(args, function (eachSeries) {
      var data = _.map(eachSeries.data, function (point) {
        return [point[0], Math.log(point[1]) / Math.log(config.base || 10)];
      });
      eachSeries.data = data;
      return eachSeries;
    });
  }
});
