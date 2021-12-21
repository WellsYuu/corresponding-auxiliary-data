'use strict';

Object.defineProperty(exports, '__esModule', {
  value: true
});

var _slicedToArray = (function () { function sliceIterator(arr, i) { var _arr = []; var _n = true; var _d = false; var _e = undefined; try { for (var _i = arr[Symbol.iterator](), _s; !(_n = (_s = _i.next()).done); _n = true) { _arr.push(_s.value); if (i && _arr.length === i) break; } } catch (err) { _d = true; _e = err; } finally { try { if (!_n && _i['return']) _i['return'](); } finally { if (_d) throw _e; } } return _arr; } return function (arr, i) { if (Array.isArray(arr)) { return arr; } else if (Symbol.iterator in Object(arr)) { return sliceIterator(arr, i); } else { throw new TypeError('Invalid attempt to destructure non-iterable instance'); } }; })();

/**
 * Returns an array of package objects. There will be one for each of
 *  package.json files in the archive
 * @param {object} settings - a plugin installer settings object
 */

var listPackages = _asyncToGenerator(function* (settings) {
  var regExp = new RegExp('(kibana/([^/]+))/package.json', 'i');
  var archiveFiles = yield (0, _zip.listFiles)(settings.tempArchiveFile);

  return (0, _lodash2['default'])(archiveFiles).map(function (file) {
    return file.replace(/\\/g, '/');
  }).map(function (file) {
    return file.match(regExp);
  }).compact().map(function (_ref) {
    var _ref2 = _slicedToArray(_ref, 3);

    var file = _ref2[0];
    var _ = _ref2[1];
    var folder = _ref2[2];
    return { file: file, folder: folder };
  }).uniq().value();
}

/**
 * Extracts the package.json files into the workingPath
 * @param {object} settings - a plugin installer settings object
 * @param {array} packages - array of package objects from listPackages()
 */
);

var extractPackageFiles = _asyncToGenerator(function* (settings, packages) {
  var filter = {
    files: packages.map(function (pkg) {
      return pkg.file;
    })
  };
  yield (0, _zip.extractFiles)(settings.tempArchiveFile, settings.workingPath, 0, filter);
}

/**
 * Deletes the package.json files created by extractPackageFiles()
 * @param {object} settings - a plugin installer settings object
 */
);

/**
 * Examine each package.json file to determine the plugin name,
 *  version, kibanaVersion, and platform. Mutates the package objects
 *  in the packages array
 * @param {object} settings - a plugin installer settings object
 * @param {array} packages - array of package objects from listPackages()
 */

var mergePackageData = _asyncToGenerator(function* (settings, packages) {
  return packages.map(function (pkg) {
    var fullPath = (0, _path.resolve)(settings.workingPath, pkg.file);
    var packageInfo = require(fullPath);

    pkg.version = _lodash2['default'].get(packageInfo, 'version');
    pkg.name = _lodash2['default'].get(packageInfo, 'name');
    pkg.path = (0, _path.resolve)(settings.pluginDir, pkg.name);

    // Plugins must specify their version, and by default that version should match
    // the version of kibana down to the patch level. If these two versions need
    // to diverge, they can specify a kibana.version to indicate the version of
    // kibana the plugin is intended to work with.
    pkg.kibanaVersion = _lodash2['default'].get(packageInfo, 'kibana.version', pkg.version);

    var regExp = new RegExp(pkg.name + '-(.+)', 'i');
    var matches = pkg.folder.match(regExp);
    pkg.platform = matches ? matches[1] : undefined;

    return pkg;
  });
}

/**
 * Extracts the first plugin in the archive.
 *  NOTE: This will need to be changed in later versions of the pack installer
 *  that allow for the installation of more than one plugin at once.
 * @param {object} settings - a plugin installer settings object
 */
);

var extractArchive = _asyncToGenerator(function* (settings) {
  var filter = {
    paths: ['kibana/' + settings.plugins[0].folder]
  };

  yield (0, _zip.extractFiles)(settings.tempArchiveFile, settings.workingPath, 2, filter);
}

/**
 * Returns the detailed information about each kibana plugin in the pack.
 *  TODO: If there are platform specific folders, determine which one to use.
 * @param {object} settings - a plugin installer settings object
 * @param {object} logger - a plugin installer logger object
 */
);

var getPackData = _asyncToGenerator(function* (settings, logger) {
  var packages = undefined;
  try {
    logger.log('Retrieving metadata from plugin archive');

    packages = yield listPackages(settings);

    yield extractPackageFiles(settings, packages);
    yield mergePackageData(settings, packages);
    yield deletePackageFiles(settings);
  } catch (err) {
    logger.error(err);
    throw new Error('Error retrieving metadata from plugin archive');
  }

  if (packages.length === 0) {
    throw new Error('No kibana plugins found in archive');
  }
  packages.forEach(assertValidPackageName);

  settings.plugins = packages;
});

exports.getPackData = getPackData;

var extract = _asyncToGenerator(function* (settings, logger) {
  try {
    logger.log('Extracting plugin archive');

    yield extractArchive(settings);

    logger.log('Extraction complete');
  } catch (err) {
    logger.error(err);
    throw new Error('Error extracting plugin archive');
  }
});

exports.extract = extract;

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { 'default': obj }; }

function _asyncToGenerator(fn) { return function () { var gen = fn.apply(this, arguments); return new Promise(function (resolve, reject) { var callNext = step.bind(null, 'next'); var callThrow = step.bind(null, 'throw'); function step(key, arg) { try { var info = gen[key](arg); var value = info.value; } catch (error) { reject(error); return; } if (info.done) { resolve(value); } else { Promise.resolve(value).then(callNext, callThrow); } } callNext(); }); }; }

var _lodash = require('lodash');

var _lodash2 = _interopRequireDefault(_lodash);

var _zip = require('./zip');

var _path = require('path');

var _rimraf = require('rimraf');

var _validateNpmPackageName = require('validate-npm-package-name');

var _validateNpmPackageName2 = _interopRequireDefault(_validateNpmPackageName);

function deletePackageFiles(settings) {
  var fullPath = (0, _path.resolve)(settings.workingPath, 'kibana');
  (0, _rimraf.sync)(fullPath);
}

/**
 * Checks the plugin name. Will throw an exception if it does not meet
 *  npm package naming conventions
 * @param {object} plugin - a package object from listPackages()
 */
function assertValidPackageName(plugin) {
  var validation = (0, _validateNpmPackageName2['default'])(plugin.name);
  if (!validation.validForNewPackages) {
    throw new Error('Invalid plugin name [' + plugin.name + '] in package.json');
  }
};
