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
import chrome from 'ui/chrome';
import uiModules from 'ui/modules';

uiModules.get('kibana')
.run(function ($rootScope, docTitle) {
  // always bind to the route events
  $rootScope.$on('$routeChangeStart', docTitle.reset);
  $rootScope.$on('$routeChangeError', docTitle.update);
  $rootScope.$on('$routeChangeSuccess', docTitle.update);
})
.service('docTitle', function ($rootScope) {
  let baseTitle = document.title;
  let self = this;

  let lastChange;

  function render() {
    lastChange = lastChange || [];

    let parts = [lastChange[0]];

    if (!lastChange[1]) parts.push(baseTitle);

    return _(parts).flattenDeep().compact().join(' - ');
  }

  self.change = function (title, complete) {
    lastChange = [title, complete];
    self.update();
  };

  self.reset = function () {
    lastChange = null;
  };

  self.update = function () {
    document.title = render();
  };
});

// return a "private module" so that it can be used both ways
export default function DoctitleProvider(docTitle) {
  return docTitle;
};
