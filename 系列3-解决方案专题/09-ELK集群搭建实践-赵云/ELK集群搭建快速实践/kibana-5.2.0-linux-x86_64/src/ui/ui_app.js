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

'use strict';

var _createClass = (function () { function defineProperties(target, props) { for (var i = 0; i < props.length; i++) { var descriptor = props[i]; descriptor.enumerable = descriptor.enumerable || false; descriptor.configurable = true; if ('value' in descriptor) descriptor.writable = true; Object.defineProperty(target, descriptor.key, descriptor); } } return function (Constructor, protoProps, staticProps) { if (protoProps) defineProperties(Constructor.prototype, protoProps); if (staticProps) defineProperties(Constructor, staticProps); return Constructor; }; })();

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { 'default': obj }; }

function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError('Cannot call a class as a function'); } }

var _lodash = require('lodash');

var _lodash2 = _interopRequireDefault(_lodash);

var UiApp = (function () {
  function UiApp(uiExports, spec) {
    _classCallCheck(this, UiApp);

    this.uiExports = uiExports;
    this.spec = spec || {};

    this.id = this.spec.id;
    if (!this.id) {
      throw new Error('Every app must specify it\'s id');
    }

    this.main = this.spec.main;
    this.title = this.spec.title;
    this.order = this.spec.order || 0;
    this.description = this.spec.description;
    this.icon = this.spec.icon;
    this.hidden = !!this.spec.hidden;
    this.listed = this.spec.listed == null ? !this.hidden : this.spec.listed;
    this.templateName = this.spec.templateName || 'ui_app';

    if (!this.hidden) {
      // any non-hidden app has a url, so it gets a "navLink"
      this.navLink = this.uiExports.navLinks['new']({
        id: this.id,
        title: this.title,
        order: this.order,
        description: this.description,
        icon: this.icon,
        url: this.spec.url || '/app/' + this.id
      });

      if (!this.listed) {
        // unlisted apps remove their navLinks from the uiExports collection though
        this.uiExports.navLinks['delete'](this.navLink);
      }
    }

    if (this.spec.autoload) {
      console.warn('"autoload" (used by ' + this.id + ' app) is no longer a valid app configuration directive.' + 'Use the \`ui/autoload/*\` modules instead.');
    }

    // once this resolves, no reason to run it again
    this.getModules = (0, _lodash.once)(this.getModules);

    // variables that are injected into the browser, must serialize to JSON
    this.getInjectedVars = this.spec.injectVars || _lodash.noop;
  }

  _createClass(UiApp, [{
    key: 'getModules',
    value: function getModules() {
      return (0, _lodash.chain)([this.uiExports.find((0, _lodash.get)(this, 'spec.uses', [])), this.uiExports.find(['chromeNavControls', 'hacks'])]).flatten().uniq().unshift(this.main).value();
    }
  }, {
    key: 'toJSON',
    value: function toJSON() {
      return (0, _lodash.pick)(this, ['id', 'title', 'description', 'icon', 'main', 'navLink']);
    }
  }]);

  return UiApp;
})();

module.exports = UiApp;
