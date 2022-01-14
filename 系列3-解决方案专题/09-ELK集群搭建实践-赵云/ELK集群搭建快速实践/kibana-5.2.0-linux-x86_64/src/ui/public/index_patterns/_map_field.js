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
import IndexPatternsCastMappingTypeProvider from 'ui/index_patterns/_cast_mapping_type';
export default function MapFieldFn(Private, config) {
  let castMappingType = Private(IndexPatternsCastMappingTypeProvider);

  /**
   * Accepts a field object and its name, and tries to give it a mapping
   * @param  {Object} field - the field mapping returned by elasticsearch
   * @param  {String} type - name of the field
   * @return {Object} - the resulting field after overrides and tweaking
   */
  return function mapField(field, name) {
    let keys = Object.keys(field.mapping);
    if (keys.length === 0 || (name[0] === '_') && !_.contains(config.get('metaFields'), name)) return;

    // Override the mapping, even if elasticsearch says otherwise
    let mappingOverrides = {
      _source: { type: '_source' },
      _index: { type: 'string' },
      _type: { type: 'string' },
      _id: { type: 'string' },
      _timestamp: {
        type: 'date',
        indexed: true
      },
      _score: {
        type: 'number',
        indexed: false
      }
    };

    let mapping = _.cloneDeep(field.mapping[keys.shift()]);

    if (!mapping.index || mapping.index === 'no') {
      // elasticsearch responds with false sometimes and 'no' others
      mapping.indexed = false;
    } else {
      mapping.indexed = true;
    }

    mapping.analyzed = mapping.index === 'analyzed' || mapping.type === 'text';

    mapping.type = castMappingType(mapping.type);

    if (mappingOverrides[name]) {
      _.merge(mapping, mappingOverrides[name]);
    }

    return mapping;
  };
};
