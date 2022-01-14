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
export default function ZeroFillDataArrayUtilService(Private) {

  /*
   * Accepts an array of zero-filled y value objects (arr1)
   * and a kibana data.series[i].values array of objects (arr2).
   * Return a zero-filled array of objects (arr1).
   */

  return function (arr1, arr2) {
    if (!_.isArray(arr1) || !_.isArray(arr2)) {
      throw new TypeError('ZeroFillDataArrayUtilService expects 2 arrays');
    }

    let i;
    let val;
    let index;
    const max = arr2.length;

    const getX = function (d) {
      return d.x === val.x;
    };

    for (i = 0; i < max; i++) {
      val = arr2[i];
      index = _.findIndex(arr1, getX);
      arr1.splice(index, 1, val);
    }

    return arr1;
  };
};
