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
  api.addEndpointDescription('_count', {
    methods: ['GET', 'POST'],
    priority: 10, // collides with get doc by id
    patterns: ["{indices}/{types}/_count", "{indices}/_count", "_count"],
    url_params: {
      preference: ["_primary", "_primary_first", "_local", "_only_node:xyz", "_prefer_node:xyz", "_shards:2,3"],
      routing: "",
      min_score: 1.0,
      terminate_after: 10
    },
    data_autocomplete_rules: {
      query: {
        // populated by a global rule
      }
    }
  });
};
