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

/**
 * Filters out a list by a given filter. This is currently used to impelment:
 *   - fieldType filters a list of fields by their type property
 *   - aggFilter filters a list of aggs by their name property
 *
 * @returns {function} - the filter function which can be registered with angular
 */
function propFilter(prop) {
  /**
   * List filtering function which accepts an array or list of values that a property
   * must contain
   *
   * @param  {array} list - array of items to filter
   * @param  {array|string} filters - the values to match against the list. Can be
   *                                an array, a single value as a string, or a comma
   *                                -seperated list of items
   * @return {array} - the filtered list
   */
  return function (list, filters) {
    if (!filters) return filters;
    if (!_.isArray(filters)) filters = filters.split(',');
    if (_.contains(filters, '*')) return list;

    let options = filters.reduce(function (options, filter) {
      let type = 'include';
      let value = filter;

      if (filter.charAt(0) === '!') {
        type = 'exclude';
        value = filter.substr(1);
      }

      if (!options[type]) options[type] = [];
      options[type].push(value);
      return options;
    }, {});

    return list.filter(function (item) {
      let value = item[prop];

      let excluded = options.exclude && _.contains(options.exclude, value);
      if (excluded) return false;

      let included = !options.include || _.contains(options.include, value);
      if (included) return true;

      return false;
    });
  };
}

export default propFilter;
