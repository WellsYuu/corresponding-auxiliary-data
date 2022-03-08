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
  api.addGlobalAutocompleteRules('highlight', {
    pre_tags: {},
    post_tags: {},
    tags_schema: {},
    fields: {
      '{field}': {
        fragment_size: 20,
        number_of_fragments: 3
      }
    }
  });

  api.addGlobalAutocompleteRules('script', {
    __template: {
      inline: "SCRIPT"
    },
    inline: "SCRIPT",
    file: "FILE_SCRIPT_NAME",
    id: "SCRIPT_ID",
    lang: "",
    params: {}
  });
};
