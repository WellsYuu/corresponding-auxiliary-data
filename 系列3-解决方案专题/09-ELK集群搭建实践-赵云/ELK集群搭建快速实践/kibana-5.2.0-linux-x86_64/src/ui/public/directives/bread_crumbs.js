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
import chrome from 'ui/chrome/chrome';
import breadCrumbsTemplate from 'ui/partials/bread_crumbs.html';
import uiModules from 'ui/modules';
let module = uiModules.get('kibana');

module.directive('breadCrumbs', function () {
  return {
    restrict: 'E',
    scope: {
      omitCurrentPage: '='
    },
    template: breadCrumbsTemplate,
    controller: function ($scope) {
      // Capitalize the first letter of each bread crumb.
      $scope.breadcrumbs = chrome.getBreadcrumbs().map(breadcrumb => _.startCase(breadcrumb));

      if ($scope.omitCurrentPage === true) {
        $scope.breadcrumbs.pop();
      }
    }
  };
});
