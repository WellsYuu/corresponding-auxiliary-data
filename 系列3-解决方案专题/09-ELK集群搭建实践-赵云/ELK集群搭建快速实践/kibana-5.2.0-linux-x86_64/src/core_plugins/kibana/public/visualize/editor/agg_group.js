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
import 'plugins/kibana/visualize/editor/agg';
import 'plugins/kibana/visualize/editor/agg_add';
import 'plugins/kibana/visualize/editor/nesting_indicator';
import uiModules from 'ui/modules';
import aggGroupTemplate from 'plugins/kibana/visualize/editor/agg_group.html';

uiModules
.get('app/visualize')
.directive('visEditorAggGroup', function (Private) {

  return {
    restrict: 'E',
    template: aggGroupTemplate,
    scope: true,
    link: function ($scope, $el, attr) {
      $scope.groupName = attr.groupName;
      $scope.$bind('group', 'vis.aggs.bySchemaGroup["' + $scope.groupName + '"]');
      $scope.$bind('schemas', 'vis.type.schemas["' + $scope.groupName + '"]');

      $scope.$watchMulti([
        'schemas',
        '[]group'
      ], function () {
        const stats = $scope.stats = {
          min: 0,
          max: 0,
          count: $scope.group ? $scope.group.length : 0
        };

        if (!$scope.schemas) return;

        $scope.schemas.forEach(function (schema) {
          stats.min += schema.min;
          stats.max += schema.max;
          stats.deprecate = schema.deprecate;
        });

        $scope.availableSchema = $scope.schemas.filter(function (schema) {
          const count = _.where($scope.group, { schema }).length;
          if (count < schema.max) return true;
        });
      });

      $scope.$on('agg-drag-start', e => $scope.dragging = true);
      $scope.$on('agg-drag-end', e => {
        $scope.dragging = false;

        //the aggs have been reordered in [group] and we need
        //to apply that ordering to [vis.aggs]
        const indexOffset = $scope.vis.aggs.indexOf($scope.group[0]);
        _.forEach($scope.group, (agg, index) => {
          _.move($scope.vis.aggs, agg, indexOffset + index);
        });
      });
    }
  };

});
