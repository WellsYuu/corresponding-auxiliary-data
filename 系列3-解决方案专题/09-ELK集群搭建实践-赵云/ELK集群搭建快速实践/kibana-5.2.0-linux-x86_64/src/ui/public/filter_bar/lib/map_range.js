/*
 * Copyright 2021-2022 the original author or authors.
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

import {has} from 'lodash';
export default function mapRangeProvider(Promise, courier) {
  return function (filter) {
    if (!filter.range) return Promise.reject(filter);

    return courier
    .indexPatterns
    .get(filter.meta.index)
    .then(function (indexPattern) {
      let key = Object.keys(filter.range)[0];
      let convert = indexPattern.fields.byName[key].format.getConverterFor('text');
      let range = filter.range[key];

      let left = has(range, 'gte') ? range.gte : range.gt;
      if (left == null) left = -Infinity;

      let right = has(range, 'lte') ? range.lte : range.lt;
      if (right == null) right = Infinity;

      return {
        key: key,
        value: `${convert(left)} to ${convert(right)}`
      };
    });

  };
};
