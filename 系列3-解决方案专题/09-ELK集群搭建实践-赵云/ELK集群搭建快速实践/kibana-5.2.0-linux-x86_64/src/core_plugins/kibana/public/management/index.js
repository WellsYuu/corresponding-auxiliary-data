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

import 'plugins/kibana/management/sections';
import 'plugins/kibana/management/styles/main.less';
import 'ui/filters/start_from';
import 'ui/field_editor';
import 'plugins/kibana/management/sections/indices/_indexed_fields';
import 'plugins/kibana/management/sections/indices/_scripted_fields';
import 'plugins/kibana/management/sections/indices/source_filters/source_filters';
import 'ui/directives/bread_crumbs';
import uiRoutes from 'ui/routes';
import uiModules from 'ui/modules';
import appTemplate from 'plugins/kibana/management/app.html';
import landingTemplate from 'plugins/kibana/management/landing.html';
import chrome from 'ui/chrome/chrome';
import management from 'ui/management';
import 'ui/kbn_top_nav';

uiRoutes
.when('/management', {
  template: landingTemplate
});

require('ui/index_patterns/route_setup/load_default')({
  whenMissingRedirectTo: '/management/kibana/index'
});

uiModules
.get('apps/management')
.directive('kbnManagementApp', function (Private, $route, $location, timefilter, buildNum, buildSha) {
  return {
    restrict: 'E',
    template: appTemplate,
    transclude: true,
    scope: {
      sectionName: '@section'
    },

    link: function ($scope) {
      timefilter.enabled = false;
      $scope.sections = management.items.inOrder;
      $scope.section = management.getSection($scope.sectionName) || management;

      if ($scope.section) {
        $scope.section.items.forEach(item => {
          item.active = `#${$location.path()}`.indexOf(item.url) > -1;
        });
      }

      management.getSection('kibana').info = `Build ${buildNum}, Commit SHA ${buildSha.substr(0, 8)}`;
    }
  };
});

uiModules
.get('apps/management')
.directive('kbnManagementLanding', function (kbnVersion) {
  return {
    restrict: 'E',
    link: function ($scope) {
      $scope.sections = management.items.inOrder;
      $scope.kbnVersion = kbnVersion;
    }
  };
});
