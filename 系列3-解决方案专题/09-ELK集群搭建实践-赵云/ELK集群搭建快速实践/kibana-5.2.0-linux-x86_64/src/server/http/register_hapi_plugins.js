'use strict';

Object.defineProperty(exports, '__esModule', {
  value: true
});

var registerPlugins = _asyncToGenerator(function* (server) {
  yield (0, _bluebird.fromNode)(function (cb) {
    server.register(plugins, cb);
  });
});

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { 'default': obj }; }

function _asyncToGenerator(fn) { return function () { var gen = fn.apply(this, arguments); return new Promise(function (resolve, reject) { var callNext = step.bind(null, 'next'); var callThrow = step.bind(null, 'throw'); function step(key, arg) { try { var info = gen[key](arg); var value = info.value; } catch (error) { reject(error); return; } if (info.done) { resolve(value); } else { Promise.resolve(value).then(callNext, callThrow); } } callNext(); }); }; }

var _vision = require('vision');

var _vision2 = _interopRequireDefault(_vision);

var _inert = require('inert');

var _inert2 = _interopRequireDefault(_inert);

var _h2o2 = require('h2o2');

var _h2o22 = _interopRequireDefault(_h2o2);

var _bluebird = require('bluebird');

var plugins = [_vision2['default'], _inert2['default'], _h2o22['default']];

exports['default'] = function (kbnServer, server, config) {
  registerPlugins(server);
};

module.exports = exports['default'];
