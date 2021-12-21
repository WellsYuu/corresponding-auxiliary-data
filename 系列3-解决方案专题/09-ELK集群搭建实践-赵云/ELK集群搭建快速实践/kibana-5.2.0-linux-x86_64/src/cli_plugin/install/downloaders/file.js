'use strict';

Object.defineProperty(exports, '__esModule', {
  value: true
});

var copyFile = _asyncToGenerator(function* (_ref2) {
  var readStream = _ref2.readStream;
  var writeStream = _ref2.writeStream;
  var progress = _ref2.progress;

  yield new Promise(function (resolve, reject) {
    // if either stream errors, fail quickly
    readStream.on('error', reject);
    writeStream.on('error', reject);

    // report progress as we transfer
    readStream.on('data', function (chunk) {
      progress.progress(chunk.length);
    });

    // write the download to the file system
    readStream.pipe(writeStream);

    // when the write is done, we are done
    writeStream.on('finish', resolve);
  });
}

/*
// Responsible for managing local file transfers
*/
);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { 'default': obj }; }

function _asyncToGenerator(fn) { return function () { var gen = fn.apply(this, arguments); return new Promise(function (resolve, reject) { var callNext = step.bind(null, 'next'); var callThrow = step.bind(null, 'throw'); function step(key, arg) { try { var info = gen[key](arg); var value = info.value; } catch (error) { reject(error); return; } if (info.done) { resolve(value); } else { Promise.resolve(value).then(callNext, callThrow); } } callNext(); }); }; }

var _progress = require('../progress');

var _progress2 = _interopRequireDefault(_progress);

var _fs = require('fs');

function openSourceFile(_ref) {
  var sourcePath = _ref.sourcePath;

  try {
    var fileInfo = (0, _fs.statSync)(sourcePath);

    var readStream = (0, _fs.createReadStream)(sourcePath);

    return { readStream: readStream, fileInfo: fileInfo };
  } catch (err) {
    if (err.code === 'ENOENT') {
      throw new Error('ENOTFOUND');
    }

    throw err;
  }
}

exports['default'] = _asyncToGenerator(function* (logger, sourcePath, targetPath) {
  try {
    var _openSourceFile = openSourceFile({ sourcePath: sourcePath });

    var readStream = _openSourceFile.readStream;
    var fileInfo = _openSourceFile.fileInfo;

    var writeStream = (0, _fs.createWriteStream)(targetPath);

    try {
      var progress = new _progress2['default'](logger);
      progress.init(fileInfo.size);

      yield copyFile({ readStream: readStream, writeStream: writeStream, progress: progress });

      progress.complete();
    } catch (err) {
      readStream.close();
      writeStream.close();
      throw err;
    }
  } catch (err) {
    logger.error(err);
    throw err;
  }
});
module.exports = exports['default'];
