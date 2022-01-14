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

import RequestQueueProvider from '../_request_queue';
import FetchTheseProvider from './fetch_these';
import CallResponseHandlersProvider from './call_response_handlers';
import ReqStatusProvider from './req_status';

export default function fetchService(Private, Promise) {

  const requestQueue = Private(RequestQueueProvider);
  const fetchThese = Private(FetchTheseProvider);

  const callResponseHandlers = Private(CallResponseHandlersProvider);
  const INCOMPLETE = Private(ReqStatusProvider).INCOMPLETE;

  function fetchQueued(strategy) {
    const requests = requestQueue.getStartable(strategy);
    if (!requests.length) return Promise.resolve();
    else return fetchThese(requests);
  }

  this.fetchQueued = fetchQueued;

  function fetchASource(source, strategy) {
    const defer = Promise.defer();

    fetchThese([
      source._createRequest(defer)
    ]);

    return defer.promise;
  }

  /**
   * Fetch a single doc source
   * @param {DocSource} source - The DocSource to request
   * @async
   */
  this.doc = fetchASource;

  /**
   * Fetch a single search source
   * @param {SearchSource} source - The SearchSource to request
   * @async
   */
  this.search = fetchASource;

  /**
   * Fetch a list of requests
   * @param {array} reqs - the requests to fetch
   * @async
   */
  this.these = fetchThese;

  /**
   * Send responses to a list of requests, used when requests
   * should be skipped (like when a doc is updated with an index).
   *
   * This logic is a simplified version of what fetch_these does, and
   * could have been added elsewhere, but I would rather the logic be
   * here than outside the courier/fetch module.
   *
   * @param {array[Request]} requests - the list of requests to respond to
   * @param {array[any]} responses - the list of responses for each request
   */
  this.fakeFetchThese = function (requests, responses) {
    return Promise.map(requests, function (req) {
      return req.start();
    })
    .then(function () {
      return callResponseHandlers(requests, responses);
    })
    .then(function (requestStates) {
      if (_.contains(requestStates, INCOMPLETE)) {
        throw new Error('responding to requests did not complete!');
      }
    });
  };
};
