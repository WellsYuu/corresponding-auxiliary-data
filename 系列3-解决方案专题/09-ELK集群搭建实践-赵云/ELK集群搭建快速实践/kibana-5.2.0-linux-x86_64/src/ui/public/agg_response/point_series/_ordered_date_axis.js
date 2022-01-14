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

import moment from 'moment';
export default function PointSeriesOrderedDateAxis(timefilter) {

  return function orderedDateAxis(vis, chart) {
    let xAgg = chart.aspects.x.agg;
    let buckets = xAgg.buckets;
    let format = buckets.getScaledDateFormat();

    chart.xAxisFormatter = function (val) {
      return moment(val).format(format);
    };

    chart.ordered = {
      date: true,
      interval: buckets.getInterval(),
    };

    let axisOnTimeField = xAgg.fieldIsTimeField();
    let bounds = buckets.getBounds();
    if (bounds && axisOnTimeField) {
      chart.ordered.min = bounds.min;
      chart.ordered.max = bounds.max;
    } else {
      chart.ordered.endzones = false;
    }
  };
};
