'use strict';

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { 'default': obj }; }

function _asyncToGenerator(fn) { return function () { var gen = fn.apply(this, arguments); return new Promise(function (resolve, reject) { var callNext = step.bind(null, 'next'); var callThrow = step.bind(null, 'throw'); function step(key, arg) { try { var info = gen[key](arg); var value = info.value; } catch (error) { reject(error); return; } if (info.done) { resolve(value); } else { Promise.resolve(value).then(callNext, callThrow); } } callNext(); }); }; }

var _plugin_init = require('./plugin_init');

var _plugin_init2 = _interopRequireDefault(_plugin_init);

module.exports = _asyncToGenerator(function* (kbnServer, server, config) {

  if (!config.get('plugins.initialize')) {
    server.log(['info'], 'Plugin initialization disabled.');
    return [];
  }

  var plugins = kbnServer.plugins;

  // extend plugin apis with additional context
  plugins.getPluginApis().forEach(function (api) {

    Object.defineProperty(api, 'uiExports', {
      value: kbnServer.uiExports
    });
  });

  yield (0, _plugin_init2['default'])(plugins);
});
