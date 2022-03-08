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

import UtilsBrushEventProvider from 'ui/utils/brush_event';
import FilterBarFilterBarClickHandlerProvider from 'ui/filter_bar/filter_bar_click_handler';

export function visualizationLoaderProvider(savedVisualizations, Private) { // Inject services here
  const brushEvent = Private(UtilsBrushEventProvider);
  const filterBarClickHandler = Private(FilterBarFilterBarClickHandlerProvider);

  return function (panel, $scope) { // Function parameters here
    return savedVisualizations.get(panel.id)
    .then(function (savedVis) {
      // $scope.state comes via $scope inheritence from the dashboard app. Don't love this.
      savedVis.vis.listeners.click = filterBarClickHandler($scope.state);
      savedVis.vis.listeners.brush = brushEvent($scope.state);

      return {
        savedObj: savedVis,
        panel: panel,
        uiState: savedVis.uiStateJSON ? JSON.parse(savedVis.uiStateJSON) : {},
        editUrl: savedVisualizations.urlFor(panel.id)
      };
    });
  };
};
