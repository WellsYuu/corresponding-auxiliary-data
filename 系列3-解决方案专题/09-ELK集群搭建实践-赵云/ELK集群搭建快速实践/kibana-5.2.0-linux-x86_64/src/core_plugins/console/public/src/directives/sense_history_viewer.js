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

const history = require('../history');
let SenseEditor = require('../sense_editor/editor');
import { useResizeCheckerProvider } from '../sense_editor_resize';

require('ui/modules')
.get('app/sense')
.directive('senseHistoryViewer', function (Private) {
  const useResizeChecker = Private(useResizeCheckerProvider);

  return {
    restrict: 'E',
    scope: {
      req: '=',
    },
    link: function ($scope, $el) {
      const viewer = new SenseEditor($el);
      viewer.setReadOnly(true);
      viewer.renderer.setShowPrintMargin(false);
      useResizeChecker($scope, $el, viewer);
      require('../settings').applyCurrentSettings(viewer);

      $scope.$watch('req', function (req) {
        if (req) {
          var s = req.method + " " + req.endpoint + "\n" + (req.data || "");
          viewer.setValue(s);
          viewer.clearSelection();
        } else {
          viewer.getSession().setValue("No history available")
        }
      });

      $scope.$on('$destroy', function () {
        viewer.destroy();
      });
    }
  }
})
