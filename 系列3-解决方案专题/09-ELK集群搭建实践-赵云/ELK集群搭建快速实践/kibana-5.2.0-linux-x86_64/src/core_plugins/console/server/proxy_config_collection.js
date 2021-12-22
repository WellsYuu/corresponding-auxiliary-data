'use strict';

Object.defineProperty(exports, '__esModule', {
  value: true
});

var _createClass = (function () { function defineProperties(target, props) { for (var i = 0; i < props.length; i++) { var descriptor = props[i]; descriptor.enumerable = descriptor.enumerable || false; descriptor.configurable = true; if ('value' in descriptor) descriptor.writable = true; Object.defineProperty(target, descriptor.key, descriptor); } } return function (Constructor, protoProps, staticProps) { if (protoProps) defineProperties(Constructor.prototype, protoProps); if (staticProps) defineProperties(Constructor, staticProps); return Constructor; }; })();

function _toConsumableArray(arr) { if (Array.isArray(arr)) { for (var i = 0, arr2 = Array(arr.length); i < arr.length; i++) arr2[i] = arr[i]; return arr2; } else { return Array.from(arr); } }

function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError('Cannot call a class as a function'); } }

var _lodash = require('lodash');

var _proxy_config = require('./proxy_config');

var _url = require('url');

var ProxyConfigCollection = (function () {
  function ProxyConfigCollection() {
    var configs = arguments.length <= 0 || arguments[0] === undefined ? [] : arguments[0];

    _classCallCheck(this, ProxyConfigCollection);

    this.configs = configs.map(function (settings) {
      return new _proxy_config.ProxyConfig(settings);
    });
  }

  _createClass(ProxyConfigCollection, [{
    key: 'configForUri',
    value: function configForUri(uri) {
      var parsedUri = (0, _url.parse)(uri);
      var settings = this.configs.map(function (config) {
        return config.getForParsedUri(parsedUri);
      });
      return _lodash.defaultsDeep.apply(undefined, [{}].concat(_toConsumableArray(settings)));
    }
  }]);

  return ProxyConfigCollection;
})();

exports.ProxyConfigCollection = ProxyConfigCollection;
