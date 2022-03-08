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
export default function AggResponseBucketsProvider() {

  function Buckets(aggResp) {
    aggResp = aggResp || false;
    this.buckets = aggResp.buckets || [];
    this.objectMode = _.isPlainObject(this.buckets);

    if (this.objectMode) {
      this._keys = _.keys(this.buckets);
      this.length = this._keys.length;
    } else {
      this.length = this.buckets.length;
    }
  }

  Buckets.prototype.forEach = function (fn) {
    let buckets = this.buckets;

    if (this.objectMode) {
      this._keys.forEach(function (key) {
        fn(buckets[key], key);
      });
    } else {
      buckets.forEach(function (bucket) {
        fn(bucket, bucket.key);
      });
    }
  };

  return Buckets;
};
