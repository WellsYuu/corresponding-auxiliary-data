'use strict';

function _asyncToGenerator(fn) { return function () { var gen = fn.apply(this, arguments); return new Promise(function (resolve, reject) { var callNext = step.bind(null, 'next'); var callThrow = step.bind(null, 'throw'); function step(key, arg) { try { var info = gen[key](arg); var value = info.value; } catch (error) { reject(error); return; } if (info.done) { resolve(value); } else { Promise.resolve(value).then(callNext, callThrow); } } callNext(); }); }; }

var Promise = require('bluebird');
var _ = require('lodash');
var Boom = require('boom');
var chainRunnerFn = require('../handlers/chain_runner.js');
var timelionDefaults = require('../lib/get_namespaced_settings')();

function replyWithError(e, reply) {
  reply({ title: e.toString(), message: e.toString(), stack: e.stack }).code(400);
}

module.exports = function (server) {
  server.route({
    method: ['POST', 'GET'],
    path: '/api/timelion/run',
    handler: _asyncToGenerator(function* (request, reply) {
      try {
        var uiSettings = yield server.uiSettings().getAll(request);

        var tlConfig = require('../handlers/lib/tl_config.js')({
          server: server,
          request: request,
          settings: _.defaults(uiSettings, timelionDefaults) // Just in case they delete some setting.
        });

        var chainRunner = chainRunnerFn(tlConfig);
        var sheet = yield Promise.all(chainRunner.processRequest(request.payload || {
          sheet: [request.query.expression],
          time: {
            from: request.query.from,
            to: request.query.to,
            interval: request.query.interval,
            timezone: request.query.timezone
          }
        }));

        reply({
          sheet: sheet,
          stats: chainRunner.getStats()
        });
      } catch (err) {
        // TODO Maybe we should just replace everywhere we throw with Boom? Probably.
        if (err.isBoom) {
          reply(err);
        } else {
          replyWithError(err, reply);
        }
      }
    })
  });
};
