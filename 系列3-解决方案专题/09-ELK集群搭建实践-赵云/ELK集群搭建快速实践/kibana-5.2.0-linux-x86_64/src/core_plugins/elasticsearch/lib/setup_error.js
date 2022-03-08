/*
 * Copyright 2021-2022 the original author or authors
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
