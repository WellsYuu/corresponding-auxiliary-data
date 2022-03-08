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

import chrome from 'ui/chrome';
import Notifier from 'ui/notify/notifier';
import { intersection } from 'lodash';

const notify = new Notifier({ location: 'Scripting Lang Service' });

export function getSupportedScriptingLangs() {
  return ['expression', 'painless'];
}

export function GetEnabledScriptingLangsProvider($http) {
  return () => {
    return $http.get(chrome.addBasePath('/api/kibana/scripts/languages'))
    .then((res) => res.data)
    .catch(() => {
      notify.error('Error getting available scripting languages from Elasticsearch');
      return [];
    });
  };
}

