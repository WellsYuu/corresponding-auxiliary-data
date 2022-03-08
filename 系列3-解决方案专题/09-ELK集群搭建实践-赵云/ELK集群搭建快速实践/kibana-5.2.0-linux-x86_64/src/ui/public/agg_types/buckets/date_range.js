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

import moment from 'moment';
import dateRange from 'ui/utils/date_range';
import 'ui/directives/validate_date_math';
import AggTypesBucketsBucketAggTypeProvider from 'ui/agg_types/buckets/_bucket_agg_type';
import AggTypesBucketsCreateFilterDateRangeProvider from 'ui/agg_types/buckets/create_filter/date_range';
import RegistryFieldFormatsProvider from 'ui/registry/field_formats';
import dateRangesTemplate from 'ui/agg_types/controls/date_ranges.html';

export default function DateRangeAggDefinition(Private, config) {
  let BucketAggType = Private(AggTypesBucketsBucketAggTypeProvider);
  let createFilter = Private(AggTypesBucketsCreateFilterDateRangeProvider);
  let fieldFormats = Private(RegistryFieldFormatsProvider);


  return new BucketAggType({
    name: 'date_range',
    title: 'Date Range',
    createFilter: createFilter,
    getKey: function (bucket, key, agg) {
      let formatter = agg.fieldOwnFormatter('text', fieldFormats.getDefaultInstance('date'));
      return dateRange.toString(bucket, formatter);
    },
    getFormat: function () {
      return fieldFormats.getDefaultInstance('string');
    },
    makeLabel: function (aggConfig) {
      return aggConfig.getFieldDisplayName() + ' date ranges';
    },
    params: [{
      name: 'field',
      filterFieldTypes: 'date',
      default: function (agg) {
        return agg.vis.indexPattern.timeFieldName;
      }
    }, {
      name: 'ranges',
      default: [{
        from: 'now-1w/w',
        to: 'now'
      }],
      editor: dateRangesTemplate
    }]
  });
};
