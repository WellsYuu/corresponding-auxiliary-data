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

import ReqStatusProvider from './req_status';

export default function CourierFetchContinueIncompleteRequests(Private) {
  const INCOMPLETE = Private(ReqStatusProvider).INCOMPLETE;

  function continueIncompleteRequests(strategy, requests, responses, fetchWithStrategy) {
    const incomplete = [];

    responses.forEach(function (resp, i) {
      if (resp === INCOMPLETE) {
        incomplete.push(requests[i]);
      }
    });

    if (!incomplete.length) return responses;

    return fetchWithStrategy(strategy, incomplete)
    .then(function (completedResponses) {
      return responses.map(function (prevResponse) {
        if (prevResponse !== INCOMPLETE) return prevResponse;
        return completedResponses.shift();
      });
    });
  }

  return continueIncompleteRequests;
};
