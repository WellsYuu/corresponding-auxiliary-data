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

import IsRequestProvider from './is_request';
import ReqStatusProvider from './req_status';

export default function FetchMergeDuplicateRequests(Private) {
  const isRequest = Private(IsRequestProvider);
  const DUPLICATE = Private(ReqStatusProvider).DUPLICATE;

  function mergeDuplicateRequests(requests) {
    // dedupe requests
    const index = {};
    return requests.map(function (req) {
      if (!isRequest(req)) return req;

      const iid = req.source._instanceid;
      if (!index[iid]) {
        // this request is unique so far
        index[iid] = req;
        // keep the request
        return req;
      }

      // the source was requested at least twice
      req._uniq = index[iid];
      return DUPLICATE;
    });
  }

  return mergeDuplicateRequests;
};
