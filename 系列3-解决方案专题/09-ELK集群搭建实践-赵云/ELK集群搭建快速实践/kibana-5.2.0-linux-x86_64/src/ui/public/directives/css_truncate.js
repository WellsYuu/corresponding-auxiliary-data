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
import _ from 'lodash';
import uiModules from 'ui/modules';
let module = uiModules.get('kibana');

module.directive('cssTruncate', function ($timeout) {
  return {
    restrict: 'A',
    scope: {},
    link: function ($scope, $elem, attrs) {

      $elem.css({
        overflow: 'hidden',
        'white-space': 'nowrap',
        'text-overflow': 'ellipsis',
        'word-break': 'break-all',
      });

      if (attrs.cssTruncateExpandable != null) {
        $scope.$watch(
          function () { return $elem.html(); },
          function () {
            if ($elem[0].offsetWidth < $elem[0].scrollWidth) {
              $elem.css({'cursor': 'pointer'});
              $elem.bind('click', function () {
                $scope.toggle();
              });
            }
          }
        );
      }

      $scope.toggle = function () {
        if ($elem.css('white-space') !== 'normal') {
          $elem.css({'white-space': 'normal'});
        } else {
          $elem.css({'white-space': 'nowrap'});
        }
      };

      $scope.$on('$destroy', function () {
        $elem.unbind('click');
        $elem.unbind('mouseenter');
      });
    }
  };
});
