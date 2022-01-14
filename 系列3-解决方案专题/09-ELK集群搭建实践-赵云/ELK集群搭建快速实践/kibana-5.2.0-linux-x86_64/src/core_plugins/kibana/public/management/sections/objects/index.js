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

import management from 'ui/management';
import 'plugins/kibana/management/sections/objects/_view';
import 'plugins/kibana/management/sections/objects/_objects';
import 'ace';
import 'ui/directives/confirm_click';
import uiModules from 'ui/modules';

// add the module deps to this module
uiModules.get('apps/management');

management.getSection('kibana').register('objects', {
  display: 'Saved Objects',
  order: 10,
  url: '#/management/kibana/objects'
});
