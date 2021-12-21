'use strict';

Object.defineProperty(exports, '__esModule', {
  value: true
});

var _slicedToArray = (function () { function sliceIterator(arr, i) { var _arr = []; var _n = true; var _d = false; var _e = undefined; try { for (var _i = arr[Symbol.iterator](), _s; !(_n = (_s = _i.next()).done); _n = true) { _arr.push(_s.value); if (i && _arr.length === i) break; } } catch (err) { _d = true; _e = err; } finally { try { if (!_n && _i['return']) _i['return'](); } finally { if (_d) throw _e; } } return _arr; } return function (arr, i) { if (Array.isArray(arr)) { return arr; } else if (Symbol.iterator in Object(arr)) { return sliceIterator(arr, i); } else { throw new TypeError('Invalid attempt to destructure non-iterable instance'); } }; })();

exports.checkForDeprecatedConfig = checkForDeprecatedConfig;

var _lodash = require('lodash');

// deprecated settings are still allowed, but will be removed at a later time. They
// are checked for after the config object is prepared and known, so legacySettings
// will have already been transformed.
var deprecatedSettings = new Map([[['server', 'xsrf', 'token'], 'server.xsrf.token is deprecated. It is no longer used when providing xsrf protection.']]);

exports.deprecatedSettings = deprecatedSettings;
// check for and warn about deprecated settings

function checkForDeprecatedConfig(object) {
  var log = arguments.length <= 1 || arguments[1] === undefined ? _lodash.noop : arguments[1];
  var _iteratorNormalCompletion = true;
  var _didIteratorError = false;
  var _iteratorError = undefined;

  try {
    for (var _iterator = deprecatedSettings.entries()[Symbol.iterator](), _step; !(_iteratorNormalCompletion = (_step = _iterator.next()).done); _iteratorNormalCompletion = true) {
      var _step$value = _slicedToArray(_step.value, 2);

      var key = _step$value[0];
      var msg = _step$value[1];

      if ((0, _lodash.has)(object, key)) log(msg);
    }
  } catch (err) {
    _didIteratorError = true;
    _iteratorError = err;
  } finally {
    try {
      if (!_iteratorNormalCompletion && _iterator['return']) {
        _iterator['return']();
      }
    } finally {
      if (_didIteratorError) {
        throw _iteratorError;
      }
    }
  }

  return object;
}
