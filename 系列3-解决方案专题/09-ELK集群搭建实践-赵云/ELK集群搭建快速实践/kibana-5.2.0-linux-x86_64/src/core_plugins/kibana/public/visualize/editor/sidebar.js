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
import 'plugins/kibana/visualize/editor/agg_group';
import 'plugins/kibana/visualize/editor/vis_options';
import uiModules from 'ui/modules';
import sidebarTemplate from 'plugins/kibana/visualize/editor/sidebar.html';
uiModules
.get('app/visualize')
.directive('visEditorSidebar', function () {


  return {
    restrict: 'E',
    template: sidebarTemplate,
    scope: true,
    controllerAs: 'sidebar',
    controller: function ($scope) {
      $scope.$bind('vis', 'editableVis');

      $scope.$watch('vis.type', (visType) => {
        if (visType) {
          this.showData = visType.schemas.buckets || visType.schemas.metrics;
          this.section = this.section || (this.showData ? 'data' : 'options');
        }
      });
    }
  };
});
