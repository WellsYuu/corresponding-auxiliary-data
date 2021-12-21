'use strict';

Object.defineProperty(exports, '__esModule', {
  value: true
});
exports.handleShortUrlError = handleShortUrlError;

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { 'default': obj }; }

var _boom = require('boom');

var _boom2 = _interopRequireDefault(_boom);

function handleShortUrlError(err) {
  if (err.isBoom) return err;
  if (err.status === 401) return _boom2['default'].unauthorized();
  if (err.status === 403) return _boom2['default'].forbidden();
  if (err.status === 404) return _boom2['default'].notFound();
  return _boom2['default'].badImplementation();
}
