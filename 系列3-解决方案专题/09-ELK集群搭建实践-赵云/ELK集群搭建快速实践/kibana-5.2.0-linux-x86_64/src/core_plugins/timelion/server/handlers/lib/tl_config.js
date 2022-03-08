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

var buildTarget = require('../../lib/build_target.js');

module.exports = function (setup) {
  var targetSeries;

  var tlConfig = {
    getTargetSeries: function getTargetSeries() {
      return _.map(targetSeries, function (bucket) {
        // eslint-disable-line no-use-before-define
        return [bucket, null];
      });
    },
    setTargetSeries: function setTargetSeries() {
      targetSeries = buildTarget(this);
    },
    writeTargetSeries: function writeTargetSeries(series) {
      targetSeries = _.map(series, function (p) {
        return p[0];
      });
    }
  };

  tlConfig = _.extend(tlConfig, setup);
  return tlConfig;
};
