'use strict';

var _createClass = (function () { function defineProperties(target, props) { for (var i = 0; i < props.length; i++) { var descriptor = props[i]; descriptor.enumerable = descriptor.enumerable || false; descriptor.configurable = true; if ('value' in descriptor) descriptor.writable = true; Object.defineProperty(target, descriptor.key, descriptor); } } return function (Constructor, protoProps, staticProps) { if (protoProps) defineProperties(Constructor.prototype, protoProps); if (staticProps) defineProperties(Constructor, staticProps); return Constructor; }; })();

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { 'default': obj }; }

function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError('Cannot call a class as a function'); } }

var _lodash = require('lodash');

var _lodash2 = _interopRequireDefault(_lodash);

var _plugin = require('./plugin');

var _plugin2 = _interopRequireDefault(_plugin);

var _path = require('path');

module.exports = (function () {
  function PluginApi(kibana, pluginPath) {
    _classCallCheck(this, PluginApi);

    this.config = kibana.config;
    this.rootDir = kibana.rootDir;
    this['package'] = require((0, _path.join)(pluginPath, 'package.json'));
    this.Plugin = _plugin2['default'].scoped(kibana, pluginPath, this['package']);
  }

  _createClass(PluginApi, [{
    key: 'uiExports',
    get: function get() {
      throw new Error('plugin.uiExports is not defined until initialize phase');
    }
  }, {
    key: 'autoload',
    get: function get() {
      console.warn(this['package'].id + ' accessed the autoload lists which are no longer available via the Plugin API.' + 'Use the `ui/autoload/*` modules instead.');

      return {
        directives: [],
        filters: [],
        styles: [],
        modules: [],
        require: []
      };
    }
  }]);

  return PluginApi;
})();
