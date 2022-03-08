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
module.exports = new Chainable('title', {
  args: [{
    name: 'inputSeries',
    types: ['seriesList']
  }, {
    name: 'title',
    types: ['string', 'null'],
    help: 'Title for the plot.'
  }],
  help: 'Adds a title to the top of the plot. If called on more than 1 seriesList the last call will be used.',
  fn: function hideFn(args) {
    return alter(args, function (eachSeries, title) {
      eachSeries._title = title;
      return eachSeries;
    });
  }
});
