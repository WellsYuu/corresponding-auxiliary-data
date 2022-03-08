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
import VislibProvider from 'ui/vislib';
import uiModules from 'ui/modules';
uiModules
.get('apps/discover')
.directive('discoverTimechart', function (Private, $compile) {
  const vislib = Private(VislibProvider);

  return {
    restrict: 'E',
    scope : {
      data: '='
    },
    link: function ($scope, elem) {

      const init = function () {
        // This elem should already have a height/width
        const myChart = new vislib.Chart(elem[0], {
          addLegend: false
        });

        $scope.$watch('data', function (data) {
          if (data != null) {
            myChart.render(data);
          }
        });
      };

      // Start the directive
      init();
    }
  };
});
