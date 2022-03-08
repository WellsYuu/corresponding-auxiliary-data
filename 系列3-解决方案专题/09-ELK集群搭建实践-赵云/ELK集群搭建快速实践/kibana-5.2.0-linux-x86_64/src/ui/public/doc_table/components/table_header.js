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
import 'ui/filters/short_dots';
import headerHtml from 'ui/doc_table/components/table_header.html';
import uiModules from 'ui/modules';
let module = uiModules.get('app/discover');


module.directive('kbnTableHeader', function (shortDotsFilter) {
  return {
    restrict: 'A',
    scope: {
      columns: '=',
      sorting: '=',
      indexPattern: '=',
    },
    template: headerHtml,
    controller: function ($scope) {

      let sortableField = function (field) {
        if (!$scope.indexPattern) return;
        let sortable = _.get($scope.indexPattern.fields.byName[field], 'sortable');
        return sortable;
      };

      $scope.tooltip = function (column) {
        if (!sortableField(column)) return '';
        return 'Sort by ' + shortDotsFilter(column);
      };

      $scope.canRemove = function (name) {
        return (name !== '_source' || $scope.columns.length !== 1);
      };

      $scope.headerClass = function (column) {
        if (!sortableField(column)) return;

        let sorting = $scope.sorting;
        let defaultClass = ['fa', 'fa-sort-up', 'table-header-sortchange'];

        if (!sorting || column !== sorting[0]) return defaultClass;
        return ['fa', sorting[1] === 'asc' ? 'fa-sort-up' : 'fa-sort-down'];
      };

      $scope.moveLeft = function (column) {
        let index = _.indexOf($scope.columns, column);
        if (index === 0) return;

        _.move($scope.columns, index, --index);
      };

      $scope.moveRight = function (column) {
        let index = _.indexOf($scope.columns, column);
        if (index === $scope.columns.length - 1) return;

        _.move($scope.columns, index, ++index);
      };

      $scope.toggleColumn = function (fieldName) {
        _.toggleInOut($scope.columns, fieldName);
      };

      $scope.sort = function (column) {
        if (!column || !sortableField(column)) return;

        let sorting = $scope.sorting = $scope.sorting || [];

        let direction = sorting[1] || 'asc';
        if (sorting[0] !== column) {
          direction = 'asc';
        } else {
          direction = sorting[1] === 'asc' ? 'desc' : 'asc';
        }

        $scope.sorting[0] = column;
        $scope.sorting[1] = direction;
      };
    }
  };
});
