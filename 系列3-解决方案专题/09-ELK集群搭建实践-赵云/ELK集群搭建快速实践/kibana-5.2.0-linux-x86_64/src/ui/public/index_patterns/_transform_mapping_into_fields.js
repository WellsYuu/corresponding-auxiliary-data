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
import IndexPatternsMapFieldProvider from 'ui/index_patterns/_map_field';
import { ConflictTracker } from 'ui/index_patterns/_conflict_tracker';

export default function transformMappingIntoFields(Private, kbnIndex, config) {
  let mapField = Private(IndexPatternsMapFieldProvider);

  /**
   * Convert the ES response into the simple map for fields to
   * mappings which we will cache
   *
   * @param  {object} response - complex, excessively nested
   *                           object returned from ES
   * @return {object} - simple object that works for all of kibana
   *                    use-cases
   */
  return function (response) {
    let fields = {};
    const conflictTracker = new ConflictTracker();

    _.each(response, function (index, indexName) {
      if (indexName === kbnIndex) return;
      _.each(index.mappings, function (mappings) {
        _.each(mappings, function (field, name) {
          let keys = Object.keys(field.mapping);
          if (keys.length === 0 || (name[0] === '_') && !_.contains(config.get('metaFields'), name)) return;

          let mapping = mapField(field, name);
          // track the name, type and index for every field encountered so that the source
          // of conflicts can be described later
          conflictTracker.trackField(name, mapping.type, indexName);

          if (fields[name]) {
            if (fields[name].type !== mapping.type) {
              // conflict fields are not available for much except showing in the discover table
              // overwrite the entire mapping object to reset all fields
              fields[name] = { type: 'conflict' };
            }
          } else {
            fields[name] = _.pick(mapping, 'type', 'indexed', 'analyzed', 'doc_values');
          }
        });
      });
    });

    config.get('metaFields').forEach(function (meta) {
      if (fields[meta]) return;

      let field = { mapping: {} };
      field.mapping[meta] = {};
      fields[meta] = mapField(field, meta);
    });

    return _.map(fields, function (mapping, name) {
      mapping.name = name;

      if (mapping.type === 'conflict') {
        mapping.conflictDescriptions = conflictTracker.describeConflict(name);
      }

      return mapping;
    });
  };
};
