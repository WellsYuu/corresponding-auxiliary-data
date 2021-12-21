'use strict';

Object.defineProperty(exports, '__esModule', {
  value: true
});

var _extends = Object.assign || function (target) { for (var i = 1; i < arguments.length; i++) { var source = arguments[i]; for (var key in source) { if (Object.prototype.hasOwnProperty.call(source, key)) { target[key] = source[key]; } } } return target; };

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { 'default': obj }; }

var _joi = require('joi');

var _joi2 = _interopRequireDefault(_joi);

var _boom = require('boom');

var _boom2 = _interopRequireDefault(_boom);

var _api_serverServer = require('./api_server/server');

var _api_serverServer2 = _interopRequireDefault(_api_serverServer);

var _fs = require('fs');

var _path = require('path');

var _lodash = require('lodash');

var _serverProxy_config_collection = require('./server/proxy_config_collection');

exports['default'] = function (kibana) {
  var modules = (0, _path.resolve)(__dirname, 'public/webpackShims/');
  var src = (0, _path.resolve)(__dirname, 'public/src/');

  var apps = [];

  if ((0, _fs.existsSync)((0, _path.resolve)(__dirname, 'public/tests'))) {
    apps.push({
      title: 'Console Tests',
      id: 'sense-tests',
      main: 'plugins/console/tests',
      hidden: true
      //listed: false // uncomment after https://github.com/elastic/kibana/pull/4755
    });
  }

  return new kibana.Plugin({
    id: 'console',
    require: ['elasticsearch'],

    config: function config(Joi) {
      return Joi.object({
        enabled: Joi.boolean()['default'](true),
        proxyFilter: Joi.array().items(Joi.string()).single()['default'](['.*']),
        ssl: Joi.object({
          verify: Joi.boolean()
        })['default'](),
        proxyConfig: Joi.array().items(Joi.object().keys({
          match: Joi.object().keys({
            protocol: Joi.string()['default']('*'),
            host: Joi.string()['default']('*'),
            port: Joi.string()['default']('*'),
            path: Joi.string()['default']('*')
          }),

          timeout: Joi.number(),
          ssl: Joi.object().keys({
            verify: Joi.boolean(),
            ca: Joi.array().single().items(Joi.string()),
            cert: Joi.string(),
            key: Joi.string()
          })['default']()
        }))['default']([{
          match: {
            protocol: '*',
            host: '*',
            port: '*',
            path: '*'
          },

          timeout: 180000,
          ssl: {
            verify: true
          }
        }])
      })['default']();
    },

    init: function init(server, options) {
      var filters = options.proxyFilter.map(function (str) {
        return new RegExp(str);
      });

      if (options.ssl && options.ssl.verify) {
        throw new Error('sense.ssl.verify is no longer supported.');
      }

      var proxyConfigCollection = new _serverProxy_config_collection.ProxyConfigCollection(options.proxyConfig);
      var proxyRouteConfig = {
        validate: {
          query: _joi2['default'].object().keys({
            uri: _joi2['default'].string()
          }).unknown(true)
        },

        pre: [function filterUri(req, reply) {
          var uri = req.query.uri;

          if (!filters.some(function (re) {
            return re.test(uri);
          })) {
            var err = _boom2['default'].forbidden();
            err.output.payload = 'Error connecting to \'' + uri + '\':\n\nUnable to send requests to that url.';
            err.output.headers['content-type'] = 'text/plain';
            reply(err);
          } else {
            reply();
          }
        }],

        handler: function handler(req, reply) {
          var baseUri = server.config().get('elasticsearch.url');
          var path = req.query.uri;

          baseUri = baseUri.replace(/\/+$/, '');
          path = path.replace(/^\/+/, '');
          var uri = baseUri + '/' + path;

          var requestHeadersWhitelist = server.config().get('elasticsearch.requestHeadersWhitelist');
          var filterHeaders = server.plugins.elasticsearch.filterHeaders;
          reply.proxy(_extends({
            mapUri: function mapUri(request, done) {
              done(null, uri, filterHeaders(request.headers, requestHeadersWhitelist));
            },
            xforward: true,
            onResponse: function onResponse(err, res, request, reply, settings, ttl) {
              if (err != null) {
                reply('Error connecting to \'' + uri + '\':\n\n' + err.message).type('text/plain').statusCode = 502;
              } else {
                reply(null, res);
              }
            }

          }, proxyConfigCollection.configForUri(uri)));
        }
      };

      server.route({
        path: '/api/console/proxy',
        method: '*',
        config: _extends({}, proxyRouteConfig, {

          payload: {
            output: 'stream',
            parse: false
          }
        })
      });

      server.route({
        path: '/api/console/proxy',
        method: 'GET',
        config: _extends({}, proxyRouteConfig)
      });

      server.route({
        path: '/api/console/api_server',
        method: ['GET', 'POST'],
        handler: function handler(req, reply) {
          var _req$query = req.query;
          var sense_version = _req$query.sense_version;
          var apis = _req$query.apis;

          if (!apis) {
            reply(_boom2['default'].badRequest('"apis" is a required param.'));
            return;
          }

          return _api_serverServer2['default'].resolveApi(sense_version, apis.split(','), reply);
        }
      });

      var testApp = kibana.uiExports.apps.hidden.byId['sense-tests'];
      if (testApp) {
        server.route({
          path: '/app/sense-tests',
          method: 'GET',
          handler: function handler(req, reply) {
            return reply.renderApp(testApp);
          }
        });
      }
    },

    uiExports: {
      apps: apps,
      hacks: ['plugins/console/hacks/register'],
      devTools: ['plugins/console/console'],

      injectDefaultVars: function injectDefaultVars(server, options) {
        var varsToInject = options;
        varsToInject.elasticsearchUrl = server.config().get('elasticsearch.url');
        return varsToInject;
      },

      noParse: [(0, _path.join)(modules, 'ace' + _path.sep), (0, _path.join)(modules, 'moment_src/moment' + _path.sep), (0, _path.join)(src, 'sense_editor/mode/worker.js')]
    }
  });
};

module.exports = exports['default'];
