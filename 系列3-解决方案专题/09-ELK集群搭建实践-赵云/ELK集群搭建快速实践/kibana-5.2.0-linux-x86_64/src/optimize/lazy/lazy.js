'use strict';

function _asyncToGenerator(fn) { return function () { var gen = fn.apply(this, arguments); return new Promise(function (resolve, reject) { var callNext = step.bind(null, 'next'); var callThrow = step.bind(null, 'throw'); function step(key, arg) { try { var info = gen[key](arg); var value = info.value; } catch (error) { reject(error); return; } if (info.done) { resolve(value); } else { Promise.resolve(value).then(callNext, callThrow); } } callNext(); }); }; }

var _cluster = require('cluster');

module.exports = _asyncToGenerator(function* (kbnServer, server, config) {

  if (!_cluster.isWorker) {
    throw new Error('lazy optimization is only available in "watch" mode');
  }

  /**
   * When running in lazy mode two workers/threads run in one
   * of the modes: 'optmzr' or 'server'
   *
   * optmzr: this thread runs the LiveOptimizer and the LazyServer
   *   which serves the LiveOptimizer's output and blocks requests
   *   while the optimizer is running
   *
   * server: this thread runs the entire kibana server and proxies
   *   all requests for /bundles/* to the optmzr
   *
   * @param  {string} process.env.kbnWorkerType
   */
  switch (process.env.kbnWorkerType) {
    case 'optmzr':
      yield kbnServer.mixin(require('./optmzr_role'));
      break;

    case 'server':
      yield kbnServer.mixin(require('./proxy_role'));
      break;

    default:
      throw new Error('unknown kbnWorkerType "' + process.env.kbnWorkerType + '"');
  }
});
