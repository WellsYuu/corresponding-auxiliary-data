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

define(function () {
  let i = 0;

  function AggConfigResult(aggConfig, parent, value, key) {
    this.key = key;
    this.value = value;
    this.aggConfig = aggConfig;
    this.$parent = parent;
    this.$order = ++i;

    if (aggConfig.schema.group === 'buckets') {
      this.type = 'bucket';
    } else {
      this.type = 'metric';
    }
  }

  /**
   * Returns an array of the aggConfigResult and parents up the branch
   * @returns {array} Array of aggConfigResults
   */
  AggConfigResult.prototype.getPath = function () {
    return (function walk(result, path) {
      path.unshift(result);
      if (result.$parent) return walk(result.$parent, path);
      return path;
    }(this, []));
  };

  /**
   * Returns an Elasticsearch filter that represents the result.
   * @returns {object} Elasticsearch filter
   */
  AggConfigResult.prototype.createFilter = function () {
    return this.aggConfig.createFilter(this.key);
  };

  AggConfigResult.prototype.toString = function (contentType) {
    return this.aggConfig.fieldFormatter(contentType)(this.value);
  };

  AggConfigResult.prototype.valueOf = function () {
    return this.value;
  };

  return AggConfigResult;
});
