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

import NotifierProvider from './notifier';
import ForEachStrategyProvider from './for_each_strategy';
import CallClientProvider from './call_client';
import CallResponseHandlersProvider from './call_response_handlers';
import ContinueIncompleteProvider from './continue_incomplete';
import ReqStatusProvider from './req_status';

export default function FetchTheseProvider(Private, Promise) {
  const notify = Private(NotifierProvider);
  const forEachStrategy = Private(ForEachStrategyProvider);

  // core tasks
  const callClient = Private(CallClientProvider);
  const callResponseHandlers = Private(CallResponseHandlersProvider);
  const continueIncomplete = Private(ContinueIncompleteProvider);

  const ABORTED = Private(ReqStatusProvider).ABORTED;
  const DUPLICATE = Private(ReqStatusProvider).DUPLICATE;
  const INCOMPLETE = Private(ReqStatusProvider).INCOMPLETE;

  function fetchThese(requests) {
    return forEachStrategy(requests, function (strategy, reqsForStrategy) {
      return fetchWithStrategy(strategy, reqsForStrategy.map(function (req) {
        if (!req.started) return req;
        return req.retry();
      }));
    })
    .catch(notify.fatal);
  }

  function fetchWithStrategy(strategy, requests) {
    function replaceAbortedRequests() {
      requests = requests.map(r => r.aborted ? ABORTED : r);
    }

    replaceAbortedRequests();
    return startRequests(requests)
    .then(function () {
      replaceAbortedRequests();
      return callClient(strategy, requests);
    })
    .then(function (responses) {
      replaceAbortedRequests();
      return callResponseHandlers(requests, responses);
    })
    .then(function (responses) {
      replaceAbortedRequests();
      return continueIncomplete(strategy, requests, responses, fetchWithStrategy);
    })
    .then(function (responses) {
      replaceAbortedRequests();
      return responses.map(function (resp) {
        switch (resp) {
          case ABORTED:
            return null;
          case DUPLICATE:
          case INCOMPLETE:
            throw new Error('Failed to clear incomplete or duplicate request from responses.');
          default:
            return resp;
        }
      });
    });
  }

  function startRequests(requests) {
    return Promise.map(requests, function (req) {
      if (req === ABORTED) {
        return req;
      }

      return new Promise(function (resolve) {
        const action = req.started ? req.continue : req.start;
        resolve(action.call(req));
      })
      .catch(err => req.handleFailure(err));
    });
  }

  return fetchThese;
};
