'use strict';

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { 'default': obj }; }

function _asyncToGenerator(fn) { return function () { var gen = fn.apply(this, arguments); return new Promise(function (resolve, reject) { var callNext = step.bind(null, 'next'); var callThrow = step.bind(null, 'throw'); function step(key, arg) { try { var info = gen[key](arg); var value = info.value; } catch (error) { reject(error); return; } if (info.done) { resolve(value); } else { Promise.resolve(value).then(callNext, callThrow); } } callNext(); }); }; }

var _url = require('url');

var _path = require('path');

var _lodash = require('lodash');

var _lodash2 = _interopRequireDefault(_lodash);

var _fs = require('fs');

var _fs2 = _interopRequireDefault(_fs);

var _boom = require('boom');

var _boom2 = _interopRequireDefault(_boom);

var _hapi = require('hapi');

var _hapi2 = _interopRequireDefault(_hapi);

var _get_default_route = require('./get_default_route');

var _get_default_route2 = _interopRequireDefault(_get_default_route);

var _version_check = require('./version_check');

var _version_check2 = _interopRequireDefault(_version_check);

var _short_url_error = require('./short_url_error');

var _short_url_assert_valid = require('./short_url_assert_valid');

module.exports = _asyncToGenerator(function* (kbnServer, server, config) {

  server = kbnServer.server = new _hapi2['default'].Server();

  var shortUrlLookup = require('./short_url_lookup')(server);
  yield kbnServer.mixin(require('./register_hapi_plugins'));
  yield kbnServer.mixin(require('./setup_connection'));

  // provide a simple way to expose static directories
  server.decorate('server', 'exposeStaticDir', function (routePath, dirPath) {
    this.route({
      path: routePath,
      method: 'GET',
      handler: {
        directory: {
          path: dirPath,
          listing: false,
          lookupCompressed: true
        }
      },
      config: { auth: false }
    });
  });

  // provide a simple way to expose static files
  server.decorate('server', 'exposeStaticFile', function (routePath, filePath) {
    this.route({
      path: routePath,
      method: 'GET',
      handler: {
        file: filePath
      },
      config: { auth: false }
    });
  });

  // helper for creating view managers for servers
  server.decorate('server', 'setupViews', function (path, engines) {
    this.views({
      path: path,
      isCached: config.get('optimize.viewCaching'),
      engines: _lodash2['default'].assign({ jade: require('jade') }, engines || {})
    });
  });

  server.decorate('server', 'redirectToSlash', function (route) {
    this.route({
      path: route,
      method: 'GET',
      handler: function handler(req, reply) {
        return reply.redirect((0, _url.format)({
          search: req.url.search,
          pathname: req.url.pathname + '/'
        }));
      }
    });
  });

  // attach the app name to the server, so we can be sure we are actually talking to kibana
  server.ext('onPreResponse', function (req, reply) {
    var response = req.response;

    if (response.isBoom) {
      response.output.headers['kbn-name'] = kbnServer.name;
      response.output.headers['kbn-version'] = kbnServer.version;
    } else {
      response.header('kbn-name', kbnServer.name);
      response.header('kbn-version', kbnServer.version);
    }

    return reply['continue']();
  });

  server.route({
    path: '/',
    method: 'GET',
    handler: function handler(req, reply) {
      return reply.view('root_redirect', {
        hashRoute: config.get('server.basePath') + '/app/kibana',
        defaultRoute: (0, _get_default_route2['default'])(kbnServer)
      });
    }
  });

  server.route({
    method: 'GET',
    path: '/{p*}',
    handler: function handler(req, reply) {
      var path = req.path;
      if (path === '/' || path.charAt(path.length - 1) !== '/') {
        return reply(_boom2['default'].notFound());
      }
      var pathPrefix = config.get('server.basePath') ? config.get('server.basePath') + '/' : '';
      return reply.redirect((0, _url.format)({
        search: req.url.search,
        pathname: pathPrefix + path.slice(0, -1)
      })).permanent(true);
    }
  });

  server.route({
    method: 'GET',
    path: '/goto/{urlId}',
    handler: _asyncToGenerator(function* (request, reply) {
      try {
        var url = yield shortUrlLookup.getUrl(request.params.urlId, request);
        (0, _short_url_assert_valid.shortUrlAssertValid)(url);
        reply().redirect(config.get('server.basePath') + url);
      } catch (err) {
        reply((0, _short_url_error.handleShortUrlError)(err));
      }
    })
  });

  server.route({
    method: 'POST',
    path: '/shorten',
    handler: _asyncToGenerator(function* (request, reply) {
      try {
        (0, _short_url_assert_valid.shortUrlAssertValid)(request.payload.url);
        var urlId = yield shortUrlLookup.generateUrlId(request.payload.url, request);
        reply(urlId);
      } catch (err) {
        reply((0, _short_url_error.handleShortUrlError)(err));
      }
    })
  });

  // Expose static assets (fonts, favicons).
  server.exposeStaticDir('/ui/fonts/{path*}', (0, _path.resolve)(__dirname, '../../ui/public/assets/fonts'));
  server.exposeStaticDir('/ui/favicons/{path*}', (0, _path.resolve)(__dirname, '../../ui/public/assets/favicons'));

  kbnServer.mixin(_version_check2['default']);

  return kbnServer.mixin(require('./xsrf'));
});
