'use strict';

Object.defineProperty(exports, '__esModule', {
  value: true
});

var _createClass = (function () { function defineProperties(target, props) { for (var i = 0; i < props.length; i++) { var descriptor = props[i]; descriptor.enumerable = descriptor.enumerable || false; descriptor.configurable = true; if ('value' in descriptor) descriptor.writable = true; Object.defineProperty(target, descriptor.key, descriptor); } } return function (Constructor, protoProps, staticProps) { if (protoProps) defineProperties(Constructor.prototype, protoProps); if (staticProps) defineProperties(Constructor, staticProps); return Constructor; }; })();

var _get = function get(_x, _x2, _x3) { var _again = true; _function: while (_again) { var object = _x, property = _x2, receiver = _x3; _again = false; if (object === null) object = Function.prototype; var desc = Object.getOwnPropertyDescriptor(object, property); if (desc === undefined) { var parent = Object.getPrototypeOf(object); if (parent === null) { return undefined; } else { _x = parent; _x2 = property; _x3 = receiver; _again = true; desc = parent = undefined; continue _function; } } else if ('value' in desc) { return desc.value; } else { var getter = desc.get; if (getter === undefined) { return undefined; } return getter.call(receiver); } } };

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { 'default': obj }; }

function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError('Cannot call a class as a function'); } }

function _inherits(subClass, superClass) { if (typeof superClass !== 'function' && superClass !== null) { throw new TypeError('Super expression must either be null or a function, not ' + typeof superClass); } subClass.prototype = Object.create(superClass && superClass.prototype, { constructor: { value: subClass, enumerable: false, writable: true, configurable: true } }); if (superClass) Object.setPrototypeOf ? Object.setPrototypeOf(subClass, superClass) : subClass.__proto__ = superClass; }

var _stream = require('stream');

var _stream2 = _interopRequireDefault(_stream);

var _lodash = require('lodash');

function doTagsMatch(event, tags) {
  return (0, _lodash.isEqual)((0, _lodash.get)(event, 'tags'), tags);
}

// converts the given event into a debug log if it's an error of the given type
function downgradeIfErrorMatches(errorType, event) {
  var isClientError = doTagsMatch(event, ['connection', 'client', 'error']);
  var matchesErrorType = isClientError && (0, _lodash.get)(event, 'data.errno') === errorType;

  if (!matchesErrorType) return null;

  var errorTypeTag = errorType.toLowerCase();

  return {
    event: 'log',
    pid: event.pid,
    timestamp: event.timestamp,
    tags: ['debug', 'connection', errorTypeTag],
    data: errorType + ': Socket was closed by the client (probably the browser) before it could be read completely'
  };
}

var LogInterceptor = (function (_Stream$Transform) {
  _inherits(LogInterceptor, _Stream$Transform);

  function LogInterceptor() {
    _classCallCheck(this, LogInterceptor);

    _get(Object.getPrototypeOf(LogInterceptor.prototype), 'constructor', this).call(this, {
      readableObjectMode: true,
      writableObjectMode: true
    });
  }

  /**
   *  Since the upgrade to hapi 14, any socket read
   *  error is surfaced as a generic "client error"
   *  but "ECONNRESET" specifically is not useful for the
   *  logs unless you are trying to debug edge-case behaviors.
   *
   *  For that reason, we downgrade this from error to debug level
   *
   *  @param {object} - log event
   */

  _createClass(LogInterceptor, [{
    key: 'downgradeIfEconnreset',
    value: function downgradeIfEconnreset(event) {
      return downgradeIfErrorMatches('ECONNRESET', event);
    }

    /**
     *  Since the upgrade to hapi 14, any socket write
     *  error is surfaced as a generic "client error"
     *  but "EPIPE" specifically is not useful for the
     *  logs unless you are trying to debug edge-case behaviors.
     *
     *  For that reason, we downgrade this from error to debug level
     *
     *  @param {object} - log event
     */
  }, {
    key: 'downgradeIfEpipe',
    value: function downgradeIfEpipe(event) {
      return downgradeIfErrorMatches('EPIPE', event);
    }

    /**
     *  Since the upgrade to hapi 14, any socket write
     *  error is surfaced as a generic "client error"
     *  but "ECANCELED" specifically is not useful for the
     *  logs unless you are trying to debug edge-case behaviors.
     *
     *  For that reason, we downgrade this from error to debug level
     *
     *  @param {object} - log event
     */
  }, {
    key: 'downgradeIfEcanceled',
    value: function downgradeIfEcanceled(event) {
      return downgradeIfErrorMatches('ECANCELED', event);
    }
  }, {
    key: '_transform',
    value: function _transform(event, enc, next) {
      var downgraded = this.downgradeIfEconnreset(event) || this.downgradeIfEpipe(event) || this.downgradeIfEcanceled(event);

      this.push(downgraded || event);
      next();
    }
  }]);

  return LogInterceptor;
})(_stream2['default'].Transform);

exports.LogInterceptor = LogInterceptor;
;
