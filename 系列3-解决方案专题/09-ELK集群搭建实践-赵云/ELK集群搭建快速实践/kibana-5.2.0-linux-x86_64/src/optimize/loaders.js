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
