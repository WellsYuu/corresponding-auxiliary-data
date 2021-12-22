'use strict';

var _createClass = (function () { function defineProperties(target, props) { for (var i = 0; i < props.length; i++) { var descriptor = props[i]; descriptor.enumerable = descriptor.enumerable || false; descriptor.configurable = true; if ('value' in descriptor) descriptor.writable = true; Object.defineProperty(target, descriptor.key, descriptor); } } return function (Constructor, protoProps, staticProps) { if (protoProps) defineProperties(Constructor.prototype, protoProps); if (staticProps) defineProperties(Constructor, staticProps); return Constructor; }; })();

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { 'default': obj }; }

function _asyncToGenerator(fn) { return function () { var gen = fn.apply(this, arguments); return new Promise(function (resolve, reject) { var callNext = step.bind(null, 'next'); var callThrow = step.bind(null, 'throw'); function step(key, arg) { try { var info = gen[key](arg); var value = info.value; } catch (error) { reject(error); return; } if (info.done) { resolve(value); } else { Promise.resolve(value).then(callNext, callThrow); } } callNext(); }); }; }

function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError('Cannot call a class as a function'); } }

var _lodash = require('lodash');

var _bluebird = require('bluebird');

var _cluster = require('cluster');

var _utils = require('../utils');

var _configConfig = require('./config/config');

var _configConfig2 = _interopRequireDefault(_configConfig);

var _loggingConfiguration = require('./logging/configuration');

var _loggingConfiguration2 = _interopRequireDefault(_loggingConfiguration);

var rootDir = (0, _utils.fromRoot)('.');

module.exports = (function () {
  function KbnServer(settings) {
    var _this = this;

    _classCallCheck(this, KbnServer);

    this.name = _utils.pkg.name;
    this.version = _utils.pkg.version;
    this.build = _utils.pkg.build || false;
    this.rootDir = rootDir;
    this.settings = settings || {};

    this.ready = (0, _lodash.constant)(this.mixin(require('./config/setup'), // sets this.config, reads this.settings
    require('./http'), // sets this.server
    require('./logging'), require('./warnings'), require('./status'),

    // writes pid file
    require('./pid'),

    // find plugins and set this.plugins
    require('./plugins/scan'),

    // disable the plugins that are disabled through configuration
    require('./plugins/check_enabled'),

    // disable the plugins that are incompatible with the current version of Kibana
    require('./plugins/check_version'),

    // tell the config we are done loading plugins
    require('./config/complete'),

    // setup this.uiExports and this.bundles
    require('../ui'),

    // setup server.uiSettings
    require('../ui/settings'),

    // ensure that all bundles are built, or that the
    // lazy bundle server is running
    require('../optimize'),

    // finally, initialize the plugins
    require('./plugins/initialize'), function () {
      if (_this.config.get('server.autoListen')) {
        _this.ready = (0, _lodash.constant)((0, _bluebird.resolve)());
        return _this.listen();
      }
    }));

    this.listen = (0, _lodash.once)(this.listen);
  }

  /**
   * Extend the KbnServer outside of the constraits of a plugin. This allows access
   * to APIs that are not exposed (intentionally) to the plugins and should only
   * be used when the code will be kept up to date with Kibana.
   *
   * @param {...function} - functions that should be called to mixin functionality.
   *                         They are called with the arguments (kibana, server, config)
   *                         and can return a promise to delay execution of the next mixin
   * @return {Promise} - promise that is resolved when the final mixin completes.
   */

  _createClass(KbnServer, [{
    key: 'mixin',
    value: _asyncToGenerator(function* () {
      var _iteratorNormalCompletion = true;
      var _didIteratorError = false;
      var _iteratorError = undefined;

      try {
        for (var _len = arguments.length, fns = Array(_len), _key = 0; _key < _len; _key++) {
          fns[_key] = arguments[_key];
        }

        for (var _iterator = (0, _lodash.compact)((0, _lodash.flatten)(fns))[Symbol.iterator](), _step; !(_iteratorNormalCompletion = (_step = _iterator.next()).done); _iteratorNormalCompletion = true) {
          var fn = _step.value;

          yield fn.call(this, this, this.server, this.config);
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
    })

    /**
     * Tell the server to listen for incoming requests, or get
     * a promise that will be resolved once the server is listening.
     *
     * @return undefined
     */
  }, {
    key: 'listen',
    value: _asyncToGenerator(function* () {
      var server = this.server;
      var config = this.config;

      yield this.ready();
      yield (0, _bluebird.fromNode)(function (cb) {
        return server.start(cb);
      });

      if (_cluster.isWorker) {
        // help parent process know when we are ready
        process.send(['WORKER_LISTENING']);
      }

      server.log(['listening', 'info'], 'Server running at ' + server.info.uri);
      return server;
    })
  }, {
    key: 'close',
    value: _asyncToGenerator(function* () {
      var _this2 = this;

      yield (0, _bluebird.fromNode)(function (cb) {
        return _this2.server.stop(cb);
      });
    })
  }, {
    key: 'inject',
    value: _asyncToGenerator(function* (opts) {
      var _this3 = this;

      if (!this.server) yield this.ready();

      return yield (0, _bluebird.fromNode)(function (cb) {
        try {
          _this3.server.inject(opts, function (resp) {
            cb(null, resp);
          });
        } catch (err) {
          cb(err);
        }
      });
    })
  }, {
    key: 'applyLoggingConfiguration',
    value: function applyLoggingConfiguration(settings) {
      var config = _configConfig2['default'].withDefaultSchema(settings);
      var loggingOptions = (0, _loggingConfiguration2['default'])(config);
      var subset = {
        ops: config.get('ops'),
        logging: config.get('logging')
      };
      var plain = JSON.stringify(subset, null, 2);
      this.server.log(['info', 'config'], 'New logging configuration:\n' + plain);
      this.server.plugins['even-better'].monitor.reconfigure(loggingOptions);
    }
  }]);

  return KbnServer;
})();
