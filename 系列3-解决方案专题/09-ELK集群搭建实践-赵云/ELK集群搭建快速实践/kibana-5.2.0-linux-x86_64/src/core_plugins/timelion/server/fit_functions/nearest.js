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
var moment = require('moment');

// Upsampling and downsampling of non-cummulative sets
// Good: average, min, max
// Bad: sum, count
module.exports = function (dataTuples, targetTuples) {
  return _.map(targetTuples, function (bucket, h) {
    var time = bucket[0];
    var i = 0;
    while (i < dataTuples.length - 1 && (Math.abs(dataTuples[i + 1][0] - time) < Math.abs(dataTuples[i][0] - time) ||
    // TODO: Certain offset= args can cause buckets with duplicate times, eg, offset=-1M
    // check for that, and only use the last of the duplicates. The reason this happens?
    // What is 1M before Mar 30th? What about 1M before Mar 31st? Both are the last day
    // in Feb. Something has to be chucked. If offsetting by M user might want to use
    // fit=average
    Math.abs(dataTuples[i + 1][0] - time) === Math.abs(dataTuples[i][0] - time))) {
      i++;
    }

    var closest = dataTuples[i];
    dataTuples.splice(0, i);

    return [bucket[0], closest[1]];
  });
};
