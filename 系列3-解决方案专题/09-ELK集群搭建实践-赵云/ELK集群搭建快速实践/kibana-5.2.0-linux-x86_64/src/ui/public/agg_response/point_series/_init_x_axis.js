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

define(function () {
  return function PointSeriesInitX() {
    return function initXAxis(chart) {
      let x = chart.aspects.x;
      chart.xAxisFormatter = x.agg ? x.agg.fieldFormatter() : String;
      chart.xAxisLabel = x.col.title;

      if (!x.agg || !x.agg.type.ordered) return;

      chart.indexPattern = x.agg.vis.indexPattern;
      chart.xAxisField = x.agg.params.field;

      chart.ordered = {};
      let xAggOutput = x.agg.write();
      if (xAggOutput.params.interval) {
        chart.ordered.interval = xAggOutput.params.interval;
      }
    };
  };
});
