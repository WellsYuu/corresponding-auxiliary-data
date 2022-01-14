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
export default function PendingRequestList() {

  /**
   * Queue of pending requests, requests are removed as
   * they are processed by fetch.[sourceType]().
   * @type {Array}
   */
  let queue = [];

  queue.getInactive = function (/* strategies */) {
    return queue.get.apply(queue, arguments)
    .filter(function (req) {
      return !req.started;
    });
  };

  queue.getStartable = function (...strategies) {
    return queue.get(...strategies).filter(req => req.canStart());
  };

  queue.get = function (...strategies) {
    return queue.filter(function (req) {
      let strategyMatch = !strategies.length;
      if (!strategyMatch) {
        strategyMatch = strategies.some(function (strategy) {
          return req.strategy === strategy;
        });
      }

      return strategyMatch;
    });
  };

  return queue;
};
