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

export default function FetchForEachRequestStrategy(Private, Promise) {
  function forEachStrategy(requests, block) {
    block = Promise.method(block);
    const sets = [];

    requests.forEach(function (req) {
      const strategy = req.strategy;
      const set = _.find(sets, { 0: strategy });
      if (set) set[1].push(req);
      else sets.push([strategy, [req]]);
    });

    return Promise.all(sets.map(function (set) {
      return block(set[0], set[1]);
    }));
  }

  return forEachStrategy;
};
