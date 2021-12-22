'use strict';

function _interopRequireWildcard(obj) { if (obj && obj.__esModule) { return obj; } else { var newObj = {}; if (obj != null) { for (var key in obj) { if (Object.prototype.hasOwnProperty.call(obj, key)) newObj[key] = obj[key]; } } newObj['default'] = obj; return newObj; } }

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { 'default': obj }; }

function _asyncToGenerator(fn) { return function () { var gen = fn.apply(this, arguments); return new _bluebird2['default'](function (resolve, reject) { var callNext = step.bind(null, 'next'); var callThrow = step.bind(null, 'throw'); function step(key, arg) { try { var info = gen[key](arg); var value = info.value; } catch (error) { reject(error); return; } if (info.done) { resolve(value); } else { _bluebird2['default'].resolve(value).then(callNext, callThrow); } } callNext(); }); }; }

var _bluebird = require('bluebird');

var _bluebird2 = _interopRequireDefault(_bluebird);

var _mkdirp = require('mkdirp');

var _serverLibManage_uuid = require('./server/lib/manage_uuid');

var _serverLibManage_uuid2 = _interopRequireDefault(_serverLibManage_uuid);

var _serverRoutesApiIngest = require('./server/routes/api/ingest');

var _serverRoutesApiIngest2 = _interopRequireDefault(_serverRoutesApiIngest);

var _serverRoutesApiSearch = require('./server/routes/api/search');

var _serverRoutesApiSearch2 = _interopRequireDefault(_serverRoutesApiSearch);

var _serverRoutesApiSettings = require('./server/routes/api/settings');

var _serverRoutesApiSettings2 = _interopRequireDefault(_serverRoutesApiSettings);

var _serverRoutesApiScripts = require('./server/routes/api/scripts');

var _serverRoutesApiScripts2 = _interopRequireDefault(_serverRoutesApiScripts);

var _serverLibSystem_api = require('./server/lib/system_api');

var systemApi = _interopRequireWildcard(_serverLibSystem_api);

var mkdirp = _bluebird2['default'].promisify(_mkdirp.mkdirp);

module.exports = function (kibana) {
  var kbnBaseUrl = '/app/kibana';
  return new kibana.Plugin({
    id: 'kibana',
    config: function config(Joi) {
      return Joi.object({
        enabled: Joi.boolean()['default'](true),
        defaultAppId: Joi.string()['default']('discover'),
        index: Joi.string()['default']('.kibana')
      })['default']();
    },

    uiExports: {
      hacks: ['plugins/kibana/dev_tools/hacks/hide_empty_tools'],
      app: {
        id: 'kibana',
        title: 'Kibana',
        listed: false,
        description: 'the kibana you know and love',
        main: 'plugins/kibana/kibana',
        uses: ['visTypes', 'spyModes', 'fieldFormats', 'navbarExtensions', 'managementSections', 'devTools', 'docViews'],

        injectVars: function injectVars(server) {
          var serverConfig = server.config();

          //DEPRECATED SETTINGS
          //if the url is set, the old settings must be used.
          //keeping this logic for backward compatibilty.
          var configuredUrl = server.config().get('tilemap.url');
          var isOverridden = typeof configuredUrl === 'string' && configuredUrl !== '';
          var tilemapConfig = serverConfig.get('tilemap');
          return {
            kbnDefaultAppId: serverConfig.get('kibana.defaultAppId'),
            tilemapsConfig: {
              deprecated: {
                isOverridden: isOverridden,
                config: tilemapConfig
              },
              manifestServiceUrl: serverConfig.get('tilemap.manifestServiceUrl')
            }
          };
        }
      },

      links: [{
        id: 'kibana:discover',
        title: 'Discover',
        order: -1003,
        url: kbnBaseUrl + '#/discover',
        description: 'interactively explore your data',
        icon: 'plugins/kibana/assets/discover.svg'
      }, {
        id: 'kibana:visualize',
        title: 'Visualize',
        order: -1002,
        url: kbnBaseUrl + '#/visualize',
        description: 'design data visualizations',
        icon: 'plugins/kibana/assets/visualize.svg'
      }, {
        id: 'kibana:dashboard',
        title: 'Dashboard',
        order: -1001,
        url: kbnBaseUrl + '#/dashboard',
        description: 'compose visualizations for much win',
        icon: 'plugins/kibana/assets/dashboard.svg'
      }, {
        id: 'kibana:dev_tools',
        title: 'Dev Tools',
        order: 9001,
        url: '/app/kibana#/dev_tools',
        description: 'development tools',
        icon: 'plugins/kibana/assets/wrench.svg'
      }, {
        id: 'kibana:management',
        title: 'Management',
        order: 9003,
        url: kbnBaseUrl + '#/management',
        description: 'define index patterns, change config, and more',
        icon: 'plugins/kibana/assets/settings.svg',
        linkToLastSubUrl: false
      }],
      injectDefaultVars: function injectDefaultVars(server, options) {
        return {
          kbnIndex: options.index,
          kbnBaseUrl: kbnBaseUrl
        };
      }
    },

    preInit: _asyncToGenerator(function* (server) {
      try {
        // Create the data directory (recursively, if the a parent dir doesn't exist).
        // If it already exists, does nothing.
        yield mkdirp(server.config().get('path.data'));
      } catch (err) {
        server.log(['error', 'init'], err);
        // Stop the server startup with a fatal error
        throw err;
      }
    }),

    init: function init(server, options) {
      // uuid
      (0, _serverLibManage_uuid2['default'])(server);
      // routes
      (0, _serverRoutesApiIngest2['default'])(server);
      (0, _serverRoutesApiSearch2['default'])(server);
      (0, _serverRoutesApiSettings2['default'])(server);
      (0, _serverRoutesApiScripts2['default'])(server);

      server.expose('systemApi', systemApi);
    }
  });
};
