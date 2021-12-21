'use strict';

Object.defineProperty(exports, '__esModule', {
  value: true
});

var _createClass = (function () { function defineProperties(target, props) { for (var i = 0; i < props.length; i++) { var descriptor = props[i]; descriptor.enumerable = descriptor.enumerable || false; descriptor.configurable = true; if ('value' in descriptor) descriptor.writable = true; Object.defineProperty(target, descriptor.key, descriptor); } } return function (Constructor, protoProps, staticProps) { if (protoProps) defineProperties(Constructor.prototype, protoProps); if (staticProps) defineProperties(Constructor, staticProps); return Constructor; }; })();

function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError('Cannot call a class as a function'); } }

var _lodash = require('lodash');

var _url = require('url');

var _https = require('https');

var _fs = require('fs');

var _wildcard_matcher = require('./wildcard_matcher');

var makeHttpsAgent = (0, _lodash.memoize)(function (opts) {
  return new _https.Agent(opts);
}, function (opts) {
  return JSON.stringify(opts);
});

var ProxyConfig = (function () {
  function ProxyConfig(config) {
    _classCallCheck(this, ProxyConfig);

    config = Object.assign({}, config);

    // -----
    // read "match" info
    // -----
    var rawMatches = Object.assign({}, config.match);
    this.id = (0, _url.format)({
      protocol: rawMatches.protocol,
      hostname: rawMatches.host,
      port: rawMatches.port,
      pathname: rawMatches.path
    }) || '*';

    this.matchers = {
      protocol: new _wildcard_matcher.WildcardMatcher(rawMatches.protocol),
      host: new _wildcard_matcher.WildcardMatcher(rawMatches.host),
      port: new _wildcard_matcher.WildcardMatcher(rawMatches.port),
      path: new _wildcard_matcher.WildcardMatcher(rawMatches.path, '/')
    };

    // -----
    // read config vars
    // -----
    this.timeout = config.timeout;
    this.sslAgent = this._makeSslAgent(config);
  }

  _createClass(ProxyConfig, [{
    key: '_makeSslAgent',
    value: function _makeSslAgent(config) {
      var ssl = config.ssl || {};
      this.verifySsl = ssl.verify;

      var sslAgentOpts = {
        ca: ssl.ca && ssl.ca.map(function (ca) {
          return (0, _fs.readFileSync)(ca);
        }),
        cert: ssl.cert && (0, _fs.readFileSync)(ssl.cert),
        key: ssl.key && (0, _fs.readFileSync)(ssl.key)
      };

      if ((0, _lodash.values)(sslAgentOpts).filter(Boolean).length) {
        return new _https.Agent(sslAgentOpts);
      }
    }
  }, {
    key: 'getForParsedUri',
    value: function getForParsedUri(_ref) {
      var protocol = _ref.protocol;
      var hostname = _ref.hostname;
      var port = _ref.port;
      var pathname = _ref.pathname;

      var match = this.matchers.protocol.match(protocol.slice(0, -1));
      match = match && this.matchers.host.match(hostname);
      match = match && this.matchers.port.match(port);
      match = match && this.matchers.path.match(pathname);

      if (!match) return {};
      return {
        timeout: this.timeout,
        rejectUnauthorized: this.sslAgent ? true : this.verifySsl,
        agent: protocol === 'https:' ? this.sslAgent : undefined
      };
    }
  }]);

  return ProxyConfig;
})();

exports.ProxyConfig = ProxyConfig;
