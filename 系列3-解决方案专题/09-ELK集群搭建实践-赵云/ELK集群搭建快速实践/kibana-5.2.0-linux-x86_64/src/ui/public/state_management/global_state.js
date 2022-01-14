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
import angular from 'angular';
import qs from 'ui/utils/query_string';
import rison from 'rison-node';
import StateManagementStateProvider from 'ui/state_management/state';
import uiModules from 'ui/modules';

let module = uiModules.get('kibana/global_state');

function GlobalStateProvider(Private, $rootScope, $location) {
  let State = Private(StateManagementStateProvider);

  _.class(GlobalState).inherits(State);
  function GlobalState(defaults) {
    GlobalState.Super.call(this, '_g', defaults);
  }

  // if the url param is missing, write it back
  GlobalState.prototype._persistAcrossApps = true;

  GlobalState.prototype.removeFromUrl = function (url) {
    return qs.replaceParamInUrl(url, this._urlParam, null);
  };

  return new GlobalState();
}

module.service('globalState', function (Private) {
  return Private(GlobalStateProvider);
});
export default GlobalStateProvider;
