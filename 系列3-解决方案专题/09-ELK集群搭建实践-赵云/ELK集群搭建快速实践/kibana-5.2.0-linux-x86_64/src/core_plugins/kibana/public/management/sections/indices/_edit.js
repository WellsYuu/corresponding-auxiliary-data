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
import 'plugins/kibana/management/sections/indices/_indexed_fields';
import 'plugins/kibana/management/sections/indices/_scripted_fields';
import 'plugins/kibana/management/sections/indices/source_filters/source_filters';
import 'plugins/kibana/management/sections/indices/_index_header';
import RefreshKibanaIndex from 'plugins/kibana/management/sections/indices/_refresh_kibana_index';
import UrlProvider from 'ui/url';
import IndicesEditSectionsProvider from 'plugins/kibana/management/sections/indices/_edit_sections';
import uiRoutes from 'ui/routes';
import uiModules from 'ui/modules';
import editTemplate from 'plugins/kibana/management/sections/indices/_edit.html';
import IngestProvider from 'ui/ingest';

uiRoutes
.when('/management/kibana/indices/:indexPatternId', {
  template: editTemplate,
  resolve: {
    indexPattern: function ($route, courier) {
      return courier.indexPatterns
        .get($route.current.params.indexPatternId)
        .catch(courier.redirectWhenMissing('/management/kibana/index'));
    }
  }
});

uiRoutes
.when('/management/kibana/indices', {
  resolve: {
    redirect: function ($location, config) {
      const defaultIndex = config.get('defaultIndex');
      let path = '/management/kibana/index';

      if (defaultIndex) {
        path = `/management/kibana/indices/${defaultIndex}`;
      }

      $location.path(path).replace();
    }
  }
});

uiModules.get('apps/management')
.controller('managementIndicesEdit', function ($scope, $location, $route, config, courier, Notifier, Private, AppState, docTitle) {

  const notify = new Notifier();
  const $state = $scope.state = new AppState();
  const refreshKibanaIndex = Private(RefreshKibanaIndex);
  const ingest = Private(IngestProvider);

  $scope.kbnUrl = Private(UrlProvider);
  $scope.indexPattern = $route.current.locals.indexPattern;
  docTitle.change($scope.indexPattern.id);
  const otherIds = _.without($route.current.locals.indexPatternIds, $scope.indexPattern.id);

  $scope.$watch('indexPattern.fields', function () {
    $scope.editSections = Private(IndicesEditSectionsProvider)($scope.indexPattern);
  });

  $scope.changeTab = function (obj) {
    $state.tab = obj.index;
    $state.save();
  };

  $scope.$watch('state.tab', function (tab) {
    if (!tab) $scope.changeTab($scope.editSections[0]);
  });

  $scope.$watchCollection('indexPattern.fields', function () {
    $scope.conflictFields = _.filter($scope.indexPattern.fields, {type: 'conflict'});
  });

  $scope.refreshFields = function () {
    $scope.indexPattern.refreshFields();
  };

  $scope.removePattern = function () {
    if ($scope.indexPattern.id === config.get('defaultIndex')) {
      config.remove('defaultIndex');
      if (otherIds.length) {
        config.set('defaultIndex', otherIds[0]);
      }
    }

    courier.indexPatterns.delete($scope.indexPattern)
    .then(refreshKibanaIndex)
    .then(function () {
      $location.url('/management/kibana/index');
    })
    .catch(notify.fatal);
  };

  $scope.setDefaultPattern = function () {
    config.set('defaultIndex', $scope.indexPattern.id);
  };

  $scope.setIndexPatternsTimeField = function (field) {
    if (field.type !== 'date') {
      notify.error('That field is a ' + field.type + ' not a date.');
      return;
    }
    $scope.indexPattern.timeFieldName = field.name;
    return $scope.indexPattern.save();
  };
});
