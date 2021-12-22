/**
 * Let's weed out the ES versions that won't work with a given Kibana version.
 * 1. Major version differences will never work together.
 * 2. Older versions of ES won't work with newer versions of Kibana.
 */

'use strict';

Object.defineProperty(exports, '__esModule', {
  value: true
});
exports['default'] = isEsCompatibleWithKibana;

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { 'default': obj }; }

var _semver = require('semver');

var _semver2 = _interopRequireDefault(_semver);

function isEsCompatibleWithKibana(esVersion, kibanaVersion) {
  var esVersionNumbers = {
    major: _semver2['default'].major(esVersion),
    minor: _semver2['default'].minor(esVersion),
    patch: _semver2['default'].patch(esVersion)
  };

  var kibanaVersionNumbers = {
    major: _semver2['default'].major(kibanaVersion),
    minor: _semver2['default'].minor(kibanaVersion),
    patch: _semver2['default'].patch(kibanaVersion)
  };

  // Reject mismatching major version numbers.
  if (esVersionNumbers.major !== kibanaVersionNumbers.major) {
    return false;
  }

  // Reject older minor versions of ES.
  if (esVersionNumbers.minor < kibanaVersionNumbers.minor) {
    return false;
  }

  return true;
}

module.exports = exports['default'];
