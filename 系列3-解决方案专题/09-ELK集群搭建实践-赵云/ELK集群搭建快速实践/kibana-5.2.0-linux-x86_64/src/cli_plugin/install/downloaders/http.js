'use strict';

Object.defineProperty(exports, '__esModule', {
  value: true
});

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { 'default': obj }; }

function _asyncToGenerator(fn) { return function () { var gen = fn.apply(this, arguments); return new Promise(function (resolve, reject) { var callNext = step.bind(null, 'next'); var callThrow = step.bind(null, 'throw'); function step(key, arg) { try { var info = gen[key](arg); var value = info.value; } catch (error) { reject(error); return; } if (info.done) { resolve(value); } else { Promise.resolve(value).then(callNext, callThrow); } } callNext(); }); }; }

var _wreck = require('wreck');

var _wreck2 = _interopRequireDefault(_wreck);

var _progress = require('../progress');

var _progress2 = _interopRequireDefault(_progress);

var _bluebird = require('bluebird');

var _fs = require('fs');

function sendRequest(_ref) {
  var sourceUrl = _ref.sourceUrl;
  var timeout = _ref.timeout;

  var maxRedirects = 11; //Because this one goes to 11.
  return (0, _bluebird.fromNode)(function (cb) {
    var req = _wreck2['default'].request('GET', sourceUrl, { timeout: timeout, redirects: maxRedirects }, function (err, resp) {
      if (err) {
        if (err.code === 'ECONNREFUSED') {
          err = new Error('ENOTFOUND');
        }

        return cb(err);
      }

      if (resp.statusCode >= 400) {
        return cb(new Error('ENOTFOUND'));
      }

      cb(null, { req: req, resp: resp });
    });
  });
}

function downloadResponse(_ref2) {
  var resp = _ref2.resp;
  var targetPath = _ref2.targetPath;
  var progress = _ref2.progress;

  return new Promise(function (resolve, reject) {
    var writeStream = (0, _fs.createWriteStream)(targetPath);

    // if either stream errors, fail quickly
    resp.on('error', reject);
    writeStream.on('error', reject);

    // report progress as we download
    resp.on('data', function (chunk) {
      progress.progress(chunk.length);
    });

    // write the download to the file system
    resp.pipe(writeStream);

    // when the write is done, we are done
    writeStream.on('finish', resolve);
  });
}

/*
Responsible for managing http transfers
*/
exports['default'] = _asyncToGenerator(function* (logger, sourceUrl, targetPath, timeout) {
  try {
    var _ref3 = yield sendRequest({ sourceUrl: sourceUrl, timeout: timeout });

    var req = _ref3.req;
    var resp = _ref3.resp;

    try {
      var totalSize = parseFloat(resp.headers['content-length']) || 0;
      var progress = new _progress2['default'](logger);
      progress.init(totalSize);

      yield downloadResponse({ resp: resp, targetPath: targetPath, progress: progress });

      progress.complete();
    } catch (err) {
      req.abort();
      throw err;
    }
  } catch (err) {
    if (err.message !== 'ENOTFOUND') {
      logger.error(err);
    }
    throw err;
  }
});
module.exports = exports['default'];
