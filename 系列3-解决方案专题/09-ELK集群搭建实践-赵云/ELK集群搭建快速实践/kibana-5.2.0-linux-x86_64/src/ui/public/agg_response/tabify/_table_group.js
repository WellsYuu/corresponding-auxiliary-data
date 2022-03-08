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
export default function TableGroupProvider() {

  /**
   * Simple object that wraps multiple tables. It contains information about the aggConfig
   * and bucket that created this group and a list of the tables within it.
   */
  function TableGroup() {
    this.aggConfig = null;
    this.key = null;
    this.title = null;
    this.tables = [];
  }

  TableGroup.prototype.field = function () {
    if (this.aggConfig) return this.aggConfig.getField();
  };

  TableGroup.prototype.fieldFormatter = function () {
    if (this.aggConfig) return this.aggConfig.fieldFormatter();
  };

  return TableGroup;
};
