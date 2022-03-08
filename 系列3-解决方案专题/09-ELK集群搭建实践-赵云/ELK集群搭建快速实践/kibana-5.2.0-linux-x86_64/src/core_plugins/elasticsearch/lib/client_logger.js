/*
 * Copyright 2021-2022 the original author or authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
