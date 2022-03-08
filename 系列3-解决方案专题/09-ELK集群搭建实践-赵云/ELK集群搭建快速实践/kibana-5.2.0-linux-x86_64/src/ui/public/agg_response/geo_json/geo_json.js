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
import rowsToFeatures from 'ui/agg_response/geo_json/rows_to_features';
import AggResponseGeoJsonTooltipFormatterProvider from 'ui/agg_response/geo_json/_tooltip_formatter';
export default function TileMapConverterFn(Private, timefilter, $compile, $rootScope) {

  let tooltipFormatter = Private(AggResponseGeoJsonTooltipFormatterProvider);

  return function (vis, table) {

    function columnIndex(schema) {
      return _.findIndex(table.columns, function (col) {
        return col.aggConfig.schema.name === schema;
      });
    }

    let geoI = columnIndex('segment');
    let metricI = columnIndex('metric');
    let geoAgg = _.get(table.columns, [geoI, 'aggConfig']);
    let metricAgg = _.get(table.columns, [metricI, 'aggConfig']);

    let features = rowsToFeatures(table, geoI, metricI);
    let values = features.map(function (feature) {
      return feature.properties.value;
    });

    return {
      title: table.title(),
      valueFormatter: metricAgg && metricAgg.fieldFormatter(),
      tooltipFormatter: tooltipFormatter,
      geohashGridAgg: geoAgg,
      geoJson: {
        type: 'FeatureCollection',
        features: features,
        properties: {
          min: _.min(values),
          max: _.max(values),
          zoom: geoAgg && geoAgg.vis.uiStateVal('mapZoom'),
          center: geoAgg && geoAgg.vis.uiStateVal('mapCenter')
        }
      }
    };
  };
};
