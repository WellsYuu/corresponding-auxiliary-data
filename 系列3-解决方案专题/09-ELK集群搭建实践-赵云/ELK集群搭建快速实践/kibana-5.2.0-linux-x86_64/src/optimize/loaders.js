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

Object.defineProperty(exports, '__esModule', {
  value: true
});

var _createClass = (function () { function defineProperties(target, props) { for (var i = 0; i < props.length; i++) { var descriptor = props[i]; descriptor.enumerable = descriptor.enumerable || false; descriptor.configurable = true; if ('value' in descriptor) descriptor.writable = true; Object.defineProperty(target, descriptor.key, descriptor); } } return function (Constructor, protoProps, staticProps) { if (protoProps) defineProperties(Constructor.prototype, protoProps); if (staticProps) defineProperties(Constructor, staticProps); return Constructor; }; })();

function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError('Cannot call a class as a function'); } }

var _url = require('url');

var Loader = (function () {
  function Loader() {
    var _ref = arguments.length <= 0 || arguments[0] === undefined ? {} : arguments[0];

    var name = _ref.name;
    var query = _ref.query;

    _classCallCheck(this, Loader);

    if (!name) {
      throw new Error('Loaders must define a name');
    }

    this.name = name;
    this.query = query || {};
  }

  _createClass(Loader, [{
    key: 'toString',
    value: function toString() {
      return (0, _url.format)({
        pathname: this.name,
        query: this.query
      });
    }
  }, {
    key: 'setQueryParam',
    value: function setQueryParam(name, value) {
      this.query[name] = value;
      return this;
    }
  }], [{
    key: 'fromUrl',
    value: function fromUrl(url) {
      var parsed = (0, _url.parse)(url, true);
      return new Loader({
        name: parsed.pathname,
        query: parsed.query
      });
    }
  }]);

  return Loader;
})();

function parseLoader(spec) {
  if (typeof spec === 'string') {
    return Loader.fromUrl(spec);
  }

  return new Loader(spec);
}

var makeLoaderString = function makeLoaderString(loaders) {
  return loaders.map(parseLoader).map(function (l) {
    return l.toString();
  }).join('!');
};

exports.makeLoaderString = makeLoaderString;
var setLoaderQueryParam = function setLoaderQueryParam(loader, name, value) {
  return parseLoader(loader).setQueryParam(name, value).toString();
};
exports.setLoaderQueryParam = setLoaderQueryParam;
