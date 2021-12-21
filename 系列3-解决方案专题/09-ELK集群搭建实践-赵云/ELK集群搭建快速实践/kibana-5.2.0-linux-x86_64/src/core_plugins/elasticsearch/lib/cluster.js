'use strict';

Object.defineProperty(exports, '__esModule', {
  value: true
});

var _createClass = (function () { function defineProperties(target, props) { for (var i = 0; i < props.length; i++) { var descriptor = props[i]; descriptor.enumerable = descriptor.enumerable || false; descriptor.configurable = true; if ('value' in descriptor) descriptor.writable = true; Object.defineProperty(target, descriptor.key, descriptor); } } return function (Constructor, protoProps, staticProps) { if (protoProps) defineProperties(Constructor.prototype, protoProps); if (staticProps) defineProperties(Constructor, staticProps); return Constructor; }; })();

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { 'default': obj }; }

function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError('Cannot call a class as a function'); } }

var _elasticsearch = require('elasticsearch');

var _elasticsearch2 = _interopRequireDefault(_elasticsearch);

var _lodash = require('lodash');

var _lodashInternalToPath = require('lodash/internal/toPath');

var _lodashInternalToPath2 = _interopRequireDefault(_lodashInternalToPath);

var _boom = require('boom');

var _boom2 = _interopRequireDefault(_boom);

var _filter_headers = require('./filter_headers');

var _filter_headers2 = _interopRequireDefault(_filter_headers);

var _parse_config = require('./parse_config');

var Cluster = (function () {
  function Cluster(config) {
    var _this = this;

    _classCallCheck(this, Cluster);

    this.callWithRequest = function (req, endpoint) {
      if (req === undefined) req = {};
      var clientParams = arguments.length <= 2 || arguments[2] === undefined ? {} : arguments[2];
      var options = arguments.length <= 3 || arguments[3] === undefined ? {} : arguments[3];

      if (req.headers) {
        var filteredHeaders = (0, _filter_headers2['default'])(req.headers, _this.getRequestHeadersWhitelist());
        (0, _lodash.set)(clientParams, 'headers', filteredHeaders);
      }

      return callAPI(_this._noAuthClient, endpoint, clientParams, options);
    };

    this.callWithInternalUser = function (endpoint) {
      var clientParams = arguments.length <= 1 || arguments[1] === undefined ? {} : arguments[1];
      var options = arguments.length <= 2 || arguments[2] === undefined ? {} : arguments[2];

      return callAPI(_this._client, endpoint, clientParams, options);
    };

    this.getRequestHeadersWhitelist = function () {
      return getClonedProperty(_this._config, 'requestHeadersWhitelist');
    };

    this.getCustomHeaders = function () {
      return getClonedProperty(_this._config, 'customHeaders');
    };

    this.getRequestTimeout = function () {
      return getClonedProperty(_this._config, 'requestTimeout');
    };

    this.getUrl = function () {
      return getClonedProperty(_this._config, 'url');
    };

    this.getSsl = function () {
      return getClonedProperty(_this._config, 'ssl');
    };

    this.getClient = function () {
      return _this._client;
    };

    this.createClient = function (configOverrides) {
      var config = Object.assign({}, _this._getClientConfig(), configOverrides);
      return new _elasticsearch2['default'].Client((0, _parse_config.parseConfig)(config));
    };

    this._getClientConfig = function () {
      return getClonedProperties(_this._config, ['url', 'ssl', 'username', 'password', 'customHeaders', 'plugins', 'apiVersion', 'keepAlive', 'pingTimeout', 'requestTimeout', 'log']);
    };

    this._config = Object.assign({}, config);
    this.errors = _elasticsearch2['default'].errors;

    this._client = this.createClient();
    this._noAuthClient = this.createClient({ auth: false });

    return this;
  }

  _createClass(Cluster, [{
    key: 'close',
    value: function close() {
      if (this._client) {
        this._client.close();
      }

      if (this._noAuthClient) {
        this._noAuthClient.close();
      }
    }
  }]);

  return Cluster;
})();

exports.Cluster = Cluster;

function callAPI(client, endpoint) {
  var clientParams = arguments.length <= 2 || arguments[2] === undefined ? {} : arguments[2];
  var options = arguments.length <= 3 || arguments[3] === undefined ? {} : arguments[3];

  var wrap401Errors = options.wrap401Errors !== false;
  var clientPath = (0, _lodashInternalToPath2['default'])(endpoint);
  var api = (0, _lodash.get)(client, clientPath);

  var apiContext = (0, _lodash.get)(client, clientPath.slice(0, -1));
  if ((0, _lodash.isEmpty)(apiContext)) {
    apiContext = client;
  }

  if (!api) {
    throw new Error('called with an invalid endpoint: ' + endpoint);
  }

  return api.call(apiContext, clientParams)['catch'](function (err) {
    if (!wrap401Errors || err.statusCode !== 401) {
      return Promise.reject(err);
    }

    var boomError = _boom2['default'].wrap(err, err.statusCode);
    var wwwAuthHeader = (0, _lodash.get)(err, 'body.error.header[WWW-Authenticate]');
    boomError.output.headers['WWW-Authenticate'] = wwwAuthHeader || 'Basic realm="Authorization Required"';

    throw boomError;
  });
}

function getClonedProperties(config, paths) {
  return (0, _lodash.cloneDeep)(paths ? (0, _lodash.pick)(config, paths) : config);
}

function getClonedProperty(config, path) {
  return (0, _lodash.cloneDeep)(path ? (0, _lodash.get)(config, path) : config);
}
