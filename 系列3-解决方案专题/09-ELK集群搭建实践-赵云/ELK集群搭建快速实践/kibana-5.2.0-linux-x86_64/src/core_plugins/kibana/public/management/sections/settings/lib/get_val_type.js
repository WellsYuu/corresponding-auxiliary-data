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

/**
 * @param {object} advanced setting definition object
 * @param {?} current value of the setting
 * @returns {string} the type to use for determining the display and editor
 */
function getValType(def, value) {
  if (def.type) {
    return def.type;
  }

  if (_.isArray(value) || _.isArray(def.value)) {
    return 'array';
  }

  return (def.value != null ? typeof def.value : typeof value);
}

export default getValType;
