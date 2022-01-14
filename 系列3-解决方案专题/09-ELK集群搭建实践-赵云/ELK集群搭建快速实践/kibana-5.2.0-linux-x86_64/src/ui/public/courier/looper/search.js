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

import FetchProvider from '../fetch';
import SearchStrategyProvider from '../fetch/strategy/search';
import RequestQueueProvider from '../_request_queue';
import LooperProvider from './_looper';

export default function SearchLooperService(Private, Promise, Notifier, $rootScope) {
  let fetch = Private(FetchProvider);
  let searchStrategy = Private(SearchStrategyProvider);
  let requestQueue = Private(RequestQueueProvider);

  let Looper = Private(LooperProvider);
  let notif = new Notifier({ location: 'Search Looper' });

  /**
   * The Looper which will manage the doc fetch interval
   * @type {Looper}
   */
  let searchLooper = new Looper(null, function () {
    $rootScope.$broadcast('courier:searchRefresh');
    return fetch.these(
      requestQueue.getInactive(searchStrategy)
    );
  });

  searchLooper.onHastyLoop = function () {
    if (searchLooper.afterHastyQueued) return;

    searchLooper.afterHastyQueued = Promise.resolve(searchLooper.active)
    .then(function () {
      return searchLooper._loopTheLoop();
    })
    .finally(function () {
      searchLooper.afterHastyQueued = null;
    });
  };

  return searchLooper;
};
