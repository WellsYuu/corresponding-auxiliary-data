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
import 'ui/filters/short_dots';
import uiModules from 'ui/modules';
let module = uiModules.get('kibana');

module.directive('fieldName', function ($compile, $rootScope, $filter) {
  return {
    restrict: 'AE',
    scope: {
      'field': '=',
      'fieldName': '=',
      'fieldType': '='
    },
    link: function ($scope, $el) {

      let typeIcon = function (fieldType) {
        switch (fieldType) {
          case 'source':
            return '<i title="source" class="fa fa-file-text-o "></i>';
          case 'string':
            return '<i title="string"><strong>t</strong></i>';
          case 'murmur3':
            return '<i title="murmur3"><strong>h</strong></i>';
          case 'number':
            return '<i title="number"><strong>#</strong></i>';
          case 'date':
            return '<i title="date" class="fa fa-clock-o"></i>';
          case 'ip':
            return '<i title="ip" class="fa fa-laptop"></i>';
          case 'geo_point':
            return '<i title="geo_point" class="fa fa-globe"></i>';
          case 'boolean':
            return '<i title="boolean" class="fa fa-adjust"></i>';
          case 'conflict':
            return '<i title="conflict" class="fa fa-warning"></i>';
          default:
            return '<i title="unknown"><strong>?</strong></i>';
        }
      };

      $rootScope.$watchMulti.call($scope, [
        'field',
        'fieldName',
        'fieldType',
        'field.rowCount'
      ], function () {

        let type = $scope.field ? $scope.field.type : $scope.fieldType;
        let name = $scope.field ? $scope.field.name : $scope.fieldName;
        let results = $scope.field ? !$scope.field.rowCount && !$scope.field.scripted : false;
        let scripted = $scope.field ? $scope.field.scripted : false;

        let displayName = $filter('shortDots')(name);

        $el
          .attr('title', name)
          .toggleClass('no-results', results)
          .toggleClass('scripted', scripted)
          .prepend(typeIcon(type))
          .append($('<span>')
            .text(displayName)
            .addClass('discover-field-name')
          );
      });
    }
  };
});
