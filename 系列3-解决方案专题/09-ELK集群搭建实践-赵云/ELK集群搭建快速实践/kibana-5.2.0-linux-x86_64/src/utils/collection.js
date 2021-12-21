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
