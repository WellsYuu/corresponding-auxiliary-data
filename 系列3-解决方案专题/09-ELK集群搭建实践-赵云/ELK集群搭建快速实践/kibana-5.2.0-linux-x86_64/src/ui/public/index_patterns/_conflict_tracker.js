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

import { uniq, where, groupBy, mapValues, pluck } from 'lodash';

export class ConflictTracker {
  constructor() {
    this._history = [];
  }

  trackField(name, type, index) {
    this._history.push({ name, type, index });
  }

  describeConflict(name) {
    const fieldHistory = where(this._history, { name });
    const entriesByType = groupBy(fieldHistory, 'type');

    return mapValues(entriesByType, (entries) => {
      const indices = uniq(pluck(entries, 'index'));

      // keep the list short so we don't polute the .kibana index
      if (indices.length > 10) {
        const total = indices.length;
        indices.length = 9;
        indices.push(`... and ${total - indices.length} others`);
      }

      return indices;
    });
  }
};
