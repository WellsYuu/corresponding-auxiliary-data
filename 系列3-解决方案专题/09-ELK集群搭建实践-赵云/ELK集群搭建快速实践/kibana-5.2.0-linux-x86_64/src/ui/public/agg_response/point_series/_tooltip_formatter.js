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

import $ from 'jquery';
export default function PointSeriesTooltipFormatter($compile, $rootScope) {

  let $tooltipScope = $rootScope.$new();
  let $tooltip = $(require('ui/agg_response/point_series/_tooltip.html'));
  $compile($tooltip)($tooltipScope);

  return function tooltipFormatter(event) {
    let datum = event.datum;
    if (!datum || !datum.aggConfigResult) return '';

    let details = $tooltipScope.details = [];
    let result = { $parent: datum.aggConfigResult };

    function addDetail(result) {
      let agg = result.aggConfig;
      let value = result.value;

      let detail = {
        value: agg.fieldFormatter()(value),
        label: agg.makeLabel()
      };

      if (agg === datum.aggConfigResult.aggConfig) {
        detail.percent = event.percent;
        if (datum.yScale != null) {
          detail.value = agg.fieldFormatter()(value * datum.yScale);
        }
      }

      details.push(detail);
    }

    datum.extraMetrics.forEach(addDetail);
    while ((result = result.$parent) && result.aggConfig) {
      addDetail(result);
    }


    $tooltipScope.$apply();
    return $tooltip[0].outerHTML;
  };
};
