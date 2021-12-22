/**
 * Generates file transfer progress messages
 */
'use strict';

Object.defineProperty(exports, '__esModule', {
  value: true
});

var _createClass = (function () { function defineProperties(target, props) { for (var i = 0; i < props.length; i++) { var descriptor = props[i]; descriptor.enumerable = descriptor.enumerable || false; descriptor.configurable = true; if ('value' in descriptor) descriptor.writable = true; Object.defineProperty(target, descriptor.key, descriptor); } } return function (Constructor, protoProps, staticProps) { if (protoProps) defineProperties(Constructor.prototype, protoProps); if (staticProps) defineProperties(Constructor, staticProps); return Constructor; }; })();

function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError('Cannot call a class as a function'); } }

var Progress = (function () {
  function Progress(logger) {
    _classCallCheck(this, Progress);

    var self = this;

    self.dotCount = 0;
    self.runningTotal = 0;
    self.totalSize = 0;
    self.logger = logger;
  }

  _createClass(Progress, [{
    key: 'init',
    value: function init(size) {
      this.totalSize = size;
      var totalDesc = this.totalSize || 'unknown number of';

      this.logger.log('Transferring ' + totalDesc + ' bytes', true);
    }
  }, {
    key: 'progress',
    value: function progress(size) {
      if (!this.totalSize) return;

      this.runningTotal += size;
      var newDotCount = Math.round(this.runningTotal / this.totalSize * 100 / 5);
      if (newDotCount > 20) newDotCount = 20;
      for (var i = 0; i < newDotCount - this.dotCount; i++) {
        this.logger.log('.', true);
      }
      this.dotCount = newDotCount;
    }
  }, {
    key: 'complete',
    value: function complete() {
      this.logger.log('Transfer complete', false);
    }
  }]);

  return Progress;
})();

exports['default'] = Progress;
module.exports = exports['default'];
