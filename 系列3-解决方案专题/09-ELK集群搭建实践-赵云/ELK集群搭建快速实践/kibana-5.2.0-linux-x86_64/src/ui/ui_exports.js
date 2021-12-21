'use strict';

var _createClass = (function () { function defineProperties(target, props) { for (var i = 0; i < props.length; i++) { var descriptor = props[i]; descriptor.enumerable = descriptor.enumerable || false; descriptor.configurable = true; if ('value' in descriptor) descriptor.writable = true; Object.defineProperty(target, descriptor.key, descriptor); } } return function (Constructor, protoProps, staticProps) { if (protoProps) defineProperties(Constructor.prototype, protoProps); if (staticProps) defineProperties(Constructor, staticProps); return Constructor; }; })();

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { 'default': obj }; }

function _toConsumableArray(arr) { if (Array.isArray(arr)) { for (var i = 0, arr2 = Array(arr.length); i < arr.length; i++) arr2[i] = arr[i]; return arr2; } else { return Array.from(arr); } }

function _asyncToGenerator(fn) { return function () { var gen = fn.apply(this, arguments); return new Promise(function (resolve, reject) { var callNext = step.bind(null, 'next'); var callThrow = step.bind(null, 'throw'); function step(key, arg) { try { var info = gen[key](arg); var value = info.value; } catch (error) { reject(error); return; } if (info.done) { resolve(value); } else { Promise.resolve(value).then(callNext, callThrow); } } callNext(); }); }; }

function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError('Cannot call a class as a function'); } }

var _lodash = require('lodash');

var _lodash2 = _interopRequireDefault(_lodash);

var _minimatch = require('minimatch');

var _minimatch2 = _interopRequireDefault(_minimatch);

var _ui_app_collection = require('./ui_app_collection');

var _ui_app_collection2 = _interopRequireDefault(_ui_app_collection);

var _ui_nav_link_collection = require('./ui_nav_link_collection');

var _ui_nav_link_collection2 = _interopRequireDefault(_ui_nav_link_collection);

var UiExports = (function () {
  function UiExports(_ref) {
    var urlBasePath = _ref.urlBasePath;

    _classCallCheck(this, UiExports);

    this.navLinks = new _ui_nav_link_collection2['default'](this);
    this.apps = new _ui_app_collection2['default'](this);
    this.aliases = {};
    this.urlBasePath = urlBasePath;
    this.exportConsumer = _lodash2['default'].memoize(this.exportConsumer);
    this.consumers = [];
    this.bundleProviders = [];
    this.defaultInjectedVars = {};
    this.injectedVarsReplacers = [];
  }

  _createClass(UiExports, [{
    key: 'consumePlugin',
    value: function consumePlugin(plugin) {
      var _this = this;

      plugin.apps = new _ui_app_collection2['default'](this);

      var types = _lodash2['default'].keys(plugin.uiExportsSpecs);
      if (!types) return false;

      var unkown = _lodash2['default'].reject(types, this.exportConsumer, this);
      if (unkown.length) {
        throw new Error('unknown export types ' + unkown.join(', ') + ' in plugin ' + plugin.id);
      }

      var _iteratorNormalCompletion = true;
      var _didIteratorError = false;
      var _iteratorError = undefined;

      try {
        for (var _iterator = this.consumers[Symbol.iterator](), _step; !(_iteratorNormalCompletion = (_step = _iterator.next()).done); _iteratorNormalCompletion = true) {
          var consumer = _step.value;

          consumer.consumePlugin && consumer.consumePlugin(plugin);
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

      types.forEach(function (type) {
        _this.exportConsumer(type)(plugin, plugin.uiExportsSpecs[type]);
      });
    }
  }, {
    key: 'addConsumer',
    value: function addConsumer(consumer) {
      this.consumers.push(consumer);
    }
  }, {
    key: 'exportConsumer',
    value: function exportConsumer(type) {
      var _this2 = this;

      var _iteratorNormalCompletion2 = true;
      var _didIteratorError2 = false;
      var _iteratorError2 = undefined;

      try {
        for (var _iterator2 = this.consumers[Symbol.iterator](), _step2; !(_iteratorNormalCompletion2 = (_step2 = _iterator2.next()).done); _iteratorNormalCompletion2 = true) {
          var consumer = _step2.value;

          if (!consumer.exportConsumer) continue;
          var fn = consumer.exportConsumer(type);
          if (fn) return fn;
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

      switch (type) {
        case 'app':
        case 'apps':
          return function (plugin, specs) {
            var id = plugin.id;
            var _iteratorNormalCompletion3 = true;
            var _didIteratorError3 = false;
            var _iteratorError3 = undefined;

            try {
              var _loop = function () {
                var spec = _step3.value;

                var app = _this2.apps['new'](_lodash2['default'].defaults({}, spec, {
                  id: plugin.id,
                  urlBasePath: _this2.urlBasePath
                }));

                plugin.extendInit(function (server, options) {
                  // eslint-disable-line no-loop-func
                  var wrapped = app.getInjectedVars;
                  app.getInjectedVars = function () {
                    return wrapped.call(plugin, server, options);
                  };
                });

                plugin.apps.add(app);
              };

              for (var _iterator3 = [].concat(specs || [])[Symbol.iterator](), _step3; !(_iteratorNormalCompletion3 = (_step3 = _iterator3.next()).done); _iteratorNormalCompletion3 = true) {
                _loop();
              }
            } catch (err) {
              _didIteratorError3 = true;
              _iteratorError3 = err;
            } finally {
              try {
                if (!_iteratorNormalCompletion3 && _iterator3['return']) {
                  _iterator3['return']();
                }
              } finally {
                if (_didIteratorError3) {
                  throw _iteratorError3;
                }
              }
            }
          };

        case 'link':
        case 'links':
          return function (plugin, spec) {
            var _iteratorNormalCompletion4 = true;
            var _didIteratorError4 = false;
            var _iteratorError4 = undefined;

            try {
              for (var _iterator4 = [].concat(spec || [])[Symbol.iterator](), _step4; !(_iteratorNormalCompletion4 = (_step4 = _iterator4.next()).done); _iteratorNormalCompletion4 = true) {
                var _spec = _step4.value;

                _this2.navLinks['new'](_spec);
              }
            } catch (err) {
              _didIteratorError4 = true;
              _iteratorError4 = err;
            } finally {
              try {
                if (!_iteratorNormalCompletion4 && _iterator4['return']) {
                  _iterator4['return']();
                }
              } finally {
                if (_didIteratorError4) {
                  throw _iteratorError4;
                }
              }
            }
          };

        case 'visTypes':
        case 'fieldFormats':
        case 'spyModes':
        case 'chromeNavControls':
        case 'navbarExtensions':
        case 'managementSections':
        case 'devTools':
        case 'docViews':
        case 'hacks':
          return function (plugin, spec) {
            _this2.aliases[type] = _lodash2['default'].union(_this2.aliases[type] || [], spec);
          };

        case 'visTypeEnhancers':
          return function (plugin, spec) {
            //used for plugins that augment capabilities of an existing visualization
            _this2.aliases.visTypes = _lodash2['default'].union(_this2.aliases.visTypes || [], spec);
          };

        case 'bundle':
          return function (plugin, spec) {
            _this2.bundleProviders.push(spec);
          };

        case 'aliases':
          return function (plugin, specs) {
            _lodash2['default'].forOwn(specs, function (spec, adhocType) {
              _this2.aliases[adhocType] = _lodash2['default'].union(_this2.aliases[adhocType] || [], spec);
            });
          };

        case 'injectDefaultVars':
          return function (plugin, injector) {
            plugin.extendInit(_asyncToGenerator(function* (server, options) {
              _lodash2['default'].merge(_this2.defaultInjectedVars, (yield injector.call(plugin, server, options)));
            }));
          };

        case 'replaceInjectedVars':
          return function (plugin, replacer) {
            _this2.injectedVarsReplacers.push(replacer);
          };
      }
    }
  }, {
    key: 'find',
    value: function find(patterns) {
      var aliases = this.aliases;
      var names = _lodash2['default'].keys(aliases);
      var matcher = _lodash2['default'].partialRight(_minimatch2['default'].filter, { matchBase: true });

      return _lodash2['default'].chain(patterns).map(function (pattern) {
        return names.filter(matcher(pattern));
      }).flattenDeep().reduce(function (found, name) {
        return found.concat(aliases[name]);
      }, []).value();
    }
  }, {
    key: 'getAllApps',
    value: function getAllApps() {
      var _ref2;

      var apps = this.apps;

      return (_ref2 = [].concat(_toConsumableArray(apps))).concat.apply(_ref2, _toConsumableArray(apps.hidden));
    }
  }, {
    key: 'getApp',
    value: function getApp(id) {
      return this.apps.byId[id];
    }
  }, {
    key: 'getHiddenApp',
    value: function getHiddenApp(id) {
      return this.apps.hidden.byId[id];
    }
  }, {
    key: 'getBundleProviders',
    value: function getBundleProviders() {
      return this.bundleProviders;
    }
  }]);

  return UiExports;
})();

module.exports = UiExports;
