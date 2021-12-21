'use strict';

Object.defineProperty(exports, '__esModule', {
  value: true
});
exports.existingInstall = existingInstall;

var rebuildCache = _asyncToGenerator(function* (settings, logger) {
  logger.log('Optimizing and caching browser bundles...');
  var serverConfig = _lodash2['default'].merge((0, _cliServeRead_yaml_config2['default'])(settings.config), {
    env: 'production',
    logging: {
      silent: settings.silent,
      quiet: !settings.silent,
      verbose: false
    },
    optimize: {
      useBundleCache: false
    },
    server: {
      autoListen: false
    },
    plugins: {
      initialize: false,
      scanDirs: [settings.pluginDir, (0, _utils.fromRoot)('src/core_plugins')]
    },
    uiSettings: {
      enabled: false
    }
  });

  var kbnServer = new _serverKbn_server2['default'](serverConfig);
  yield kbnServer.ready();
  yield kbnServer.close();
});

exports.rebuildCache = rebuildCache;
exports.assertVersion = assertVersion;

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { 'default': obj }; }

function _asyncToGenerator(fn) { return function () { var gen = fn.apply(this, arguments); return new Promise(function (resolve, reject) { var callNext = step.bind(null, 'next'); var callThrow = step.bind(null, 'throw'); function step(key, arg) { try { var info = gen[key](arg); var value = info.value; } catch (error) { reject(error); return; } if (info.done) { resolve(value); } else { Promise.resolve(value).then(callNext, callThrow); } } callNext(); }); }; }

var _lodash = require('lodash');

var _lodash2 = _interopRequireDefault(_lodash);

var _utils = require('../../utils');

var _serverKbn_server = require('../../server/kbn_server');

var _serverKbn_server2 = _interopRequireDefault(_serverKbn_server);

var _cliServeRead_yaml_config = require('../../cli/serve/read_yaml_config');

var _cliServeRead_yaml_config2 = _interopRequireDefault(_cliServeRead_yaml_config);

var _utilsVersion = require('../../utils/version');

var _fs = require('fs');

function existingInstall(settings, logger) {
  try {
    (0, _fs.statSync)(settings.plugins[0].path);

    logger.error('Plugin ' + settings.plugins[0].name + ' already exists, please remove before installing a new version');
    process.exit(70); // eslint-disable-line no-process-exit
  } catch (e) {
    if (e.code !== 'ENOENT') throw e;
  }
}

function assertVersion(settings) {
  if (!settings.plugins[0].kibanaVersion) {
    throw new Error('Plugin package.json is missing both a version property (required) and a kibana.version property (optional).');
  }

  var actual = (0, _utilsVersion.cleanVersion)(settings.plugins[0].kibanaVersion);
  var expected = (0, _utilsVersion.cleanVersion)(settings.version);
  if (!(0, _utilsVersion.versionSatisfies)(actual, expected)) {
    throw new Error('Incorrect Kibana version in plugin [' + settings.plugins[0].name + ']. ' + ('Expected [' + expected + ']; found [' + actual + ']'));
  }
}
