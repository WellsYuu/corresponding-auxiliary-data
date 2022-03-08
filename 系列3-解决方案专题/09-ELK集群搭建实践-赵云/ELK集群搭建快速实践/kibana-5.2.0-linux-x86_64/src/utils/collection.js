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

'use strict';

var _createClass = (function () { function defineProperties(target, props) { for (var i = 0; i < props.length; i++) { var descriptor = props[i]; descriptor.enumerable = descriptor.enumerable || false; descriptor.configurable = true; if ('value' in descriptor) descriptor.writable = true; Object.defineProperty(target, descriptor.key, descriptor); } } return function (Constructor, protoProps, staticProps) { if (protoProps) defineProperties(Constructor.prototype, protoProps); if (staticProps) defineProperties(Constructor, staticProps); return Constructor; }; })();

function _toConsumableArray(arr) { if (Array.isArray(arr)) { for (var i = 0, arr2 = Array(arr.length); i < arr.length; i++) arr2[i] = arr[i]; return arr2; } else { return Array.from(arr); } }

function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError('Cannot call a class as a function'); } }

var set = Symbol('internal set');

module.exports = (function () {
  function Collection() {
    _classCallCheck(this, Collection);

    // Set's have a length of 0, mimic that
    this[set] = new Set(arguments[0] || []);
  }

  /******
   ** Collection API
   ******/

  _createClass(Collection, [{
    key: 'toArray',
    value: function toArray() {
      return [].concat(_toConsumableArray(this.values()));
    }
  }, {
    key: 'toJSON',
    value: function toJSON() {
      return this.toArray();
    }

    /******
     ** ES Set Api
     ******/

  }, {
    key: 'add',
    value: function add(value) {
      return this[set].add(value);
    }
  }, {
    key: 'clear',
    value: function clear() {
      return this[set].clear();
    }
  }, {
    key: 'delete',
    value: function _delete(value) {
      return this[set]['delete'](value);
    }
  }, {
    key: 'entries',
    value: function entries() {
      return this[set].entries();
    }
  }, {
    key: 'forEach',
    value: function forEach(callbackFn, thisArg) {
      return this[set].forEach(callbackFn, thisArg);
    }
  }, {
    key: 'has',
    value: function has(value) {
      return this[set].has(value);
    }
  }, {
    key: 'keys',
    value: function keys() {
      return this[set].keys();
    }
  }, {
    key: 'values',
    value: function values() {
      return this[set].values();
    }
  }, {
    key: Symbol.iterator,
    value: function value() {
      return this[set][Symbol.iterator]();
    }
  }, {
    key: 'size',
    get: function get() {
      return this[set].size;
    }
  }], [{
    key: Symbol.species,
    get: function get() {
      return Collection;
    }
  }]);

  return Collection;
})();
