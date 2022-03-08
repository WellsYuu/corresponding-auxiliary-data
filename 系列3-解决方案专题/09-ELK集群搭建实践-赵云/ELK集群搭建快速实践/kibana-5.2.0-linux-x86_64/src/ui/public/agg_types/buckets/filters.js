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
import AggTypesBucketsBucketAggTypeProvider from 'ui/agg_types/buckets/_bucket_agg_type';
import AggTypesBucketsCreateFilterFiltersProvider from 'ui/agg_types/buckets/create_filter/filters';
import DecorateQueryProvider from 'ui/courier/data_source/_decorate_query';
import filtersTemplate from 'ui/agg_types/controls/filters.html';
export default function FiltersAggDefinition(Private, Notifier) {
  let BucketAggType = Private(AggTypesBucketsBucketAggTypeProvider);
  let createFilter = Private(AggTypesBucketsCreateFilterFiltersProvider);
  let decorateQuery = Private(DecorateQueryProvider);
  let notif = new Notifier({ location: 'Filters Agg' });

  return new BucketAggType({
    name: 'filters',
    title: 'Filters',
    createFilter: createFilter,
    customLabels: false,
    params: [
      {
        name: 'filters',
        editor: filtersTemplate,
        default: [ {input: {}, label: ''} ],
        write: function (aggConfig, output) {
          let inFilters = aggConfig.params.filters;
          if (!_.size(inFilters)) return;

          let outFilters = _.transform(inFilters, function (filters, filter) {
            let input = filter.input;
            if (!input) return notif.log('malformed filter agg params, missing "input" query');

            let query = input.query;
            if (!query) return notif.log('malformed filter agg params, missing "query" on input');

            decorateQuery(query);

            let label = filter.label || _.get(query, 'query_string.query') || angular.toJson(query);
            filters[label] = input;
          }, {});

          if (!_.size(outFilters)) return;

          let params = output.params || (output.params = {});
          params.filters = outFilters;
        }
      }
    ]
  });
};
