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

import UiModules from 'ui/modules';
import { words, camelCase, kebabCase } from 'lodash';

export function kbnUrlDirective(name) {
  const srcAttr = kebabCase(name);
  const attr = kebabCase(words(name).slice(1));

  UiModules
  .get('kibana')
  .directive(name, function (Private, chrome) {
    return {
      restrict: 'A',
      link: function ($scope, $el, $attr) {
        $attr.$observe(name, function (val) {
          $attr.$set(attr, chrome.addBasePath(val));
        });
      }
    };
  });
}

kbnUrlDirective('kbnHref');
