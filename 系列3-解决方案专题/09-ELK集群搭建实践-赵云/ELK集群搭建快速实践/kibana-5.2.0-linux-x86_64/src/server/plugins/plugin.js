'use strict';

var _get = function get(_x, _x2, _x3) { var _again = true; _function: while (_again) { var object = _x, property = _x2, receiver = _x3; _again = false; if (object === null) object = Function.prototype; var desc = Object.getOwnPropertyDescriptor(object, property); if (desc === undefined) { var parent = Object.getPrototypeOf(object); if (parent === null) { return undefined; } else { _x = parent; _x2 = property; _x3 = receiver; _again = true; desc = parent = undefined; continue _function; } } else if ('value' in desc) { return desc.value; } else { var getter = desc.get; if (getter === undefined) { return undefined; } return getter.call(receiver); } } };

var _createClass = (function () { function defineProperties(target, props) { for (var i = 0; i < props.length; i++) { var descriptor = props[i]; descriptor.enumerable = descriptor.enumerable || false; descriptor.configurable = true; if ('value' in descriptor) descriptor.writable = true; Object.defineProperty(target, descriptor.key, descriptor); } } return function (Constructor, protoProps, staticProps) { if (protoProps) defineProperties(Constructor.prototype, protoProps); if (staticProps) defineProperties(Constructor, staticProps); return Constructor; }; })();

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { 'default': obj }; }

function _inherits(subClass, superClass) { if (typeof superClass !== 'function' && superClass !== null) { throw new TypeError('Super expression must either be null or a function, not ' + typeof superClass); } subClass.prototype = Object.create(superClass && superClass.prototype, { constructor: { value: subClass, enumerable: false, writable: true, configurable: true } }); if (superClass) Object.setPrototypeOf ? Object.setPrototypeOf(subClass, superClass) : subClass.__proto__ = superClass; }

function _asyncToGenerator(fn) { return function () { var gen = fn.apply(this, arguments); return new Promise(function (resolve, reject) { var callNext = step.bind(null, 'next'); var callThrow = step.bind(null, 'throw'); function step(key, arg) { try { var info = gen[key](arg); var value = info.value; } catch (error) { reject(error); return; } if (info.done) { resolve(value); } else { Promise.resolve(value).then(callNext, callThrow); } } callNext(); }); }; }

function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError('Cannot call a class as a function'); } }

var _lodash = require('lodash');

var _lodash2 = _interopRequireDefault(_lodash);

var _joi = require('joi');

var _joi2 = _interopRequireDefault(_joi);

var _bluebird = require('bluebird');

var _bluebird2 = _interopRequireDefault(_bluebird);

var _path = require('path');

var _util = require('util');

var extendInitFns = Symbol('extend plugin initialization');

var defaultConfigSchema = _joi2['default'].object({
  enabled: _joi2['default'].boolean()['default'](true)
})['default']();

/**
 * The server plugin class, used to extend the server
 * and add custom behavior. A "scoped" plugin class is
 * created by the PluginApi class and provided to plugin
 * providers that automatically binds all but the `opts`
 * arguments.
 *
 * @class Plugin
 * @param {KbnServer} kbnServer - the KbnServer this plugin
 *                              belongs to.
 * @param {String} path - the path from which the plugin hails
 * @param {Object} pkg - the value of package.json for the plugin
 * @param {Objects} opts - the options for this plugin
 * @param {String} [opts.id=pkg.name] - the id for this plugin.
 * @param {Object} [opts.uiExports] - a mapping of UiExport types
 *                                  to UI modules or metadata about
 *                                  the UI module
 * @param {Array} [opts.require] - the other plugins that this plugin
 *                               requires. These plugins must exist and
 *                               be enabled for this plugin to function.
 *                               The require'd plugins will also be
 *                               initialized first, in order to make sure
 *                               that dependencies provided by these plugins
 *                               are available
 * @param {String} [opts.version=pkg.version] - the version of this plugin
 * @param {Function} [opts.init] - A function that will be called to initialize
 *                               this plugin at the appropriate time.
 * @param {Function} [opts.configPrefix=this.id] - The prefix to use for configuration
 *                               values in the main configuration service
 * @param {Function} [opts.config] - A function that produces a configuration
 *                                 schema using Joi, which is passed as its
 *                                 first argument.
 * @param {String|False} [opts.publicDir=path + '/public']
 *    - the public directory for this plugin. The final directory must
 *    have the name "public", though it can be located somewhere besides
 *    the root of the plugin. Set this to false to disable exposure of a
 *    public directory
 */
module.exports = (function () {
  function Plugin(kbnServer, path, pkg, opts) {
    _classCallCheck(this, Plugin);

    this.kbnServer = kbnServer;
    this.pkg = pkg;
    this.path = path;

    this.id = opts.id || pkg.name;
    this.uiExportsSpecs = opts.uiExports || {};
    this.requiredIds = opts.require || [];
    this.version = opts.version || pkg.version;

    // Plugins must specify their version, and by default that version should match
    // the version of kibana down to the patch level. If these two versions need
    // to diverge, they can specify a kibana.version in the package to indicate the
    // version of kibana the plugin is intended to work with.
    this.kibanaVersion = opts.kibanaVersion || _lodash2['default'].get(pkg, 'kibana.version', this.version);
    this.externalPreInit = opts.preInit || _lodash2['default'].noop;
    this.externalInit = opts.init || _lodash2['default'].noop;
    this.configPrefix = opts.configPrefix || this.id;
    this.getExternalConfigSchema = opts.config || _lodash2['default'].noop;
    this.preInit = _lodash2['default'].once(this.preInit);
    this.init = _lodash2['default'].once(this.init);
    this[extendInitFns] = [];

    if (opts.publicDir === false) {
      this.publicDir = null;
    } else if (!opts.publicDir) {
      this.publicDir = (0, _path.resolve)(this.path, 'public');
    } else {
      this.publicDir = opts.publicDir;
      if ((0, _path.basename)(this.publicDir) !== 'public') {
        throw new Error('publicDir for plugin ' + this.id + ' must end with a "public" directory.');
      }
    }
  }

  _createClass(Plugin, [{
    key: 'getConfigSchema',
    value: _asyncToGenerator(function* () {
      var schema = yield this.getExternalConfigSchema(_joi2['default']);
      return schema || defaultConfigSchema;
    })
  }, {
    key: 'preInit',
    value: _asyncToGenerator(function* () {
      return yield this.externalPreInit(this.kbnServer.server);
    })
  }, {
    key: 'init',
    value: _asyncToGenerator(function* () {
      var _this = this;

      var id = this.id;
      var version = this.version;
      var kbnServer = this.kbnServer;
      var configPrefix = this.configPrefix;
      var config = kbnServer.config;

      // setup the hapi register function and get on with it
      var asyncRegister = _asyncToGenerator(function* (server, options) {
        _this.server = server;

        yield Promise.all(_this[extendInitFns].map(_asyncToGenerator(function* (fn) {
          yield fn.call(_this, server, options);
        })));

        server.log(['plugins', 'debug'], {
          tmpl: 'Initializing plugin <%= plugin.toString() %>',
          plugin: _this
        });

        if (_this.publicDir) {
          server.exposeStaticDir('/plugins/' + id + '/{path*}', _this.publicDir);
        }

        // Many of the plugins are simply adding static assets to the server and we don't need
        // to track their "status". Since plugins must have an init() function to even set its status
        // we shouldn't even create a status unless the plugin can use it.
        if (_this.externalInit !== _lodash2['default'].noop) {
          _this.status = kbnServer.status.createForPlugin(_this);
          server.expose('status', _this.status);
        }

        return yield (0, _bluebird.attempt)(_this.externalInit, [server, options], _this);
      });

      var register = function register(server, options, next) {
        _bluebird2['default'].resolve(asyncRegister(server, options)).nodeify(next);
      };

      register.attributes = { name: id, version: version };

      yield (0, _bluebird.fromNode)(function (cb) {
        kbnServer.server.register({
          register: register,
          options: config.has(configPrefix) ? config.get(configPrefix) : null
        }, cb);
      });

      // Only change the plugin status to green if the
      // intial status has not been changed
      if (this.status && this.status.state === 'uninitialized') {
        this.status.green('Ready');
      }
    })
  }, {
    key: 'extendInit',
    value: function extendInit(fn) {
      this[extendInitFns].push(fn);
    }
  }, {
    key: 'toJSON',
    value: function toJSON() {
      return this.pkg;
    }
  }, {
    key: 'toString',
    value: function toString() {
      return this.id + '@' + this.version;
    }
  }], [{
    key: 'scoped',
    value: function scoped(kbnServer, path, pkg) {
      return (function (_Plugin) {
        _inherits(ScopedPlugin, _Plugin);

        function ScopedPlugin(opts) {
          _classCallCheck(this, ScopedPlugin);

          _get(Object.getPrototypeOf(ScopedPlugin.prototype), 'constructor', this).call(this, kbnServer, path, pkg, opts || {});
        }

        return ScopedPlugin;
      })(Plugin);
    }
  }]);

  return Plugin;
})();
