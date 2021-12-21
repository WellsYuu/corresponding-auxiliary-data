'use strict';

var _createClass = (function () { function defineProperties(target, props) { for (var i = 0; i < props.length; i++) { var descriptor = props[i]; descriptor.enumerable = descriptor.enumerable || false; descriptor.configurable = true; if ('value' in descriptor) descriptor.writable = true; Object.defineProperty(target, descriptor.key, descriptor); } } return function (Constructor, protoProps, staticProps) { if (protoProps) defineProperties(Constructor.prototype, protoProps); if (staticProps) defineProperties(Constructor, staticProps); return Constructor; }; })();

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { 'default': obj }; }

function _toConsumableArray(arr) { if (Array.isArray(arr)) { for (var i = 0, arr2 = Array(arr.length); i < arr.length; i++) arr2[i] = arr[i]; return arr2; } else { return Array.from(arr); } }

function _asyncToGenerator(fn) { return function () { var gen = fn.apply(this, arguments); return new Promise(function (resolve, reject) { var callNext = step.bind(null, 'next'); var callThrow = step.bind(null, 'throw'); function step(key, arg) { try { var info = gen[key](arg); var value = info.value; } catch (error) { reject(error); return; } if (info.done) { resolve(value); } else { Promise.resolve(value).then(callNext, callThrow); } } callNext(); }); }; }

function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError('Cannot call a class as a function'); } }

var _path = require('path');

var _fs = require('fs');

var _webpack = require('webpack');

var _webpack2 = _interopRequireDefault(_webpack);

var _boom = require('boom');

var _boom2 = _interopRequireDefault(_boom);

var _elasticWebpackDirectoryNameAsMain = require('@elastic/webpack-directory-name-as-main');

var _elasticWebpackDirectoryNameAsMain2 = _interopRequireDefault(_elasticWebpackDirectoryNameAsMain);

var _extractTextWebpackPlugin = require('extract-text-webpack-plugin');

var _extractTextWebpackPlugin2 = _interopRequireDefault(_extractTextWebpackPlugin);

var _webpackLibOptimizeCommonsChunkPlugin = require('webpack/lib/optimize/CommonsChunkPlugin');

var _webpackLibOptimizeCommonsChunkPlugin2 = _interopRequireDefault(_webpackLibOptimizeCommonsChunkPlugin);

var _webpackLibDefinePlugin = require('webpack/lib/DefinePlugin');

var _webpackLibDefinePlugin2 = _interopRequireDefault(_webpackLibDefinePlugin);

var _webpackLibOptimizeUglifyJsPlugin = require('webpack/lib/optimize/UglifyJsPlugin');

var _webpackLibOptimizeUglifyJsPlugin2 = _interopRequireDefault(_webpackLibOptimizeUglifyJsPlugin);

var _lodash = require('lodash');

var _utilsFrom_root = require('../utils/from_root');

var _utilsFrom_root2 = _interopRequireDefault(_utilsFrom_root);

var _babel_options = require('./babel_options');

var _babel_options2 = _interopRequireDefault(_babel_options);

var _packageJson = require('../../package.json');

var _packageJson2 = _interopRequireDefault(_packageJson);

var _loaders = require('./loaders');

var babelExclude = [/[\/\\](webpackShims|node_modules|bower_components)[\/\\]/];

var BaseOptimizer = (function () {
  function BaseOptimizer(opts) {
    _classCallCheck(this, BaseOptimizer);

    this.env = opts.env;
    this.urlBasePath = opts.urlBasePath;
    this.bundles = opts.bundles;
    this.profile = opts.profile || false;

    switch (opts.sourceMaps) {
      case true:
        this.sourceMaps = 'source-map';
        break;

      case 'fast':
        this.sourceMaps = 'cheap-module-eval-source-map';
        break;

      default:
        this.sourceMaps = opts.sourceMaps || false;
        break;
    }

    this.unsafeCache = opts.unsafeCache || false;
    if (typeof this.unsafeCache === 'string') {
      this.unsafeCache = [new RegExp(this.unsafeCache.slice(1, -1))];
    }
  }

  _createClass(BaseOptimizer, [{
    key: 'initCompiler',
    value: _asyncToGenerator(function* () {
      var _this = this;

      if (this.compiler) return this.compiler;

      var compilerConfig = this.getConfig();
      this.compiler = (0, _webpack2['default'])(compilerConfig);

      this.compiler.plugin('done', function (stats) {
        if (!_this.profile) return;

        var path = (0, _path.resolve)(_this.env.workingDir, 'stats.json');
        var content = JSON.stringify(stats.toJson());
        (0, _fs.writeFile)(path, content, function (err) {
          if (err) throw err;
        });
      });

      return this.compiler;
    })
  }, {
    key: 'getConfig',
    value: function getConfig() {
      var _this2 = this;

      var loaderWithSourceMaps = function loaderWithSourceMaps(loader) {
        return (0, _loaders.setLoaderQueryParam)(loader, 'sourceMap', !!_this2.sourceMaps);
      };

      var makeStyleLoader = function makeStyleLoader(preprocessor) {
        var loaders = [loaderWithSourceMaps('css-loader?autoprefixer=false'), {
          name: 'postcss-loader',
          query: {
            config: require.resolve('./postcss.config')
          }
        }];

        if (preprocessor) {
          loaders = [].concat(_toConsumableArray(loaders), [loaderWithSourceMaps(preprocessor)]);
        }

        return _extractTextWebpackPlugin2['default'].extract((0, _loaders.makeLoaderString)(loaders));
      };

      return {
        context: (0, _utilsFrom_root2['default'])('.'),
        entry: this.bundles.toWebpackEntries(),

        devtool: this.sourceMaps,
        profile: this.profile || false,

        output: {
          path: this.env.workingDir,
          filename: '[name].bundle.js',
          sourceMapFilename: '[file].map',
          publicPath: (this.urlBasePath || '') + '/bundles/',
          devtoolModuleFilenameTemplate: '[absolute-resource-path]'
        },

        recordsPath: (0, _path.resolve)(this.env.workingDir, 'webpack.records'),

        plugins: [new _webpack2['default'].ResolverPlugin([new _elasticWebpackDirectoryNameAsMain2['default']()]), new _webpack2['default'].NoErrorsPlugin(), new _extractTextWebpackPlugin2['default']('[name].style.css', {
          allChunks: true
        }), new _webpackLibOptimizeCommonsChunkPlugin2['default']({
          name: 'commons',
          filename: 'commons.bundle.js'
        })].concat(_toConsumableArray(this.pluginsForEnv(this.env.context.env))),

        module: {
          loaders: [{ test: /\.less$/, loader: makeStyleLoader('less-loader') }, { test: /\.css$/, loader: makeStyleLoader() }, { test: /\.jade$/, loader: 'jade-loader' }, { test: /\.json$/, loader: 'json-loader' }, { test: /\.(html|tmpl)$/, loader: 'raw-loader' }, { test: /\.png$/, loader: 'url-loader' }, { test: /\.(woff|woff2|ttf|eot|svg|ico)(\?|$)/, loader: 'file-loader' }, { test: /[\/\\]src[\/\\](core_plugins|ui)[\/\\].+\.js$/, loader: loaderWithSourceMaps('rjs-repack-loader') }, {
            test: /\.jsx?$/,
            exclude: babelExclude.concat(this.env.noParse),
            loader: (0, _loaders.makeLoaderString)([{
              name: 'babel-loader',
              query: _babel_options2['default'].webpack
            }])
          }],
          postLoaders: this.env.postLoaders || [],
          noParse: this.env.noParse
        },

        resolve: {
          extensions: ['.js', '.json', '.jsx', '.less', ''],
          postfixes: [''],
          modulesDirectories: ['webpackShims', 'node_modules'],
          fallback: [(0, _utilsFrom_root2['default'])('webpackShims'), (0, _utilsFrom_root2['default'])('node_modules')],
          loaderPostfixes: ['-loader', ''],
          root: (0, _utilsFrom_root2['default'])('.'),
          alias: this.env.aliases,
          unsafeCache: this.unsafeCache
        },

        resolveLoader: {
          alias: (0, _lodash.transform)(_packageJson2['default'].dependencies, function (aliases, version, name) {
            if (name.endsWith('-loader')) {
              aliases[name] = require.resolve(name);
            }
          }, {})
        }
      };
    }
  }, {
    key: 'pluginsForEnv',
    value: function pluginsForEnv(env) {
      if (env !== 'production') {
        return [];
      }

      return [new _webpackLibDefinePlugin2['default']({
        'process.env': {
          'NODE_ENV': '"production"'
        }
      }), new _webpackLibOptimizeUglifyJsPlugin2['default']({
        compress: {
          warnings: false
        },
        sourceMap: false,
        mangle: false
      })];
    }
  }, {
    key: 'failedStatsToError',
    value: function failedStatsToError(stats) {
      var statFormatOpts = {
        hash: false, // add the hash of the compilation
        version: false, // add webpack version information
        timings: false, // add timing information
        assets: false, // add assets information
        chunks: false, // add chunk information
        chunkModules: false, // add built modules information to chunk information
        modules: false, // add built modules information
        cached: false, // add also information about cached (not built) modules
        reasons: false, // add information about the reasons why modules are included
        source: false, // add the source code of modules
        errorDetails: false, // add details to errors (like resolving log)
        chunkOrigins: false, // add the origins of chunks and chunk merging info
        modulesSort: false, // (string) sort the modules by that field
        chunksSort: false, // (string) sort the chunks by that field
        assetsSort: false, // (string) sort the assets by that field
        children: false
      };

      var details = stats.toString((0, _lodash.defaults)({ colors: true }, statFormatOpts));

      return _boom2['default'].create(500, 'Optimizations failure.\n' + details.split('\n').join('\n    ') + '\n', stats.toJson(statFormatOpts));
    }
  }]);

  return BaseOptimizer;
})();

module.exports = BaseOptimizer;
