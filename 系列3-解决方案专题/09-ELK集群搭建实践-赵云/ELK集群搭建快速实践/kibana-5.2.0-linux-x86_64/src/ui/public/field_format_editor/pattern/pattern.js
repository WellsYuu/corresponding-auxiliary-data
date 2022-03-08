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

import 'ui/field_format_editor/samples/samples';
import uiModules from 'ui/modules';
import patternTemplate from 'ui/field_format_editor/pattern/pattern.html';

uiModules
.get('kibana')
.directive('fieldFormatEditorPattern', function () {
  return {
    restrict: 'E',
    template: patternTemplate,
    require: ['ngModel', '^fieldEditor'],
    scope: true,
    link: function ($scope, $el, attrs, cntrls) {
      let ngModelCntrl = cntrls[0];

      $scope.$bind('inputs', attrs.inputs);
      $scope.$bind('placeholder', attrs.placeholder);

      // bind our local model with the outside ngModel
      $scope.$watch('model', ngModelCntrl.$setViewValue);
      ngModelCntrl.$render = function () {
        $scope.model = ngModelCntrl.$viewValue;
      };
    }
  };
});
