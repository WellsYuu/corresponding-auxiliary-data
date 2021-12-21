'use strict';

var _createClass = (function () { function defineProperties(target, props) { for (var i = 0; i < props.length; i++) { var descriptor = props[i]; descriptor.enumerable = descriptor.enumerable || false; descriptor.configurable = true; if ('value' in descriptor) descriptor.writable = true; Object.defineProperty(target, descriptor.key, descriptor); } } return function (Constructor, protoProps, staticProps) { if (protoProps) defineProperties(Constructor.prototype, protoProps); if (staticProps) defineProperties(Constructor, staticProps); return Constructor; }; })();

var _get = function get(_x, _x2, _x3) { var _again = true; _function: while (_again) { var object = _x, property = _x2, receiver = _x3; _again = false; if (object === null) object = Function.prototype; var desc = Object.getOwnPropertyDescriptor(object, property); if (desc === undefined) { var parent = Object.getPrototypeOf(object); if (parent === null) { return undefined; } else { _x = parent; _x2 = property; _x3 = receiver; _again = true; desc = parent = undefined; continue _function; } } else if ('value' in desc) { return desc.value; } else { var getter = desc.get; if (getter === undefined) { return undefined; } return getter.call(receiver); } } };

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { 'default': obj }; }

function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError('Cannot call a class as a function'); } }

function _inherits(subClass, superClass) { if (typeof superClass !== 'function' && superClass !== null) { throw new TypeError('Super expression must either be null or a function, not ' + typeof superClass); } subClass.prototype = Object.create(superClass && superClass.prototype, { constructor: { value: subClass, enumerable: false, writable: true, configurable: true } }); if (superClass) Object.setPrototypeOf ? Object.setPrototypeOf(subClass, superClass) : subClass.__proto__ = superClass; }

var _lodash = require('lodash');

var _lodash2 = _interopRequireDefault(_lodash);

var _states = require('./states');

var _states2 = _interopRequireDefault(_states);

var _events = require('events');

var Status = (function (_EventEmitter) {
  _inherits(Status, _EventEmitter);

  function Status(id, server) {
    _classCallCheck(this, Status);

    _get(Object.getPrototypeOf(Status.prototype), 'constructor', this).call(this);

    if (!id || typeof id !== 'string') {
      throw new TypeError('Status constructor requires an `id` string');
    }

    this.id = id;
    this.since = new Date();
    this.state = 'uninitialized';
    this.message = 'uninitialized';

    this.on('change', function (previous, previousMsg) {
      this.since = new Date();

      var tags = ['status', this.id, this.state === 'red' ? 'error' : 'info'];

      server.log(tags, {
        tmpl: 'Status changed from <%= prevState %> to <%= state %><%= message ? " - " + message : "" %>',
        state: this.state,
        message: this.message,
        prevState: previous,
        prevMsg: previousMsg
      });
    });
  }

  _createClass(Status, [{
    key: 'toJSON',
    value: function toJSON() {
      return {
        id: this.id,
        state: this.state,
        icon: _states2['default'].get(this.state).icon,
        message: this.message,
        since: this.since
      };
    }
  }, {
    key: 'on',
    value: function on(eventName, handler) {
      var _this = this;

      _get(Object.getPrototypeOf(Status.prototype), 'on', this).call(this, eventName, handler);

      if (eventName === this.state) {
        setImmediate(function () {
          return handler(_this.state, _this.message);
        });
      }
    }
  }, {
    key: 'once',
    value: function once(eventName, handler) {
      var _this2 = this;

      if (eventName === this.state) {
        setImmediate(function () {
          return handler(_this2.state, _this2.message);
        });
      } else {
        _get(Object.getPrototypeOf(Status.prototype), 'once', this).call(this, eventName, handler);
      }
    }
  }]);

  return Status;
})(_events.EventEmitter);

_states2['default'].all.forEach(function (state) {
  Status.prototype[state.id] = function (message) {
    if (this.state === 'disabled') return;

    var previous = this.state;
    var previousMsg = this.message;

    this.error = null;
    this.message = message || state.title;
    this.state = state.id;

    if (message instanceof Error) {
      this.error = message;
      this.message = message.message;
    }

    if (previous === this.state && previousMsg === this.message) {
      // noop
      return;
    }

    this.emit(state.id, previous, previousMsg, this.state, this.message);
    this.emit('change', previous, previousMsg, this.state, this.message);
  };
});

module.exports = Status;
