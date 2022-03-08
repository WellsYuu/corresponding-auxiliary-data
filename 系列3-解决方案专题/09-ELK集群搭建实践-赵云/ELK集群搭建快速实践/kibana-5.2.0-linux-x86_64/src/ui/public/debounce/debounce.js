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
import uiModules from 'ui/modules';
// Debounce service, angularized version of lodash debounce
// borrowed heavily from https://github.com/shahata/angular-debounce

let module = uiModules.get('kibana');

module.service('debounce', ['$timeout', function ($timeout) {
  return function (func, wait, options) {
    let timeout;
    let args;
    let self;
    let result;
    options = _.defaults(options || {}, {
      leading: false,
      trailing: true
    });

    function debounce() {
      self = this;
      args = arguments;

      let later = function () {
        timeout = null;
        if (!options.leading || options.trailing) {
          result = func.apply(self, args);
        }
      };

      let callNow = options.leading && !timeout;

      if (timeout) {
        $timeout.cancel(timeout);
      }
      timeout = $timeout(later, wait);

      if (callNow) {
        result = func.apply(self, args);
      }

      return result;
    }

    debounce.cancel = function () {
      $timeout.cancel(timeout);
      timeout = null;
    };

    return debounce;
  };
}]);
