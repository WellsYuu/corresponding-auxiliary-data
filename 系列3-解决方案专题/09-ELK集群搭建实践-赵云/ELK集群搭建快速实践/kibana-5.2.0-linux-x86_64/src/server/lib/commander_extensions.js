'use strict';

Object.defineProperty(exports, '__esModule', {
  value: true
});

exports['default'] = function (program) {
  function isCommand(val) {
    return typeof val === 'object' && val._name;
  }

  program.isCommandSpecified = function () {
    return program.args.some(isCommand);
  };
};

;
module.exports = exports['default'];
