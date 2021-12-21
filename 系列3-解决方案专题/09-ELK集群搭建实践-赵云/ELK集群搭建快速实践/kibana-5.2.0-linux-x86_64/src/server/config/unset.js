'use strict';

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { 'default': obj }; }

var _lodash = require('lodash');

var _lodash2 = _interopRequireDefault(_lodash);

var _lodashInternalToPath = require('lodash/internal/toPath');

var _lodashInternalToPath2 = _interopRequireDefault(_lodashInternalToPath);

module.exports = function unset(object, rawPath) {
  if (!object) return;
  var path = (0, _lodashInternalToPath2['default'])(rawPath);

  switch (path.length) {
    case 0:
      return;

    case 1:
      delete object[rawPath];
      break;

    default:
      var leaf = path.pop();
      var parentPath = path.slice();
      var parent = _lodash2['default'].get(object, parentPath);
      unset(parent, leaf);
      if (!_lodash2['default'].size(parent)) {
        unset(object, parentPath);
      }
      break;
  }
};
