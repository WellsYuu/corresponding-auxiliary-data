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
export default function filterOutTimeBaseFilter(courier, Promise) {
  return Promise.method(function (filters) {
    let id = _.get(filters, '[0].meta.index');
    if (id == null) return;

    return courier.indexPatterns.get(id).then(function (indexPattern) {
      return _.filter(filters, function (filter) {
        return !(filter.range && filter.range[indexPattern.timeFieldName]);
      });
    });
  });
};
