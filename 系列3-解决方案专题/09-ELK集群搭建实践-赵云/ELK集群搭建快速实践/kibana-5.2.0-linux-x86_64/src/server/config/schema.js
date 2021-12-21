'use strict';

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { 'default': obj }; }

var _joi = require('joi');

var _joi2 = _interopRequireDefault(_joi);

var _lodash = require('lodash');

var _crypto = require('crypto');

var _os = require('os');

var _os2 = _interopRequireDefault(_os);

var _utils = require('../../utils');

var _path = require('../path');

var _srcUtilsPackage_json = require('../../../src/utils/package_json');

var _srcUtilsPackage_json2 = _interopRequireDefault(_srcUtilsPackage_json);

module.exports = function () {
  return _joi2['default'].object({
    pkg: _joi2['default'].object({
      version: _joi2['default'].string()['default'](_joi2['default'].ref('$version')),
      buildNum: _joi2['default'].number()['default'](_joi2['default'].ref('$buildNum')),
      buildSha: _joi2['default'].string()['default'](_joi2['default'].ref('$buildSha'))
    })['default'](),

    env: _joi2['default'].object({
      name: _joi2['default'].string()['default'](_joi2['default'].ref('$env')),
      dev: _joi2['default'].boolean()['default'](_joi2['default'].ref('$dev')),
      prod: _joi2['default'].boolean()['default'](_joi2['default'].ref('$prod'))
    })['default'](),

    dev: _joi2['default'].object({
      basePathProxyTarget: _joi2['default'].number()['default'](5603)
    })['default'](),

    pid: _joi2['default'].object({
      file: _joi2['default'].string(),
      exclusive: _joi2['default'].boolean()['default'](false)
    })['default'](),

    server: _joi2['default'].object({
      uuid: _joi2['default'].string().guid()['default'](),
      name: _joi2['default'].string()['default'](_os2['default'].hostname()),
      host: _joi2['default'].string().hostname()['default']('localhost'),
      port: _joi2['default'].number()['default'](5601),
      maxPayloadBytes: _joi2['default'].number()['default'](1048576),
      autoListen: _joi2['default'].boolean()['default'](true),
      defaultRoute: _joi2['default'].string()['default']('/app/kibana').regex(/^\//, 'start with a slash'),
      basePath: _joi2['default'].string()['default']('').allow('').regex(/(^$|^\/.*[^\/]$)/, 'start with a slash, don\'t end with one'),
      ssl: _joi2['default'].object({
        cert: _joi2['default'].string(),
        key: _joi2['default'].string()
      })['default'](),
      cors: _joi2['default'].when('$dev', {
        is: true,
        then: _joi2['default'].object()['default']({
          origin: ['*://localhost:9876'] // karma test server
        }),
        otherwise: _joi2['default'].boolean()['default'](false)
      }),
      xsrf: _joi2['default'].object({
        disableProtection: _joi2['default'].boolean()['default'](false),
        token: _joi2['default'].string().optional().notes('Deprecated')
      })['default']()
    })['default'](),

    logging: _joi2['default'].object().keys({
      silent: _joi2['default'].boolean()['default'](false),

      quiet: _joi2['default'].boolean().when('silent', {
        is: true,
        then: _joi2['default']['default'](true).valid(true),
        otherwise: _joi2['default']['default'](false)
      }),

      verbose: _joi2['default'].boolean().when('quiet', {
        is: true,
        then: _joi2['default'].valid(false)['default'](false),
        otherwise: _joi2['default']['default'](false)
      }),

      events: _joi2['default'].any()['default']({}),
      dest: _joi2['default'].string()['default']('stdout'),
      filter: _joi2['default'].any()['default']({}),
      json: _joi2['default'].boolean().when('dest', {
        is: 'stdout',
        then: _joi2['default']['default'](!process.stdout.isTTY),
        otherwise: _joi2['default']['default'](true)
      })
    })['default'](),

    ops: _joi2['default'].object({
      interval: _joi2['default'].number()['default'](5000)
    })['default'](),

    plugins: _joi2['default'].object({
      paths: _joi2['default'].array().items(_joi2['default'].string())['default']([]),
      scanDirs: _joi2['default'].array().items(_joi2['default'].string())['default']([]),
      initialize: _joi2['default'].boolean()['default'](true)
    })['default'](),

    path: _joi2['default'].object({
      data: _joi2['default'].string()['default']((0, _path.getData)())
    })['default'](),

    optimize: _joi2['default'].object({
      enabled: _joi2['default'].boolean()['default'](true),
      bundleFilter: _joi2['default'].string()['default']('!tests'),
      bundleDir: _joi2['default'].string()['default']((0, _utils.fromRoot)('optimize/bundles')),
      viewCaching: _joi2['default'].boolean()['default'](_joi2['default'].ref('$prod')),
      lazy: _joi2['default'].boolean()['default'](false),
      lazyPort: _joi2['default'].number()['default'](5602),
      lazyHost: _joi2['default'].string().hostname()['default']('localhost'),
      lazyPrebuild: _joi2['default'].boolean()['default'](false),
      lazyProxyTimeout: _joi2['default'].number()['default'](5 * 60000),
      useBundleCache: _joi2['default'].boolean()['default'](_joi2['default'].ref('$prod')),
      unsafeCache: _joi2['default'].alternatives()['try'](_joi2['default'].boolean(), _joi2['default'].string().regex(/^\/.+\/$/))['default']('/[\\/\\\\](node_modules|bower_components)[\\/\\\\]/'),
      sourceMaps: _joi2['default'].alternatives()['try'](_joi2['default'].string().required(), _joi2['default'].boolean())['default'](_joi2['default'].ref('$dev')),
      profile: _joi2['default'].boolean()['default'](false)
    })['default'](),

    status: _joi2['default'].object({
      allowAnonymous: _joi2['default'].boolean()['default'](false)
    })['default'](),
    tilemap: _joi2['default'].object({
      manifestServiceUrl: _joi2['default'].string()['default']('https://tiles.elastic.co/v2/manifest'),
      url: _joi2['default'].string(),
      options: _joi2['default'].object({
        attribution: _joi2['default'].string(),
        minZoom: _joi2['default'].number().min(1, 'Must not be less than 1')['default'](1),
        maxZoom: _joi2['default'].number()['default'](10),
        tileSize: _joi2['default'].number(),
        subdomains: _joi2['default'].array().items(_joi2['default'].string()).single(),
        errorTileUrl: _joi2['default'].string().uri(),
        tms: _joi2['default'].boolean(),
        reuseTiles: _joi2['default'].boolean(),
        bounds: _joi2['default'].array().items(_joi2['default'].array().items(_joi2['default'].number()).min(2).required()).min(2)
      })['default']()
    })['default'](),
    uiSettings: _joi2['default'].object({
      // this is used to prevent the uiSettings from initializing. Since they
      // require the elasticsearch plugin in order to function we need to turn
      // them off when we turn off the elasticsearch plugin (like we do in the
      // optimizer half of the dev server)
      enabled: _joi2['default'].boolean()['default'](true)
    })['default']()

  })['default']();
};
