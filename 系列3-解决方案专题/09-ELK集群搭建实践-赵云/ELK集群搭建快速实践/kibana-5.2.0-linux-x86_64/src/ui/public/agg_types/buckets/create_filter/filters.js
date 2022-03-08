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

import buildQueryFilter from 'ui/filter_manager/lib/query';
import _ from 'lodash';
export default function CreateFilterFiltersProvider(Private) {
  return function (aggConfig, key) {
    // have the aggConfig write agg dsl params
    let dslFilters = _.get(aggConfig.toDsl(), 'filters.filters');
    let filter = dslFilters[key];

    if (filter) {
      return buildQueryFilter(filter.query, aggConfig.vis.indexPattern.id);
    }
  };
};
