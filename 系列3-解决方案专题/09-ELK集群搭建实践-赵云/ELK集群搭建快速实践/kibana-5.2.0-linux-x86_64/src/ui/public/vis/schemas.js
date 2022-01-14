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
import IndexedArray from 'ui/indexed_array';
import AggTypesAggParamsProvider from 'ui/agg_types/agg_params';
export default function VisTypeSchemasFactory(Private) {
  let AggParams = Private(AggTypesAggParamsProvider);

  function Schemas(schemas) {
    let self = this;

    _(schemas || [])
    .map(function (schema) {
      if (!schema.name) throw new Error('all schema must have a unique name');

      if (schema.name === 'split') {
        schema.params = [
          {
            name: 'row',
            default: true
          }
        ];
        schema.editor = require('plugins/kbn_vislib_vis_types/controls/rows_or_columns.html');
      } else if (schema.name === 'radius') {
        schema.editor = require('plugins/kbn_vislib_vis_types/controls/radius_ratio_option.html');
      }

      _.defaults(schema, {
        min: 0,
        max: Infinity,
        group: 'buckets',
        title: schema.name,
        aggFilter: '*',
        editor: false,
        params: [],
        deprecate: false
      });

      // convert the params into a params registry
      schema.params = new AggParams(schema.params);

      return schema;
    })
    .tap(function (schemas) {
      self.all = new IndexedArray({
        index: ['name'],
        group: ['group'],
        immutable: true,
        initialSet: schemas
      });
    })
    .groupBy('group')
    .forOwn(function (group, groupName) {
      self[groupName] = new IndexedArray({
        index: ['name'],
        immutable: true,
        initialSet: group
      });
    })
    .commit();
  }

  return Schemas;
};
