'use strict';

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { 'default': obj }; }

var _bluebird = require('bluebird');

var _bluebird2 = _interopRequireDefault(_bluebird);

var _is_upgradeable = require('./is_upgradeable');

var _is_upgradeable2 = _interopRequireDefault(_is_upgradeable);

var _lodash = require('lodash');

var _lodash2 = _interopRequireDefault(_lodash);

var _util = require('util');

module.exports = function (server) {
  var MAX_INTEGER = Math.pow(2, 53) - 1;

  var _server$plugins$elasticsearch$getCluster = server.plugins.elasticsearch.getCluster('admin');

  var callWithInternalUser = _server$plugins$elasticsearch$getCluster.callWithInternalUser;

  var config = server.config();

  function createNewConfig() {
    return callWithInternalUser('create', {
      index: config.get('kibana.index'),
      type: 'config',
      body: { buildNum: config.get('pkg.buildNum') },
      id: config.get('pkg.version')
    });
  }

  return function (response) {
    var newConfig = {};

    // Check to see if there are any doc. If not then we set the build number and id
    if (response.hits.hits.length === 0) {
      return createNewConfig();
    }

    // if we already have a the current version in the index then we need to stop
    var devConfig = _lodash2['default'].find(response.hits.hits, function currentVersion(hit) {
      return hit._id !== '@@version' && hit._id === config.get('pkg.version');
    });

    if (devConfig) {
      return _bluebird2['default'].resolve();
    }

    // Look for upgradeable configs. If none of them are upgradeable
    // then create a new one.
    var body = _lodash2['default'].find(response.hits.hits, _is_upgradeable2['default'].bind(null, server));
    if (!body) {
      return createNewConfig();
    }

    // if the build number is still the template string (which it wil be in development)
    // then we need to set it to the max interger. Otherwise we will set it to the build num
    body._source.buildNum = config.get('pkg.buildNum');

    server.log(['plugin', 'elasticsearch'], {
      tmpl: 'Upgrade config from <%= prevVersion %> to <%= newVersion %>',
      prevVersion: body._id,
      newVersion: config.get('pkg.version')
    });

    return callWithInternalUser('create', {
      index: config.get('kibana.index'),
      type: 'config',
      body: body._source,
      id: config.get('pkg.version')
    });
  };
};
