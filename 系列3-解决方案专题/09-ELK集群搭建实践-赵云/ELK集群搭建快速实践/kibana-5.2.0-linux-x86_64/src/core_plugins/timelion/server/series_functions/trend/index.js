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

function _interopRequireWildcard(obj) { if (obj && obj.__esModule) { return obj; } else { var newObj = {}; if (obj != null) { for (var key in obj) { if (Object.prototype.hasOwnProperty.call(obj, key)) newObj[key] = obj[key]; } } newObj['default'] = obj; return newObj; } }

var _libRegress = require('./lib/regress');

var regress = _interopRequireWildcard(_libRegress);

var _ = require('lodash');
var Chainable = require('../../lib/classes/chainable');

var validRegressions = {
  linear: 'linear',
  log: 'logarithmic'
};

module.exports = new Chainable('trend', {
  args: [{
    name: 'inputSeries',
    types: ['seriesList']
  }, {
    name: 'mode',
    types: ['string'],
    help: 'The algorithm to use for generating the trend line. One of: ' + _.keys(validRegressions).join(', ')
  }, {
    name: 'start',
    types: ['number', 'null'],
    help: 'Where to start calculating from the beginning or end. For example -10 would start calculating 10 points from' + ' the end, +15 would start 15 points from the beginning. Default: 0'
  }, {
    name: 'end',
    types: ['number', 'null'],
    help: 'Where to stop calculating from the beginning or end. For example -10 would stop calculating 10 points from' + ' the end, +15 would stop 15 points from the beginning. Default: 0'
  }],
  help: 'Draws a trend line using a specified regression algorithm',
  fn: function absFn(args) {
    var newSeries = _.cloneDeep(args.byName.inputSeries);

    _.each(newSeries.list, function (series) {
      var length = series.data.length;
      var start = args.byName.start == null ? 0 : args.byName.start;
      var end = args.byName.end == null ? length : args.byName.end;
      start = start >= 0 ? start : length + start;
      end = end > 0 ? end : length + end;

      var subset = series.data.slice(start, end);

      var result = regress[args.byName.mode || 'linear'](subset);

      _.each(series.data, function (point) {
        point[1] = null;
      });

      _.each(result, function (point, i) {
        series.data[start + i] = point;
      });
    });
    return newSeries;
  }
});
