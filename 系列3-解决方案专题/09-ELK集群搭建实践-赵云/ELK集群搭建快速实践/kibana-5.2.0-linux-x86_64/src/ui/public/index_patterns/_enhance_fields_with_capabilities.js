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

import chrome from 'ui/chrome';
import _ from 'lodash';

export default function ($http) {

  return function (fields, indices) {
    return $http.get(chrome.addBasePath(`/api/kibana/${indices}/field_capabilities`))
    .then((res) => {
      const stats = _.get(res, 'data.fields', {});

      return _.map(fields, (field) => {
        if (field.type === 'geo_point' && !stats[field.name]) {
          // FIXME: remove once https://github.com/elastic/elasticsearch/issues/20707 is fixed
          return _.assign(field, {
            'searchable': true,
            'aggregatable': true
          });
        }

        return _.assign(field, stats[field.name]);
      });
    });
  };
}
