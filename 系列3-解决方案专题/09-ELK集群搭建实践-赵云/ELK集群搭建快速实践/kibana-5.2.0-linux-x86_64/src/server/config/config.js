'use strict';

var _createClass = (function () { function defineProperties(target, props) { for (var i = 0; i < props.length; i++) { var descriptor = props[i]; descriptor.enumerable = descriptor.enumerable || false; descriptor.configurable = true; if ('value' in descriptor) descriptor.writable = true; Object.defineProperty(target, descriptor.key, descriptor); } } return function (Constructor, protoProps, staticProps) { if (protoProps) defineProperties(Constructor.prototype, protoProps); if (staticProps) defineProperties(Constructor, staticProps); return Constructor; }; })();

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { 'default': obj }; }

function _defineProperty(obj, key, value) { if (key in obj) { Object.defineProperty(obj, key, { value: value, enumerable: true, configurable: true, writable: true }); } else { obj[key] = value; } return obj; }

function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError('Cannot call a class as a function'); } }

var _joi = require('joi');

var _joi2 = _interopRequireDefault(_joi);

var _lodash = require('lodash');

var _lodash2 = _interopRequireDefault(_lodash);

var _override = require('./override');

var _override2 = _interopRequireDefault(_override);

var _unset = require('./unset');

var _unset2 = _interopRequireDefault(_unset);

var _schema = require('./schema');

var _schema2 = _interopRequireDefault(_schema);

var _utilsPackage_json = require('../../utils/package_json');

var _utilsPackage_json2 = _interopRequireDefault(_utilsPackage_json);

var _deep_clone_with_buffers = require('./deep_clone_with_buffers');

var _deep_clone_with_buffers2 = _interopRequireDefault(_deep_clone_with_buffers);

var schema = Symbol('Joi Schema');
var schemaExts = Symbol('Schema Extensions');
var vals = Symbol('config values');
var pendingSets = Symbol('Pending Settings');

module.exports = (function () {
  _createClass(Config, null, [{
    key: 'withDefaultSchema',
    value: function withDefaultSchema() {
      var settings = arguments.length <= 0 || arguments[0] === undefined ? {} : arguments[0];

      return new Config((0, _schema2['default'])(), settings);
    }
  }]);

  function Config(initialSchema, initialSettings) {
    _classCallCheck(this, Config);

    this[schemaExts] = Object.create(null);
    this[vals] = Object.create(null);
    this[pendingSets] = _lodash2['default'].merge(Object.create(null), initialSettings || {});

    if (initialSchema) this.extendSchema(initialSchema);
  }

  _createClass(Config, [{
    key: 'getPendingSets',
    value: function getPendingSets() {
      return new Map(_lodash2['default'].pairs(this[pendingSets]));
    }
  }, {
    key: 'extendSchema',
    value: function extendSchema(key, extension) {
      var _this = this;

      if (key && key.isJoi) {
        return _lodash2['default'].each(key._inner.children, function (child) {
          _this.extendSchema(child.key, child.schema);
        });
      }

      if (this.has(key)) {
        throw new Error('Config schema already has key: ' + key);
      }

      _lodash2['default'].set(this[schemaExts], key, extension);
      this[schema] = null;

      var initialVals = _lodash2['default'].get(this[pendingSets], key);
      if (initialVals) {
        this.set(key, initialVals);
        (0, _unset2['default'])(this[pendingSets], key);
      } else {
        this._commit(this[vals]);
      }
    }
  }, {
    key: 'removeSchema',
    value: function removeSchema(key) {
      if (!_lodash2['default'].has(this[schemaExts], key)) {
        throw new TypeError('Unknown schema key: ' + key);
      }

      this[schema] = null;
      (0, _unset2['default'])(this[schemaExts], key);
      (0, _unset2['default'])(this[pendingSets], key);
      (0, _unset2['default'])(this[vals], key);
    }
  }, {
    key: 'resetTo',
    value: function resetTo(obj) {
      this._commit(obj);
    }
  }, {
    key: 'set',
    value: function set(key, value) {
      // clone and modify the config
      var config = (0, _deep_clone_with_buffers2['default'])(this[vals]);
      if (_lodash2['default'].isPlainObject(key)) {
        config = (0, _override2['default'])(config, key);
      } else {
        _lodash2['default'].set(config, key, value);
      }

      // attempt to validate the config value
      this._commit(config);
    }
  }, {
    key: '_commit',
    value: function _commit(newVals) {
      // resolve the current environment
      var env = newVals.env;
      delete newVals.env;
      if (_lodash2['default'].isObject(env)) env = env.name;
      if (!env) env = process.env.NODE_ENV || 'production';

      var dev = env === 'development';
      var prod = env === 'production';

      // pass the environment as context so that it can be refed in config
      var context = {
        env: env,
        prod: prod,
        dev: dev,
        notProd: !prod,
        notDev: !dev,
        version: _lodash2['default'].get(_utilsPackage_json2['default'], 'version'),
        buildNum: dev ? Math.pow(2, 53) - 1 : _lodash2['default'].get(_utilsPackage_json2['default'], 'build.number', NaN),
        buildSha: dev ? 'XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX' : _lodash2['default'].get(_utilsPackage_json2['default'], 'build.sha', '')
      };

      if (!context.dev && !context.prod) {
        throw new TypeError('Unexpected environment "' + env + '", expected one of "development" or "production"');
      }

      var results = _joi2['default'].validate(newVals, this.getSchema(), { context: context });

      if (results.error) {
        throw results.error;
      }

      this[vals] = results.value;
    }
  }, {
    key: 'get',
    value: function get(key) {
      if (!key) {
        return (0, _deep_clone_with_buffers2['default'])(this[vals]);
      }

      var value = _lodash2['default'].get(this[vals], key);
      if (value === undefined) {
        if (!this.has(key)) {
          throw new Error('Unknown config key: ' + key);
        }
      }
      return (0, _deep_clone_with_buffers2['default'])(value);
    }
  }, {
    key: 'has',
    value: function has(key) {
      function has(key, schema, path) {
        path = path || [];
        // Catch the partial paths
        if (path.join('.') === key) return true;
        // Only go deep on inner objects with children
        if (_lodash2['default'].size(schema._inner.children)) {
          for (var i = 0; i < schema._inner.children.length; i++) {
            var child = schema._inner.children[i];
            // If the child is an object recurse through it's children and return
            // true if there's a match
            if (child.schema._type === 'object') {
              if (has(key, child.schema, path.concat([child.key]))) return true;
              // if the child matches, return true
            } else if (path.concat([child.key]).join('.') === key) {
                return true;
              }
          }
        }
      }

      if (_lodash2['default'].isArray(key)) {
        // TODO: add .has() support for array keys
        key = key.join('.');
      }

      return !!has(key, this.getSchema());
    }
  }, {
    key: 'getSchema',
    value: function getSchema() {
      if (!this[schema]) {
        this[schema] = (function convertToSchema(children) {
          var schema = _joi2['default'].object().keys({})['default']();

          var _iteratorNormalCompletion = true;
          var _didIteratorError = false;
          var _iteratorError = undefined;

          try {
            for (var _iterator = Object.keys(children)[Symbol.iterator](), _step; !(_iteratorNormalCompletion = (_step = _iterator.next()).done); _iteratorNormalCompletion = true) {
              var key = _step.value;

              var child = children[key];
              var childSchema = _lodash2['default'].isPlainObject(child) ? convertToSchema(child) : child;

              if (!childSchema || !childSchema.isJoi) {
                throw new TypeError('Unable to convert configuration definition value to Joi schema: ' + childSchema);
              }

              schema = schema.keys(_defineProperty({}, key, childSchema));
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

          return schema;
        })(this[schemaExts]);
      }

      return this[schema];
    }
  }]);

  return Config;
})();
