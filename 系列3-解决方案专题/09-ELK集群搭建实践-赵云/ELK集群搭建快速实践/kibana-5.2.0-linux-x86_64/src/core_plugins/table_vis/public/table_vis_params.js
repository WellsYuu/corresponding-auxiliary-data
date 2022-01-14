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
import uiModules from 'ui/modules';
import tableVisParamsTemplate from 'plugins/table_vis/table_vis_params.html';

uiModules.get('kibana/table_vis')
.directive('tableVisParams', function () {
  return {
    restrict: 'E',
    template: tableVisParamsTemplate,
    link: function ($scope) {
      $scope.totalAggregations = ['sum', 'avg', 'min', 'max', 'count'];

      $scope.$watchMulti([
        'vis.params.showPartialRows',
        'vis.params.showMeticsAtAllLevels'
      ], function () {
        if (!$scope.vis) return;

        const params = $scope.vis.params;
        if (params.showPartialRows || params.showMeticsAtAllLevels) {
          $scope.metricsAtAllLevels = true;
        } else {
          $scope.metricsAtAllLevels = false;
        }
      });
    }
  };
});
