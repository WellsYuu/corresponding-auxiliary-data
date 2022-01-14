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

import 'ui/collapsible_sidebar/collapsible_sidebar.less';
import _ from 'lodash';
import $ from 'jquery';
import uiModules from 'ui/modules';


uiModules
.get('kibana')
.directive('collapsibleSidebar', function () {
  // simply a list of all of all of angulars .col-md-* classes except 12
  let listOfWidthClasses = _.times(11, function (i) { return 'col-md-' + i; });

  return {
    restrict: 'C',
    link: function ($scope, $elem) {
      let $collapser = $('<div class="sidebar-collapser"><div class="chevron-cont"></div></div>');
      let $siblings = $elem.siblings();

      let siblingsClass = listOfWidthClasses.reduce(function (prev, className) {
        if (prev) return prev;
        return $siblings.hasClass(className) && className;
      }, false);

      $collapser.on('click', function () {
        $elem.toggleClass('closed');
        // if there is are only two elements we can assume the other one will take 100% of the width
        if ($siblings.length === 1 && siblingsClass) {
          $siblings.toggleClass(siblingsClass + ' col-md-12');
        }
      })

      .appendTo($elem);
    }
  };
});
