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

import $ from 'jquery';
import uiModules from 'ui/modules';
let module = uiModules.get('kibana');

module.directive('kbnInfiniteScroll', function () {
  return {
    restrict: 'E',
    scope: {
      more: '='
    },
    link: function ($scope, $element, attrs) {
      let $window = $(window);
      let checkTimer;

      function onScroll() {
        if (!$scope.more) return;

        let winHeight = $window.height();
        let winBottom = winHeight + $window.scrollTop();
        let elTop = $element.offset().top;
        let remaining = elTop - winBottom;

        if (remaining <= winHeight * 0.50) {
          $scope[$scope.$$phase ? '$eval' : '$apply'](function () {
            let more = $scope.more();
          });
        }
      }

      function scheduleCheck() {
        if (checkTimer) return;
        checkTimer = setTimeout(function () {
          checkTimer = null;
          onScroll();
        }, 50);
      }

      $window.on('scroll', scheduleCheck);
      $scope.$on('$destroy', function () {
        clearTimeout(checkTimer);
        $window.off('scroll', scheduleCheck);
      });
      scheduleCheck();
    }
  };
});
