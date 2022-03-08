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
import VislibComponentsColorColorPaletteProvider from 'ui/vis/components/color/color_palette';
import uiModules from 'ui/modules';
uiModules
.get('kibana')
.directive('nestingIndicator', function ($rootScope, $parse, Private) {
  const getColors = Private(VislibComponentsColorColorPaletteProvider);

  return {
    restrict: 'E',
    scope: {
      item: '=',
      list: '='
    },
    link: function ($scope, $el, attr) {
      $scope.$watchCollection('list', function () {
        if (!$scope.list || !$scope.item) return;

        const item = $scope.item;
        const index = $scope.list.indexOf($scope.item);
        const bars = $scope.list.slice(0, index + 1);
        const colors = getColors(bars.length);

        $el.html(bars.map(function (bar, i) {
          return $(document.createElement('span'))
          .css('background-color', colors[i]);
        }));
      });
    }
  };
});
