'use strict';

var _createClass = (function () { function defineProperties(target, props) { for (var i = 0; i < props.length; i++) { var descriptor = props[i]; descriptor.enumerable = descriptor.enumerable || false; descriptor.configurable = true; if ('value' in descriptor) descriptor.writable = true; Object.defineProperty(target, descriptor.key, descriptor); } } return function (Constructor, protoProps, staticProps) { if (protoProps) defineProperties(Constructor.prototype, protoProps); if (staticProps) defineProperties(Constructor, staticProps); return Constructor; }; })();

var _get = function get(_x, _x2, _x3) { var _again = true; _function: while (_again) { var object = _x, property = _x2, receiver = _x3; _again = false; if (object === null) object = Function.prototype; var desc = Object.getOwnPropertyDescriptor(object, property); if (desc === undefined) { var parent = Object.getPrototypeOf(object); if (parent === null) { return undefined; } else { _x = parent; _x2 = property; _x3 = receiver; _again = true; desc = parent = undefined; continue _function; } } else if ('value' in desc) { return desc.value; } else { var getter = desc.get; if (getter === undefined) { return undefined; } return getter.call(receiver); } } };

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { 'default': obj }; }

function _asyncToGenerator(fn) { return function () { var gen = fn.apply(this, arguments); return new Promise(function (resolve, reject) { var callNext = step.bind(null, 'next'); var callThrow = step.bind(null, 'throw'); function step(key, arg) { try { var info = gen[key](arg); var value = info.value; } catch (error) { reject(error); return; } if (info.done) { resolve(value); } else { Promise.resolve(value).then(callNext, callThrow); } } callNext(); }); }; }

function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError('Cannot call a class as a function'); } }

function _inherits(subClass, superClass) { if (typeof superClass !== 'function' && superClass !== null) { throw new TypeError('Super expression must either be null or a function, not ' + typeof superClass); } subClass.prototype = Object.create(superClass && superClass.prototype, { constructor: { value: subClass, enumerable: false, writable: true, configurable: true } }); if (superClass) Object.setPrototypeOf ? Object.setPrototypeOf(subClass, superClass) : subClass.__proto__ = superClass; }

var _boom = require('boom');

var _boom2 = _interopRequireDefault(_boom);

var _base_optimizer = require('../base_optimizer');

var _base_optimizer2 = _interopRequireDefault(_base_optimizer);

var _weird_control_flow = require('./weird_control_flow');

var _weird_control_flow2 = _interopRequireDefault(_weird_control_flow);

var _lodash = require('lodash');

var _path = require('path');

module.exports = (function (_BaseOptimizer) {
  _inherits(LazyOptimizer, _BaseOptimizer);

  function LazyOptimizer(opts) {
    var _this = this;

    _classCallCheck(this, LazyOptimizer);

    _get(Object.getPrototypeOf(LazyOptimizer.prototype), 'constructor', this).call(this, opts);
    this.log = opts.log || function () {
      return null;
    };
    this.prebuild = opts.prebuild || false;

    this.timer = {
      ms: null,
      start: function start() {
        return _this.timer.ms = Date.now();
      },
      end: function end() {
        return _this.timer.ms = ((Date.now() - _this.timer.ms) / 1000).toFixed(2);
      }
    };

    this.build = new _weird_control_flow2['default']();
  }

  _createClass(LazyOptimizer, [{
    key: 'init',
    value: _asyncToGenerator(function* () {
      var _this2 = this;

      this.initializing = true;

      yield this.bundles.writeEntryFiles();
      yield this.initCompiler();

      this.compiler.plugin('watch-run', function (w, webpackCb) {
        _this2.build.work((0, _lodash.once)(function () {
          _this2.timer.start();
          _this2.logRunStart();
          webpackCb();
        }));
      });

      this.compiler.plugin('done', function (stats) {
        if (!stats.hasErrors() && !stats.hasWarnings()) {
          _this2.logRunSuccess();
          _this2.build.success();
          return;
        }

        var err = _this2.failedStatsToError(stats);
        _this2.logRunFailure(err);
        _this2.build.failure(err);
        _this2.watching.invalidate();
      });

      this.watching = this.compiler.watch({ aggregateTimeout: 200 }, function (err) {
        if (err) {
          _this2.log('fatal', err);
          process.exit(1);
        }
      });

      var buildPromise = this.build.get();
      if (this.prebuild) yield buildPromise;

      this.initializing = false;
      this.log(['info', 'optimize'], {
        tmpl: 'Lazy optimization of ' + this.bundles.desc() + ' ready',
        bundles: this.bundles.getIds()
      });
    })
  }, {
    key: 'getPath',
    value: _asyncToGenerator(function* (relativePath) {
      yield this.build.get();
      return (0, _path.join)(this.compiler.outputPath, relativePath);
    })
  }, {
    key: 'bindToServer',
    value: function bindToServer(server) {
      var _this3 = this;

      server.route({
        path: '/bundles/{asset*}',
        method: 'GET',
        handler: _asyncToGenerator(function* (request, reply) {
          try {
            var path = yield _this3.getPath(request.params.asset);
            return reply.file(path);
          } catch (error) {
            console.log(error.stack);
            return reply(error);
          }
        })
      });
    }
  }, {
    key: 'logRunStart',
    value: function logRunStart() {
      this.log(['info', 'optimize'], {
        tmpl: 'Lazy optimization started',
        bundles: this.bundles.getIds()
      });
    }
  }, {
    key: 'logRunSuccess',
    value: function logRunSuccess() {
      this.log(['info', 'optimize'], {
        tmpl: 'Lazy optimization <%= status %> in <%= seconds %> seconds',
        bundles: this.bundles.getIds(),
        status: 'success',
        seconds: this.timer.end()
      });
    }
  }, {
    key: 'logRunFailure',
    value: function logRunFailure(err) {
      // errors during initialization to the server, unlike the rest of the
      // errors produced here. Lets not muddy the console with extra errors
      if (this.initializing) return;

      this.log(['fatal', 'optimize'], {
        tmpl: 'Lazy optimization <%= status %> in <%= seconds %> seconds<%= err %>',
        bundles: this.bundles.getIds(),
        status: 'failed',
        seconds: this.timer.end(),
        err: err
      });
    }
  }]);

  return LazyOptimizer;
})(_base_optimizer2['default']);
