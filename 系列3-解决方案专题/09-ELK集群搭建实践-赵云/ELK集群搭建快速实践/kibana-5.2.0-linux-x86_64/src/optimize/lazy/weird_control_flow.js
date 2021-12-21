'use strict';

var _createClass = (function () { function defineProperties(target, props) { for (var i = 0; i < props.length; i++) { var descriptor = props[i]; descriptor.enumerable = descriptor.enumerable || false; descriptor.configurable = true; if ('value' in descriptor) descriptor.writable = true; Object.defineProperty(target, descriptor.key, descriptor); } } return function (Constructor, protoProps, staticProps) { if (protoProps) defineProperties(Constructor.prototype, protoProps); if (staticProps) defineProperties(Constructor, staticProps); return Constructor; }; })();

function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError('Cannot call a class as a function'); } }

var _bluebird = require('bluebird');

module.exports = (function () {
  function WeirdControlFlow(work) {
    _classCallCheck(this, WeirdControlFlow);

    this.handlers = [];
  }

  _createClass(WeirdControlFlow, [{
    key: 'get',
    value: function get() {
      var _this = this;

      return (0, _bluebird.fromNode)(function (cb) {
        if (_this.ready) return cb();
        _this.handlers.push(cb);
        _this.start();
      });
    }
  }, {
    key: 'work',
    value: function work(_work) {
      this._work = _work;
      this.stop();

      if (this.handlers.length) {
        this.start();
      }
    }
  }, {
    key: 'start',
    value: function start() {
      if (this.running) return;
      this.stop();
      if (this._work) {
        this.running = true;
        this._work();
      }
    }
  }, {
    key: 'stop',
    value: function stop() {
      this.ready = false;
      this.error = false;
      this.running = false;
    }
  }, {
    key: 'success',
    value: function success() {
      this.stop();
      this.ready = true;

      for (var _len = arguments.length, args = Array(_len), _key = 0; _key < _len; _key++) {
        args[_key] = arguments[_key];
      }

      this._flush(args);
    }
  }, {
    key: 'failure',
    value: function failure(err) {
      this.stop();
      this.error = err;
      this._flush([err]);
    }
  }, {
    key: '_flush',
    value: function _flush(args) {
      var _iteratorNormalCompletion = true;
      var _didIteratorError = false;
      var _iteratorError = undefined;

      try {
        for (var _iterator = this.handlers.splice(0)[Symbol.iterator](), _step; !(_iteratorNormalCompletion = (_step = _iterator.next()).done); _iteratorNormalCompletion = true) {
          var fn = _step.value;

          fn.apply(null, args);
        }
      } catch (err) {
        _didIteratorError = true;
        _iteratorError = err;
      } finally {
        try {
          if (!_iteratorNormalCompletion && _iterator['return']) {
            _iterator['return']();
          }
        } finally {
          if (_didIteratorError) {
            throw _iteratorError;
          }
        }
      }
    }
  }]);

  return WeirdControlFlow;
})();
