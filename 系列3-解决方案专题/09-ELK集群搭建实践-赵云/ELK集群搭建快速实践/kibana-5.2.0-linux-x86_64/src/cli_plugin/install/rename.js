'use strict';

Object.defineProperty(exports, '__esModule', {
  value: true
});
exports.renamePlugin = renamePlugin;

var _fs = require('fs');

var _lodash = require('lodash');

function renamePlugin(workingPath, finalPath) {
  return new Promise(function (resolve, reject) {
    var start = Date.now();
    var retryTime = 3000;
    var retryDelay = 100;
    (0, _fs.rename)(workingPath, finalPath, function retry(err) {
      if (err) {
        // In certain cases on Windows, such as running AV, plugin folders can be locked shortly after extracting
        // Retry for up to retryTime seconds
        var windowsEPERM = process.platform === 'win32' && err.code === 'EPERM';
        var retryAvailable = Date.now() - start < retryTime;
        if (windowsEPERM && retryAvailable) return (0, _lodash.delay)(_fs.rename, retryDelay, workingPath, finalPath, retry);
        reject(err);
      }
      resolve();
    });
  });
}
