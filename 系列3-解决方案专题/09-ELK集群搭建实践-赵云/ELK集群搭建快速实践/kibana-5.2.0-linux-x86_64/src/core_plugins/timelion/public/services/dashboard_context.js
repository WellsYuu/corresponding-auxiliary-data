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

var _ = require('lodash');
require('ui/state_management/app_state');


module.exports = function dashboardContext(Private, getAppState) {
  return function () {
    var queryFilter = Private(require('ui/filter_bar/query_filter'));
    var bool = {must: [], must_not: []};
    var filterBarFilters = queryFilter.getFilters();
    var queryBarFilter = getAppState().query;

    // Add the query bar filter, its handled differently.
    bool.must.push(queryBarFilter);

    // Add each of the filter bar filters
    _.each(filterBarFilters, function (filter) {
      var esFilter = _.omit(filter, function (val, key) {
        if (key === 'meta' || key[0] === '$') return true;
        return false;
      });

      if (filter.meta.disabled) return;
      if (filter.meta.negate) {
        bool.must_not.push(esFilter.query || esFilter);
      } else {
        bool.must.push(esFilter.query || esFilter);
      }
    });

    return {bool: bool};
  };
};
