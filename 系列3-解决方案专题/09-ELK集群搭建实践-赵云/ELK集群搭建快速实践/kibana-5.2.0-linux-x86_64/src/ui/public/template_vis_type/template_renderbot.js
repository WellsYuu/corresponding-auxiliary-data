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
import VisRenderbotProvider from 'ui/vis/renderbot';
export default function TemplateRenderbotFactory(Private, $compile, $rootScope) {
  let Renderbot = Private(VisRenderbotProvider);

  _.class(TemplateRenderbot).inherits(Renderbot);
  function TemplateRenderbot(vis, $el, uiState) {
    TemplateRenderbot.Super.call(this, vis, $el, uiState);

    this.$scope = $rootScope.$new();
    this.$scope.vis = vis;
    this.$scope.uiState = uiState;

    $el.html($compile(this.vis.type.template)(this.$scope));
  }

  TemplateRenderbot.prototype.render = function (esResponse) {
    this.$scope.esResponse = esResponse;
  };

  TemplateRenderbot.prototype.destroy = function () {
    this.$scope.$destroy();
  };

  return TemplateRenderbot;
};
