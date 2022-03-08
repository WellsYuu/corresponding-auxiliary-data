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
import parseRange from 'ui/utils/range';
import 'ui/number_list/number_list_input';
import uiModules from 'ui/modules';
import numberListTemplate from 'ui/number_list/number_list.html';

uiModules
.get('kibana')
.directive('kbnNumberList', function () {
  return {
    restrict: 'E',
    template: numberListTemplate,
    controllerAs: 'numberListCntr',
    require: 'ngModel',
    controller: function ($scope, $attrs, $parse) {
      let self = this;

      // Called from the pre-link function once we have the controllers
      self.init = function (modelCntr) {
        self.modelCntr = modelCntr;

        self.getList = function () {
          return self.modelCntr.$modelValue;
        };

        self.getUnitName = _.partial($parse($attrs.unit), $scope);

        let defaultRange = self.range = parseRange('[0,Infinity)');

        $scope.$watch(function () {
          return $attrs.range;
        }, function (range, prev) {
          if (!range) {
            self.range = defaultRange;
            return;
          }

          try {
            self.range = parseRange(range);
          } catch (e) {
            throw new TypeError('Unable to parse range: ' + e.message);
          }
        });

        /**
         * Remove an item from list by index
         * @param  {number} index
         * @return {undefined}
         */
        self.remove = function (index) {
          let list = self.getList();
          if (!list) return;

          list.splice(index, 1);
        };

        /**
         * Add an item to the end of the list
         * @return {undefined}
         */
        self.add = function () {
          let list = self.getList();
          if (!list) return;

          list.push(_.last(list) + 1);
        };

        /**
         * Check to see if the list is too short.
         *
         * @return {Boolean}
         */
        self.tooShort = function () {
          return _.size(self.getList()) < 1;
        };

        /**
         * Check to see if the list is too short, but simply
         * because the user hasn't interacted with it yet
         *
         * @return {Boolean}
         */
        self.undefinedLength = function () {
          return self.tooShort() && (self.modelCntr.$untouched && self.modelCntr.$pristine);
        };

        /**
         * Check to see if the list is too short
         *
         * @return {Boolean}
         */
        self.invalidLength = function () {
          return self.tooShort() && !self.undefinedLength();
        };

        $scope.$watchCollection(self.getList, function () {
          self.modelCntr.$setValidity('numberListLength', !self.tooShort());
        });
      };
    },
    link: {
      pre: function ($scope, $el, attrs, ngModelCntr) {
        $scope.numberListCntr.init(ngModelCntr);
      }
    },
  };
});

