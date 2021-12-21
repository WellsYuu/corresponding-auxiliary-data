'use strict';

Object.defineProperty(exports, '__esModule', {
  value: true
});
exports._downloadSingle = _downloadSingle;
exports.download = download;

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { 'default': obj }; }

var _downloadersHttp = require('./downloaders/http');

var _downloadersHttp2 = _interopRequireDefault(_downloadersHttp);

var _downloadersFile = require('./downloaders/file');

var _downloadersFile2 = _interopRequireDefault(_downloadersFile);

var _libErrors = require('../lib/errors');

var _url = require('url');

function _downloadSingle(settings, logger, sourceUrl) {
  var urlInfo = (0, _url.parse)(sourceUrl);
  var downloadPromise = undefined;

  if (/^file/.test(urlInfo.protocol)) {
    downloadPromise = (0, _downloadersFile2['default'])(logger, decodeURI(urlInfo.path), settings.tempArchiveFile);
  } else if (/^https?/.test(urlInfo.protocol)) {
    downloadPromise = (0, _downloadersHttp2['default'])(logger, sourceUrl, settings.tempArchiveFile, settings.timeout);
  } else {
    downloadPromise = Promise.reject(new _libErrors.UnsupportedProtocolError());
  }

  return downloadPromise;
}

//Attempts to download each url in turn until one is successful

function download(settings, logger) {
  var urls = settings.urls.slice(0);

  function tryNext() {
    var sourceUrl = urls.shift();
    if (!sourceUrl) {
      throw new Error('No valid url specified.');
    }

    logger.log('Attempting to transfer from ' + sourceUrl);

    return _downloadSingle(settings, logger, sourceUrl)['catch'](function (err) {
      var isUnsupportedProtocol = err instanceof _libErrors.UnsupportedProtocolError;
      var isDownloadResourceNotFound = err.message === 'ENOTFOUND';
      if (isUnsupportedProtocol || isDownloadResourceNotFound) {
        return tryNext();
      }
      throw err;
    });
  }

  return tryNext();
}

;
