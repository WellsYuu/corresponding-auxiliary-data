'use strict';

var _createClass = (function () { function defineProperties(target, props) { for (var i = 0; i < props.length; i++) { var descriptor = props[i]; descriptor.enumerable = descriptor.enumerable || false; descriptor.configurable = true; if ('value' in descriptor) descriptor.writable = true; Object.defineProperty(target, descriptor.key, descriptor); } } return function (Constructor, protoProps, staticProps) { if (protoProps) defineProperties(Constructor.prototype, protoProps); if (staticProps) defineProperties(Constructor, staticProps); return Constructor; }; })();

var _get = function get(_x, _x2, _x3) { var _again = true; _function: while (_again) { var object = _x, property = _x2, receiver = _x3; _again = false; if (object === null) object = Function.prototype; var desc = Object.getOwnPropertyDescriptor(object, property); if (desc === undefined) { var parent = Object.getPrototypeOf(object); if (parent === null) { return undefined; } else { _x = parent; _x2 = property; _x3 = receiver; _again = true; desc = parent = undefined; continue _function; } } else if ('value' in desc) { return desc.value; } else { var getter = desc.get; if (getter === undefined) { return undefined; } return getter.call(receiver); } } };

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { 'default': obj }; }

function _toConsumableArray(arr) { if (Array.isArray(arr)) { for (var i = 0, arr2 = Array(arr.length); i < arr.length; i++) arr2[i] = arr[i]; return arr2; } else { return Array.from(arr); } }

function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError('Cannot call a class as a function'); } }

function _inherits(subClass, superClass) { if (typeof superClass !== 'function' && superClass !== null) { throw new TypeError('Super expression must either be null or a function, not ' + typeof superClass); } subClass.prototype = Object.create(superClass && superClass.prototype, { constructor: { value: subClass, enumerable: false, writable: true, configurable: true } }); if (superClass) Object.setPrototypeOf ? Object.setPrototypeOf(subClass, superClass) : subClass.__proto__ = superClass; }

var _lodash = require('lodash');

var _lodash2 = _interopRequireDefault(_lodash);

var _ui_app = require('./ui_app');

var _ui_app2 = _interopRequireDefault(_ui_app);

var _utilsCollection = require('../utils/collection');

var _utilsCollection2 = _interopRequireDefault(_utilsCollection);

var byIdCache = Symbol('byId');

module.exports = (function (_Collection) {
  _inherits(UiAppCollection, _Collection);

  function UiAppCollection(uiExports, parent) {
    _classCallCheck(this, UiAppCollection);

    _get(Object.getPrototypeOf(UiAppCollection.prototype), 'constructor', this).call(this);

    this.uiExports = uiExports;

    if (!parent) {
      this.claimedIds = [];
      this.hidden = new UiAppCollection(uiExports, this);
    } else {
      this.claimedIds = parent.claimedIds;
    }
  }

  _createClass(UiAppCollection, [{
    key: 'new',
    value: function _new(spec) {
      if (this.hidden && spec.hidden) {
        return this.hidden['new'](spec);
      }

      var app = new _ui_app2['default'](this.uiExports, spec);

      if (_lodash2['default'].includes(this.claimedIds, app.id)) {
        throw new Error('Unable to create two apps with the id ' + app.id + '.');
      } else {
        this.claimedIds.push(app.id);
      }

      this[byIdCache] = null;
      this.add(app);
      return app;
    }
  }, {
    key: 'byId',
    get: function get() {
      return this[byIdCache] || (this[byIdCache] = _lodash2['default'].indexBy([].concat(_toConsumableArray(this)), 'id'));
    }
  }]);

  return UiAppCollection;
})(_utilsCollection2['default']);
