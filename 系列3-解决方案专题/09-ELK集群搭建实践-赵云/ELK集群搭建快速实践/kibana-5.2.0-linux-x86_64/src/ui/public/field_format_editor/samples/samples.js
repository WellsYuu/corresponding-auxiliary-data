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
import uiModules from 'ui/modules';
import samplesTemplate from 'ui/field_format_editor/samples/samples.html';

uiModules
.get('kibana')
.directive('fieldFormatEditorSamples', function ($sce, Promise) {
  return {
    restrict: 'E',
    template: samplesTemplate,
    require: ['?^ngModel', '^fieldEditor'],
    scope: true,
    link: function ($scope, $el, attrs, cntrls) {
      let ngModelCntrl = cntrls[0];

      $scope.samples = null;
      $scope.$bind('inputs', attrs.inputs);

      $scope.$watchMulti([
        'editor.field.format',
        '[]inputs'
      ], function () {
        $scope.samples = null;
        let field = $scope.editor.field;

        if (!field || !field.format) {
          return;
        }

        Promise.try(function () {
          let converter = field.format.getConverterFor('html');
          $scope.samples = _.map($scope.inputs, function (input) {
            return [input, $sce.trustAsHtml(converter(input))];
          });
        })
        .then(validity, validity);
      });

      function validity(err) {
        $scope.error = err;
        ngModelCntrl && ngModelCntrl.$setValidity('patternExecutes', !err);
      }
    }
  };
});
