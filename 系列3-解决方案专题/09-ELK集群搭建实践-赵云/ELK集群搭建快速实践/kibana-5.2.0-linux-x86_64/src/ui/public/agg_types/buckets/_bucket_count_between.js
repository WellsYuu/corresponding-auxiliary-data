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

export default function BucketCountBetweenProvider() {

  /**
   * Count the number of bucket aggs between two agg config objects owned
   * by the same vis.
   *
   * If one of the two aggs was not found in the agg list, returns null.
   * If a was found after b, the count will be negative
   * If a was found first, the count will be positive.
   *
   * @param  {AggConfig} aggConfigA - the aggConfig that is expected first
   * @param  {AggConfig} aggConfigB - the aggConfig that is expected second
   * @return {null|number}
   */
  function bucketCountBetween(aggConfigA, aggConfigB) {
    let aggs = aggConfigA.vis.aggs.getRequestAggs();

    let aIndex = aggs.indexOf(aggConfigA);
    let bIndex = aggs.indexOf(aggConfigB);

    if (aIndex === -1 || bIndex === -1) {
      return null;
    }

    // return a negative distance, if b is before a
    let negative = (aIndex > bIndex);

    let count = aggs
      .slice(Math.min(aIndex, bIndex), Math.max(aIndex, bIndex))
      .reduce(function (count, cfg) {
        if (cfg === aggConfigA || cfg === aggConfigB || cfg.schema.group !== 'buckets') {
          return count;
        } else {
          return count + 1;
        }
      }, 0);

    return (negative ? -1 : 1) * count;
  }

  return bucketCountBetween;
};
