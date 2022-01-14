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
import VislibComponentsLabelsPieRemoveZeroSlicesProvider from './remove_zero_slices';
import VislibComponentsLabelsPieGetPieNamesProvider from './get_pie_names';

export default function PieLabels(Private) {
  const removeZeroSlices = Private(VislibComponentsLabelsPieRemoveZeroSlicesProvider);
  const getNames = Private(VislibComponentsLabelsPieGetPieNamesProvider);

  return function (obj) {
    if (!_.isObject(obj)) { throw new TypeError('PieLabel expects an object'); }

    const data = obj.columns || obj.rows || [obj];
    const names = [];

    data.forEach(function (obj) {
      const columns = obj.raw ? obj.raw.columns : undefined;
      obj.slices = removeZeroSlices(obj.slices);

      getNames(obj, columns).forEach(function (name) {
        names.push(name);
      });
    });

    return _.uniq(names);
  };
};
