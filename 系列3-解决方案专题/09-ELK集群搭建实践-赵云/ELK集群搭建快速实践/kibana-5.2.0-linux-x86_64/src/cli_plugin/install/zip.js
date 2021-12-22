'use strict';

Object.defineProperty(exports, '__esModule', {
  value: true
});

/**
 * Extracts files from a zip archive to a file path using a filter function
 * @param {string} zipPath - file path to a zip archive
 * @param {string} targetPath - directory path to where the files should
 *  extracted
 * @param {integer} strip - Number of nested directories within the archive
 *  that should be ignored when determining the target path of an archived
 *  file.
 * @param {function} filter - A function that accepts a single parameter 'file'
 *  and returns true if the file should be extracted from the archive
 */

var extractFiles = _asyncToGenerator(function* (zipPath, targetPath, strip, filter) {
  yield new Promise(function (resolve, reject) {
    var unzipper = new _bigfungerDecompressZip2['default'](zipPath);

    unzipper.on('error', reject);

    var options = {
      path: targetPath,
      strip: strip
    };
    if (filter) {
      options.filter = extractFilter(filter);
    }

    unzipper.extract(options);

    unzipper.on('extract', resolve);
  });
}

/**
 * Returns all files within an archive
 * @param {string} zipPath - file path to a zip archive
 * @returns {array} all files within an archive with their relative paths
 */
);

exports.extractFiles = extractFiles;

var listFiles = _asyncToGenerator(function* (zipPath) {
  return yield new Promise(function (resolve, reject) {
    var unzipper = new _bigfungerDecompressZip2['default'](zipPath);

    unzipper.on('error', reject);

    unzipper.on('list', function (files) {
      files = files.map(function (file) {
        return file.replace(/\\/g, '/');
      });
      resolve(files);
    });

    unzipper.list();
  });
});

exports.listFiles = listFiles;

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { 'default': obj }; }

function _asyncToGenerator(fn) { return function () { var gen = fn.apply(this, arguments); return new Promise(function (resolve, reject) { var callNext = step.bind(null, 'next'); var callThrow = step.bind(null, 'throw'); function step(key, arg) { try { var info = gen[key](arg); var value = info.value; } catch (error) { reject(error); return; } if (info.done) { resolve(value); } else { Promise.resolve(value).then(callNext, callThrow); } } callNext(); }); }; }

var _lodash = require('lodash');

var _lodash2 = _interopRequireDefault(_lodash);

var _bigfungerDecompressZip = require('@bigfunger/decompress-zip');

var _bigfungerDecompressZip2 = _interopRequireDefault(_bigfungerDecompressZip);

var SYMBOLIC_LINK = 'SymbolicLink';

/**
 * Creates a filter function to be consumed by extractFiles that filters by
 *  an array of files
 * @param {array} files - an array of full file paths to extract. Should match
 *   exactly a value from listFiles
 */
function extractFilterFromFiles(files) {
  var filterFiles = files.map(function (file) {
    return file.replace(/\\/g, '/');
  });
  return function filterByFiles(file) {
    if (file.type === SYMBOLIC_LINK) return false;

    var path = file.path.replace(/\\/g, '/');
    return _lodash2['default'].includes(filterFiles, path);
  };
}

/**
 * Creates a filter function to be consumed by extractFiles that filters by
 *  an array of root paths
 * @param {array} paths - an array of root paths from the archive. All files and
 *   folders will be extracted recursively using these paths as roots.
 */
function extractFilterFromPaths(paths) {
  return function filterByRootPath(file) {
    if (file.type === SYMBOLIC_LINK) return false;

    return paths.some(function (path) {
      var regex = new RegExp(path + '($|/)', 'i');
      return file.parent.match(regex);
    });
  };
}

/**
 * Creates a filter function to be consumed by extractFiles
 * @param {object} filter - an object with either a files or paths property.
 */
function extractFilter(filter) {
  if (filter.files) return extractFilterFromFiles(filter.files);
  if (filter.paths) return extractFilterFromPaths(filter.paths);
  return _lodash2['default'].noop;
}
