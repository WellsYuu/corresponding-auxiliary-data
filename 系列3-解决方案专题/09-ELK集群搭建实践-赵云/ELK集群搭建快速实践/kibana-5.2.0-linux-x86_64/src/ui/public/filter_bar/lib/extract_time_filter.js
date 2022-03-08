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
export default function extractTimeFilterProvider(courier, Promise) {
  return Promise.method(function (filters) {
    // Assume all the index patterns are the same since they will be added
    // from the same visualization.
    let id = _.get(filters, '[0].meta.index');
    if (id == null) return;

    return courier.indexPatterns.get(id).then(function (indexPattern) {
      let filter = _.find(filters, function (obj) {
        let key = _.keys(obj.range)[0];
        return key === indexPattern.timeFieldName;
      });
      if (filter && filter.range) {
        return filter;
      }
    });
  });
};
