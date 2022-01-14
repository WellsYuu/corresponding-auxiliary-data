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

function WorkQueue() {
  let q = this;

  let work = [];
  let fullDefers = [];

  q.limit = 0;
  Object.defineProperty(q, 'length', {
    get: function () {
      return work.length;
    }
  });

  let resolve = function (defers) {
    return defers.splice(0).map(function (defer) {
      return defer.resolve();
    });
  };

  let checkIfFull = function () {
    if (work.length >= q.limit && fullDefers.length) {
      resolve(fullDefers);
    }
  };

  q.resolveWhenFull = function (defer) {
    fullDefers.push(defer);
    checkIfFull();
  };

  q.doWork = function () {
    let resps = resolve(work);
    checkIfFull();
    return resps;
  };

  q.empty = function () {
    work.splice(0);
    checkIfFull();
  };

  q.push = function (defer) {
    work.push(defer);
    checkIfFull();
  };
}

export default WorkQueue;
