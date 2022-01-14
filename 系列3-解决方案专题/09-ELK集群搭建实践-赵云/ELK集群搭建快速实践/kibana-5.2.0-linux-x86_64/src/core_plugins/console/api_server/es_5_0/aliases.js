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

'use strict';

module.exports = function (api) {
  api.addEndpointDescription('_post_aliases', {
    methods: ['POST'],
    patterns: ["_aliases"],
    data_autocomplete_rules: {
      'actions': {
        __template: [{ 'add': { 'index': 'test1', 'alias': 'alias1' } }],
        __any_of: [{
          add: {
            index: '{index}',
            alias: '',
            filter: {},
            routing: '1',
            search_routing: '1,2',
            index_routing: '1'
          },
          remove: {
            index: '',
            alias: ''
          }
        }]
      }
    }
  });
  api.addEndpointDescription('_get_aliases', {
    methods: ['GET'],
    patterns: ["_aliases"]
  });

  var aliasRules = {
    filter: {},
    routing: '1',
    search_routing: '1,2',
    index_routing: '1'
  };

  api.addEndpointDescription('_post_alias', {
    methods: ["POST", "PUT"],
    patterns: ["{indices}/_alias/{name}"],
    data_autocomplete_rules: aliasRules
  });
  api.addEndpointDescription('_delete_alias', {
    methods: ["DELETE"],
    patterns: ["{indices}/_alias/{name}"]
  });
  api.addEndpointDescription('_get_alias', {
    methods: ["GET"],
    patterns: ["_alias", "{indices}/_alias", "{indices}/_alias/{name}", "_alias/{name}"]
  });

  api.addGlobalAutocompleteRules('aliases', {
    '*': aliasRules
  });
};
