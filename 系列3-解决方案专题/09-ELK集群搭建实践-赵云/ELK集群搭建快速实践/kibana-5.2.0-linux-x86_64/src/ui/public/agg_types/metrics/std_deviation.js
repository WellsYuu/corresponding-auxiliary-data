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

import _ from 'lodash';
import AggTypesMetricsMetricAggTypeProvider from 'ui/agg_types/metrics/metric_agg_type';
import AggTypesMetricsGetResponseAggConfigClassProvider from 'ui/agg_types/metrics/get_response_agg_config_class';
export default function AggTypeMetricStandardDeviationProvider(Private) {
  let MetricAggType = Private(AggTypesMetricsMetricAggTypeProvider);
  let getResponseAggConfigClass = Private(AggTypesMetricsGetResponseAggConfigClassProvider);

  let responseAggConfigProps = {
    valProp: function () {
      let details = this.keyedDetails(this.params.customLabel)[this.key];
      return details.valProp;
    },
    makeLabel: function () {
      const fieldDisplayName = this.getFieldDisplayName();
      const details = this.keyedDetails(this.params.customLabel, fieldDisplayName);
      return _.get(details, [this.key, 'title']);
    },
    keyedDetails: function (customLabel, fieldDisplayName) {
      const label = customLabel ? customLabel : 'Standard Deviation of ' + fieldDisplayName;
      return {
        std_lower: {
          valProp: ['std_deviation_bounds', 'lower'],
          title: 'Lower ' + label
        },
        std_upper: {
          valProp: ['std_deviation_bounds', 'upper'],
          title: 'Upper ' + label
        }
      };
    }
  };

  return new MetricAggType({
    name: 'std_dev',
    dslName: 'extended_stats',
    title: 'Standard Deviation',
    makeLabel: function (agg) {
      return 'Standard Deviation of ' + agg.getFieldDisplayName();
    },
    params: [
      {
        name: 'field',
        filterFieldTypes: 'number'
      }
    ],

    getResponseAggs: function (agg) {
      let ValueAggConfig = getResponseAggConfigClass(agg, responseAggConfigProps);

      return [
        new ValueAggConfig('std_lower'),
        new ValueAggConfig('std_upper')
      ];
    },

    getValue: function (agg, bucket) {
      return _.get(bucket[agg.parentId], agg.valProp());
    }
  });
};
