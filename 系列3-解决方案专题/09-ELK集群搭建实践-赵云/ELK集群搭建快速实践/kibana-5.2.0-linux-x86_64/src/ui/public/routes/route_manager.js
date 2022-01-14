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

import wrapRouteWithPrep from './wrap_route_with_prep';
import RouteSetupManager from './route_setup_manager';

function RouteManager() {
  let self = this;
  let setup = new RouteSetupManager();
  let when = [];
  let defaults = [];
  let otherwise;

  self.config = function ($routeProvider) {
    when.forEach(function (args) {
      let path = args[0];
      let route = args[1] || {};

      // merge in any defaults
      defaults.forEach(function (args) {
        if (args[0].test(path)) {
          _.merge(route, args[1]);
        }
      });

      if (route.reloadOnSearch === void 0) {
        route.reloadOnSearch = false;
      }

      if (route.requireDefaultIndex === void 0) {
        route.requireDefaultIndex = false;
      }

      wrapRouteWithPrep(route, setup);
      $routeProvider.when(path, route);
    });

    if (otherwise) {
      wrapRouteWithPrep(otherwise, setup);
      $routeProvider.otherwise(otherwise);
    }
  };

  let wrapSetupAndChain = (fn, ...args) => {
    fn.apply(setup, args);
    return this;
  };

  this.addSetupWork = _.wrap(setup.addSetupWork, wrapSetupAndChain);
  this.afterSetupWork = _.wrap(setup.afterSetupWork, wrapSetupAndChain);
  this.afterWork = _.wrap(setup.afterWork, wrapSetupAndChain);

  self.when = function (path, route) {
    when.push([path, route]);
    return self;
  };

  // before attaching the routes to the routeProvider, test the RE
  // against the .when() path and add/override the resolves if there is a match
  self.defaults = function (RE, def) {
    defaults.push([RE, def]);
    return self;
  };

  self.otherwise = function (route) {
    otherwise = route;
    return self;
  };

  self.RouteManager = RouteManager;
}

module.exports = RouteManager;
