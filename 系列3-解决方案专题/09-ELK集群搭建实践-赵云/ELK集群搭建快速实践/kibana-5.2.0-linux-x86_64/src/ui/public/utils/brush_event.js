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
import moment from 'moment';
import buildRangeFilter from 'ui/filter_manager/lib/range';
export default function brushEventProvider(timefilter) {
  return $state => {
    return event => {
      if (!event.data.xAxisField) {
        return;
      }

      switch (event.data.xAxisField.type) {
        case 'date':
          let from = moment(event.range[0]);
          let to = moment(event.range[1]);

          if (to - from === 0) return;

          timefilter.time.from = from;
          timefilter.time.to = to;
          timefilter.time.mode = 'absolute';
          break;

        case 'number':
          if (event.range.length <= 1) return;

          const existingFilter = $state.filters.find(filter => (
            filter.meta && filter.meta.key === event.data.xAxisField.name
          ));

          const min = event.range[0];
          const max = event.range[event.range.length - 1];
          const range = {gte: min, lt: max};
          if (_.has(existingFilter, 'range')) {
            existingFilter.range[event.data.xAxisField.name] = range;
          } else if (_.has(existingFilter, 'script.script.params.gte')
            && _.has(existingFilter, 'script.script.params.lt')) {
            existingFilter.script.script.params.gte = min;
            existingFilter.script.script.params.lt = max;
          } else {
            const newFilter = buildRangeFilter(
              event.data.xAxisField,
              range,
              event.data.indexPattern,
              event.data.xAxisFormatter);
            $state.$newFilters = [newFilter];
          }
          break;
      }
    };
  };
};
