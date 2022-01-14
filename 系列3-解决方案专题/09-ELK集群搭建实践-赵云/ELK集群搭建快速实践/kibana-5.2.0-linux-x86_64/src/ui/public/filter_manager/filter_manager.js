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
import FilterBarQueryFilterProvider from 'ui/filter_bar/query_filter';
import { buildInlineScriptForPhraseFilter } from './lib/phrase';

// Adds a filter to a passed state
export default function (Private) {
  let queryFilter = Private(FilterBarQueryFilterProvider);
  let filterManager = {};

  filterManager.add = function (field, values, operation, index) {
    values = _.isArray(values) ? values : [values];
    let fieldName = _.isObject(field) ? field.name : field;
    let filters = _.flatten([queryFilter.getAppFilters()]);
    let newFilters = [];

    let negate = (operation === '-');

    // TODO: On array fields, negating does not negate the combination, rather all terms
    _.each(values, function (value) {
      let filter;
      let existing = _.find(filters, function (filter) {
        if (!filter) return;

        if (fieldName === '_exists_' && filter.exists) {
          return filter.exists.field === value;
        }

        if (_.get(filter, 'query.match')) {
          return filter.query.match[fieldName] && filter.query.match[fieldName].query === value;
        }

        if (filter.script) {
          return filter.meta.field === fieldName && filter.script.script.params.value === value;
        }
      });

      if (existing) {
        existing.meta.disabled = false;
        if (existing.meta.negate !== negate) {
          queryFilter.invertFilter(existing);
        }
        return;
      }

      switch (fieldName) {
        case '_exists_':
          filter = {
            meta: {
              negate: negate,
              index: index
            },
            exists: {
              field: value
            }
          };
          break;
        default:
          if (field.scripted) {
            filter = {
              meta: { negate: negate, index: index, field: fieldName },
              script: {
                script: {
                  inline: buildInlineScriptForPhraseFilter(field),
                  lang: field.lang,
                  params: {
                    value: value
                  }
                }
              }
            };
          } else {
            filter = { meta: { negate: negate, index: index }, query: { match: {} } };
            filter.query.match[fieldName] = { query: value, type: 'phrase' };
          }

          break;
      }

      newFilters.push(filter);
    });

    return queryFilter.addFilters(newFilters);
  };

  return filterManager;
};

