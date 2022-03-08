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

'use strict';

module.exports = function (api) {
  api.addEndpointDescription('_field_stats', {
    methods: ['GET', 'POST'],
    patterns: ["_field_stats", "{indices}/_field_stats"],
    url_params: {
      fields: [],
      level: ["cluster", "indices"],
      ignore_unavailable: ["true", "false"],
      allow_no_indices: [false, true],
      expand_wildcards: ["open", "closed", "none", "all"]
    },
    data_autocomplete_rules: {
      fields: ["{field}"],
      index_constraints: {
        "{field}": {
          min_value: {
            gt: "MIN",
            gte: "MAX",
            lt: "MIN",
            lte: "MAX"
          },
          max_value: {
            gt: "MIN",
            gte: "MAX",
            lt: "MIN",
            lte: "MAX"
          }
        },
        __template: {
          "FIELD": {
            min_value: {
              gt: "MIN"
            },
            max_value: {
              lt: "MAX"
            }
          }
        }
      }
    }
  });
};
