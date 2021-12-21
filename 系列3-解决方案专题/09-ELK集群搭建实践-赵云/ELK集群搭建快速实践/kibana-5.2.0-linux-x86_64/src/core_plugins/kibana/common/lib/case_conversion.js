'use strict';

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { 'default': obj }; }

var _lodash = require('lodash');

var _lodash2 = _interopRequireDefault(_lodash);

module.exports = {
  keysToSnakeCaseShallow: function keysToSnakeCaseShallow(object) {
    return _lodash2['default'].mapKeys(object, function (value, key) {
      return _lodash2['default'].snakeCase(key);
    });
  },

  keysToCamelCaseShallow: function keysToCamelCaseShallow(object) {
    return _lodash2['default'].mapKeys(object, function (value, key) {
      return _lodash2['default'].camelCase(key);
    });
  }
};
