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

Object.defineProperty(exports, '__esModule', {
  value: true
});

var _createClass = (function () { function defineProperties(target, props) { for (var i = 0; i < props.length; i++) { var descriptor = props[i]; descriptor.enumerable = descriptor.enumerable || false; descriptor.configurable = true; if ('value' in descriptor) descriptor.writable = true; Object.defineProperty(target, descriptor.key, descriptor); } } return function (Constructor, protoProps, staticProps) { if (protoProps) defineProperties(Constructor.prototype, protoProps); if (staticProps) defineProperties(Constructor, staticProps); return Constructor; }; })();

var _get = function get(_x, _x2, _x3) { var _again = true; _function: while (_again) { var object = _x, property = _x2, receiver = _x3; _again = false; if (object === null) object = Function.prototype; var desc = Object.getOwnPropertyDescriptor(object, property); if (desc === undefined) { var parent = Object.getPrototypeOf(object); if (parent === null) { return undefined; } else { _x = parent; _x2 = property; _x3 = receiver; _again = true; desc = parent = undefined; continue _function; } } else if ('value' in desc) { return desc.value; } else { var getter = desc.get; if (getter === undefined) { return undefined; } return getter.call(receiver); } } };

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { 'default': obj }; }

function _toConsumableArray(arr) { if (Array.isArray(arr)) { for (var i = 0, arr2 = Array(arr.length); i < arr.length; i++) arr2[i] = arr[i]; return arr2; } else { return Array.from(arr); } }

function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError('Cannot call a class as a function'); } }

function _inherits(subClass, superClass) { if (typeof superClass !== 'function' && superClass !== null) { throw new TypeError('Super expression must either be null or a function, not ' + typeof superClass); } subClass.prototype = Object.create(superClass && superClass.prototype, { constructor: { value: subClass, enumerable: false, writable: true, configurable: true } }); if (superClass) Object.setPrototypeOf ? Object.setPrototypeOf(subClass, superClass) : subClass.__proto__ = superClass; }

var _lodash = require('lodash');

var _ui_nav_link = require('./ui_nav_link');

var _ui_nav_link2 = _interopRequireDefault(_ui_nav_link);

var _utilsCollection = require('../utils/collection');

var _utilsCollection2 = _interopRequireDefault(_utilsCollection);

var inOrderCache = Symbol('inOrder');

var UiNavLinkCollection = (function (_Collection) {
  _inherits(UiNavLinkCollection, _Collection);

  function UiNavLinkCollection(uiExports, parent) {
    _classCallCheck(this, UiNavLinkCollection);

    _get(Object.getPrototypeOf(UiNavLinkCollection.prototype), 'constructor', this).call(this);
    this.uiExports = uiExports;
  }

  _createClass(UiNavLinkCollection, [{
    key: 'new',
    value: function _new(spec) {
      var link = new _ui_nav_link2['default'](this.uiExports, spec);
      this[inOrderCache] = null;
      this.add(link);
      return link;
    }
  }, {
    key: 'delete',
    value: function _delete(value) {
      this[inOrderCache] = null;
      return _get(Object.getPrototypeOf(UiNavLinkCollection.prototype), 'delete', this).call(this, value);
    }
  }, {
    key: 'inOrder',
    get: function get() {
      if (!this[inOrderCache]) {
        this[inOrderCache] = (0, _lodash.sortBy)([].concat(_toConsumableArray(this)), 'order');
      }

      return this[inOrderCache];
    }
  }]);

  return UiNavLinkCollection;
})(_utilsCollection2['default']);

exports['default'] = UiNavLinkCollection;
;
module.exports = exports['default'];
