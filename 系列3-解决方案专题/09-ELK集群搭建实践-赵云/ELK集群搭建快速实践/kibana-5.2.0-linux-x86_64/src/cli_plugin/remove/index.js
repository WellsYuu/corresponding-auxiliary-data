'use strict';

Object.defineProperty(exports, '__esModule', {
  value: true
});
exports['default'] = pluginRemove;

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { 'default': obj }; }

var _utils = require('../../utils');

var _remove = require('./remove');

var _remove2 = _interopRequireDefault(_remove);

var _libLogger = require('../lib/logger');

var _libLogger2 = _interopRequireDefault(_libLogger);

var _settings = require('./settings');

var _serverPath = require('../../server/path');

var _libLog_warnings = require('../lib/log_warnings');

var _libLog_warnings2 = _interopRequireDefault(_libLog_warnings);

function processCommand(command, options) {
  var settings = undefined;
  try {
    settings = (0, _settings.parse)(command, options);
  } catch (ex) {
    //The logger has not yet been initialized.
    console.error(ex.message);
    process.exit(64); // eslint-disable-line no-process-exit
  }

  var logger = new _libLogger2['default'](settings);
  (0, _libLog_warnings2['default'])(settings, logger);
  (0, _remove2['default'])(settings, logger);
}

function pluginRemove(program) {
  program.command('remove <plugin>').option('-q, --quiet', 'disable all process messaging except errors').option('-s, --silent', 'disable all process messaging').option('-c, --config <path>', 'path to the config file', (0, _serverPath.getConfig)()).option('-d, --plugin-dir <path>', 'path to the directory where plugins are stored', (0, _utils.fromRoot)('plugins')).description('remove a plugin', 'common examples:\n  remove x-pack').action(processCommand);
}

;
module.exports = exports['default'];
