'use strict';

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { 'default': obj }; }

var _lodash = require('lodash');

var _lodash2 = _interopRequireDefault(_lodash);

// Creates an ES field mapping from a single field object in a kibana index pattern
module.exports = function createMappingsFromPatternFields(fields) {
  if (_lodash2['default'].isEmpty(fields)) {
    throw new Error('argument must not be empty');
  }

  var mappings = {};

  _lodash2['default'].forEach(fields, function (field) {
    var mapping = undefined;

    if (field.type === 'string') {
      mapping = {
        type: 'text',
        fields: {
          keyword: { type: 'keyword', ignore_above: 256 }
        }
      };
    } else {
      var fieldType = field.type === 'number' ? 'double' : field.type;
      mapping = {
        type: fieldType,
        index: true,
        doc_values: true
      };
    }

    _lodash2['default'].set(mappings, field.name.split('.').join('.properties.'), mapping);
  });

  return mappings;
};
