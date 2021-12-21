'use strict';

Object.defineProperty(exports, '__esModule', {
  value: true
});
exports.parseMilliseconds = parseMilliseconds;
exports.parse = parse;

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { 'default': obj }; }

var _expiryJs = require('expiry-js');

var _expiryJs2 = _interopRequireDefault(_expiryJs);

var _lodash = require('lodash');

var _path = require('path');

var _os = require('os');

function generateUrls(_ref) {
  var version = _ref.version;
  var plugin = _ref.plugin;

  return [plugin, 'https://artifacts.elastic.co/downloads/kibana-plugins/' + plugin + '/' + plugin + '-' + version + '.zip'];
}

function parseMilliseconds(val) {
  var result = undefined;

  try {
    var timeVal = (0, _expiryJs2['default'])(val);
    result = timeVal.asMilliseconds();
  } catch (ex) {
    result = 0;
  }

  return result;
}

;

function parse(command, options, kbnPackage) {
  var settings = {
    timeout: options.timeout || 0,
    quiet: options.quiet || false,
    silent: options.silent || false,
    config: options.config || '',
    plugin: command,
    version: kbnPackage.version,
    pluginDir: options.pluginDir || ''
  };

  settings.urls = generateUrls(settings);
  settings.workingPath = (0, _path.resolve)(settings.pluginDir, '.plugin.installing');
  settings.tempArchiveFile = (0, _path.resolve)(settings.workingPath, 'archive.part');
  settings.tempPackageFile = (0, _path.resolve)(settings.workingPath, 'package.json');
  settings.setPlugin = function (plugin) {
    settings.plugin = plugin;
    settings.pluginPath = (0, _path.resolve)(settings.pluginDir, settings.plugin.name);
  };

  return settings;
}

;
