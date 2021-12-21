'use strict';

Object.defineProperty(exports, '__esModule', {
  value: true
});

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { 'default': obj }; }

function _asyncToGenerator(fn) { return function () { var gen = fn.apply(this, arguments); return new _bluebird2['default'](function (resolve, reject) { var callNext = step.bind(null, 'next'); var callThrow = step.bind(null, 'throw'); function step(key, arg) { try { var info = gen[key](arg); var value = info.value; } catch (error) { reject(error); return; } if (info.done) { resolve(value); } else { _bluebird2['default'].resolve(value).then(callNext, callThrow); } } callNext(); }); }; }

var _nodeUuid = require('node-uuid');

var _nodeUuid2 = _interopRequireDefault(_nodeUuid);

var _bluebird = require('bluebird');

var _bluebird2 = _interopRequireDefault(_bluebird);

var _path = require('path');

var _fs = require('fs');

var FILE_ENCODING = 'utf8';

exports['default'] = _asyncToGenerator(function* (server) {
  var detectUuid = _asyncToGenerator(function* () {
    var readFile = _bluebird2['default'].promisify(_fs.readFile);
    try {
      var result = yield readFile(uuidFile);
      return result.toString(FILE_ENCODING);
    } catch (err) {
      if (err.code === 'ENOENT') {
        // non-existant uuid file is ok
        return false;
      }
      server.log(['error', 'read-uuid'], err);
      // Note: this will most likely be logged as an Unhandled Rejection
      throw err;
    }
  });

  var writeUuid = _asyncToGenerator(function* (uuid) {
    var writeFile = _bluebird2['default'].promisify(_fs.writeFile);
    try {
      return yield writeFile(uuidFile, uuid, { encoding: FILE_ENCODING });
    } catch (err) {
      server.log(['error', 'write-uuid'], err);
      // Note: this will most likely be logged as an Unhandled Rejection
      throw err;
    }
  }

  // detect if uuid exists already from before a restart
  );

  var config = server.config();
  var fileName = 'uuid';
  var uuidFile = (0, _path.join)(config.get('path.data'), fileName);

  var logToServer = function logToServer(msg) {
    return server.log(['server', 'uuid', fileName], msg);
  };
  var dataFileUuid = yield detectUuid();
  var serverConfigUuid = config.get('server.uuid'); // check if already set in config

  if (dataFileUuid) {
    // data uuid found
    if (serverConfigUuid === dataFileUuid) {
      // config uuid exists, data uuid exists and matches
      logToServer('Kibana instance UUID: ' + dataFileUuid);
      return;
    }

    if (!serverConfigUuid) {
      // config uuid missing, data uuid exists
      serverConfigUuid = dataFileUuid;
      logToServer('Resuming persistent Kibana instance UUID: ' + serverConfigUuid);
      config.set('server.uuid', serverConfigUuid);
      return;
    }

    if (serverConfigUuid !== dataFileUuid) {
      // config uuid exists, data uuid exists but mismatches
      logToServer('Updating Kibana instance UUID to: ' + serverConfigUuid + ' (was: ' + dataFileUuid + ')');
      return writeUuid(serverConfigUuid);
    }
  }

  // data uuid missing

  if (!serverConfigUuid) {
    // config uuid missing
    serverConfigUuid = _nodeUuid2['default'].v4();
    config.set('server.uuid', serverConfigUuid);
  }

  logToServer('Setting new Kibana instance UUID: ' + serverConfigUuid);
  return writeUuid(serverConfigUuid);
});
module.exports = exports['default'];
