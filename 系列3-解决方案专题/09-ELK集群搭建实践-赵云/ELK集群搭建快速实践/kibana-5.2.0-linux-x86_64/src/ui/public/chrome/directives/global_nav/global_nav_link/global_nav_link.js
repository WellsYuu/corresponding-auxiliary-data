
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

import globalNavLinkTemplate from './global_nav_link.html';
import './global_nav_link.less';
import uiModules from 'ui/modules';

const module = uiModules.get('kibana');

module.directive('globalNavLink', chrome => {
  return {
    restrict: 'E',
    replace: true,
    scope: {
      isActive: '=',
      isDisabled: '=',
      tooltipContent: '=',
      onClick: '&',
      href: '=',
      kbnRoute: '=',
      icon: '=',
      title: '=',
    },
    template: globalNavLinkTemplate,
    link: scope => {
      scope.getHref = () => {
        if (scope.href) {
          return scope.href;
        }

        if (scope.kbnRoute) {
          return chrome.addBasePath(scope.kbnRoute);
        }
      };
    }
  };
});
