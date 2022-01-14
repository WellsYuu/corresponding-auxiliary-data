/*
 * Copyright 2021-2022 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

// The default ciphers in node 0.12.x include insecure ciphers, so until
// we enforce a more recent version of node, we craft our own list
// @see https://github.com/nodejs/node/blob/master/src/node_constants.h#L8-L28
'use strict';

Object.defineProperty(exports, '__esModule', {
  value: true
});
exports['default'] = ['ECDHE-RSA-AES128-GCM-SHA256', 'ECDHE-ECDSA-AES128-GCM-SHA256', 'ECDHE-RSA-AES256-GCM-SHA384', 'ECDHE-ECDSA-AES256-GCM-SHA384', 'DHE-RSA-AES128-GCM-SHA256', 'ECDHE-RSA-AES128-SHA256', 'DHE-RSA-AES128-SHA256', 'ECDHE-RSA-AES256-SHA384', 'DHE-RSA-AES256-SHA384', 'ECDHE-RSA-AES256-SHA256', 'DHE-RSA-AES256-SHA256', 'HIGH', '!aNULL', '!eNULL', '!EXPORT', '!DES', '!RC4', '!MD5', '!PSK', '!SRP', '!CAMELLIA'].join(':');
module.exports = exports['default'];
