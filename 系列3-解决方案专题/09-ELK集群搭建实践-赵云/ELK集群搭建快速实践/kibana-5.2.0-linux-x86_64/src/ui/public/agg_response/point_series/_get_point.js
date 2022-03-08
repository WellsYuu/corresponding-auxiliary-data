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

import _ from 'lodash';
export default function PointSeriesGetPoint() {
  function unwrap(aggConfigResult, def) {
    return aggConfigResult ? aggConfigResult.value : def;
  }

  return function getPoint(x, series, yScale, row, y, z) {
    let zRow = z && row[z.i];
    let xRow = row[x.i];

    let point = {
      x: unwrap(xRow, '_all'),
      xi: xRow && xRow.$order,
      y: unwrap(row[y.i]),
      z: zRow && unwrap(zRow),
      aggConfigResult: row[y.i],
      extraMetrics: _.compact([zRow]),
      yScale: yScale
    };

    if (point.y === 'NaN') {
      // filter out NaN from stats
      // from metrics that are not based at zero
      return;
    }

    if (series) {
      point.aggConfig = series.agg;
      point.series = series.agg.fieldFormatter()(unwrap(row[series.i]));
    } else if (y) {
      // If the data is not split up with a series aspect, then
      // each point's "series" becomes the y-agg that produced it
      point.aggConfig = y.col.aggConfig;
      point.series = y.col.title;
    }

    if (yScale) {
      point.y *= yScale;
    }

    return point;
  };
};
