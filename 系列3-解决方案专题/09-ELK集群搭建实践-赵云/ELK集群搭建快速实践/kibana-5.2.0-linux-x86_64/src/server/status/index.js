'use strict';

Object.defineProperty(exports, '__esModule', {
  value: true
});

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { 'default': obj }; }

function _asyncToGenerator(fn) { return function () { var gen = fn.apply(this, arguments); return new Promise(function (resolve, reject) { var callNext = step.bind(null, 'next'); var callThrow = step.bind(null, 'throw'); function step(key, arg) { try { var info = gen[key](arg); var value = info.value; } catch (error) { reject(error); return; } if (info.done) { resolve(value); } else { Promise.resolve(value).then(callNext, callThrow); } } callNext(); }); }; }

var _lodash = require('lodash');

var _lodash2 = _interopRequireDefault(_lodash);

var _server_status = require('./server_status');

var _server_status2 = _interopRequireDefault(_server_status);

var _wrap_auth_config = require('./wrap_auth_config');

var _wrap_auth_config2 = _interopRequireDefault(_wrap_auth_config);

var _path = require('path');

exports['default'] = function (kbnServer, server, config) {
  kbnServer.status = new _server_status2['default'](kbnServer.server);

  if (server.plugins['even-better']) {
    kbnServer.mixin(require('./metrics'));
  }

  var wrapAuth = (0, _wrap_auth_config2['default'])(config.get('status.allowAnonymous'));

  server.route(wrapAuth({
    method: 'GET',
    path: '/api/status',
    handler: function handler(request, reply) {
      return reply({
        name: config.get('server.name'),
        version: config.get('pkg.version'),
        buildNum: config.get('pkg.buildNum'),
        buildSha: config.get('pkg.buildSha'),
        uuid: config.get('server.uuid'),
        status: kbnServer.status.toJSON(),
        metrics: kbnServer.metrics
      });
    }
  }));

  server.decorate('reply', 'renderStatusPage', _asyncToGenerator(function* () {
    var app = kbnServer.uiExports.getHiddenApp('status_page');
    var response = yield getResponse(this);
    response.code(kbnServer.status.isGreen() ? 200 : 503);
    return response;

    function getResponse(ctx) {
      if (app) {
        return ctx.renderApp(app);
      }
      return ctx(kbnServer.status.toString());
    }
  }));

  server.route(wrapAuth({
    method: 'GET',
    path: '/status',
    handler: function handler(request, reply) {
      return reply.renderStatusPage();
    }
  }));
};

;
module.exports = exports['default'];
