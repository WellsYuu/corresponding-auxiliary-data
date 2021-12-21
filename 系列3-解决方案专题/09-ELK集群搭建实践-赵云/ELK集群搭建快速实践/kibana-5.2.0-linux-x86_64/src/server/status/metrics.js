'use strict';

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { 'default': obj }; }

var _lodash = require('lodash');

var _lodash2 = _interopRequireDefault(_lodash);

var _samples = require('./samples');

var _samples2 = _interopRequireDefault(_samples);

module.exports = function (kbnServer, server, config) {
  var lastReport = Date.now();

  kbnServer.metrics = new _samples2['default'](12);

  server.plugins['even-better'].monitor.on('ops', function (event) {
    var now = Date.now();
    var secSinceLast = (now - lastReport) / 1000;
    lastReport = now;

    var port = config.get('server.port');
    var requests = _lodash2['default'].get(event, ['requests', port, 'total'], 0);
    var requestsPerSecond = requests / secSinceLast;

    kbnServer.metrics.add({
      heapTotal: _lodash2['default'].get(event, 'psmem.heapTotal'),
      heapUsed: _lodash2['default'].get(event, 'psmem.heapUsed'),
      load: event.osload,
      responseTimeAvg: _lodash2['default'].get(event, ['responseTimes', port, 'avg']),
      responseTimeMax: _lodash2['default'].get(event, ['responseTimes', port, 'max']),
      requestsPerSecond: requestsPerSecond
    });
  });
};
