'use strict';

Object.defineProperty(exports, '__esModule', {
  value: true
});

var _createClass = (function () { function defineProperties(target, props) { for (var i = 0; i < props.length; i++) { var descriptor = props[i]; descriptor.enumerable = descriptor.enumerable || false; descriptor.configurable = true; if ('value' in descriptor) descriptor.writable = true; Object.defineProperty(target, descriptor.key, descriptor); } } return function (Constructor, protoProps, staticProps) { if (protoProps) defineProperties(Constructor.prototype, protoProps); if (staticProps) defineProperties(Constructor, staticProps); return Constructor; }; })();

function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError('Cannot call a class as a function'); } }

var _lodash = require('lodash');

var _path = require('path');

var UiNavLink = (function () {
  function UiNavLink(uiExports, spec) {
    _classCallCheck(this, UiNavLink);

    this.id = spec.id;
    this.title = spec.title;
    this.order = spec.order || 0;
    this.url = '' + (uiExports.urlBasePath || '') + spec.url;
    this.description = spec.description;
    this.icon = spec.icon;
    this.linkToLastSubUrl = spec.linkToLastSubUrl === false ? false : true;
    this.hidden = spec.hidden || false;
    this.disabled = spec.disabled || false;
    this.tooltip = spec.tooltip || '';
  }

  _createClass(UiNavLink, [{
    key: 'toJSON',
    value: function toJSON() {
      return (0, _lodash.pick)(this, ['id', 'title', 'url', 'order', 'description', 'icon', 'linkToLastSubUrl', 'hidden', 'disabled', 'tooltip']);
    }
  }]);

  return UiNavLink;
})();

exports['default'] = UiNavLink;
module.exports = exports['default'];
