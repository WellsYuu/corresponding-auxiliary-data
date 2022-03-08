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
import angular from 'angular';
import compareFilters from 'ui/filter_bar/lib/compare_filters';

/**
 * Combine 2 filter collections, removing duplicates
 * @param {object} existing The filters to compare to
 * @param {object} filters The filters being added
 * @param {object} comparatorOptions Parameters to use for comparison
 * @returns {object} An array of filters that were not in existing
 */
export default function (existingFilters, filters, comparatorOptions) {
  if (!_.isArray(filters)) filters = [filters];

  return _.filter(filters, function (filter) {
    return !_.find(existingFilters, function (existingFilter) {
      return compareFilters(existingFilter, filter, comparatorOptions);
    });
  });
};
