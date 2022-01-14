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

module.exports = function createDateAgg(config, tlConfig) {
  var dateAgg = {
    time_buckets: {
      meta: { type: 'time_buckets' },
      date_histogram: {
        field: config.timefield,
        interval: config.interval,
        time_zone: tlConfig.time.timezone,
        extended_bounds: {
          min: tlConfig.time.from,
          max: tlConfig.time.to
        },
        min_doc_count: 0
      }
    }
  };

  dateAgg.time_buckets.aggs = {};
  _.each(config.metric, function (metric, i) {
    metric = metric.split(':');
    if (metric[0] === 'count') {
      // This is pretty lame, but its how the "doc_count" metric has to be implemented at the moment
      // It simplifies the aggregation tree walking code considerably
      dateAgg.time_buckets.aggs[metric] = {
        bucket_script: {
          buckets_path: '_count',
          script: { inline: '_value', lang: 'expression' }
        }
      };
    } else if (metric[0] && metric[1]) {
      var metricName = metric[0] + '(' + metric[1] + ')';
      dateAgg.time_buckets.aggs[metricName] = {};
      dateAgg.time_buckets.aggs[metricName][metric[0]] = { field: metric[1] };
    } else {
      throw new Error('`metric` requires metric:field or simply count');
    }
  });

  return dateAgg;
};
