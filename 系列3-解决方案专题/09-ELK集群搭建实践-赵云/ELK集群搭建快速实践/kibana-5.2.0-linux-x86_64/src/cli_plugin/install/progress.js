/*
 * Copyright 2021-2022 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
