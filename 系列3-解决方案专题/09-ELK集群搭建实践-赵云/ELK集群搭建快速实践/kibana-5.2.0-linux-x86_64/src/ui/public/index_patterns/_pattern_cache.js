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
export default function PatternCache() {

  let vals = {};

  let validId = function (id) {
    return typeof id !== 'object';
  };

  this.get = function (id) {
    if (validId(id)) return vals[id];
  };

  this.set = function (id, prom) {
    if (validId(id)) vals[id] = prom;
    return prom;
  };

  this.clear = this.delete = function (id) {
    if (validId(id)) delete vals[id];
  };
};
