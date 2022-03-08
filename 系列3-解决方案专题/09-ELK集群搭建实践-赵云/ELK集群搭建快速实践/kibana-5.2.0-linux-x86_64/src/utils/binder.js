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

"use strict";

Object.defineProperty(exports, "__esModule", {
  value: true
});

var _createClass = (function () { function defineProperties(target, props) { for (var i = 0; i < props.length; i++) { var descriptor = props[i]; descriptor.enumerable = descriptor.enumerable || false; descriptor.configurable = true; if ("value" in descriptor) descriptor.writable = true; Object.defineProperty(target, descriptor.key, descriptor); } } return function (Constructor, protoProps, staticProps) { if (protoProps) defineProperties(Constructor.prototype, protoProps); if (staticProps) defineProperties(Constructor, staticProps); return Constructor; }; })();

function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError("Cannot call a class as a function"); } }

var Binder = (function () {
  function Binder() {
    _classCallCheck(this, Binder);

    this.disposal = [];
  }

  _createClass(Binder, [{
    key: "on",
    value: function on(emitter) {
      for (var _len = arguments.length, args = Array(_len > 1 ? _len - 1 : 0), _key = 1; _key < _len; _key++) {
        args[_key - 1] = arguments[_key];
      }

      var on = emitter.on || emitter.addListener;
      var off = emitter.off || emitter.removeListener;

      on.apply(emitter, args);
      this.disposal.push(function () {
        return off.apply(emitter, args);
      });
    }
  }, {
    key: "destroy",
    value: function destroy() {
      var destroyers = this.disposal;
      this.disposal = [];
      destroyers.forEach(function (fn) {
        return fn();
      });
    }
  }]);

  return Binder;
})();

exports["default"] = Binder;
module.exports = exports["default"];
