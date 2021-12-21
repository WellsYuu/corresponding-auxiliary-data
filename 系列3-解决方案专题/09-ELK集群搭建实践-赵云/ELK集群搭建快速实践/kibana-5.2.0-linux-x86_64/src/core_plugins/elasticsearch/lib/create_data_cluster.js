'use strict';

Object.defineProperty(exports, '__esModule', {
  value: true
});

var _get = function get(_x, _x2, _x3) { var _again = true; _function: while (_again) { var object = _x, property = _x2, receiver = _x3; _again = false; if (object === null) object = Function.prototype; var desc = Object.getOwnPropertyDescriptor(object, property); if (desc === undefined) { var parent = Object.getPrototypeOf(object); if (parent === null) { return undefined; } else { _x = parent; _x2 = property; _x3 = receiver; _again = true; desc = parent = undefined; continue _function; } } else if ('value' in desc) { return desc.value; } else { var getter = desc.get; if (getter === undefined) { return undefined; } return getter.call(receiver); } } };

exports.createDataCluster = createDataCluster;

function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError('Cannot call a class as a function'); } }

function _inherits(subClass, superClass) { if (typeof superClass !== 'function' && superClass !== null) { throw new TypeError('Super expression must either be null or a function, not ' + typeof superClass); } subClass.prototype = Object.create(superClass && superClass.prototype, { constructor: { value: subClass, enumerable: false, writable: true, configurable: true } }); if (superClass) Object.setPrototypeOf ? Object.setPrototypeOf(subClass, superClass) : subClass.__proto__ = superClass; }

var _lodash = require('lodash');

var _client_logger = require('./client_logger');

function createDataCluster(server) {
  var config = server.config();
  var ElasticsearchClientLogging = (0, _client_logger.clientLogger)(server);

  var DataClientLogging = (function (_ElasticsearchClientLogging) {
    _inherits(DataClientLogging, _ElasticsearchClientLogging);

    function DataClientLogging() {
      _classCallCheck(this, DataClientLogging);

      _get(Object.getPrototypeOf(DataClientLogging.prototype), 'constructor', this).apply(this, arguments);

      this.tags = ['data'];
      this.logQueries = getConfig().logQueries;
    }

    return DataClientLogging;
  })(ElasticsearchClientLogging);

  function getConfig() {
    if (Boolean(config.get('elasticsearch.tribe.url'))) {
      return config.get('elasticsearch.tribe');
    }

    return config.get('elasticsearch');
  }

  var dataCluster = server.plugins.elasticsearch.createCluster('data', Object.assign({ log: DataClientLogging }, getConfig()));

  server.on('close', (0, _lodash.bindKey)(dataCluster, 'close'));
}
