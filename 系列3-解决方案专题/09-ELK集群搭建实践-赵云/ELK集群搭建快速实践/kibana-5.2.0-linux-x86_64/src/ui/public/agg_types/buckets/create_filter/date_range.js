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

import dateRange from 'ui/utils/date_range';
import buildRangeFilter from 'ui/filter_manager/lib/range';

export default function createDateRangeFilterProvider(config) {

  return function (agg, key) {
    let range = dateRange.parse(key, config.get('dateFormat'));

    let filter = {};
    if (range.from) filter.gte = +range.from;
    if (range.to) filter.lt = +range.to;
    if (range.to && range.from) filter.format = 'epoch_millis';

    return buildRangeFilter(agg.params.field, filter, agg.vis.indexPattern);
  };

};
