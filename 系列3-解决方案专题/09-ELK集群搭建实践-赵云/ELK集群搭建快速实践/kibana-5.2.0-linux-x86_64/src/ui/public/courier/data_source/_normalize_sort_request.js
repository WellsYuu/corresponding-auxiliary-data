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

export default function normalizeSortRequest(config) {
  let defaultSortOptions = config.get('sort:options');

  /**
   * Decorate queries with default parameters
   * @param {query} query object
   * @returns {object}
   */
  return function (sortObject, indexPattern) {
    let normalizedSort = [];

    // [].concat({}) -> [{}], [].concat([{}]) -> [{}]
    return [].concat(sortObject).map(function (sortable) {
      return normalize(sortable, indexPattern);
    });
  };

  /*
    Normalize the sort description to the more verbose format:
    { someField: "desc" } into { someField: { "order": "desc"}}
  */
  function normalize(sortable, indexPattern) {
    let normalized = {};
    let sortField = _.keys(sortable)[0];
    let sortValue = sortable[sortField];
    let indexField = indexPattern.fields.byName[sortField];

    if (indexField && indexField.scripted && indexField.sortable) {
      let direction;
      if (_.isString(sortValue)) direction = sortValue;
      if (_.isObject(sortValue) && sortValue.order) direction = sortValue.order;

      sortField = '_script';
      sortValue = {
        script: {
          inline: indexField.script,
          lang: indexField.lang
        },
        type: castSortType(indexField.type),
        order: direction
      };
    } else {
      if (_.isString(sortValue)) {
        sortValue = { order: sortValue };
      }
      sortValue = _.defaults({}, sortValue, defaultSortOptions);

      if (sortField === '_score') {
        delete sortValue.unmapped_type;
      }
    }

    normalized[sortField] = sortValue;
    return normalized;
  }
};

// The ES API only supports sort scripts of type 'number' and 'string'
function castSortType(type) {
  const typeCastings = {
    number: 'number',
    string: 'string',
    date: 'number',
    boolean: 'string'
  };

  const castedType = typeCastings[type];
  if (!castedType) {
    throw new Error(`Unsupported script sort type: ${type}`);
  }

  return castedType;
}
