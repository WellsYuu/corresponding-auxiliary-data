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

var _createClass = (function () { function defineProperties(target, props) { for (var i = 0; i < props.length; i++) { var descriptor = props[i]; descriptor.enumerable = descriptor.enumerable || false; descriptor.configurable = true; if ('value' in descriptor) descriptor.writable = true; Object.defineProperty(target, descriptor.key, descriptor); } } return function (Constructor, protoProps, staticProps) { if (protoProps) defineProperties(Constructor.prototype, protoProps); if (staticProps) defineProperties(Constructor, staticProps); return Constructor; }; })();

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { 'default': obj }; }

function _asyncToGenerator(fn) { return function () { var gen = fn.apply(this, arguments); return new Promise(function (resolve, reject) { var callNext = step.bind(null, 'next'); var callThrow = step.bind(null, 'throw'); function step(key, arg) { try { var info = gen[key](arg); var value = info.value; } catch (error) { reject(error); return; } if (info.done) { resolve(value); } else { Promise.resolve(value).then(callNext, callThrow); } } callNext(); }); }; }

function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError('Cannot call a class as a function'); } }

var _boom = require('boom');

var _boom2 = _interopRequireDefault(_boom);

var _hapi = require('hapi');

var _bluebird = require('bluebird');

var _serverHttpRegister_hapi_plugins = require('../../server/http/register_hapi_plugins');

var _serverHttpRegister_hapi_plugins2 = _interopRequireDefault(_serverHttpRegister_hapi_plugins);

module.exports = (function () {
  function LazyServer(host, port, optimizer) {
    _classCallCheck(this, LazyServer);

    this.optimizer = optimizer;
    this.server = new _hapi.Server();

    (0, _serverHttpRegister_hapi_plugins2['default'])(null, this.server);

    this.server.connection({
      host: host,
      port: port
    });
  }

  _createClass(LazyServer, [{
    key: 'init',
    value: _asyncToGenerator(function* () {
      var _this = this;

      yield this.optimizer.init();
      this.optimizer.bindToServer(this.server);
      yield (0, _bluebird.fromNode)(function (cb) {
        return _this.server.start(cb);
      });
    })
  }]);

  return LazyServer;
})();
