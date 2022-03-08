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
import 'ui/elastic_textarea';
import 'ui/filters/markdown';
import uiModules from 'ui/modules';
import advancedRowTemplate from 'plugins/kibana/management/sections/settings/advanced_row.html';

uiModules.get('apps/management')
.directive('advancedRow', function (config, Notifier) {
  return {
    restrict: 'A',
    replace: true,
    template: advancedRowTemplate,
    scope: {
      conf: '=advancedRow',
      configs: '='
    },
    link: function ($scope) {
      const notify = new Notifier();
      const keyCodes = {
        ESC: 27
      };

      // To allow passing form validation state back
      $scope.forms = {};

      // setup loading flag, run async op, then clear loading and editing flag (just in case)
      const loading = function (conf, fn) {
        conf.loading = true;
        fn()
          .then(function () {
            conf.loading = conf.editing = false;
          })
          .catch(notify.fatal);
      };

      $scope.maybeCancel = function ($event, conf) {
        if ($event.keyCode === keyCodes.ESC) {
          $scope.cancelEdit(conf);
        }
      };

      $scope.edit = function (conf) {
        conf.unsavedValue = conf.value == null ? conf.defVal : conf.value;
        $scope.configs.forEach(function (c) {
          c.editing = (c === conf);
        });
      };

      $scope.save = function (conf) {
        loading(conf, function () {
          if (conf.unsavedValue === conf.defVal) {
            return config.remove(conf.name);
          }

          return config.set(conf.name, conf.unsavedValue);
        });
      };

      $scope.cancelEdit = function (conf) {
        conf.editing = false;
      };

      $scope.clear = function (conf) {
        return loading(conf, function () {
          return config.remove(conf.name);
        });
      };

    }
  };
});
