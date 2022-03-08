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
import angular from 'angular';
import uiModules from 'ui/modules';

uiModules
.get('kibana')
.directive('jsonInput', function () {
  return {
    restrict: 'A',
    require: 'ngModel',
    link: function (scope, $el, attrs, ngModelCntrl) {
      ngModelCntrl.$formatters.push(toJSON);
      ngModelCntrl.$parsers.push(fromJSON);

      function fromJSON(value) {
        try {
          value = JSON.parse(value);
          let validity = !scope.$eval(attrs.requireKeys) ? true : _.keys(value).length > 0;
          ngModelCntrl.$setValidity('json', validity);
        } catch (e) {
          ngModelCntrl.$setValidity('json', false);
        }
        return value;
      }

      function toJSON(value) {
        return angular.toJson(value, 2);
      }
    }
  };
});

