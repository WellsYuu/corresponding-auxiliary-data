'use strict';

Object.defineProperty(exports, '__esModule', {
  value: true
});
var mappings = {
  config: {
    properties: {
      buildNum: {
        type: 'string',
        index: 'not_analyzed'
      }
    }
  },
  server: {
    properties: {
      uuid: {
        type: 'keyword'
      }
    }
  }
};
exports.mappings = mappings;
