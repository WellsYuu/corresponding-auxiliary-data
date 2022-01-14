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

define(function (require) {
  require('plugins/timelion/directives/chart/chart');
  require('plugins/timelion/directives/interval/interval');
  require('plugins/timelion/directives/refresh_hack');
  require('ui/state_management/app_state');

  var _ = require('lodash');
  var module = require('ui/modules').get('kibana/timelion_vis', ['kibana']);
  module.controller('TimelionVisController', function (
    $scope,
    $element,
    Private,
    Notifier,
    $http,
    $rootScope,
    timefilter,
    getAppState
  ) {
    var queryFilter = Private(require('ui/filter_bar/query_filter'));
    var timezone = Private(require('plugins/timelion/services/timezone'))();
    var dashboardContext = Private(require('plugins/timelion/services/dashboard_context'));

    var notify = new Notifier({
      location: 'Timelion'
    });

    $scope.search = function run() {
      var expression = $scope.vis.params.expression;
      if (!expression) return;

      $http.post('../api/timelion/run', {
        sheet: [expression],
        extended: {
          es: {
            filter: dashboardContext()
          }
        },
        time: _.extend(timefilter.time, {
          interval: $scope.vis.params.interval,
          timezone: timezone
        }),
      })
      // data, status, headers, config
      .success(function (resp) {
        $scope.sheet = resp.sheet;
      })
      .error(function (resp) {
        $scope.sheet = [];
        var err = new Error(resp.message);
        err.stack = resp.stack;
        notify.error(err);
      });
    };

    // This is bad, there should be a single event that triggers a refresh of data.

    // When the expression updates
    $scope.$watchMulti(['vis.params.expression', 'vis.params.interval'], $scope.search);

    // When the time filter changes
    $scope.$listen(timefilter, 'fetch', $scope.search);

    // When a filter is added to the filter bar?
    $scope.$listen(queryFilter, 'fetch', $scope.search);

    // When auto refresh happens
    $scope.$on('courier:searchRefresh', $scope.search);

    $scope.$on('fetch', $scope.search);

    $scope.$on('renderComplete', event => {
      event.stopPropagation();
      $element.trigger('renderComplete');
    });

  });
});
