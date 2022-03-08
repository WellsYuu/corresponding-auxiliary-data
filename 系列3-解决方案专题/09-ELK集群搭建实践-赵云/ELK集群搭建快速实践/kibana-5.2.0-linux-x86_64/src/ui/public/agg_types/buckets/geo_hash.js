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
import AggTypesBucketsBucketAggTypeProvider from 'ui/agg_types/buckets/_bucket_agg_type';
import precisionTemplate from 'ui/agg_types/controls/precision.html';
import {geohashColumns} from 'ui/utils/decode_geo_hash';

export default function GeoHashAggDefinition(Private, config) {
  const BucketAggType = Private(AggTypesBucketsBucketAggTypeProvider);
  const defaultPrecision = 2;
  const maxPrecision = parseInt(config.get('visualization:tileMap:maxPrecision'), 10) || 12;
  /**
   * Map Leaflet zoom levels to geohash precision levels.
   * The size of a geohash column-width on the map should be at least `minGeohashPixels` pixels wide.
   */
  let zoomPrecision = {};
  const minGeohashPixels = 16;
  for (let zoom = 0; zoom <= 21; zoom += 1) {
    const worldPixels = 256 * Math.pow(2, zoom);
    zoomPrecision[zoom] = 1;
    for (let precision = 2; precision <= maxPrecision; precision += 1) {
      const columns = geohashColumns(precision);
      if ((worldPixels / columns) >= minGeohashPixels) {
        zoomPrecision[zoom] = precision;
      } else {
        break;
      }
    }
  }

  function getPrecision(precision) {

    precision = parseInt(precision, 10);

    if (isNaN(precision)) {
      precision = defaultPrecision;
    }

    if (precision > maxPrecision) {
      return maxPrecision;
    }

    return precision;
  }

  return new BucketAggType({
    name: 'geohash_grid',
    title: 'Geohash',
    params: [
      {
        name: 'field',
        filterFieldTypes: 'geo_point'
      },
      {
        name: 'autoPrecision',
        default: true,
        write: _.noop
      },
      {
        name: 'mapZoom',
        write: _.noop
      },
      {
        name: 'mapCenter',
        write: _.noop
      },
      {
        name: 'precision',
        editor: precisionTemplate,
        deserialize: getPrecision,
        controller: function ($scope) {
        },
        write: function (aggConfig, output) {
          const vis = aggConfig.vis;
          let currZoom;
          if (vis.hasUiState()) {
            currZoom = parseInt(vis.uiStateVal('mapZoom'), 10);
          }
          const autoPrecisionVal = zoomPrecision[currZoom >= 0 ? currZoom : parseInt(vis.params.mapZoom)];
          output.params.precision = aggConfig.params.autoPrecision ? autoPrecisionVal : getPrecision(aggConfig.params.precision);
        }
      }
    ]
  });
};
