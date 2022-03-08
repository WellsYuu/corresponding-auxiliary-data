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

import uiModules from 'ui/modules';
let module = uiModules.get('kibana');

// By default, ElasticSearch surrounds matched values in <em></em>. This is not ideal because it is possible that
// the value could contain <em></em> in the value. We define these custom tags that we would never expect to see
// inside a field value.
module.constant('highlightTags', {
  pre: '@kibana-highlighted-field@',
  post: '@/kibana-highlighted-field@'
});
