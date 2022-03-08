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

import uiModules from 'ui/modules';

uiModules
  .get('kibana')
  .directive('renderCounter', () => ({
    controller($scope, $element) {
      let counter = 0;

      const increment = () => {
        counter += 1;
        $element.attr('render-counter', counter);
      };

      const teardown = () => {
        $element.off('renderComplete', increment);
      };

      const setup = () => {
        $element.attr('render-counter', counter);
        $element.on('renderComplete', increment);
        $scope.$on('$destroy', teardown);
      };

      this.disable = () => {
        $element.attr('render-counter', 'disabled');
        teardown();
      };

      setup();
    }
  }));
