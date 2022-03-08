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
import KbnFormController from 'ui/fancy_forms/kbn_form_controller';
import uiModules from 'ui/modules';


uiModules
.get('kibana')
.config(function ($provide) {
  function decorateDirectiveController(DecorativeController) {
    return function ($delegate, $injector) {
      // directive providers are arrays
      $delegate.forEach(function (directive) {
        // get metadata about all init fns
        let chain = [directive.controller, DecorativeController].map(function (fn) {
          let deps = $injector.annotate(fn);
          return { deps: deps, fn: _.isArray(fn) ? _.last(fn) : fn };
        });

        // replace the controller with one that will setup the actual controller
        directive.controller = function stub() {
          let allDeps = _.toArray(arguments);
          return chain.reduce(function (controller, link, i) {
            let deps = allDeps.splice(0, link.deps.length);
            return link.fn.apply(controller, deps) || controller;
          }, this);
        };

        // set the deps of our new controller to be the merged deps of every fn
        directive.controller.$inject = chain.reduce(function (deps, link) {
          return deps.concat(link.deps);
        }, []);
      });

      return $delegate;
    };
  }


  $provide.decorator('formDirective', decorateDirectiveController(KbnFormController));
  $provide.decorator('ngFormDirective', decorateDirectiveController(KbnFormController));
});
