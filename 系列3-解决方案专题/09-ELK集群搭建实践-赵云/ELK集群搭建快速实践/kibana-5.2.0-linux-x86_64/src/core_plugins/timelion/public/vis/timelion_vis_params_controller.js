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
  require('plugins/timelion/directives/expression_directive');

  var module = require('ui/modules').get('kibana/timelion_vis', ['kibana']);
  module.controller('TimelionVisParamsController', function ($scope, $rootScope) {
    $scope.vis.params.expression = $scope.vis.params.expression || '.es(*)';
    $scope.vis.params.interval = $scope.vis.params.interval || '1m';


    $scope.search = function () {
      $rootScope.$broadcast('courier:searchRefresh');
    };
  });
});
