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
 * Take a sorting array and make it into an object
 * @param {array} 2 item array [fieldToSort, directionToSort]
 * @param {object} indexPattern used for determining default sort
 * @returns {object} a sort object suitable for returning to elasticsearch
 */
function getSort(sort, indexPattern) {
  let sortObj = {};
  let field;
  let direction;

  function isSortable(field) {
    return (indexPattern.fields.byName[field] && indexPattern.fields.byName[field].sortable);
  }

  if (_.isArray(sort) && sort.length === 2 && isSortable(sort[0])) {
    // At some point we need to refact the sorting logic, this array sucks.
    field = sort[0];
    direction = sort[1];
  } else if (indexPattern.timeFieldName && isSortable(indexPattern.timeFieldName)) {
    field = indexPattern.timeFieldName;
    direction = 'desc';
  }

  if (field) {
    sortObj[field] = direction;
  } else {
    sortObj._score = 'desc';
  }



  return sortObj;
}

getSort.array = function (sort, indexPattern) {
  return _(getSort(sort, indexPattern)).pairs().pop();
};

export default getSort;
