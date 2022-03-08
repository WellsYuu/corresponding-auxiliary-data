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
import $ from 'jquery';
import 'ui/config';
import uiModules from 'ui/modules';
let $style = $('<style>').appendTo('head').attr('id', 'style-compile');


uiModules
.get('kibana')
.run(function ($rootScope, $compile, config) {
  let truncateGradientHeight = 15;
  let template = _.template(require('./style_compile.css.tmpl'));
  let locals = {};

  // watch the value of the truncate:maxHeight config param
  $rootScope.$watch(function () {
    return config.get('truncate:maxHeight');
  }, function (maxHeight) {
    if (maxHeight > 0) {
      locals.truncateMaxHeight = maxHeight + 'px !important';
      locals.truncateGradientTop = maxHeight - truncateGradientHeight + 'px';
    } else {
      locals.truncateMaxHeight = 'none';
      locals.truncateGradientTop = '-' + truncateGradientHeight + 'px';
    }

    $style.html(template(locals));
  });
});
