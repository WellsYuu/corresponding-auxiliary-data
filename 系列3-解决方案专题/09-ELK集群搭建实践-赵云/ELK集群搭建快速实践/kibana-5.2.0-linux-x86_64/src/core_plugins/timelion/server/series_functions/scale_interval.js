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
var toMS = require('../lib/to_milliseconds.js');

var _ = require('lodash');
var Chainable = require('../lib/classes/chainable');
module.exports = new Chainable('scale_interval', {
  args: [{
    name: 'inputSeries',
    types: ['seriesList']
  }, {
    name: 'interval',
    types: ['string'],
    help: 'The new interval in date math notation, eg 1s for 1 second. 1m, 5m, 1M, 1w, 1y, etc.'
  }],
  help: 'Changes scales a value (usually a sum or a count) to a new interval. For example, as a per-second rate',
  fn: function scaleIntervalFn(args, tlConfig) {
    var currentInterval = toMS(tlConfig.time.interval);
    var scaleInterval = toMS(args.byName.interval);

    return alter(args, function (eachSeries) {
      var data = _.map(eachSeries.data, function (point) {
        return [point[0], point[1] / currentInterval * scaleInterval];
      });
      eachSeries.data = data;
      return eachSeries;
    });
  }
});
