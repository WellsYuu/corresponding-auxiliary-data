'use strict';

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { 'default': obj }; }

function _asyncToGenerator(fn) { return function () { var gen = fn.apply(this, arguments); return new Promise(function (resolve, reject) { var callNext = step.bind(null, 'next'); var callThrow = step.bind(null, 'throw'); function step(key, arg) { try { var info = gen[key](arg); var value = info.value; } catch (error) { reject(error); return; } if (info.done) { resolve(value); } else { Promise.resolve(value).then(callNext, callThrow); } } callNext(); }); }; }

var _fs_optimizer = require('./fs_optimizer');

var _fs_optimizer2 = _interopRequireDefault(_fs_optimizer);

module.exports = _asyncToGenerator(function* (kbnServer, server, config) {
  if (!config.get('optimize.enabled')) return;

  // the lazy optimizer sets up two threads, one is the server listening
  // on 5601 and the other is a server listening on 5602 that builds the
  // bundles in a "middleware" style.
  //
  // the server listening on 5601 may be restarted a number of times, depending
  // on the watch setup managed by the cli. It proxies all bundles/* requests to
  // the other server. The server on 5602 is long running, in order to prevent
  // complete rebuilds of the optimize content.
  var lazy = config.get('optimize.lazy');
  if (lazy) {
    return yield kbnServer.mixin(require('./lazy/lazy'));
  }

  var bundles = kbnServer.bundles;
  server.exposeStaticDir('/bundles/{path*}', bundles.env.workingDir);
  yield bundles.writeEntryFiles();

  // in prod, only bundle when someing is missing or invalid
  var invalidBundles = config.get('optimize.useBundleCache') ? (yield bundles.getInvalidBundles()) : bundles;

  // we might not have any work to do
  if (!invalidBundles.getIds().length) {
    server.log(['debug', 'optimize'], 'All bundles are cached and ready to go!');
    return;
  }

  // only require the FsOptimizer when we need to
  var optimizer = new _fs_optimizer2['default']({
    env: bundles.env,
    bundles: bundles,
    profile: config.get('optimize.profile'),
    urlBasePath: config.get('server.basePath'),
    sourceMaps: config.get('optimize.sourceMaps'),
    unsafeCache: config.get('optimize.unsafeCache')
  });

  server.log(['info', 'optimize'], 'Optimizing and caching ' + bundles.desc() + '. This may take a few minutes');

  var start = Date.now();
  yield optimizer.run();
  var seconds = ((Date.now() - start) / 1000).toFixed(2);

  server.log(['info', 'optimize'], 'Optimization of ' + bundles.desc() + ' complete in ' + seconds + ' seconds');
});
