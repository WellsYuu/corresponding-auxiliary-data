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

import $ from 'jquery';
import { remove } from 'lodash';

import './kbn_chrome.less';
import UiModules from 'ui/modules';
import { isSystemApiRequest } from 'ui/system_api';
import {
  getUnhashableStatesProvider,
  unhashUrl,
} from 'ui/state_management/state_hashing';

export default function (chrome, internals) {

  UiModules
  .get('kibana')
  .directive('kbnChrome', $rootScope => {
    return {
      template($el) {
        const $content = $(require('./kbn_chrome.html'));
        const $app = $content.find('.application');

        if (internals.rootController) {
          $app.attr('ng-controller', internals.rootController);
        }

        if (internals.rootTemplate) {
          $app.removeAttr('ng-view');
          $app.html(internals.rootTemplate);
        }

        return $content;
      },

      controllerAs: 'chrome',
      controller($scope, $rootScope, $location, $http, Private) {
        const getUnhashableStates = Private(getUnhashableStatesProvider);

        // are we showing the embedded version of the chrome?
        internals.setVisibleDefault(!$location.search().embed);

        // listen for route changes, propogate to tabs
        const onRouteChange = function () {
          const urlWithHashes = window.location.href;
          const urlWithStates = unhashUrl(urlWithHashes, getUnhashableStates());
          internals.trackPossibleSubUrl(urlWithStates);
        };

        $rootScope.$on('$routeChangeSuccess', onRouteChange);
        $rootScope.$on('$routeUpdate', onRouteChange);
        onRouteChange();

        const allPendingHttpRequests = () => $http.pendingRequests;
        const removeSystemApiRequests = (pendingHttpRequests = []) => remove(pendingHttpRequests, isSystemApiRequest);
        $scope.$watchCollection(allPendingHttpRequests, removeSystemApiRequests);

        // and some local values
        chrome.httpActive = $http.pendingRequests;
        $scope.notifList = require('ui/notify')._notifs;

        return chrome;
      }
    };
  });

}
