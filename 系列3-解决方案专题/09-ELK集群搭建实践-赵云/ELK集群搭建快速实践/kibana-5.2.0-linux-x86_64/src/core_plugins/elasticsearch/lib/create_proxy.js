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

var _slicedToArray = (function () { function sliceIterator(arr, i) { var _arr = []; var _n = true; var _d = false; var _e = undefined; try { for (var _i = arr[Symbol.iterator](), _s; !(_n = (_s = _i.next()).done); _n = true) { _arr.push(_s.value); if (i && _arr.length === i) break; } } catch (err) { _d = true; _e = err; } finally { try { if (!_n && _i['return']) _i['return'](); } finally { if (_d) throw _e; } } return _arr; } return function (arr, i) { if (Array.isArray(arr)) { return arr; } else if (Symbol.iterator in Object(arr)) { return sliceIterator(arr, i); } else { throw new TypeError('Invalid attempt to destructure non-iterable instance'); } }; })();

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { 'default': obj }; }

var _create_agent = require('./create_agent');

var _create_agent2 = _interopRequireDefault(_create_agent);

var _map_uri = require('./map_uri');

var _map_uri2 = _interopRequireDefault(_map_uri);

var _url = require('url');

var _lodash = require('lodash');

function createProxy(server, method, path, config) {
  var proxies = new Map([['/elasticsearch', server.plugins.elasticsearch.getCluster('data')], ['/es_admin', server.plugins.elasticsearch.getCluster('admin')]]);

  var responseHandler = function responseHandler(err, upstreamResponse, request, reply) {
    if (err) {
      reply(err);
      return;
    }

    if (upstreamResponse.headers.location) {
      // TODO: Workaround for #8705 until hapi has been updated to >= 15.0.0
      upstreamResponse.headers.location = encodeURI(upstreamResponse.headers.location);
    }

    reply(null, upstreamResponse);
  };

  var _iteratorNormalCompletion = true;
  var _didIteratorError = false;
  var _iteratorError = undefined;

  try {
    for (var _iterator = proxies[Symbol.iterator](), _step; !(_iteratorNormalCompletion = (_step = _iterator.next()).done); _iteratorNormalCompletion = true) {
      var _step$value = _slicedToArray(_step.value, 2);

      var proxyPrefix = _step$value[0];
      var cluster = _step$value[1];

      var options = {
        method: method,
        path: createProxy.createPath(proxyPrefix, path),
        config: {
          timeout: {
            socket: cluster.getRequestTimeout()
          }
        },
        handler: {
          proxy: {
            mapUri: (0, _map_uri2['default'])(cluster, proxyPrefix),
            agent: (0, _create_agent2['default'])({
              url: cluster.getUrl(),
              ssl: cluster.getSsl()
            }),
            xforward: true,
            timeout: cluster.getRequestTimeout(),
            onResponse: responseHandler
          }
        }
      };

      (0, _lodash.assign)(options.config, config);

      server.route(options);
    }
  } catch (err) {
    _didIteratorError = true;
    _iteratorError = err;
  } finally {
    try {
      if (!_iteratorNormalCompletion && _iterator['return']) {
        _iterator['return']();
      }
    } finally {
      if (_didIteratorError) {
        throw _iteratorError;
      }
    }
  }
}

createProxy.createPath = function createPath(prefix, path) {
  path = path[0] === '/' ? path : '/' + path;
  prefix = prefix[0] === '/' ? prefix : '/' + prefix;

  return '' + prefix + path;
};

module.exports = createProxy;
