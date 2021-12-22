'use strict';

var _createClass = (function () { function defineProperties(target, props) { for (var i = 0; i < props.length; i++) { var descriptor = props[i]; descriptor.enumerable = descriptor.enumerable || false; descriptor.configurable = true; if ('value' in descriptor) descriptor.writable = true; Object.defineProperty(target, descriptor.key, descriptor); } } return function (Constructor, protoProps, staticProps) { if (protoProps) defineProperties(Constructor.prototype, protoProps); if (staticProps) defineProperties(Constructor, staticProps); return Constructor; }; })();

var _get = function get(_x, _x2, _x3) { var _again = true; _function: while (_again) { var object = _x, property = _x2, receiver = _x3; _again = false; if (object === null) object = Function.prototype; var desc = Object.getOwnPropertyDescriptor(object, property); if (desc === undefined) { var parent = Object.getPrototypeOf(object); if (parent === null) { return undefined; } else { _x = parent; _x2 = property; _x3 = receiver; _again = true; desc = parent = undefined; continue _function; } } else if ('value' in desc) { return desc.value; } else { var getter = desc.get; if (getter === undefined) { return undefined; } return getter.call(receiver); } } };

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { 'default': obj }; }

function _asyncToGenerator(fn) { return function () { var gen = fn.apply(this, arguments); return new Promise(function (resolve, reject) { var callNext = step.bind(null, 'next'); var callThrow = step.bind(null, 'throw'); function step(key, arg) { try { var info = gen[key](arg); var value = info.value; } catch (error) { reject(error); return; } if (info.done) { resolve(value); } else { Promise.resolve(value).then(callNext, callThrow); } } callNext(); }); }; }

function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError('Cannot call a class as a function'); } }

function _inherits(subClass, superClass) { if (typeof superClass !== 'function' && superClass !== null) { throw new TypeError('Super expression must either be null or a function, not ' + typeof superClass); } subClass.prototype = Object.create(superClass && superClass.prototype, { constructor: { value: subClass, enumerable: false, writable: true, configurable: true } }); if (superClass) Object.setPrototypeOf ? Object.setPrototypeOf(subClass, superClass) : subClass.__proto__ = superClass; }

var _base_optimizer = require('./base_optimizer');

var _base_optimizer2 = _interopRequireDefault(_base_optimizer);

var _utilsFrom_root = require('../utils/from_root');

var _utilsFrom_root2 = _interopRequireDefault(_utilsFrom_root);

var _bluebird = require('bluebird');

var _fs = require('fs');

module.exports = (function (_BaseOptimizer) {
  _inherits(FsOptimizer, _BaseOptimizer);

  function FsOptimizer() {
    _classCallCheck(this, FsOptimizer);

    _get(Object.getPrototypeOf(FsOptimizer.prototype), 'constructor', this).apply(this, arguments);
  }

  _createClass(FsOptimizer, [{
    key: 'init',
    value: _asyncToGenerator(function* () {
      yield this.initCompiler();
    })
  }, {
    key: 'run',
    value: _asyncToGenerator(function* () {
      var _this = this;

      if (!this.compiler) yield this.init();

      var stats = yield (0, _bluebird.fromNode)(function (cb) {
        _this.compiler.run(function (err, stats) {
          if (err || !stats) return cb(err);

          if (stats.hasErrors() || stats.hasWarnings()) {
            return cb(_this.failedStatsToError(stats));
          } else {
            cb(null, stats);
          }
        });
      });
    })
  }]);

  return FsOptimizer;
})(_base_optimizer2['default']);
