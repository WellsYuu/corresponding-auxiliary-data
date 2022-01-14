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

var _ = require('lodash');
var $ = require('jquery');

var app = require('ui/modules').get('apps/timelion', []);
app.directive('timelionGrid', function ($compile) {
  return {
    restrict: 'A',
    scope: {
      timelionGridRows: '=',
      timelionGridColumns: '='
    },
    link: function ($scope, $elem, attrs) {

      function init() {
        setDimensions();
      }

      $scope.$on('$destroy', function () {
        $(window).off('resize'); //remove the handler added earlier
      });

      $(window).resize(function () {
        setDimensions();
      });

      $scope.$watchMulti(['timelionGridColumns', 'timelionGridRows'], function () {
        setDimensions();
      });

      function setDimensions() {
        var borderSize = 2;
        var headerSize = 45 + 35 + 28 + (20 * 2); // chrome + subnav + buttons + (container padding)
        var verticalPadding = 10;

        if ($scope.timelionGridColumns != null) {
          $elem.width($elem.parent().width() / $scope.timelionGridColumns - (borderSize * 2));
        }

        if ($scope.timelionGridRows != null) {
          $elem.height(($(window).height() - headerSize) / $scope.timelionGridRows - (verticalPadding + borderSize * 2));
        }
      }

      init();


    }
  };
});
