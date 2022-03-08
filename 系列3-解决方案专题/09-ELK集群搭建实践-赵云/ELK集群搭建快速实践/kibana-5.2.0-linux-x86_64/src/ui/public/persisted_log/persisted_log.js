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

import modules from 'ui/modules';
import _ from 'lodash';

modules.get('kibana/persisted_log')
.factory('PersistedLog', function ($window, localStorage) {
  function PersistedLog(name, options) {
    options = options || {};
    this.name = name;
    this.maxLength = parseInt(options.maxLength, 10);
    this.filterDuplicates = options.filterDuplicates || false;
    this.items = localStorage.get(this.name) || [];
    if (!isNaN(this.maxLength)) this.items = _.take(this.items, this.maxLength);
  }

  PersistedLog.prototype.add = function (val) {
    if (val == null) {
      return this.items;
    }

    // remove any matching items from the stack if option is set
    if (this.filterDuplicates) {
      _.remove(this.items, function (item) {
        return _.isEqual(item, val);
      });
    }

    this.items.unshift(val);

    // if maxLength is set, truncate the stack
    if (!isNaN(this.maxLength)) this.items = _.take(this.items, this.maxLength);

    // persist the stack
    localStorage.set(this.name, this.items);
    return this.items;
  };

  PersistedLog.prototype.get = function () {
    return this.items;
  };

  return PersistedLog;
});
