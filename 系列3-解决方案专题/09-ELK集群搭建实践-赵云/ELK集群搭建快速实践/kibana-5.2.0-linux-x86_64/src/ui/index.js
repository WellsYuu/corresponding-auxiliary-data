'use strict';

Object.defineProperty(exports, '__esModule', {
  value: true
});

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { 'default': obj }; }

function _asyncToGenerator(fn) { return function () { var gen = fn.apply(this, arguments); return new Promise(function (resolve, reject) { var callNext = step.bind(null, 'next'); var callThrow = step.bind(null, 'throw'); function step(key, arg) { try { var info = gen[key](arg); var value = info.value; } catch (error) { reject(error); return; } if (info.done) { resolve(value); } else { Promise.resolve(value).then(callNext, callThrow); } } callNext(); }); }; }

var _url = require('url');

var _fs = require('fs');

var _lodash = require('lodash');

var _bluebird = require('bluebird');

var _boom = require('boom');

var _boom2 = _interopRequireDefault(_boom);

var _path = require('path');

var _utilsFrom_root = require('../utils/from_root');

var _utilsFrom_root2 = _interopRequireDefault(_utilsFrom_root);

var _ui_exports = require('./ui_exports');

var _ui_exports2 = _interopRequireDefault(_ui_exports);

var _ui_bundle = require('./ui_bundle');

var _ui_bundle2 = _interopRequireDefault(_ui_bundle);

var _ui_bundle_collection = require('./ui_bundle_collection');

var _ui_bundle_collection2 = _interopRequireDefault(_ui_bundle_collection);

var _ui_bundler_env = require('./ui_bundler_env');

var _ui_bundler_env2 = _interopRequireDefault(_ui_bundler_env);

exports['default'] = _asyncToGenerator(function* (kbnServer, server, config) {
  var getKibanaPayload = _asyncToGenerator(function* (_ref) {
    var app = _ref.app;
    var request = _ref.request;
    var includeUserProvidedConfig = _ref.includeUserProvidedConfig;

    var uiSettings = server.uiSettings();

    return {
      app: app,
      nav: uiExports.navLinks.inOrder,
      version: kbnServer.version,
      buildNum: config.get('pkg.buildNum'),
      buildSha: config.get('pkg.buildSha'),
      basePath: config.get('server.basePath'),
      serverName: config.get('server.name'),
      devMode: config.get('env.dev'),
      uiSettings: yield (0, _bluebird.props)({
        defaults: uiSettings.getDefaults(),
        user: includeUserProvidedConfig && uiSettings.getUserProvided(request)
      }),
      vars: yield (0, _bluebird.reduce)(uiExports.injectedVarsReplacers, _asyncToGenerator(function* (acc, replacer) {
        return yield replacer(acc, request, server);
      }), (0, _lodash.defaults)((yield app.getInjectedVars()) || {}, uiExports.defaultInjectedVars))
    };
  });

  var renderApp = _asyncToGenerator(function* (_ref2) {
    var app = _ref2.app;
    var reply = _ref2.reply;
    var _ref2$includeUserProvidedConfig = _ref2.includeUserProvidedConfig;
    var includeUserProvidedConfig = _ref2$includeUserProvidedConfig === undefined ? true : _ref2$includeUserProvidedConfig;

    try {
      return reply.view(app.templateName, {
        app: app,
        kibanaPayload: yield getKibanaPayload({
          app: app,
          request: reply.request,
          includeUserProvidedConfig: includeUserProvidedConfig
        }),
        bundlePath: config.get('server.basePath') + '/bundles'
      });
    } catch (err) {
      reply(err);
    }
  });

  var uiExports = kbnServer.uiExports = new _ui_exports2['default']({
    urlBasePath: config.get('server.basePath')
  });

  var bundlerEnv = new _ui_bundler_env2['default'](config.get('optimize.bundleDir'));
  bundlerEnv.addContext('env', config.get('env.name'));
  bundlerEnv.addContext('urlBasePath', config.get('server.basePath'));
  bundlerEnv.addContext('sourceMaps', config.get('optimize.sourceMaps'));
  bundlerEnv.addContext('kbnVersion', config.get('pkg.version'));
  bundlerEnv.addContext('buildNum', config.get('pkg.buildNum'));
  uiExports.addConsumer(bundlerEnv);

  var _iteratorNormalCompletion = true;
  var _didIteratorError = false;
  var _iteratorError = undefined;

  try {
    for (var _iterator = kbnServer.plugins[Symbol.iterator](), _step; !(_iteratorNormalCompletion = (_step = _iterator.next()).done); _iteratorNormalCompletion = true) {
      var plugin = _step.value;

      uiExports.consumePlugin(plugin);
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

  var bundles = kbnServer.bundles = new _ui_bundle_collection2['default'](bundlerEnv, config.get('optimize.bundleFilter'));

  var _iteratorNormalCompletion2 = true;
  var _didIteratorError2 = false;
  var _iteratorError2 = undefined;

  try {
    for (var _iterator2 = uiExports.getAllApps()[Symbol.iterator](), _step2; !(_iteratorNormalCompletion2 = (_step2 = _iterator2.next()).done); _iteratorNormalCompletion2 = true) {
      var app = _step2.value;

      bundles.addApp(app);
    }
  } catch (err) {
    _didIteratorError2 = true;
    _iteratorError2 = err;
  } finally {
    try {
      if (!_iteratorNormalCompletion2 && _iterator2['return']) {
        _iterator2['return']();
      }
    } finally {
      if (_didIteratorError2) {
        throw _iteratorError2;
      }
    }
  }

  var _iteratorNormalCompletion3 = true;
  var _didIteratorError3 = false;
  var _iteratorError3 = undefined;

  try {
    for (var _iterator3 = uiExports.getBundleProviders()[Symbol.iterator](), _step3; !(_iteratorNormalCompletion3 = (_step3 = _iterator3.next()).done); _iteratorNormalCompletion3 = true) {
      var gen = _step3.value;

      var bundle = yield gen(_ui_bundle2['default'], bundlerEnv, uiExports.getAllApps(), kbnServer.plugins);
      if (bundle) bundles.add(bundle);
    }

    // render all views from the ui/views directory
  } catch (err) {
    _didIteratorError3 = true;
    _iteratorError3 = err;
  } finally {
    try {
      if (!_iteratorNormalCompletion3 && _iterator3['return']) {
        _iterator3['return']();
      }
    } finally {
      if (_didIteratorError3) {
        throw _iteratorError3;
      }
    }
  }

  server.setupViews((0, _path.resolve)(__dirname, 'views'));

  server.route({
    path: '/app/{id}',
    method: 'GET',
    handler: _asyncToGenerator(function* (req, reply) {
      var id = req.params.id;
      var app = uiExports.apps.byId[id];
      if (!app) return reply(_boom2['default'].notFound('Unknown app ' + id));

      try {
        if (kbnServer.status.isGreen()) {
          yield reply.renderApp(app);
        } else {
          yield reply.renderStatusPage();
        }
      } catch (err) {
        reply(_boom2['default'].wrap(err));
      }
    })
  });

  server.decorate('reply', 'renderApp', function (app) {
    return renderApp({
      app: app,
      reply: this,
      includeUserProvidedConfig: true
    });
  });

  server.decorate('reply', 'renderAppWithDefaultConfig', function (app) {
    return renderApp({
      app: app,
      reply: this,
      includeUserProvidedConfig: false
    });
  });
});
module.exports = exports['default'];
