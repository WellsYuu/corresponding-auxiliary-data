'use strict';

Object.defineProperty(exports, '__esModule', {
  value: true
});

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { 'default': obj }; }

function _asyncToGenerator(fn) { return function () { var gen = fn.apply(this, arguments); return new _bluebird2['default'](function (resolve, reject) { var callNext = step.bind(null, 'next'); var callThrow = step.bind(null, 'throw'); function step(key, arg) { try { var info = gen[key](arg); var value = info.value; } catch (error) { reject(error); return; } if (info.done) { resolve(value); } else { _bluebird2['default'].resolve(value).then(callNext, callThrow); } } callNext(); }); }; }

var _download = require('./download');

var _bluebird = require('bluebird');

var _bluebird2 = _interopRequireDefault(_bluebird);

var _cleanup = require('./cleanup');

var _pack = require('./pack');

var _rename = require('./rename');

var _rimraf = require('rimraf');

var _kibana = require('./kibana');

var _mkdirp = require('mkdirp');

var _mkdirp2 = _interopRequireDefault(_mkdirp);

var mkdir = _bluebird2['default'].promisify(_mkdirp2['default']);

exports['default'] = _asyncToGenerator(function* (settings, logger) {
  try {
    yield (0, _cleanup.cleanPrevious)(settings, logger);

    yield mkdir(settings.workingPath);

    yield (0, _download.download)(settings, logger);

    yield (0, _pack.getPackData)(settings, logger);

    yield (0, _pack.extract)(settings, logger);

    (0, _rimraf.sync)(settings.tempArchiveFile);

    (0, _kibana.existingInstall)(settings, logger);

    (0, _kibana.assertVersion)(settings);

    yield (0, _rename.renamePlugin)(settings.workingPath, settings.plugins[0].path);

    yield (0, _kibana.rebuildCache)(settings, logger);

    logger.log('Plugin installation complete');
  } catch (err) {
    logger.error('Plugin installation was unsuccessful due to error "' + err.message + '"');
    (0, _cleanup.cleanArtifacts)(settings);
    process.exit(70); // eslint-disable-line no-process-exit
  }
});
module.exports = exports['default'];
