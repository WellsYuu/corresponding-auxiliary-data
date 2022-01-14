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
import tagCloudVisParamsTemplate from 'plugins/tagcloud/tag_cloud_vis_params.html';
import noUiSlider from 'no-ui-slider';
import 'no-ui-slider/css/nouislider.css';
import 'no-ui-slider/css/nouislider.pips.css';
import 'no-ui-slider/css/nouislider.tooltips.css';

uiModules.get('kibana/table_vis')
  .directive('tagcloudVisParams', function () {
    return {
      restrict: 'E',
      template: tagCloudVisParamsTemplate,
      link: function ($scope, $element) {
        const sliderContainer = $element[0];
        var slider = sliderContainer.querySelector('.tag-cloud-fontsize-slider');
        noUiSlider.create(slider, {
          start: [$scope.vis.params.minFontSize, $scope.vis.params.maxFontSize],
          connect: true,
          tooltips: true,
          step: 1,
          range: {'min': 1, 'max': 100},
          format: {to: (value) => parseInt(value) + 'px', from: value => parseInt(value)}
        });
        slider.noUiSlider.on('change', function () {
          const fontSize = slider.noUiSlider.get();
          $scope.vis.params.minFontSize = parseInt(fontSize[0], 10);
          $scope.vis.params.maxFontSize = parseInt(fontSize[1], 10);
          $scope.$apply();
        });
      }
    };
  });
