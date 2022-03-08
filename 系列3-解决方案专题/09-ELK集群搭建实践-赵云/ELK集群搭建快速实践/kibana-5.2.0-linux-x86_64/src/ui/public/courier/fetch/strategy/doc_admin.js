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

export default function FetchStrategyForDoc(Promise) {
  return {
    id: 'doc_admin',
    clientMethod: 'mget',

    /**
     * Flatten a series of requests into as ES request body
     * @param  {array} requests - an array of flattened requests
     * @return {Promise} - a promise that is fulfilled by the request body
     */
    reqsFetchParamsToBody: function (reqsFetchParams) {
      return Promise.resolve({
        docs: reqsFetchParams
      });
    },

    /**
     * Fetch the multiple responses from the ES Response
     * @param  {object} resp - The response sent from Elasticsearch
     * @return {array} - the list of responses
     */
    getResponses: function (resp) {
      return resp.docs;
    }
  };
}
