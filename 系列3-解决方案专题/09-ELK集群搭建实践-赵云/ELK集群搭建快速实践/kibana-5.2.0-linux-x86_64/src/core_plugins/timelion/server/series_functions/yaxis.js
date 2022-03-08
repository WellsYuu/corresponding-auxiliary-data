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
module.exports = new Chainable('yaxis', {
  args: [{
    name: 'inputSeries',
    types: ['seriesList']
  }, {
    name: 'yaxis',
    types: ['number', 'null'],
    help: 'The numbered y-axis to plot this series on, eg .yaxis(2) for a 2nd y-axis.'
  }, {
    name: 'min',
    types: ['number', 'null'],
    help: 'Min value'
  }, {
    name: 'max',
    types: ['number', 'null'],
    help: 'Max value'
  }, {
    name: 'position',
    types: ['string', 'null'],
    help: 'left or right'
  }, {
    name: 'label',
    types: ['string', 'null'],
    help: 'Label for axis'
  }, {
    name: 'color',
    types: ['string', 'null'],
    help: 'Color of axis label'
  }],
  help: 'Configures a variety of y-axis options, the most important likely being the ability to add an Nth (eg 2nd) y-axis',
  fn: function yaxisFn(args) {
    return alter(args, function (eachSeries, yaxis, min, max, position, label, color) {
      yaxis = yaxis || 1;

      eachSeries.yaxis = yaxis;
      eachSeries._global = eachSeries._global || {};

      eachSeries._global.yaxes = eachSeries._global.yaxes || [];
      eachSeries._global.yaxes[yaxis - 1] = eachSeries._global.yaxes[yaxis - 1] || {};

      var myAxis = eachSeries._global.yaxes[yaxis - 1];
      myAxis.position = position || (yaxis % 2 ? 'left' : 'right');
      myAxis.min = min;
      myAxis.max = max;
      myAxis.axisLabelFontSizePixels = 11;
      myAxis.axisLabel = label;
      myAxis.axisLabelColour = color;
      myAxis.axisLabelUseCanvas = true;

      return eachSeries;
    });
  }
});
