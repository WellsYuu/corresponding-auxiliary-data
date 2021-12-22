'use strict';

var _createClass = (function () { function defineProperties(target, props) { for (var i = 0; i < props.length; i++) { var descriptor = props[i]; descriptor.enumerable = descriptor.enumerable || false; descriptor.configurable = true; if ('value' in descriptor) descriptor.writable = true; Object.defineProperty(target, descriptor.key, descriptor); } } return function (Constructor, protoProps, staticProps) { if (protoProps) defineProperties(Constructor.prototype, protoProps); if (staticProps) defineProperties(Constructor, staticProps); return Constructor; }; })();

var _get = function get(_x, _x2, _x3) { var _again = true; _function: while (_again) { var object = _x, property = _x2, receiver = _x3; _again = false; if (object === null) object = Function.prototype; var desc = Object.getOwnPropertyDescriptor(object, property); if (desc === undefined) { var parent = Object.getPrototypeOf(object); if (parent === null) { return undefined; } else { _x = parent; _x2 = property; _x3 = receiver; _again = true; desc = parent = undefined; continue _function; } } else if ('value' in desc) { return desc.value; } else { var getter = desc.get; if (getter === undefined) { return undefined; } return getter.call(receiver); } } };

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { 'default': obj }; }

function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError('Cannot call a class as a function'); } }

function _inherits(subClass, superClass) { if (typeof superClass !== 'function' && superClass !== null) { throw new TypeError('Super expression must either be null or a function, not ' + typeof superClass); } subClass.prototype = Object.create(superClass && superClass.prototype, { constructor: { value: subClass, enumerable: false, writable: true, configurable: true } }); if (superClass) Object.setPrototypeOf ? Object.setPrototypeOf(subClass, superClass) : subClass.__proto__ = superClass; }

var _lodash = require('lodash');

var _lodash2 = _interopRequireDefault(_lodash);

var _ansicolors = require('ansicolors');

var _ansicolors2 = _interopRequireDefault(_ansicolors);

var _moment = require('moment');

var _moment2 = _interopRequireDefault(_moment);

var _log_format = require('./log_format');

var _log_format2 = _interopRequireDefault(_log_format);

var statuses = ['err', 'info', 'error', 'warning', 'fatal', 'status', 'debug'];

var typeColors = {
  log: 'blue',
  req: 'green',
  res: 'green',
  ops: 'cyan',
  config: 'cyan',
  err: 'red',
  info: 'green',
  error: 'red',
  warning: 'red',
  fatal: 'magenta',
  status: 'yellow',
  debug: 'brightBlack',
  server: 'brightBlack',
  optmzr: 'white',
  managr: 'green',
  optimize: 'magenta',
  listening: 'magenta'
};

var color = _lodash2['default'].memoize(function (name) {
  return _ansicolors2['default'][typeColors[name]] || _lodash2['default'].identity;
});

var type = _lodash2['default'].memoize(function (t) {
  return color(t)(_lodash2['default'].pad(t, 7).slice(0, 7));
});

var workerType = process.env.kbnWorkerType ? type(process.env.kbnWorkerType) + ' ' : '';

module.exports = (function (_LogFormat) {
  _inherits(KbnLoggerJsonFormat, _LogFormat);

  function KbnLoggerJsonFormat() {
    _classCallCheck(this, KbnLoggerJsonFormat);

    _get(Object.getPrototypeOf(KbnLoggerJsonFormat.prototype), 'constructor', this).apply(this, arguments);
  }

  _createClass(KbnLoggerJsonFormat, [{
    key: 'format',
    value: function format(data) {
      var time = color('time')((0, _moment2['default'])(data.timestamp).utc().format('HH:mm:ss.SSS'));
      var msg = data.error ? color('error')(data.error.stack) : color('message')(data.message);

      var tags = (0, _lodash2['default'])(data.tags).sortBy(function (tag) {
        if (color(tag) === _lodash2['default'].identity) return '2' + tag;
        if (_lodash2['default'].includes(statuses, tag)) return '0' + tag;
        return '1' + tag;
      }).reduce(function (s, t) {
        return s + ('[' + color(t)(t) + ']');
      }, '');

      return '' + workerType + type(data.type) + ' [' + time + '] ' + tags + ' ' + msg;
    }
  }]);

  return KbnLoggerJsonFormat;
})(_log_format2['default']);
