'use strict';

Object.defineProperty(exports, '__esModule', {
  value: true
});

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { 'default': obj }; }

function _asyncToGenerator(fn) { return function () { var gen = fn.apply(this, arguments); return new Promise(function (resolve, reject) { var callNext = step.bind(null, 'next'); var callThrow = step.bind(null, 'throw'); function step(key, arg) { try { var info = gen[key](arg); var value = info.value; } catch (error) { reject(error); return; } if (info.done) { resolve(value); } else { Promise.resolve(value).then(callNext, callThrow); } } callNext(); }); }; }

var _lazy_server = require('./lazy_server');

var _lazy_server2 = _interopRequireDefault(_lazy_server);

var _lazy_optimizer = require('./lazy_optimizer');

var _lazy_optimizer2 = _interopRequireDefault(_lazy_optimizer);

var _utils = require('../../utils');

exports['default'] = _asyncToGenerator(function* (kbnServer, kibanaHapiServer, config) {
  var server = new _lazy_server2['default'](config.get('optimize.lazyHost'), config.get('optimize.lazyPort'), new _lazy_optimizer2['default']({
    log: function log(tags, data) {
      return kibanaHapiServer.log(tags, data);
    },
    env: kbnServer.bundles.env,
    bundles: kbnServer.bundles,
    profile: config.get('optimize.profile'),
    sourceMaps: config.get('optimize.sourceMaps'),
    prebuild: config.get('optimize.lazyPrebuild'),
    urlBasePath: config.get('server.basePath'),
    unsafeCache: config.get('optimize.unsafeCache')
  }));

  var ready = false;

  var sendReady = function sendReady() {
    if (!process.connected) return;
    process.send(['WORKER_BROADCAST', { optimizeReady: ready }]);
  };

  process.on('message', function (msg) {
    if (msg && msg.optimizeReady === '?') sendReady();
  });

  sendReady();

  yield server.init();

  ready = true;
  sendReady();
});
module.exports = exports['default'];
