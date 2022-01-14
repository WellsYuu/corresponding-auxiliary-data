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

var _ = require('lodash');
var fetch = require('node-fetch');
var moment = require('moment');
var Datasource = require('../lib/classes/datasource');
var Promise = require('bluebird');

module.exports = new Datasource('static', {
  aliases: ['value'],
  args: [{
    name: 'value', // _test-data.users.*.data
    types: ['number', 'string'],
    help: 'The single value to to display, you can also pass several values and I will interpolate them evenly ' + 'across your time range.'
  }, {
    name: 'label',
    types: ['string', 'null'],
    help: 'A quick way to set the label for the series. You could also use the .label() function'
  }],
  help: 'Draws a single value across the chart',
  fn: function staticFn(args, tlConfig) {

    var data;
    var target = tlConfig.getTargetSeries();
    if (typeof args.byName.value === 'string') {
      var points = args.byName.value.split(':');
      var begin = _.first(target)[0];
      var end = _.last(target)[0];
      var step = (end - begin) / (points.length - 1);
      data = _.map(points, function (point, i) {
        return [begin + i * step, parseFloat(point)];
      });
    } else {
      data = _.map(target, function (bucket) {
        return [bucket[0], args.byName.value];
      });
    }

    return Promise.resolve({
      type: 'seriesList',
      list: [{
        data: data,
        type: 'series',
        label: args.byName.label == null ? String(args.byName.value) : args.byName.label,
        fit: args.byName.fit || 'average'
      }]
    });
  }
});
