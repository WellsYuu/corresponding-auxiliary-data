'use strict';

var _createClass = (function () { function defineProperties(target, props) { for (var i = 0; i < props.length; i++) { var descriptor = props[i]; descriptor.enumerable = descriptor.enumerable || false; descriptor.configurable = true; if ('value' in descriptor) descriptor.writable = true; Object.defineProperty(target, descriptor.key, descriptor); } } return function (Constructor, protoProps, staticProps) { if (protoProps) defineProperties(Constructor.prototype, protoProps); if (staticProps) defineProperties(Constructor, staticProps); return Constructor; }; })();

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { 'default': obj }; }

function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError('Cannot call a class as a function'); } }

var _lodash = require('lodash');

var _lodash2 = _interopRequireDefault(_lodash);

var _goodSqueeze = require('good-squeeze');

var _fs = require('fs');

var _log_format_json = require('./log_format_json');

var _log_format_json2 = _interopRequireDefault(_log_format_json);

var _log_format_string = require('./log_format_string');

var _log_format_string2 = _interopRequireDefault(_log_format_string);

var _log_interceptor = require('./log_interceptor');

module.exports = (function () {
  function KbnLogger(events, config) {
    _classCallCheck(this, KbnLogger);

    this.squeeze = new _goodSqueeze.Squeeze(events);
    this.format = config.json ? new _log_format_json2['default'](config) : new _log_format_string2['default'](config);
    this.logInterceptor = new _log_interceptor.LogInterceptor();

    if (config.dest === 'stdout') {
      this.dest = process.stdout;
    } else {
      this.dest = (0, _fs.createWriteStream)(config.dest, {
        flags: 'a',
        encoding: 'utf8'
      });
    }
  }

  _createClass(KbnLogger, [{
    key: 'init',
    value: function init(readstream, emitter, callback) {
      var _this = this;

      this.output = readstream.pipe(this.logInterceptor).pipe(this.squeeze).pipe(this.format);

      this.output.pipe(this.dest);

      emitter.on('stop', function () {
        _this.output.unpipe(_this.dest);
      });

      callback();
    }
  }]);

  return KbnLogger;
})();
