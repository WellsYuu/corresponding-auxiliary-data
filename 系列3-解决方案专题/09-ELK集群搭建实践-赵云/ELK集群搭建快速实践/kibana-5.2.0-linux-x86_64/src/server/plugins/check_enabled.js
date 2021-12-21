'use strict';

Object.defineProperty(exports, '__esModule', {
  value: true
});

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { 'default': obj }; }

function _toConsumableArray(arr) { if (Array.isArray(arr)) { for (var i = 0, arr2 = Array(arr.length); i < arr.length; i++) arr2[i] = arr[i]; return arr2; } else { return Array.from(arr); } }

function _asyncToGenerator(fn) { return function () { var gen = fn.apply(this, arguments); return new Promise(function (resolve, reject) { var callNext = step.bind(null, 'next'); var callThrow = step.bind(null, 'throw'); function step(key, arg) { try { var info = gen[key](arg); var value = info.value; } catch (error) { reject(error); return; } if (info.done) { resolve(value); } else { Promise.resolve(value).then(callNext, callThrow); } } callNext(); }); }; }

var _lodashInternalToPath = require('lodash/internal/toPath');

var _lodashInternalToPath2 = _interopRequireDefault(_lodashInternalToPath);

exports['default'] = _asyncToGenerator(function* (kbnServer, server, config) {
  var forcedOverride = {
    console: function console(enabledInConfig) {
      return !config.get('elasticsearch.tribe.url') && enabledInConfig;
    }
  };

  var plugins = kbnServer.plugins;
  var _iteratorNormalCompletion = true;
  var _didIteratorError = false;
  var _iteratorError = undefined;

  try {

    for (var _iterator = plugins[Symbol.iterator](), _step; !(_iteratorNormalCompletion = (_step = _iterator.next()).done); _iteratorNormalCompletion = true) {
      var plugin = _step.value;

      var enabledInConfig = config.get([].concat(_toConsumableArray((0, _lodashInternalToPath2['default'])(plugin.configPrefix)), ['enabled']));
      var hasOveride = forcedOverride.hasOwnProperty(plugin.id);
      if (hasOveride) {
        if (!forcedOverride[plugin.id](enabledInConfig)) {
          plugins.disable(plugin);
        }
      } else if (!enabledInConfig) {
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

  return;
});
module.exports = exports['default'];
