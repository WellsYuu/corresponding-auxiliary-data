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

import uiModules from 'ui/modules';
import template from './pattern_checker.html';
import './pattern_checker.less';
import chrome from 'ui/chrome';

const module = uiModules.get('kibana');

module.directive('patternChecker', function () {
  return {
    restrict: 'E',
    template: template,
    controllerAs: 'checker',
    bindToController: true,
    scope: {
      pattern: '='
    },
    controller: function (Notifier, $scope, $timeout, $http) {
      let validationTimeout;

      var notify = new Notifier({
        location: 'Add Data'
      });

      this.validateInstall = () => {
        $http.post(chrome.addBasePath(`/api/kibana/${this.pattern}/_count`))
        .then(
          (response) => {
            this.resultCount = response.data.count;
          },
          (error) => {
            if (error.status === 404) {
              this.resultCount = 0;
            }
            else {
              notify.fatal(error);
            }
          }
        )
        .then(() => {
          validationTimeout = $timeout(this.validateInstall, 5000);
        });
      };

      $scope.$on('$destroy', () => {
        $timeout.cancel(validationTimeout);
      });

      this.validateInstall();
    }
  };
});

