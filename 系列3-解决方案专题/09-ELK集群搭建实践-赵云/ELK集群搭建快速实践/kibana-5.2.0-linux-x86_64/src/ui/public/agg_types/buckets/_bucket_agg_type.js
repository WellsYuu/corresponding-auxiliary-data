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
import AggTypesAggTypeProvider from 'ui/agg_types/agg_type';
export default function BucketAggTypeProvider(Private) {
  let AggType = Private(AggTypesAggTypeProvider);

  _.class(BucketAggType).inherits(AggType);
  function BucketAggType(config) {
    BucketAggType.Super.call(this, config);

    if (_.isFunction(config.getKey)) {
      this.getKey = config.getKey;
    }
  }

  BucketAggType.prototype.getKey = function (bucket, key) {
    return key || bucket.key;
  };

  return BucketAggType;
};
