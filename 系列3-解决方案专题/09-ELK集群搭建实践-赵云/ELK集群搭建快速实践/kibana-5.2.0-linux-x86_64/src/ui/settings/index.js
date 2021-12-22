'use strict';

Object.defineProperty(exports, '__esModule', {
  value: true
});

var _slicedToArray = (function () { function sliceIterator(arr, i) { var _arr = []; var _n = true; var _d = false; var _e = undefined; try { for (var _i = arr[Symbol.iterator](), _s; !(_n = (_s = _i.next()).done); _n = true) { _arr.push(_s.value); if (i && _arr.length === i) break; } } catch (err) { _d = true; _e = err; } finally { try { if (!_n && _i['return']) _i['return'](); } finally { if (_d) throw _e; } } return _arr; } return function (arr, i) { if (Array.isArray(arr)) { return arr; } else if (Symbol.iterator in Object(arr)) { return sliceIterator(arr, i); } else { throw new TypeError('Invalid attempt to destructure non-iterable instance'); } }; })();

var _extends = Object.assign || function (target) { for (var i = 1; i < arguments.length; i++) { var source = arguments[i]; for (var key in source) { if (Object.prototype.hasOwnProperty.call(source, key)) { target[key] = source[key]; } } } return target; };

exports['default'] = setupSettings;

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { 'default': obj }; }

function _defineProperty(obj, key, value) { if (key in obj) { Object.defineProperty(obj, key, { value: value, enumerable: true, configurable: true, writable: true }); } else { obj[key] = value; } return obj; }

function _asyncToGenerator(fn) { return function () { var gen = fn.apply(this, arguments); return new Promise(function (resolve, reject) { var callNext = step.bind(null, 'next'); var callThrow = step.bind(null, 'throw'); function step(key, arg) { try { var info = gen[key](arg); var value = info.value; } catch (error) { reject(error); return; } if (info.done) { resolve(value); } else { Promise.resolve(value).then(callNext, callThrow); } } callNext(); }); }; }

var _lodash = require('lodash');

var _defaults = require('./defaults');

var _defaults2 = _interopRequireDefault(_defaults);

var _bluebird = require('bluebird');

var _bluebird2 = _interopRequireDefault(_bluebird);

function setupSettings(kbnServer, server, config) {
  var get = _asyncToGenerator(function* (req, key) {
    assertRequest(req);
    return getAll(req).then(function (all) {
      return all[key];
    });
  });

  var getAll = _asyncToGenerator(function* (req) {
    assertRequest(req);
    return getRaw(req).then(function (raw) {
      return Object.keys(raw).reduce(function (all, key) {
        var item = raw[key];
        var hasUserValue = ('userValue' in item);
        all[key] = hasUserValue ? item.userValue : item.value;
        return all;
      }, {});
    });
  });

  var getRaw = _asyncToGenerator(function* (req) {
    assertRequest(req);
    return Promise.all([getDefaults(), getUserProvided(req)]).then(function (_ref) {
      var _ref2 = _slicedToArray(_ref, 2);

      var defaults = _ref2[0];
      var user = _ref2[1];
      return (0, _lodash.defaultsDeep)(user, defaults);
    });
  });

  var getUserProvided = _asyncToGenerator(function* (req) {
    var _Bluebird$resolve;

    var _ref3 = arguments.length <= 1 || arguments[1] === undefined ? {} : arguments[1];

    var _ref3$ignore401Errors = _ref3.ignore401Errors;
    var ignore401Errors = _ref3$ignore401Errors === undefined ? false : _ref3$ignore401Errors;

    assertRequest(req);

    var _server$plugins$elasticsearch$getCluster = server.plugins.elasticsearch.getCluster('admin');

    var callWithRequest = _server$plugins$elasticsearch$getCluster.callWithRequest;
    var errors = _server$plugins$elasticsearch$getCluster.errors;

    // If the ui settings status isn't green, we shouldn't be attempting to get
    // user settings, since we can't be sure that all the necessary conditions
    // (e.g. elasticsearch being available) are met.
    if (status.state !== 'green') {
      return hydrateUserSettings({});
    }

    var params = getClientSettings(config);
    var allowedErrors = [errors[404], errors[403], errors.NoConnections];
    if (ignore401Errors) allowedErrors.push(errors[401]);

    return (_Bluebird$resolve = _bluebird2['default'].resolve(callWithRequest(req, 'get', params, { wrap401Errors: !ignore401Errors })))['catch'].apply(_Bluebird$resolve, allowedErrors.concat([function (err) {
      return {};
    }])).then(function (resp) {
      return resp._source || {};
    }).then(function (source) {
      return hydrateUserSettings(source);
    });
  });

  var setMany = _asyncToGenerator(function* (req, changes) {
    assertRequest(req);

    var _server$plugins$elasticsearch$getCluster2 = server.plugins.elasticsearch.getCluster('admin');

    var callWithRequest = _server$plugins$elasticsearch$getCluster2.callWithRequest;

    var clientParams = _extends({}, getClientSettings(config), {
      body: { doc: changes }
    });
    return callWithRequest(req, 'update', clientParams).then(function () {
      return {};
    });
  });

  var set = _asyncToGenerator(function* (req, key, value) {
    assertRequest(req);
    return setMany(req, _defineProperty({}, key, value));
  });

  var remove = _asyncToGenerator(function* (req, key) {
    assertRequest(req);
    return set(req, key, null);
  });

  var removeMany = _asyncToGenerator(function* (req, keys) {
    assertRequest(req);
    var changes = {};
    keys.forEach(function (key) {
      changes[key] = null;
    });
    return setMany(req, changes);
  });

  var status = kbnServer.status.create('ui settings');

  if (!config.get('uiSettings.enabled')) {
    status.disabled('uiSettings.enabled config is set to `false`');
    return;
  }

  var uiSettings = {
    // returns a Promise for the value of the requested setting
    get: get,
    // returns a Promise for a hash of setting key/value pairs
    getAll: getAll,
    // .set(key, value), returns a Promise for persisting the new value to ES
    set: set,
    // takes a key/value hash, returns a Promise for persisting the new values to ES
    setMany: setMany,
    // returns a Promise for removing the provided key from user-specific settings
    remove: remove,
    // takes an array, returns a Promise for removing every provided key from user-specific settings
    removeMany: removeMany,

    // returns a Promise for the default settings, follows metadata format (see ./defaults)
    getDefaults: getDefaults,
    // returns a Promise for user-specific settings stored in ES, follows metadata format
    getUserProvided: getUserProvided,
    // returns a Promise merging results of getDefaults & getUserProvided, follows metadata format
    getRaw: getRaw
  };

  server.decorate('server', 'uiSettings', function () {
    return uiSettings;
  });
  kbnServer.ready().then(mirrorEsStatus);

  function getDefaults() {
    return Promise.resolve((0, _defaults2['default'])());
  }

  function mirrorEsStatus() {
    var esStatus = kbnServer.status.getForPluginId('elasticsearch');

    if (!esStatus) {
      status.red('UI Settings requires the elasticsearch plugin');
      return;
    }

    copyStatus();
    esStatus.on('change', copyStatus);

    function copyStatus() {
      var state = esStatus.state;

      var statusMessage = state === 'green' ? 'Ready' : 'Elasticsearch plugin is ' + state;
      status[state](statusMessage);
    }
  }
}

function hydrateUserSettings(user) {
  return Object.keys(user).reduce(expand, {});
  function expand(expanded, key) {
    var userValue = user[key];
    if (userValue !== null) {
      expanded[key] = { userValue: userValue };
    }
    return expanded;
  }
}

function getClientSettings(config) {
  var index = config.get('kibana.index');
  var id = config.get('pkg.version');
  var type = 'config';
  return { index: index, type: type, id: id };
}

function assertRequest(req) {
  if (typeof req === 'object' && typeof req.path === 'string' && typeof req.headers === 'object') return;

  throw new TypeError('all uiSettings methods must be passed a hapi.Request object');
}
module.exports = exports['default'];
