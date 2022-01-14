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
var util = require('util');

var Chainable = require('../lib/classes/chainable');
module.exports = new Chainable('label', {
  args: [{
    name: 'inputSeries',
    types: ['seriesList']
  }, {
    name: 'label',
    types: ['string'],
    help: 'Legend value for series. You can use $1, $2, etc, in the string to match up with the regex capture groups'
  }, {
    name: 'regex',
    types: ['string', 'null'],
    help: 'A regex with capture group support'
  }],
  help: 'Change the label of the series. Use %s reference the existing label',
  fn: function labelFn(args) {
    var config = args.byName;
    return alter(args, function (eachSeries) {
      if (config.regex) {
        eachSeries.label = eachSeries.label.replace(new RegExp(config.regex), config.label);
      } else {
        eachSeries.label = config.label;
      }

      return eachSeries;
    });
  }
});
