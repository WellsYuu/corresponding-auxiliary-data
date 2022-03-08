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

import { visualizationLoaderProvider } from 'plugins/kibana/dashboard/components/panel/lib/visualization';
import { searchLoaderProvider } from 'plugins/kibana/dashboard/components/panel/lib/search';

export function loadPanelProvider(Private) { // Inject services here
  return function (panel, $scope) { // Function parameters here
    const panelTypes = {
      visualization: Private(visualizationLoaderProvider),
      search: Private(searchLoaderProvider)
    };

    try {
      return panelTypes[panel.type](panel, $scope);
    } catch (e) {
      throw new Error('Loader not found for unknown panel type: ' + panel.type);
    }

  };
};
