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
export default function mapTermsProvider(Promise, courier) {
  return function (filter) {
    let key;
    let value;
    let field;
    if (filter.query && filter.query.match) {
      return courier
      .indexPatterns
      .get(filter.meta.index).then(function (indexPattern) {
        key = _.keys(filter.query.match)[0];
        field = indexPattern.fields.byName[key];
        value = filter.query.match[key].query;
        value = field.format.convert(value);
        return { key: key, value: value };
      });
    }
    return Promise.reject(filter);
  };
};
