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
module.exports = new Chainable('legend', {
  args: [{
    name: 'inputSeries',
    types: ['seriesList']
  }, {
    name: 'position',
    types: ['string', 'boolean', 'null'],
    help: 'Corner to place the legend in: nw, ne, se, or sw. You can also pass false to disable the legend'
  }, {
    name: 'columns',
    types: ['number', 'null'],
    help: 'Number of columns to divide the legend into'
  }],
  help: 'Set the position and style of the legend on the plot',
  fn: function legendFn(args) {
    return alter(args, function (eachSeries, position, columns) {
      eachSeries._global = eachSeries._global || {};
      eachSeries._global.legend = eachSeries._global.legend || {};
      eachSeries._global.legend.noColumns = columns;

      if (position === false) {
        eachSeries._global.legend.show = false;
      } else {
        eachSeries._global.legend.position = position;
      }

      return eachSeries;
    });
  }
});
