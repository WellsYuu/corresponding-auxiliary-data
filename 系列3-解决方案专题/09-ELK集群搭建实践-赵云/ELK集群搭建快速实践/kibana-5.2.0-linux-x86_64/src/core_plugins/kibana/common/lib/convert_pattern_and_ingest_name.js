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

// To avoid index template naming collisions the index pattern creation API
// namespaces template names by prepending 'kibana-' to the matching pattern's title.
// e.g. a pattern with title `logstash-*` will have a matching template named `kibana-logstash-*`.
// This module provides utility functions for easily converting between template and pattern names.

'use strict';

module.exports = {
  ingestToPattern: function ingestToPattern(templateName) {
    if (templateName.indexOf('kibana-') === -1) {
      throw new Error('not a valid kibana namespaced template name');
    }

    return templateName.slice(templateName.indexOf('-') + 1);
  },

  patternToIngest: function patternToIngest(patternName) {
    if (patternName === '') {
      throw new Error('pattern must not be empty');
    }

    return 'kibana-' + patternName.toLowerCase();
  }
};
