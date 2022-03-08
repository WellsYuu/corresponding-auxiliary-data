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

define(function () {
  return function mapScriptProvider(Promise, courier) {
    return function (filter) {
      let key;
      let value;
      let field;
      if (filter.script) {
        return courier
        .indexPatterns
        .get(filter.meta.index).then(function (indexPattern) {
          key = filter.meta.field;
          field = indexPattern.fields.byName[key];

          if (filter.meta.formattedValue) {
            value = filter.meta.formattedValue;
          } else {
            value = filter.script.script.params.value;
            value = field.format.convert(value);
          }

          return { key: key, value: value };
        });
      }
      return Promise.reject(filter);
    };
  };
});
