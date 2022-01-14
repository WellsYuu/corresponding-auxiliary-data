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

import { saveAs } from '@spalger/filesaver';
import _ from 'lodash';
import 'ui/agg_table';
import AggResponseTabifyTabifyProvider from 'ui/agg_response/tabify/tabify';
import tableSpyModeTemplate from 'plugins/spy_modes/table_spy_mode.html';
function VisSpyTableProvider(Notifier, $filter, $rootScope, config, Private) {
  const tabifyAggResponse = Private(AggResponseTabifyTabifyProvider);

  const PER_PAGE_DEFAULT = 10;


  return {
    name: 'table',
    display: 'Table',
    order: 1,
    template: tableSpyModeTemplate,
    link: function tableLinkFn($scope, $el) {
      $rootScope.$watchMulti.call($scope, [
        'vis',
        'esResp'
      ], function () {
        if (!$scope.vis || !$scope.esResp) {
          $scope.table = null;
        } else {
          if (!$scope.spy.params.spyPerPage) {
            $scope.spy.params.spyPerPage = PER_PAGE_DEFAULT;
          }

          $scope.table = tabifyAggResponse($scope.vis, $scope.esResp, {
            canSplit: false,
            asAggConfigResults: true,
            partialRows: true
          });
        }
      });
    }
  };
}

require('ui/registry/spy_modes').register(VisSpyTableProvider);
