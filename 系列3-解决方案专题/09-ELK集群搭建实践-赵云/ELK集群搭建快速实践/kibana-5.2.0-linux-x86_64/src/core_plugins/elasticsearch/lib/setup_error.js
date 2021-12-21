'use strict';

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { 'default': obj }; }

var _lodash = require('lodash');

var _lodash2 = _interopRequireDefault(_lodash);

var _util = require('util');

var _util2 = _interopRequireDefault(_util);

function SetupError(server, template, err) {
  var config = server.config().get();
  // don't override other setup errors
  if (err && err instanceof SetupError) return err;
  Error.captureStackTrace(this, this.constructor);
  this.name = this.constructor.name;
  this.message = _lodash2['default'].template(template)(config);
  if (err) {
    this.origError = err;
    if (err.stack) this.stack = err.stack;
  }
}
_util2['default'].inherits(SetupError, Error);
module.exports = SetupError;
