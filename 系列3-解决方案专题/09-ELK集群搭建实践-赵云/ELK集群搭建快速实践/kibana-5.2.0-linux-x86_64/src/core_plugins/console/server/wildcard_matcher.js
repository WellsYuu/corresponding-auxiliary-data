'use strict';

Object.defineProperty(exports, '__esModule', {
  value: true
});

var _createClass = (function () { function defineProperties(target, props) { for (var i = 0; i < props.length; i++) { var descriptor = props[i]; descriptor.enumerable = descriptor.enumerable || false; descriptor.configurable = true; if ('value' in descriptor) descriptor.writable = true; Object.defineProperty(target, descriptor.key, descriptor); } } return function (Constructor, protoProps, staticProps) { if (protoProps) defineProperties(Constructor.prototype, protoProps); if (staticProps) defineProperties(Constructor, staticProps); return Constructor; }; })();

function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError('Cannot call a class as a function'); } }

var _minimatch = require('minimatch');

var WildcardMatcher = (function () {
  function WildcardMatcher(wildcardPattern, emptyVal) {
    _classCallCheck(this, WildcardMatcher);

    this.emptyVal = emptyVal;
    this.pattern = String(wildcardPattern || '*');
    this.matcher = new _minimatch.Minimatch(this.pattern, {
      noglobstar: true,
      dot: true,
      nocase: true,
      matchBase: true,
      nocomment: true
    });
  }

  _createClass(WildcardMatcher, [{
    key: 'match',
    value: function match(candidate) {
      var empty = !candidate || candidate === this.emptyVal;
      if (empty && this.pattern === '*') {
        return true;
      }

      return this.matcher.match(candidate || '');
    }
  }]);

  return WildcardMatcher;
})();

exports.WildcardMatcher = WildcardMatcher;
