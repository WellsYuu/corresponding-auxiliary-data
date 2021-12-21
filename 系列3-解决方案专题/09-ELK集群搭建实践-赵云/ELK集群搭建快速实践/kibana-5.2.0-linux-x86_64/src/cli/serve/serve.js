'use strict';

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { 'default': obj }; }

function _asyncToGenerator(fn) { return function () { var gen = fn.apply(this, arguments); return new Promise(function (resolve, reject) { var callNext = step.bind(null, 'next'); var callThrow = step.bind(null, 'throw'); function step(key, arg) { try { var info = gen[key](arg); var value = info.value; } catch (error) { reject(error); return; } if (info.done) { resolve(value); } else { Promise.resolve(value).then(callNext, callThrow); } } callNext(); }); }; }

var _lodash = require('lodash');

var _lodash2 = _interopRequireDefault(_lodash);

var _fs = require('fs');

var _cluster = require('cluster');

var _path = require('path');

var _utils = require('../../utils');

var _serverPath = require('../../server/path');

var _read_yaml_config = require('./read_yaml_config');

var _read_yaml_config2 = _interopRequireDefault(_read_yaml_config);

var _dev_ssl = require('../dev_ssl');

var canCluster = undefined;
try {
  require.resolve('../cluster/cluster_manager');
  canCluster = true;
} catch (e) {
  canCluster = false;
}

var pathCollector = function pathCollector() {
  var paths = [];
  return function (path) {
    paths.push((0, _path.resolve)(process.cwd(), path));
    return paths;
  };
};

var configPathCollector = pathCollector();
var pluginDirCollector = pathCollector();
var pluginPathCollector = pathCollector();

function readServerSettings(opts, extraCliOptions) {
  var settings = (0, _read_yaml_config2['default'])(opts.config);
  var set = _lodash2['default'].partial(_lodash2['default'].set, settings);
  var get = _lodash2['default'].partial(_lodash2['default'].get, settings);
  var has = _lodash2['default'].partial(_lodash2['default'].has, settings);
  var merge = _lodash2['default'].partial(_lodash2['default'].merge, settings);

  if (opts.dev) {
    set('env', 'development');
    set('optimize.lazy', true);
    if (opts.ssl && !has('server.ssl.cert') && !has('server.ssl.key')) {
      set('server.ssl.cert', _dev_ssl.DEV_SSL_CERT_PATH);
      set('server.ssl.key', _dev_ssl.DEV_SSL_KEY_PATH);
    }
  }

  if (opts.elasticsearch) set('elasticsearch.url', opts.elasticsearch);
  if (opts.port) set('server.port', opts.port);
  if (opts.host) set('server.host', opts.host);
  if (opts.quiet) set('logging.quiet', true);
  if (opts.silent) set('logging.silent', true);
  if (opts.verbose) set('logging.verbose', true);
  if (opts.logFile) set('logging.dest', opts.logFile);

  set('plugins.scanDirs', _lodash2['default'].compact([].concat(get('plugins.scanDirs'), opts.pluginDir)));

  set('plugins.paths', _lodash2['default'].compact([].concat(get('plugins.paths'), opts.pluginPath)));

  merge(extraCliOptions);

  return settings;
}

module.exports = function (program) {
  var command = program.command('serve');

  command.description('Run the kibana server').collectUnknownOptions().option('-e, --elasticsearch <uri>', 'Elasticsearch instance').option('-c, --config <path>', 'Path to the config file, can be changed with the CONFIG_PATH environment variable as well. ' + 'Use mulitple --config args to include multiple config files.', configPathCollector, [(0, _serverPath.getConfig)()]).option('-p, --port <port>', 'The port to bind to', parseInt).option('-q, --quiet', 'Prevent all logging except errors').option('-Q, --silent', 'Prevent all logging').option('--verbose', 'Turns on verbose logging').option('-H, --host <host>', 'The host to bind to').option('-l, --log-file <path>', 'The file to log to').option('--plugin-dir <path>', 'A path to scan for plugins, this can be specified multiple ' + 'times to specify multiple directories', pluginDirCollector, [(0, _utils.fromRoot)('plugins'), (0, _utils.fromRoot)('src/core_plugins')]).option('--plugin-path <path>', 'A path to a plugin which should be included by the server, ' + 'this can be specified multiple times to specify multiple paths', pluginPathCollector, []).option('--plugins <path>', 'an alias for --plugin-dir', pluginDirCollector);

  if (canCluster) {
    command.option('--dev', 'Run the server with development mode defaults').option('--no-ssl', 'Don\'t run the dev server using HTTPS').option('--no-base-path', 'Don\'t put a proxy in front of the dev server, which adds a random basePath').option('--no-watch', 'Prevents automatic restarts of the server in --dev mode');
  }

  command.action(_asyncToGenerator(function* (opts) {
    var _this = this;

    if (opts.dev) {
      try {
        var kbnDevConfig = (0, _utils.fromRoot)('config/kibana.dev.yml');
        if ((0, _fs.statSync)(kbnDevConfig).isFile()) {
          opts.config.push(kbnDevConfig);
        }
      } catch (err) {
        // ignore, kibana.dev.yml does not exist
      }
    }

    var getCurrentSettings = function getCurrentSettings() {
      return readServerSettings(opts, _this.getUnknownOptions());
    };
    var settings = getCurrentSettings();

    if (canCluster && opts.dev && !_cluster.isWorker) {
      // stop processing the action and handoff to cluster manager
      var ClusterManager = require('../cluster/cluster_manager');
      new ClusterManager(opts, settings);
      return;
    }

    var kbnServer = {};
    var KbnServer = require('../../server/kbn_server');
    try {
      kbnServer = new KbnServer(settings);
      yield kbnServer.ready();
    } catch (err) {
      var _kbnServer = kbnServer;
      var server = _kbnServer.server;

      if (err.code === 'EADDRINUSE') {
        logFatal('Port ' + err.port + ' is already in use. Another instance of Kibana may be running!', server);
      } else {
        logFatal(err, server);
      }

      kbnServer.close();
      process.exit(1); // eslint-disable-line no-process-exit
    }

    process.on('SIGHUP', function reloadConfig() {
      var settings = getCurrentSettings();
      kbnServer.server.log(['info', 'config'], 'Reloading logging configuration due to SIGHUP.');
      kbnServer.applyLoggingConfiguration(settings);
      kbnServer.server.log(['info', 'config'], 'Reloaded logging configuration due to SIGHUP.');
    });

    return kbnServer;
  }));
};

function logFatal(message, server) {
  if (server) {
    server.log(['fatal'], message);
  }
  console.error('FATAL', message);
}
