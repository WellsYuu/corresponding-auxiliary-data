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
import 'ui/notify/directives';
import uiModules from 'ui/modules';
let typeahead = uiModules.get('kibana/typeahead');


typeahead.directive('kbnTypeaheadInput', function ($rootScope) {

  return {
    restrict: 'A',
    require: ['^ngModel', '^kbnTypeahead'],

    link: function ($scope, $el, $attr, deps) {
      let model = deps[0];
      let typeaheadCtrl = deps[1];

      typeaheadCtrl.setInputModel(model);

      // disable browser autocomplete
      $el.attr('autocomplete', 'off');

      // handle keypresses
      $el.on('keydown', function (ev) {
        typeaheadCtrl.keypressHandler(ev);
        digest();
      });

      // update focus state based on the input focus state
      $el.on('focus', function () {
        typeaheadCtrl.setFocused(true);
        digest();
      });

      $el.on('blur', function () {
        typeaheadCtrl.setFocused(false);
        digest();
      });

      // unbind event listeners
      $scope.$on('$destroy', function () {
        $el.off();
      });

      function digest() {
        $rootScope.$$phase || $scope.$digest();
      }
    }
  };
});
