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
import 'plugins/kibana/visualize/saved_visualizations/saved_visualizations';
import 'ui/directives/saved_object_finder';
import 'ui/directives/paginated_selectable_list';
import 'plugins/kibana/discover/saved_searches/saved_searches';
import routes from 'ui/routes';
import RegistryVisTypesProvider from 'ui/registry/vis_types';
import uiModules from 'ui/modules';
import './wizard.less';

const templateStep = function (num, txt) {
  return '<div ng-controller="VisualizeWizardStep' + num + '" class="container-fluid vis-wizard">' + txt + '</div>';
};

const module = uiModules.get('app/visualize', ['kibana/courier']);

/********
/** Wizard Step 1
/********/
routes.when('/visualize/step/1', {
  template: templateStep(1, require('plugins/kibana/visualize/wizard/step_1.html'))
});

module.controller('VisualizeWizardStep1', function ($scope, $route, $location, timefilter, Private) {
  timefilter.enabled = false;

  $scope.visTypes = Private(RegistryVisTypesProvider);
  $scope.visTypeUrl = function (visType) {
    if (!visType.requiresSearch) return '#/visualize/create?type=' + encodeURIComponent(visType.name);
    else return '#/visualize/step/2?type=' + encodeURIComponent(visType.name);
  };
});

/********
/** Wizard Step 2
/********/
routes.when('/visualize/step/2', {
  template: templateStep(2, require('plugins/kibana/visualize/wizard/step_2.html')),
  resolve: {
    indexPatternIds: function (courier) {
      return courier.indexPatterns.getIds();
    }
  }
});

module.controller('VisualizeWizardStep2', function ($route, $scope, $location, timefilter, kbnUrl) {
  const type = $route.current.params.type;

  $scope.step2WithSearchUrl = function (hit) {
    return kbnUrl.eval('#/visualize/create?&type={{type}}&savedSearchId={{id}}', {type: type, id: hit.id});
  };

  timefilter.enabled = false;

  $scope.indexPattern = {
    selection: null,
    list: $route.current.locals.indexPatternIds
  };

  $scope.makeUrl = function (pattern) {
    if (!pattern) return;
    return `#/visualize/create?type=${type}&indexPattern=${pattern}`;
  };
});
