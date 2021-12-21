'use strict';

Object.defineProperty(exports, '__esModule', {
  value: true
});

var _register_languages = require('./register_languages');

exports['default'] = function (server) {
  (0, _register_languages.registerLanguages)(server);
};

module.exports = exports['default'];
