'use strict';

Object.defineProperty(exports, '__esModule', {
  value: true
});
exports['default'] = pluginInstall;

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { 'default': obj }; }

var _utils = require('../../utils');

var _fs = require('fs');

var _fs2 = _interopRequireDefault(_fs);

var _install = require('./install');

var _install2 = _interopRequireDefault(_install);

var _libLogger = require('../lib/logger');

var _libLogger2 = _interopRequireDefault(_libLogger);

var _utilsPackage_json = require('../../utils/package_json');

var _utilsPackage_json2 = _interopRequireDefault(_utilsPackage_json);

var _serverPath = require('../../server/path');

var _settings = require('./settings');

var _lodash = require('lodash');

var _libLog_warnings = require('../lib/log_warnings');

var _libLog_warnings2 = _interopRequireDefault(_libLog_warnings);

function processCommand(command, options) {
  var settings = undefined;
  try {
    settings = (0, _settings.parse)(command, options, _utilsPackage_json2['default']);
  } catch (ex) {
    //The logger has not yet been initialized.
    console.error(ex.message);
    process.exit(64); // eslint-disable-line no-process-exit
  }

  var logger = new _libLogger2['default'](settings);
  (0, _libLog_warnings2['default'])(settings, logger);
  (0, _install2['default'])(settings, logger);
}

function pluginInstall(program) {
  program.command('install <plugin/url>').option('-q, --quiet', 'disable all process messaging except errors').option('-s, --silent', 'disable all process messaging').option('-c, --config <path>', 'path to the config file', (0, _serverPath.getConfig)()).option('-t, --timeout <duration>', 'length of time before failing; 0 for never fail', _settings.parseMilliseconds).option('-d, --plugin-dir <path>', 'path to the directory where plugins are stored', (0, _utils.fromRoot)('plugins')).description('install a plugin', 'Common examples:\n  install x-pack\n  install file:///Path/to/my/x-pack.zip\n  install https://path.to/my/x-pack.zip').action(processCommand);
}

;
module.exports = exports['default'];
