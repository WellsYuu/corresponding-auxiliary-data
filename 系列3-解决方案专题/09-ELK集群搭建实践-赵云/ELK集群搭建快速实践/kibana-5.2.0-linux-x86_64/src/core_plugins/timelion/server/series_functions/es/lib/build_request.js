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
var createDateAgg = require('./create_date_agg');

module.exports = function buildRequest(config, tlConfig) {

  var bool = { must: [], must_not: [] };

  var timeFilter = { range: {} };
  timeFilter.range[config.timefield] = { gte: tlConfig.time.from, lte: tlConfig.time.to, format: 'epoch_millis' };
  bool.must.push(timeFilter);

  // Use the kibana filter bar filters
  if (config.kibana) {
    bool.filter = _.get(tlConfig, 'request.payload.extended.es.filter') || {};
  }

  var aggs = {
    'q': {
      meta: { type: 'split' },
      filters: {
        filters: _.chain(config.q).map(function (q) {
          return [q, { query_string: { query: q } }];
        }).zipObject().value()
      },
      aggs: {}
    }
  };

  var aggCursor = aggs.q.aggs;

  _.each(config.split, function (clause, i) {
    clause = clause.split(':');
    if (clause[0] && clause[1]) {
      aggCursor[clause[0]] = {
        meta: { type: 'split' },
        terms: {
          field: clause[0],
          size: parseInt(clause[1], 10)
        },
        aggs: {}
      };
      aggCursor = aggCursor[clause[0]].aggs;
    } else {
      throw new Error('`split` requires field:limit');
    }
  });

  _.assign(aggCursor, createDateAgg(config, tlConfig));

  return {
    index: config.index,
    body: {
      query: {
        bool: bool
      },
      aggs: aggs,
      size: 0
    }
  };
};
