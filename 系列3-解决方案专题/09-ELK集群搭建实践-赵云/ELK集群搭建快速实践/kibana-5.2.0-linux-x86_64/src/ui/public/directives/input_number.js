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

import uiModules from 'ui/modules';
let module = uiModules.get('kibana');

module.directive('inputNumber', function () {
  return {
    restrict: 'A',
    require: 'ngModel',
    link: function ($scope, $elem, attrs, ngModel) {
      ngModel.$parsers.push(checkNumber);
      ngModel.$formatters.push(checkNumber);

      function checkNumber(value) {
        ngModel.$setValidity('number', !isNaN(parseFloat(value)));
        return value;
      }
    }
  };
});
