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

import { RequestFailure, SearchTimeout, ShardFailure } from 'ui/errors';

import ReqStatusProvider from './req_status';
import NotifierProvider from './notifier';

export default function CourierFetchCallResponseHandlers(Private, Promise) {
  const ABORTED = Private(ReqStatusProvider).ABORTED;
  const INCOMPLETE = Private(ReqStatusProvider).INCOMPLETE;
  const notify = Private(NotifierProvider);


  function callResponseHandlers(requests, responses) {
    return Promise.map(requests, function (req, i) {
      if (req === ABORTED || req.aborted) {
        return ABORTED;
      }

      let resp = responses[i];

      if (resp.timed_out) {
        notify.warning(new SearchTimeout());
      }

      if (resp._shards && resp._shards.failed) {
        notify.warning(new ShardFailure(resp));
      }

      function progress() {
        if (req.isIncomplete()) {
          return INCOMPLETE;
        }

        req.complete();
        return resp;
      }

      if (resp.error) {
        if (req.filterError(resp)) {
          return progress();
        } else {
          return req.handleFailure(new RequestFailure(null, resp));
        }
      }

      return Promise.try(function () {
        return req.transformResponse(resp);
      })
      .then(function () {
        resp = arguments[0];
        return req.handleResponse(resp);
      })
      .then(progress);
    });
  }

  return callResponseHandlers;
};
