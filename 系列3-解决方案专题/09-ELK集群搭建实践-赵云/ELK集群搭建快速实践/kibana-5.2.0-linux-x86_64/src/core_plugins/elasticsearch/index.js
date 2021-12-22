'use strict';

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { 'default': obj }; }

var _lodash = require('lodash');

var _boom = require('boom');

var _libHealth_check = require('./lib/health_check');

var _libHealth_check2 = _interopRequireDefault(_libHealth_check);

var _libCreate_data_cluster = require('./lib/create_data_cluster');

var _libCreate_admin_cluster = require('./lib/create_admin_cluster');

var _libClient_logger = require('./lib/client_logger');

var _libCreate_clusters = require('./lib/create_clusters');

var _libFilter_headers = require('./lib/filter_headers');

var _libFilter_headers2 = _interopRequireDefault(_libFilter_headers);

var _libCreate_proxy = require('./lib/create_proxy');

var _libCreate_proxy2 = _interopRequireDefault(_libCreate_proxy);

var DEFAULT_REQUEST_HEADERS = ['authorization'];

module.exports = function (_ref) {
  var Plugin = _ref.Plugin;

  return new Plugin({
    require: ['kibana'],

    config: function config(Joi) {
      var array = Joi.array;
      var boolean = Joi.boolean;
      var number = Joi.number;
      var object = Joi.object;
      var string = Joi.string;
      var ref = Joi.ref;

      return object({
        enabled: boolean()['default'](true),
        url: string().uri({ scheme: ['http', 'https'] })['default']('http://localhost:9200'),
        preserveHost: boolean()['default'](true),
        username: string(),
        password: string(),
        shardTimeout: number()['default'](0),
        requestTimeout: number()['default'](30000),
        requestHeadersWhitelist: array().items().single()['default'](DEFAULT_REQUEST_HEADERS),
        customHeaders: object()['default']({}),
        pingTimeout: number()['default'](ref('requestTimeout')),
        startupTimeout: number()['default'](5000),
        logQueries: boolean()['default'](false),
        ssl: object({
          verify: boolean()['default'](true),
          ca: array().single().items(string()),
          cert: string(),
          key: string()
        })['default'](),
        apiVersion: Joi.string()['default']('master'),
        healthCheck: object({
          delay: number()['default'](2500)
        })['default'](),
        tribe: object({
          url: string().uri({ scheme: ['http', 'https'] }),
          preserveHost: boolean()['default'](true),
          username: string(),
          password: string(),
          shardTimeout: number()['default'](0),
          requestTimeout: number()['default'](30000),
          requestHeadersWhitelist: array().items().single()['default'](DEFAULT_REQUEST_HEADERS),
          customHeaders: object()['default']({}),
          pingTimeout: number()['default'](ref('requestTimeout')),
          startupTimeout: number()['default'](5000),
          logQueries: boolean()['default'](false),
          ssl: object({
            verify: boolean()['default'](true),
            ca: array().single().items(string()),
            cert: string(),
            key: string()
          })['default'](),
          apiVersion: Joi.string()['default']('master')
        })['default']()
      })['default']();
    },

    uiExports: {
      injectDefaultVars: function injectDefaultVars(server, options) {
        return {
          esRequestTimeout: options.requestTimeout,
          esShardTimeout: options.shardTimeout,
          esApiVersion: options.apiVersion,
          esDataIsTribe: (0, _lodash.get)(options, 'tribe.url') ? true : false
        };
      }
    },

    init: function init(server, options) {
      var kibanaIndex = server.config().get('kibana.index');
      var clusters = (0, _libCreate_clusters.createClusters)(server);

      server.expose('getCluster', clusters.get);
      server.expose('createCluster', clusters.create);

      server.expose('filterHeaders', _libFilter_headers2['default']);
      server.expose('ElasticsearchClientLogging', (0, _libClient_logger.clientLogger)(server));

      (0, _libCreate_data_cluster.createDataCluster)(server);
      (0, _libCreate_admin_cluster.createAdminCluster)(server);

      (0, _libCreate_proxy2['default'])(server, 'GET', '/{paths*}');
      (0, _libCreate_proxy2['default'])(server, 'POST', '/_mget');
      (0, _libCreate_proxy2['default'])(server, 'POST', '/{index}/_search');
      (0, _libCreate_proxy2['default'])(server, 'POST', '/{index}/_field_stats');
      (0, _libCreate_proxy2['default'])(server, 'POST', '/_msearch');
      (0, _libCreate_proxy2['default'])(server, 'POST', '/_search/scroll');

      function noBulkCheck(_ref2, reply) {
        var path = _ref2.path;

        if (/\/_bulk/.test(path)) {
          return reply({
            error: 'You can not send _bulk requests to this interface.'
          }).code(400).takeover();
        }
        return reply['continue']();
      }

      function noDirectIndex(_ref3, reply) {
        var path = _ref3.path;

        var requestPath = (0, _lodash.trimRight)((0, _lodash.trim)(path), '/');
        var matchPath = (0, _libCreate_proxy.createPath)('/elasticsearch', kibanaIndex);

        if (requestPath === matchPath) {
          return reply((0, _boom.methodNotAllowed)('You cannot modify the primary kibana index through this interface.'));
        }

        reply['continue']();
      }

      // These routes are actually used to deal with things such as managing
      // index patterns and advanced settings, but since hapi treats route
      // wildcards as zero-or-more, the routes also match the kibana index
      // itself. The client-side kibana code does not deal with creating nor
      // destroying the kibana index, so we limit that ability here.
      (0, _libCreate_proxy2['default'])(server, ['PUT', 'POST', 'DELETE'], '/' + kibanaIndex + '/{paths*}', {
        pre: [noDirectIndex, noBulkCheck]
      });

      // Set up the health check service and start it.

      var _healthCheck = (0, _libHealth_check2['default'])(this, server);

      var start = _healthCheck.start;
      var waitUntilReady = _healthCheck.waitUntilReady;

      server.expose('waitUntilReady', waitUntilReady);
      start();
    }
  });
};
