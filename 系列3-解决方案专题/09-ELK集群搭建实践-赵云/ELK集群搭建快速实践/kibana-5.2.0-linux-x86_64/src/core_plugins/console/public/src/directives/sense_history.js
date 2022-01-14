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

var { assign, memoize } = require('lodash');
let moment = require('moment');

var history = require('../history');
require('./sense_history_viewer');

require('ui/modules')
.get('app/sense')
.directive('senseHistory', function () {
  return {
    restrict: 'E',
    template: require('./history.html'),
    controllerAs: 'history',
    controller: function ($scope, $element) {
      this.reqs = history.getHistory();
      this.selectedReq = this.reqs[0];
      this.viewingReq = this.selectedReq;

      // calculate the text description of a request
      this.describeReq = memoize((req) => {
        const endpoint = req.endpoint;
        const date = moment(req.time);

        let formattedDate = date.format("MMM D");
        if (date.diff(moment(), "days") > -7) {
          formattedDate = date.fromNow();
        }

        return `${endpoint} (${formattedDate})`;
      });
      this.describeReq.cache = new WeakMap();

      // main actions
      this.clear = () => {
        history.clearHistory($element);
        $scope.kbnTopNav.close();
      };

      this.restore = (req = this.selectedReq) => {
        history.restoreFromHistory(req);
        $scope.kbnTopNav.close();
      };
    }
  };
});
