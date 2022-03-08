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

import _ from 'lodash';
import toEditableConfig from 'plugins/kibana/management/sections/settings/lib/to_editable_config';
import 'plugins/kibana/management/sections/settings/advanced_row';
import management from 'ui/management';
import uiRoutes from 'ui/routes';
import uiModules from 'ui/modules';
import indexTemplate from 'plugins/kibana/management/sections/settings/index.html';

uiRoutes
.when('/management/kibana/settings', {
  template: indexTemplate
});

uiModules.get('apps/management')
.directive('kbnManagementAdvanced', function (config, Notifier, Private, $rootScope) {
  return {
    restrict: 'E',
    link: function ($scope) {
      // react to changes of the config values
      config.watchAll(changed, $scope);

      // initial config setup
      changed();

      function changed(values) {
        const all = config.getAll();
        const editable = _(all)
          .map((def, name) => toEditableConfig({
            def,
            name,
            value: def.userValue,
            isCustom: config.isCustom(name)
          }))
          .value();
        const writable = _.reject(editable, 'readonly');
        $scope.configs = writable;
      }
    }
  };
});

management.getSection('kibana').register('settings', {
  display: 'Advanced Settings',
  order: 20,
  url: '#/management/kibana/settings'
});
