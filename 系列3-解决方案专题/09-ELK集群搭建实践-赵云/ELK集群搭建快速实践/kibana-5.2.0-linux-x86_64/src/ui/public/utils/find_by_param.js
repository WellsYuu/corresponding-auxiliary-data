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
// given an object or array of objects, return the value of the passed param
// if the param is missing, return undefined
export default function findByParam(values, param) {
  if (_.isArray(values)) { // point series chart
    let index = _.findIndex(values, param);
    if (index === -1) return;
    return values[index][param];
  }
  return values[param]; // pie chart
};