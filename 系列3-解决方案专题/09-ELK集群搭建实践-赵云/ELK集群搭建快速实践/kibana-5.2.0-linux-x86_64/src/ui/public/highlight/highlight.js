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

import 'ui/highlight/highlight_tags';
import _ from 'lodash';
import angular from 'angular';
import uiModules from 'ui/modules';

let module = uiModules.get('kibana');

module.filter('highlight', function (highlightTags) {
  return function (formatted, highlight) {
    if (typeof formatted === 'object') formatted = angular.toJson(formatted);

    _.each(highlight, function (section) {
      section = _.escape(section);

      // Strip out the highlight tags to compare against the formatted string
      let untagged = section
        .split(highlightTags.pre).join('')
        .split(highlightTags.post).join('');

      // Replace all highlight tags with proper html tags
      let tagged = section
        .split(highlightTags.pre).join('<mark>')
        .split(highlightTags.post).join('</mark>');

      // Replace all instances of the untagged string with the properly tagged string
      formatted = formatted.split(untagged).join(tagged);
    });

    return formatted;
  };
});
