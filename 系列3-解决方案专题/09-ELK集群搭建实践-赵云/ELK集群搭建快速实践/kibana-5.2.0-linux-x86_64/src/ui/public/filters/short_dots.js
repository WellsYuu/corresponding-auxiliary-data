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
import uiModules from 'ui/modules';
// Shorts dot notated strings
// eg: foo.bar.baz becomes f.b.baz
// 'foo.bar.baz'.replace(/(.+?\.)/g,function(v) {return v[0]+'.';});

uiModules
  .get('kibana')
  .filter('shortDots', function (Private) {
    return Private(shortDotsFilterProvider);
  });

function shortDotsFilterProvider(config) {
  let filter;

  config.watch('shortDots:enable', updateFilter);

  return wrapper;

  function updateFilter(enabled) {
    filter = enabled ? _.shortenDottedString : _.identity;
  }
  function wrapper(str) {
    return filter(str);
  }
}

export default shortDotsFilterProvider;
