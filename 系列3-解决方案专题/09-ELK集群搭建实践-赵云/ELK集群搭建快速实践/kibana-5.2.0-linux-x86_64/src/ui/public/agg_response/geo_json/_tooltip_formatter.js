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

import $ from 'jquery';
import _ from 'lodash';
import RegistryFieldFormatsProvider from 'ui/registry/field_formats';
export default function TileMapTooltipFormatter($compile, $rootScope, Private) {

  let fieldFormats = Private(RegistryFieldFormatsProvider);
  let $tooltipScope = $rootScope.$new();
  let $el = $('<div>').html(require('ui/agg_response/geo_json/_tooltip.html'));
  $compile($el)($tooltipScope);

  return function tooltipFormatter(feature) {
    if (!feature) return '';

    let value = feature.properties.value;
    let acr = feature.properties.aggConfigResult;
    let vis = acr.aggConfig.vis;

    let metricAgg = acr.aggConfig;
    let geoFormat = _.get(vis.aggs, 'byTypeName.geohash_grid[0].format');
    if (!geoFormat) geoFormat = fieldFormats.getDefaultInstance('geo_point');

    $tooltipScope.details = [
      {
        label: metricAgg.makeLabel(),
        value: metricAgg.fieldFormatter()(value)
      },
      {
        label: 'Center',
        value: geoFormat.convert({
          lat: feature.geometry.coordinates[1],
          lon: feature.geometry.coordinates[0]
        })
      }
    ];

    $tooltipScope.$apply();

    return $el.html();
  };
};
