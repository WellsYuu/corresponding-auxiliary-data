'use strict';

Object.defineProperty(exports, '__esModule', {
  value: true
});
exports.merge = merge;

var _lodash = require('lodash');

var _fs = require('fs');

var _jsYaml = require('js-yaml');

var _ansicolors = require('ansicolors');

var _utils = require('../../utils');

var _legacy_config = require('./legacy_config');

var _deprecated_config = require('./deprecated_config');

var log = (0, _lodash.memoize)(function (message) {
  console.log((0, _ansicolors.red)('WARNING:'), message);
});

function merge(sources) {
  return (0, _lodash.transform)(sources, function (merged, source) {
    (0, _lodash.forOwn)(source, function apply(val, key) {
      if ((0, _lodash.isPlainObject)(val)) {
        (0, _lodash.forOwn)(val, function (subVal, subKey) {
          apply(subVal, key + '.' + subKey);
        });
        return;
      }

      if ((0, _lodash.isArray)(val)) {
        (0, _lodash.set)(merged, key, []);
        val.forEach(function (subVal, i) {
          return apply(subVal, key + '.' + i);
        });
        return;
      }

      (0, _lodash.set)(merged, key, val);
    });
  }, {});
}

exports['default'] = function (paths) {
  var files = [].concat(paths || []);
  var yamls = files.map(function (path) {
    return (0, _jsYaml.safeLoad)((0, _fs.readFileSync)(path, 'utf8'));
  });
  var config = merge(yamls.map(function (file) {
    return (0, _legacy_config.rewriteLegacyConfig)(file, log);
  }));
  return (0, _deprecated_config.checkForDeprecatedConfig)(config, log);
};
