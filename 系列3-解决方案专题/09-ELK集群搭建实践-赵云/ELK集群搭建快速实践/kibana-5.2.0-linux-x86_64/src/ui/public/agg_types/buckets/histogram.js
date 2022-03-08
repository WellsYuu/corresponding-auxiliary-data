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
import 'ui/validate_date_interval';
import AggTypesBucketsBucketAggTypeProvider from 'ui/agg_types/buckets/_bucket_agg_type';
import AggTypesBucketsCreateFilterHistogramProvider from 'ui/agg_types/buckets/create_filter/histogram';
import intervalTemplate from 'ui/agg_types/controls/interval.html';
import minDocCountTemplate from 'ui/agg_types/controls/min_doc_count.html';
import extendedBoundsTemplate from 'ui/agg_types/controls/extended_bounds.html';
export default function HistogramAggDefinition(Private) {
  let BucketAggType = Private(AggTypesBucketsBucketAggTypeProvider);
  let createFilter = Private(AggTypesBucketsCreateFilterHistogramProvider);


  return new BucketAggType({
    name: 'histogram',
    title: 'Histogram',
    ordered: {},
    makeLabel: function (aggConfig) {
      return aggConfig.getFieldDisplayName();
    },
    createFilter: createFilter,
    params: [
      {
        name: 'field',
        filterFieldTypes: 'number'
      },

      {
        name: 'interval',
        editor: intervalTemplate,
        write: function (aggConfig, output) {
          output.params.interval = parseFloat(aggConfig.params.interval);
        }
      },

      {
        name: 'min_doc_count',
        default: null,
        editor: minDocCountTemplate,
        write: function (aggConfig, output) {
          if (aggConfig.params.min_doc_count) {
            output.params.min_doc_count = 0;
          } else {
            output.params.min_doc_count = 1;
          }
        }
      },

      {
        name: 'extended_bounds',
        default: {},
        editor: extendedBoundsTemplate,
        write: function (aggConfig, output) {
          let val = aggConfig.params.extended_bounds;

          if (aggConfig.params.min_doc_count && (val.min != null || val.max != null)) {
            output.params.extended_bounds = {
              min: val.min,
              max: val.max
            };
          }
        },

        // called from the editor
        shouldShow: function (aggConfig) {
          let field = aggConfig.params.field;
          if (
            field
            && (field.type === 'number' || field.type === 'date')
          ) {
            return aggConfig.params.min_doc_count;
          }
        }
      }
    ]
  });
};
