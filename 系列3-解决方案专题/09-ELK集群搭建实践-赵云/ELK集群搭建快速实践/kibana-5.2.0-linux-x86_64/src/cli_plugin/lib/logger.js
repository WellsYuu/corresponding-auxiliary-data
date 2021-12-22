/**
 * Logs messages and errors
 */
'use strict';

Object.defineProperty(exports, '__esModule', {
  value: true
});

var _createClass = (function () { function defineProperties(target, props) { for (var i = 0; i < props.length; i++) { var descriptor = props[i]; descriptor.enumerable = descriptor.enumerable || false; descriptor.configurable = true; if ('value' in descriptor) descriptor.writable = true; Object.defineProperty(target, descriptor.key, descriptor); } } return function (Constructor, protoProps, staticProps) { if (protoProps) defineProperties(Constructor.prototype, protoProps); if (staticProps) defineProperties(Constructor, staticProps); return Constructor; }; })();

function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError('Cannot call a class as a function'); } }

var Logger = (function () {
  function Logger(settings) {
    _classCallCheck(this, Logger);

    this.previousLineEnded = true;
    this.silent = !!settings.silent;
    this.quiet = !!settings.quiet;
  }

  _createClass(Logger, [{
    key: 'log',
    value: function log(data, sameLine) {
      if (this.silent || this.quiet) return;

      if (!sameLine && !this.previousLineEnded) {
        process.stdout.write('\n');
      }

      //if data is a stream, pipe it.
      if (data.readable) {
        data.pipe(process.stdout);
        return;
      }

      process.stdout.write(data);
      if (!sameLine) process.stdout.write('\n');
      this.previousLineEnded = !sameLine;
    }
  }, {
    key: 'error',
    value: function error(data) {
      if (this.silent) return;

      if (!this.previousLineEnded) {
        process.stderr.write('\n');
      }

      //if data is a stream, pipe it.
      if (data.readable) {
        data.pipe(process.stderr);
        return;
      }
      process.stderr.write(data + '\n');
      this.previousLineEnded = true;
    }
  }]);

  return Logger;
})();

exports['default'] = Logger;
module.exports = exports['default'];
