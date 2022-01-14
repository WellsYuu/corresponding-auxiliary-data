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
import VislibComponentsLabelsDataArrayProvider from './data_array';
import VislibComponentsLabelsUniqLabelsProvider from './uniq_labels';
import VislibComponentsLabelsPiePieLabelsProvider from './pie/pie_labels';
export default function LabelUtilService(Private) {

  const createArr = Private(VislibComponentsLabelsDataArrayProvider);
  const getArrOfUniqLabels = Private(VislibComponentsLabelsUniqLabelsProvider);
  const getPieLabels = Private(VislibComponentsLabelsPiePieLabelsProvider);

  /*
   * Accepts a Kibana data object and returns an array of unique labels (strings).
   * Extracts the field formatter from the raw object and passes it to the
   * getArrOfUniqLabels function.
   *
   * Currently, this service is only used for vertical bar charts and line charts.
   */
  return function (obj, chartType) {
    if (!_.isObject(obj)) { throw new TypeError('LabelUtil expects an object'); }
    if (chartType === 'pie') { return getPieLabels(obj); }
    return getArrOfUniqLabels(createArr(obj));
  };
};
