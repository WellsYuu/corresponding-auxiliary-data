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

import L from 'leaflet';
import VislibVisualizationsMarkerTypesBaseMarkerProvider from './base_marker';
export default function GeohashGridMarkerFactory(Private) {

  const BaseMarker = Private(VislibVisualizationsMarkerTypesBaseMarkerProvider);

  /**
   * Map overlay: rectangles that show the geohash grid bounds
   *
   * @param map {Leaflet Object}
   * @param geoJson {geoJson Object}
   * @param params {Object}
   */
  class GeohashGridMarker extends BaseMarker {
    constructor(map, geoJson, params) {
      super(map, geoJson, params);

      this._createMarkerGroup({
        pointToLayer: function (feature, latlng) {
          const geohashRect = feature.properties.rectangle;
          // get bounds from northEast[3] and southWest[1]
          // corners in geohash rectangle
          const corners = [
            [geohashRect[3][0], geohashRect[3][1]],
            [geohashRect[1][0], geohashRect[1][1]]
          ];
          return L.rectangle(corners);
        }
      });
    }
  }

  return GeohashGridMarker;
};
