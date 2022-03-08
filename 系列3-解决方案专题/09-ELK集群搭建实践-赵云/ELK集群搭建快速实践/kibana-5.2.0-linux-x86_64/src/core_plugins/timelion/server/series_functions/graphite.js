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

var _ = require('lodash');
var fetch = require('node-fetch');
var moment = require('moment');
var Datasource = require('../lib/classes/datasource');

module.exports = new Datasource('graphite', {
  args: [{
    name: 'metric', // _test-data.users.*.data
    types: ['string'],
    help: 'Graphite metric to pull, eg _test-data.users.*.data'
  }],
  help: '[experimental] Pull data from graphite. Configure your graphite server in Kibana\'s Advanced Settings',
  fn: function graphite(args, tlConfig) {

    var config = args.byName;

    var time = {
      min: moment(tlConfig.time.from).format('HH:mm[_]YYYYMMDD'),
      max: moment(tlConfig.time.to).format('HH:mm[_]YYYYMMDD')
    };

    var URL = tlConfig.settings['timelion:graphite.url'] + '/render/' + '?format=json' + '&from=' + time.min + '&until=' + time.max + '&target=' + config.metric;

    return fetch(URL).then(function (resp) {
      return resp.json();
    }).then(function (resp) {
      var list = _.map(resp, function (series) {
        var data = _.map(series.datapoints, function (point) {
          return [point[1] * 1000, point[0]];
        });
        return {
          data: data,
          type: 'series',
          fit: 'nearest', // TODO make this customizable
          label: series.target
        };
      });

      return {
        type: 'seriesList',
        list: list
      };
    })['catch'](function (e) {
      throw e;
    });
  }
});
