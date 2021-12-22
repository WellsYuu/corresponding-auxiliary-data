'use strict';

Object.defineProperty(exports, '__esModule', {
  value: true
});
exports['default'] = pluginList;

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { 'default': obj }; }

var _utils = require('../../utils');

var _list = require('./list');

var _list2 = _interopRequireDefault(_list);

var _libLogger = require('../lib/logger');

var _libLogger2 = _interopRequireDefault(_libLogger);

var _settings = require('./settings');

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
  (0, _list2['default'])(settings, logger);
}

function pluginList(program) {
  program.command('list').option('-d, --plugin-dir <path>', 'path to the directory where plugins are stored', (0, _utils.fromRoot)('plugins')).description('list installed plugins').action(processCommand);
}

;
module.exports = exports['default'];
