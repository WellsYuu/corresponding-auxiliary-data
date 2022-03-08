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
import $ from 'jquery';
import uiModules from 'ui/modules';
import visOptionsTemplate from 'plugins/kibana/visualize/editor/vis_options.html';

uiModules
.get('app/visualize')
.directive('visEditorVisOptions', function (Private, $timeout, $compile) {
  return {
    restrict: 'E',
    template: visOptionsTemplate,
    scope: {
      vis: '=',
      savedVis: '=',
      uiState: '=',
    },
    link: function ($scope, $el) {
      const $optionContainer = $el.find('.visualization-options');
      const $editor = $compile($scope.vis.type.params.editor)($scope);
      $optionContainer.append($editor);

      $scope.$watch('vis.type.schemas.all.length', function (len) {
        $scope.alwaysShowOptions = len === 0;
      });
    }
  };
});
