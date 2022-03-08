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
import $ from 'jquery';
import collectBranch from 'ui/agg_response/hierarchical/_collect_branch';
import numeral from 'numeral';
export default function HierarchicalTooltipFormaterProvider($rootScope, $compile, $sce) {
  let $tooltip = $(require('ui/agg_response/hierarchical/_tooltip.html'));
  let $tooltipScope = $rootScope.$new();

  $compile($tooltip)($tooltipScope);

  return function (columns) {
    return function (event) {
      let datum = event.datum;

      // Collect the current leaf and parents into an array of values
      $tooltipScope.rows = collectBranch(datum);

      let metricCol = $tooltipScope.metricCol = _.find(columns, { categoryName: 'metric' });

      // Map those values to what the tooltipSource.rows format.
      _.forEachRight($tooltipScope.rows, function (row, i, rows) {
        row.spacer = $sce.trustAsHtml(_.repeat('&nbsp;', row.depth));

        let percent;
        if (row.item.percentOfGroup != null) {
          percent = row.item.percentOfGroup;
        }

        row.metric = metricCol.aggConfig.fieldFormatter()(row.metric);

        if (percent != null) {
          row.metric += ' (' + numeral(percent).format('0.[00]%') + ')';
        }

        return row;
      });

      $tooltipScope.$apply();
      return $tooltip[0].outerHTML;
    };

  };

};
