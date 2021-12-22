'use strict';

Object.defineProperty(exports, '__esModule', {
  value: true
});

var _createClass = (function () { function defineProperties(target, props) { for (var i = 0; i < props.length; i++) { var descriptor = props[i]; descriptor.enumerable = descriptor.enumerable || false; descriptor.configurable = true; if ('value' in descriptor) descriptor.writable = true; Object.defineProperty(target, descriptor.key, descriptor); } } return function (Constructor, protoProps, staticProps) { if (protoProps) defineProperties(Constructor.prototype, protoProps); if (staticProps) defineProperties(Constructor, staticProps); return Constructor; }; })();

exports.clientLogger = clientLogger;

function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError('Cannot call a class as a function'); } }

function clientLogger(server) {
  return (function () {
    function ElasticsearchClientLogging() {
      _classCallCheck(this, ElasticsearchClientLogging);

      this.tags = [];
      this.logQueries = false;
    }

    _createClass(ElasticsearchClientLogging, [{
      key: 'error',
      value: function error(err) {
        server.log(['error', 'elasticsearch'].concat(this.tags), err);
      }
    }, {
      key: 'warning',
      value: function warning(message) {
        server.log(['warning', 'elasticsearch'].concat(this.tags), message);
      }
    }, {
      key: 'trace',
      value: function trace(method, options, query, _response, statusCode) {
        /* Check if query logging is enabled
         * It requires Kibana to be configured with verbose logging turned on. */
        if (this.logQueries) {
          var methodAndPath = method + ' ' + options.path;
          var queryDsl = query ? query.trim() : '';
          server.log(['elasticsearch', 'query', 'debug'].concat(this.tags), [statusCode, methodAndPath, queryDsl].join('\n'));
        }
      }

      // elasticsearch-js expects the following functions to exist

    }, {
      key: 'info',
      value: function info() {}
    }, {
      key: 'debug',
      value: function debug() {}
    }, {
      key: 'close',
      value: function close() {}
    }]);

    return ElasticsearchClientLogging;
  })();
}

// additional tags to differentiate connection
