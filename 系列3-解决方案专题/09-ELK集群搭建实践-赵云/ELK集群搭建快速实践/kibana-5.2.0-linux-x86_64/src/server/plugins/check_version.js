/*
 * Copyright 2021-2022 the original author or authors.
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

function _asyncToGenerator(fn) { return function () { var gen = fn.apply(this, arguments); return new Promise(function (resolve, reject) { var callNext = step.bind(null, 'next'); var callThrow = step.bind(null, 'throw'); function step(key, arg) { try { var info = gen[key](arg); var value = info.value; } catch (error) { reject(error); return; } if (info.done) { resolve(value); } else { Promise.resolve(value).then(callNext, callThrow); } } callNext(); }); }; }

var _utilsVersion = require('../../utils/version');

var _lodash = require('lodash');

function compatibleWithKibana(kbnServer, plugin) {
  //core plugins have a version of 'kibana' and are always compatible
  if (plugin.kibanaVersion === 'kibana') return true;

  var pluginKibanaVersion = (0, _utilsVersion.cleanVersion)(plugin.kibanaVersion);
  var kibanaVersion = (0, _utilsVersion.cleanVersion)(kbnServer.version);

  return (0, _utilsVersion.versionSatisfies)(pluginKibanaVersion, kibanaVersion);
}

exports['default'] = _asyncToGenerator(function* (kbnServer, server, config) {
  //because a plugin pack can contain more than one actual plugin, (for example x-pack)
  //we make sure that the warning messages are unique
  var warningMessages = new Set();
  var plugins = kbnServer.plugins;

  var _iteratorNormalCompletion = true;
  var _didIteratorError = false;
  var _iteratorError = undefined;

  try {
    for (var _iterator = plugins[Symbol.iterator](), _step; !(_iteratorNormalCompletion = (_step = _iterator.next()).done); _iteratorNormalCompletion = true) {
      var plugin = _step.value;

      var version = plugin.kibanaVersion;
      var _name = (0, _lodash.get)(plugin, 'pkg.name');

      if (!compatibleWithKibana(kbnServer, plugin)) {
        var message = 'Plugin "' + _name + '" was disabled because it expected Kibana version "' + version + '", and found "' + kbnServer.version + '".';
        warningMessages.add(message);
        plugins.disable(plugin);
      }
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

  var _iteratorNormalCompletion2 = true;
  var _didIteratorError2 = false;
  var _iteratorError2 = undefined;

  try {
    for (var _iterator2 = warningMessages[Symbol.iterator](), _step2; !(_iteratorNormalCompletion2 = (_step2 = _iterator2.next()).done); _iteratorNormalCompletion2 = true) {
      var message = _step2.value;

      server.log(['warning'], message);
    }
  } catch (err) {
    _didIteratorError2 = true;
    _iteratorError2 = err;
  } finally {
    try {
      if (!_iteratorNormalCompletion2 && _iterator2['return']) {
        _iterator2['return']();
      }
    } finally {
      if (_didIteratorError2) {
        throw _iteratorError2;
      }
    }
  }

  return;
});
module.exports = exports['default'];
