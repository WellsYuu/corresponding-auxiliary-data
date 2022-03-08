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

import management from 'ui/management';
import 'plugins/kibana/management/sections/indices/_create';
import 'plugins/kibana/management/sections/indices/_edit';
import 'plugins/kibana/management/sections/indices/_field_editor';
import uiRoutes from 'ui/routes';
import uiModules from 'ui/modules';
import indexTemplate from 'plugins/kibana/management/sections/indices/index.html';

const indexPatternsResolutions = {
  indexPatternIds: function (courier) {
    return courier.indexPatterns.getIds();
  }
};

// add a dependency to all of the subsection routes
uiRoutes
.defaults(/management\/kibana\/indices/, {
  resolve: indexPatternsResolutions
});

uiRoutes
.defaults(/management\/kibana\/index/, {
  resolve: indexPatternsResolutions
});

// wrapper directive, which sets some global stuff up like the left nav
uiModules.get('apps/management')
.directive('kbnManagementIndices', function ($route, config, kbnUrl) {
  return {
    restrict: 'E',
    transclude: true,
    template: indexTemplate,
    link: function ($scope) {
      $scope.editingId = $route.current.params.indexPatternId;
      config.bindToScope($scope, 'defaultIndex');

      $scope.$watch('defaultIndex', function () {
        const ids = $route.current.locals.indexPatternIds;
        $scope.indexPatternList = ids.map(function (id) {
          return {
            id: id,
            url: kbnUrl.eval('#/management/kibana/indices/{{id}}', {id: id}),
            class: 'sidebar-item-title ' + ($scope.editingId === id ? 'active' : ''),
            default: $scope.defaultIndex === id
          };
        });
      });

      $scope.$emit('application.load');
    }
  };
});

management.getSection('kibana').register('indices', {
  display: 'Index Patterns',
  order: 0,
  url: '#/management/kibana/indices/'
});
