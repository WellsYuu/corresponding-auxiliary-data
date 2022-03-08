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

import _ from 'lodash';
import angular from 'angular';

import { toJson } from 'ui/utils/aggressive_parse';

export default function FetchStrategyForSearch(Private, Promise, timefilter, kbnIndex, sessionId) {

  return {
    clientMethod: 'msearch',

    /**
     * Flatten a series of requests into as ES request body
     *
     * @param  {array} requests - the requests to serialize
     * @return {Promise} - a promise that is fulfilled by the request body
     */
    reqsFetchParamsToBody: function (reqsFetchParams) {
      return Promise.map(reqsFetchParams, function (fetchParams) {
        return Promise.resolve(fetchParams.index)
        .then(function (indexList) {
          if (!_.isFunction(_.get(indexList, 'toIndexList'))) {
            return indexList;
          }

          const timeBounds = timefilter.getBounds();
          return indexList.toIndexList(timeBounds.min, timeBounds.max);
        })
        .then(function (indexList) {
          let body = fetchParams.body || {};
          // If we've reached this point and there are no indexes in the
          // index list at all, it means that we shouldn't expect any indexes
          // to contain the documents we're looking for, so we instead
          // perform a request for an index pattern that we know will always
          // return an empty result (ie. -*). If instead we had gone ahead
          // with an msearch without any index patterns, elasticsearch would
          // handle that request by querying *all* indexes, which is the
          // opposite of what we want in this case.
          if (_.isArray(indexList) && indexList.length === 0) {
            indexList.push(kbnIndex);
            body = emptySearch();
          }
          return angular.toJson({
            index: indexList,
            type: fetchParams.type,
            search_type: fetchParams.search_type,
            ignore_unavailable: true,
            preference: sessionId,
          })
          + '\n'
          + toJson(body, angular.toJson);
        });
      })
      .then(function (requests) {
        return requests.join('\n') + '\n';
      });
    },

    /**
     * Fetch the multiple responses from the ES Response
     * @param  {object} resp - The response sent from Elasticsearch
     * @return {array} - the list of responses
     */
    getResponses: function (resp) {
      return resp.responses;
    }
  };
};

function emptySearch() {
  return {
    query: {
      bool: {
        must_not: [
          { match_all: {} }
        ]
      }
    }
  };
}
