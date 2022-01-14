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

import uiModules from 'ui/modules';
let module = uiModules.get('kibana');

module.constant('refreshIntervals', [
  { value : 0, display: 'Off', section: 0},

  { value : 5000, display: '5 seconds', section: 1},
  { value : 10000, display: '10 seconds', section: 1},
  { value : 30000, display: '30 seconds', section: 1},
  { value : 45000, display: '45 seconds', section: 1},

  { value : 60000, display: '1 minute', section: 2},
  { value : 300000, display: '5 minutes', section: 2},
  { value : 900000, display: '15 minutes', section: 2},
  { value : 1800000, display: '30 minutes', section: 2},

  { value : 3600000, display: '1 hour', section: 3},
  { value : 7200000, display: '2 hour', section: 3},
  { value : 43200000, display: '12 hour', section: 3},
  { value : 86400000, display: '1 day', section: 3}
]);

