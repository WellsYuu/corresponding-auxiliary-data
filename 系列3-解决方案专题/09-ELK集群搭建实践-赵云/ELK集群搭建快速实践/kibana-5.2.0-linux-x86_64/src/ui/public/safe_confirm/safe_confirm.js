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
uiModules.get('kibana')

/*
 * Angular doesn't play well with thread blocking calls such as
 * window.confirm() unless those calls are specifically handled inside a call
 * to $timeout(). Rather than litter the code with that implementation
 * detail, safeConfirm() can be used.
 *
 * WARNING: safeConfirm differs from a native call to window.confirm in that
 * it only blocks the thread beginning on the next tick. For that reason, a
 * promise is returned so consumers can handle the control flow.
 *
 * Usage:
 *  safeConfirm('This message will be passed to window.confirm()').then(
 *    function () {
 *      // user clicked confirm
 *    },
 *    function () {
 *      // user canceled the confirmation
 *    }
 *  );
 */
.factory('safeConfirm', function ($window, $timeout, $q) {
  return function safeConfirm(message) {
    return $timeout(function () {
      return $window.confirm(message) || $q.reject(false);
    });
  };
});
