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
var _ = require('lodash');
var Chainable = require('../lib/classes/chainable');
module.exports = new Chainable('movingstd', {
  args: [{
    name: 'inputSeries',
    types: ['seriesList']
  }, {
    name: 'window',
    types: ['number'],
    help: 'Number of points to compute the standard deviation over'
  }],
  aliases: ['mvstd'],
  help: 'Calculate the moving standard deviation over a given window. Uses naive two-pass algorithm. Rounding errors ' + 'may become more noticeable with very long series, or series with very large numbers.',
  fn: function movingstdFn(args) {
    return alter(args, function (eachSeries, _window) {

      var pairs = eachSeries.data;

      eachSeries.data = _.map(pairs, function (point, i) {
        if (i < _window) {
          return [point[0], null];
        }

        var average = _.chain(pairs.slice(i - _window, i)).map(function (point) {
          return point[1];
        }).reduce(function (memo, num) {
          return memo + num;
        }).value() / _window;

        var variance = _.chain(pairs.slice(i - _window, i)).map(function (point) {
          return point[1];
        }).reduce(function (memo, num) {
          return memo + Math.pow(num - average, 2);
        }).value() / (_window - 1);

        return [point[0], Math.sqrt(variance)];
      });
      return eachSeries;
    });
  }
});
