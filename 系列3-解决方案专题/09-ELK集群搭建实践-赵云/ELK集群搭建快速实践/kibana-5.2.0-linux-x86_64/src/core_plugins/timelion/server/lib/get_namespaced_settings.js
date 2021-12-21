'use strict';

var _ = require('lodash');
var configFile = require('../../timelion.json');

module.exports = function () {
  function flattenWith(dot, nestedObj, flattenArrays) {
    var stack = []; // track key stack
    var flatObj = {};
    (function flattenObj(obj) {
      _.keys(obj).forEach(function (key) {
        stack.push(key);
        if (!flattenArrays && _.isArray(obj[key])) flatObj[stack.join(dot)] = obj[key];else if (_.isObject(obj[key])) flattenObj(obj[key]);else flatObj[stack.join(dot)] = obj[key];
        stack.pop();
      });
    })(nestedObj);
    return flatObj;
  };

  var timelionDefaults = flattenWith('.', configFile);
  return _.reduce(timelionDefaults, function (result, value, key) {
    result['timelion:' + key] = value;
    return result;
  }, {});
};
