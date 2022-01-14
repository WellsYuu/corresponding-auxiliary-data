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

Object.defineProperty(exports, '__esModule', {
  value: true
});
exports.timeBucketsToPairs = timeBucketsToPairs;
exports.flattenBucket = flattenBucket;
exports['default'] = toSeriesList;
var _ = require('lodash');

function timeBucketsToPairs(buckets) {
  var timestamps = _.pluck(buckets, 'key');
  var series = {};
  _.each(buckets, function (bucket) {
    _.forOwn(bucket, function (val, key) {
      if (_.isPlainObject(val)) {
        series[key] = series[key] || [];
        series[key].push(val.value);
      }
    });
  });

  return _.mapValues(series, function (values) {
    return _.zip(timestamps, values);
  });
}

function flattenBucket(bucket, path, result) {
  result = result || {};
  path = path || [];
  _.forOwn(bucket, function (val, key) {
    if (!_.isPlainObject(val)) return;
    if (_.get(val, 'meta.type') === 'split') {
      _.each(val.buckets, function (bucket, bucketKey) {
        if (bucket.key == null) bucket.key = bucketKey; // For handling "keyed" response formats, eg filters agg
        flattenBucket(bucket, path.concat([key + ':' + bucket.key]), result);
      });
    } else if (_.get(val, 'meta.type') === 'time_buckets') {
      var metrics = timeBucketsToPairs(val.buckets);
      _.each(metrics, function (pairs, metricName) {
        result[path.concat([metricName]).join(' > ')] = pairs;
      });
    }
  });
  return result;
}

function toSeriesList(aggs, config) {
  return _.map(flattenBucket(aggs), function (values, name) {
    return {
      data: values,
      type: 'series',
      fit: config.fit,
      label: name
    };
  });
}

;
