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
import listTemplate from 'ui/typeahead/partials/typeahead-items.html';
import 'ui/notify/directives';
import uiModules from 'ui/modules';
let typeahead = uiModules.get('kibana/typeahead');


typeahead.directive('kbnTypeaheadItems', function () {
  return {
    restrict: 'E',
    require: '^kbnTypeahead',
    replace: true,
    template: listTemplate,

    link: function ($scope, $el, attr, typeaheadCtrl) {
      $scope.typeahead = typeaheadCtrl;
    }
  };
});
