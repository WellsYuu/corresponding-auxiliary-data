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

export default function () {
  return function ($state) {
    if (!_.isObject($state)) throw new Error('pushFilters requires a state object');
    return function (filter, negate, index) {
      // Hierarchical and tabular data set their aggConfigResult parameter
      // differently because of how the point is rewritten between the two. So
      // we need to check if the point.orig is set, if not use try the point.aggConfigResult
      let filters = _.clone($state.filters || []);
      let pendingFilter = { meta: { negate: negate, index: index }};
      _.extend(pendingFilter, filter);
      filters.push(pendingFilter);
      $state.filters = filters;
    };
  };
};
