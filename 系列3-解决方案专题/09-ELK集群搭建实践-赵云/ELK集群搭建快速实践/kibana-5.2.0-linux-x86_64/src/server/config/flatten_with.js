'use strict';

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { 'default': obj }; }

var _lodash = require('lodash');

var _lodash2 = _interopRequireDefault(_lodash);

module.exports = function (dot, nestedObj, flattenArrays) {
  var stack = []; // track key stack
  var flatObj = {};
  (function flattenObj(obj) {
    _lodash2['default'].keys(obj).forEach(function (key) {
      stack.push(key);
      if (!flattenArrays && _lodash2['default'].isArray(obj[key])) flatObj[stack.join(dot)] = obj[key];else if (_lodash2['default'].isObject(obj[key])) flattenObj(obj[key]);else flatObj[stack.join(dot)] = obj[key];
      stack.pop();
    });
  })(nestedObj);
  return flatObj;
};
