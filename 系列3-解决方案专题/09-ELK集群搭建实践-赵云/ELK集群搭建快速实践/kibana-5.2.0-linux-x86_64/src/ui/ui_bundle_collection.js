'use strict';

var _createClass = (function () { function defineProperties(target, props) { for (var i = 0; i < props.length; i++) { var descriptor = props[i]; descriptor.enumerable = descriptor.enumerable || false; descriptor.configurable = true; if ('value' in descriptor) descriptor.writable = true; Object.defineProperty(target, descriptor.key, descriptor); } } return function (Constructor, protoProps, staticProps) { if (protoProps) defineProperties(Constructor.prototype, protoProps); if (staticProps) defineProperties(Constructor, staticProps); return Constructor; }; })();

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { 'default': obj }; }

function _asyncToGenerator(fn) { return function () { var gen = fn.apply(this, arguments); return new Promise(function (resolve, reject) { var callNext = step.bind(null, 'next'); var callThrow = step.bind(null, 'throw'); function step(key, arg) { try { var info = gen[key](arg); var value = info.value; } catch (error) { reject(error); return; } if (info.done) { resolve(value); } else { Promise.resolve(value).then(callNext, callThrow); } } callNext(); }); }; }

function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError('Cannot call a class as a function'); } }

var _ui_bundle = require('./ui_bundle');

var _ui_bundle2 = _interopRequireDefault(_ui_bundle);

var _app_entry_template = require('./app_entry_template');

var _app_entry_template2 = _interopRequireDefault(_app_entry_template);

var _fs = require('fs');

var _lodash = require('lodash');

var _bluebird = require('bluebird');

var _minimatch = require('minimatch');

var rimraf = (0, _bluebird.promisify)(require('rimraf'));
var mkdirp = (0, _bluebird.promisify)(require('mkdirp'));
var unlink = (0, _bluebird.promisify)(require('fs').unlink);
var readdir = (0, _bluebird.promisify)(require('fs').readdir);

var UiBundleCollection = (function () {
  function UiBundleCollection(bundlerEnv, filter) {
    _classCallCheck(this, UiBundleCollection);

    this.each = [];
    this.env = bundlerEnv;
    this.filter = (0, _minimatch.makeRe)(filter || '*', {
      noglobstar: true,
      noext: true,
      matchBase: true
    });
  }

  _createClass(UiBundleCollection, [{
    key: 'add',
    value: function add(bundle) {
      if (!(bundle instanceof _ui_bundle2['default'])) {
        throw new TypeError('expected bundle to be an instance of UiBundle');
      }

      if (this.filter.test(bundle.id)) {
        this.each.push(bundle);
      }
    }
  }, {
    key: 'addApp',
    value: function addApp(app) {
      this.add(new _ui_bundle2['default']({
        id: app.id,
        modules: app.getModules(),
        template: _app_entry_template2['default'],
        env: this.env
      }));
    }
  }, {
    key: 'desc',
    value: function desc() {
      switch (this.each.length) {
        case 0:
          return '0 bundles';
        case 1:
          return 'bundle for ' + this.each[0].id;
        default:
          var ids = this.getIds();
          var last = ids.pop();
          var commas = ids.join(', ');
          return 'bundles for ' + commas + ' and ' + last;
      }
    }
  }, {
    key: 'ensureDir',
    value: _asyncToGenerator(function* () {
      yield mkdirp(this.env.workingDir);
    })
  }, {
    key: 'writeEntryFiles',
    value: _asyncToGenerator(function* () {
      yield this.ensureDir();

      var _iteratorNormalCompletion = true;
      var _didIteratorError = false;
      var _iteratorError = undefined;

      try {
        for (var _iterator = this.each[Symbol.iterator](), _step; !(_iteratorNormalCompletion = (_step = _iterator.next()).done); _iteratorNormalCompletion = true) {
          var bundle = _step.value;

          var existing = yield bundle.readEntryFile();
          var expected = bundle.renderContent();

          if (existing !== expected) {
            yield bundle.writeEntryFile();
            yield bundle.clearBundleFile();
          }
        }
      } catch (err) {
        _didIteratorError = true;
        _iteratorError = err;
      } finally {
        try {
          if (!_iteratorNormalCompletion && _iterator['return']) {
            _iterator['return']();
          }
        } finally {
          if (_didIteratorError) {
            throw _iteratorError;
          }
        }
      }
    })
  }, {
    key: 'getInvalidBundles',
    value: _asyncToGenerator(function* () {
      var invalids = new UiBundleCollection(this.env);

      var _iteratorNormalCompletion2 = true;
      var _didIteratorError2 = false;
      var _iteratorError2 = undefined;

      try {
        for (var _iterator2 = this.each[Symbol.iterator](), _step2; !(_iteratorNormalCompletion2 = (_step2 = _iterator2.next()).done); _iteratorNormalCompletion2 = true) {
          var bundle = _step2.value;

          var exists = yield bundle.checkForExistingOutput();
          if (!exists) {
            invalids.add(bundle);
          }
        }
      } catch (err) {
        _didIteratorError2 = true;
        _iteratorError2 = err;
      } finally {
        try {
          if (!_iteratorNormalCompletion2 && _iterator2['return']) {
            _iterator2['return']();
          }
        } finally {
          if (_didIteratorError2) {
            throw _iteratorError2;
          }
        }
      }

      return invalids;
    })
  }, {
    key: 'toWebpackEntries',
    value: function toWebpackEntries() {
      return (0, _lodash.transform)(this.each, function (entries, bundle) {
        entries[bundle.id] = bundle.entryPath;
      }, {});
    }
  }, {
    key: 'getIds',
    value: function getIds() {
      return (0, _lodash.pluck)(this.each, 'id');
    }
  }, {
    key: 'toJSON',
    value: function toJSON() {
      return this.each;
    }
  }]);

  return UiBundleCollection;
})();

module.exports = UiBundleCollection;
