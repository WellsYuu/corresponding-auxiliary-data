/*
 * Copyright 2021-2022 the original author or authors
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

'use strict';

var _createClass = (function () { function defineProperties(target, props) { for (var i = 0; i < props.length; i++) { var descriptor = props[i]; descriptor.enumerable = descriptor.enumerable || false; descriptor.configurable = true; if ('value' in descriptor) descriptor.writable = true; Object.defineProperty(target, descriptor.key, descriptor); } } return function (Constructor, protoProps, staticProps) { if (protoProps) defineProperties(Constructor.prototype, protoProps); if (staticProps) defineProperties(Constructor, staticProps); return Constructor; }; })();

function _asyncToGenerator(fn) { return function () { var gen = fn.apply(this, arguments); return new Promise(function (resolve, reject) { var callNext = step.bind(null, 'next'); var callThrow = step.bind(null, 'throw'); function step(key, arg) { try { var info = gen[key](arg); var value = info.value; } catch (error) { reject(error); return; } if (info.done) { resolve(value); } else { Promise.resolve(value).then(callNext, callThrow); } } callNext(); }); }; }

function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError('Cannot call a class as a function'); } }

var _path = require('path');

var _bluebird = require('bluebird');

var read = (0, _bluebird.promisify)(require('fs').readFile);
var write = (0, _bluebird.promisify)(require('fs').writeFile);
var unlink = (0, _bluebird.promisify)(require('fs').unlink);
var stat = (0, _bluebird.promisify)(require('fs').stat);

module.exports = (function () {
  function UiBundle(opts) {
    _classCallCheck(this, UiBundle);

    opts = opts || {};
    this.id = opts.id;
    this.modules = opts.modules;
    this.template = opts.template;
    this.env = opts.env;

    var pathBase = (0, _path.join)(this.env.workingDir, this.id);
    this.entryPath = pathBase + '.entry.js';
    this.outputPath = pathBase + '.bundle.js';
  }

  _createClass(UiBundle, [{
    key: 'renderContent',
    value: function renderContent() {
      return this.template({
        env: this.env,
        bundle: this
      });
    }
  }, {
    key: 'readEntryFile',
    value: _asyncToGenerator(function* () {
      try {
        var content = yield read(this.entryPath);
        return content.toString('utf8');
      } catch (e) {
        return null;
      }
    })
  }, {
    key: 'writeEntryFile',
    value: _asyncToGenerator(function* () {
      return yield write(this.entryPath, this.renderContent(), { encoding: 'utf8' });
    })
  }, {
    key: 'clearBundleFile',
    value: _asyncToGenerator(function* () {
      try {
        yield unlink(this.outputPath);
      } catch (e) {
        return null;
      }
    })
  }, {
    key: 'checkForExistingOutput',
    value: _asyncToGenerator(function* () {
      try {
        yield stat(this.outputPath);
        return true;
      } catch (e) {
        return false;
      }
    })
  }, {
    key: 'toJSON',
    value: function toJSON() {
      return {
        id: this.id,
        modules: this.modules,
        entryPath: this.entryPath,
        outputPath: this.outputPath
      };
    }
  }]);

  return UiBundle;
})();
