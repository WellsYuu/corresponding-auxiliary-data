'use strict';

Object.defineProperty(exports, '__esModule', {
  value: true
});
exports.isSystemApiRequest = isSystemApiRequest;
var SYSTEM_API_HEADER_NAME = 'kbn-system-api';

/**
 * Checks on the *server-side*, if an HTTP request is a system API request
 *
 * @param request HAPI request object
 * @return        true if request is a system API request; false, otherwise
 */

function isSystemApiRequest(request) {
  return !!request.headers[SYSTEM_API_HEADER_NAME];
}
