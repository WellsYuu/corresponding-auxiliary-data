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
import reqRespStatsHTML from 'plugins/spy_modes/req_resp_stats_spy_mode.html';

const linkReqRespStats = function ($scope, config) {
  $scope.$bind('req', 'searchSource.history[searchSource.history.length - 1]');
  $scope.$watchMulti([
    'req',
    'req.started',
    'req.stopped',
    'searchSource'
  ], function () {
    if (!$scope.searchSource || !$scope.req) return;

    const req = $scope.req;
    const resp = $scope.req.resp;
    const stats = $scope.stats = [];

    if (resp && resp.took != null) stats.push(['Query Duration', resp.took + 'ms']);
    if (req && req.ms != null) stats.push(['Request Duration', req.ms + 'ms']);
    if (resp && resp.hits) stats.push(['Hits', resp.hits.total]);

    if (req.fetchParams) {
      if (req.fetchParams.index) stats.push(['Index', req.fetchParams.index]);
      if (req.fetchParams.type) stats.push(['Type', req.fetchParams.type]);
      if (req.fetchParams.id) stats.push(['Id', req.fetchParams.id]);
    }
  });
};

require('ui/registry/spy_modes')
.register(function () {
  return {
    name: 'request',
    display: 'Request',
    order: 2,
    template: reqRespStatsHTML,
    link: linkReqRespStats
  };
})
.register(function () {
  return {
    name: 'response',
    display: 'Response',
    order: 3,
    template: reqRespStatsHTML,
    link: linkReqRespStats
  };
})
.register(function () {
  return {
    name: 'stats',
    display: 'Statistics',
    order: 4,
    template: reqRespStatsHTML,
    link: linkReqRespStats
  };
});
