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
var Chainable = require('../lib/classes/chainable');
var tinygradient = require('tinygradient');

module.exports = new Chainable('color', {
  args: [{
    name: 'inputSeries',
    types: ['seriesList']
  }, {
    name: 'color',
    types: ['string'],
    help: 'Color of series, as hex, eg #c6c6c6 is a lovely light grey. If you specify multiple colors, and have ' + 'multiple series, you will get a gradient, eg "#00B1CC:#00FF94:#FF3A39:#CC1A6F"'
  }],
  help: 'Change the color of the series',
  fn: function colorFn(args) {
    var colors = args.byName.color.split(':');

    if (colors.length > 1 && args.byName.inputSeries.list.length > 1) {
      colors = tinygradient(colors).rgb(args.byName.inputSeries.list.length);
    }

    var i = 0;
    return alter(args, function (eachSeries) {
      if (colors.length === 1 || args.byName.inputSeries.list.length === 1) {
        eachSeries.color = colors[0];
      } else if (colors.length > 1) {
        eachSeries.color = colors[i].toHexString();
        i++;
      } else {
        throw new Error('Hey, I need at least one color to work with');
      }

      return eachSeries;
    });
  }
});
