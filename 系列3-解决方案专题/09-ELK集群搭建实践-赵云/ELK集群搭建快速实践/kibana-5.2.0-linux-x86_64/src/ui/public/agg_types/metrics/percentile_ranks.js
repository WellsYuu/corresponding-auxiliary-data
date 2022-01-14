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
import valuesEditor from 'ui/agg_types/controls/percentile_ranks.html';
import 'ui/number_list';
import AggTypesMetricsMetricAggTypeProvider from 'ui/agg_types/metrics/metric_agg_type';
import AggTypesMetricsGetResponseAggConfigClassProvider from 'ui/agg_types/metrics/get_response_agg_config_class';
import RegistryFieldFormatsProvider from 'ui/registry/field_formats';
import getPercentileValue from './percentiles_get_value';

export default function AggTypeMetricPercentileRanksProvider(Private) {
  let MetricAggType = Private(AggTypesMetricsMetricAggTypeProvider);
  let getResponseAggConfigClass = Private(AggTypesMetricsGetResponseAggConfigClassProvider);
  let fieldFormats = Private(RegistryFieldFormatsProvider);

  // required by the values editor

  let valueProps = {
    makeLabel: function () {
      let field = this.getField();
      let format = (field && field.format) || fieldFormats.getDefaultInstance('number');
      const label = this.params.customLabel || this.getFieldDisplayName();

      return 'Percentile rank ' + format.convert(this.key, 'text') + ' of "' + label + '"';
    }
  };

  return new MetricAggType({
    name: 'percentile_ranks',
    title: 'Percentile Ranks',
    makeLabel: function (agg) {
      return 'Percentile ranks of ' + agg.getFieldDisplayName();
    },
    params: [
      {
        name: 'field',
        filterFieldTypes: 'number'
      },
      {
        name: 'values',
        editor: valuesEditor,
        default: []
      },
      {
        write(agg, output) {
          output.params.keyed = false;
        }
      }
    ],
    getResponseAggs: function (agg) {
      let ValueAggConfig = getResponseAggConfigClass(agg, valueProps);

      return agg.params.values.map(function (value) {
        return new ValueAggConfig(value);
      });
    },
    getFormat: function () {
      return fieldFormats.getInstance('percent') || fieldFormats.getDefaultInstance('number');
    },
    getValue: function (agg, bucket) {
      return getPercentileValue(agg, bucket) / 100;
    }
  });
};
