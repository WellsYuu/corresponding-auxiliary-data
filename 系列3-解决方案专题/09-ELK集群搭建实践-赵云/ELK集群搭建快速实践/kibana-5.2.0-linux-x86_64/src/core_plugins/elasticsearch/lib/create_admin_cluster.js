'use strict';

Object.defineProperty(exports, '__esModule', {
  value: true
});

var _get = function get(_x, _x2, _x3) { var _again = true; _function: while (_again) { var object = _x, property = _x2, receiver = _x3; _again = false; if (object === null) object = Function.prototype; var desc = Object.getOwnPropertyDescriptor(object, property); if (desc === undefined) { var parent = Object.getPrototypeOf(object); if (parent === null) { return undefined; } else { _x = parent; _x2 = property; _x3 = receiver; _again = true; desc = parent = undefined; continue _function; } } else if ('value' in desc) { return desc.value; } else { var getter = desc.get; if (getter === undefined) { return undefined; } return getter.call(receiver); } } };

exports.createAdminCluster = createAdminCluster;

function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError('Cannot call a class as a function'); } }

function _inherits(subClass, superClass) { if (typeof superClass !== 'function' && superClass !== null) { throw new TypeError('Super expression must either be null or a function, not ' + typeof superClass); } subClass.prototype = Object.create(superClass && superClass.prototype, { constructor: { value: subClass, enumerable: false, writable: true, configurable: true } }); if (superClass) Object.setPrototypeOf ? Object.setPrototypeOf(subClass, superClass) : subClass.__proto__ = superClass; }

var _lodash = require('lodash');

var _client_logger = require('./client_logger');

function createAdminCluster(server) {
  var config = server.config();
  var ElasticsearchClientLogging = (0, _client_logger.clientLogger)(server);

  var AdminClientLogging = (function (_ElasticsearchClientLogging) {
    _inherits(AdminClientLogging, _ElasticsearchClientLogging);

    function AdminClientLogging() {
      _classCallCheck(this, AdminClientLogging);

      _get(Object.getPrototypeOf(AdminClientLogging.prototype), 'constructor', this).apply(this, arguments);

      this.tags = ['admin'];
      this.logQueries = config.get('elasticsearch.logQueries');
    }

    return AdminClientLogging;
  })(ElasticsearchClientLogging);

  var adminCluster = server.plugins.elasticsearch.createCluster('admin', Object.assign({ log: AdminClientLogging }, config.get('elasticsearch')));

  server.on('close', (0, _lodash.bindKey)(adminCluster, 'close'));
}
