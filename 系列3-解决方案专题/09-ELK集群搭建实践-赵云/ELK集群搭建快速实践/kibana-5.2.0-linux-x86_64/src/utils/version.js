'use strict';

Object.defineProperty(exports, '__esModule', {
  value: true
});
exports.versionSatisfies = versionSatisfies;
exports.cleanVersion = cleanVersion;

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { 'default': obj }; }

var _semver = require('semver');

var _semver2 = _interopRequireDefault(_semver);

function versionSatisfies(cleanActual, cleanExpected) {
  try {
    return cleanActual === cleanExpected;
  } catch (err) {
    return false;
  }
}

function cleanVersion(version) {
  var match = version.match(/\d+\.\d+\.\d+/);
  if (!match) return version;
  return match[0];
}
