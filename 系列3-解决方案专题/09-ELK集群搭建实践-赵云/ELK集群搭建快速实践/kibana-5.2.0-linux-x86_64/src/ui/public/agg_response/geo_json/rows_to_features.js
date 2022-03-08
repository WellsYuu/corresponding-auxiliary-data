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

import decodeGeoHash from 'ui/utils/decode_geo_hash';
import AggConfigResult from 'ui/vis/agg_config_result';
import _ from 'lodash';

function getAcr(val) {
  return val instanceof AggConfigResult ? val : null;
}

function unwrap(val) {
  return getAcr(val) ? val.value : val;
}

function convertRowsToFeatures(table, geoI, metricI) {
  return _.transform(table.rows, function (features, row) {
    let geohash = unwrap(row[geoI]);
    if (!geohash) return;

    // fetch latLn of northwest and southeast corners, and center point
    let location = decodeGeoHash(geohash);

    let centerLatLng = [
      location.latitude[2],
      location.longitude[2]
    ];

    // order is nw, ne, se, sw
    let rectangle = [
      [location.latitude[0], location.longitude[0]],
      [location.latitude[0], location.longitude[1]],
      [location.latitude[1], location.longitude[1]],
      [location.latitude[1], location.longitude[0]],
    ];

    // geoJson coords use LngLat, so we reverse the centerLatLng
    // See here for details: http://geojson.org/geojson-spec.html#positions
    features.push({
      type: 'Feature',
      geometry: {
        type: 'Point',
        coordinates: centerLatLng.slice(0).reverse()
      },
      properties: {
        geohash: geohash,
        value: unwrap(row[metricI]),
        aggConfigResult: getAcr(row[metricI]),
        center: centerLatLng,
        rectangle: rectangle
      }
    });
  }, []);
}

export default convertRowsToFeatures;
