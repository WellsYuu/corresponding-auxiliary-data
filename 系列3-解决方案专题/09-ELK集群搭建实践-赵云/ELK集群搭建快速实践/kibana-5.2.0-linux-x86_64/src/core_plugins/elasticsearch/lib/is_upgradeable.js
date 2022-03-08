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

var _semver = require('semver');

var _semver2 = _interopRequireDefault(_semver);

var rcVersionRegex = /(\d+\.\d+\.\d+)\-rc(\d+)/i;

module.exports = function (server, doc) {
  var config = server.config();
  if (/alpha|beta|snapshot/i.test(doc._id)) return false;
  if (!doc._id) return false;
  if (doc._id === config.get('pkg.version')) return false;

  var packageRcRelease = Infinity;
  var rcRelease = Infinity;
  var packageVersion = config.get('pkg.version');
  var version = doc._id;
  var matches = doc._id.match(rcVersionRegex);
  var packageMatches = config.get('pkg.version').match(rcVersionRegex);

  if (matches) {
    version = matches[1];
    rcRelease = parseInt(matches[2], 10);
  }

  if (packageMatches) {
    packageVersion = packageMatches[1];
    packageRcRelease = parseInt(packageMatches[2], 10);
  }

  try {
    if (_semver2['default'].gte(version, packageVersion) && rcRelease >= packageRcRelease) return false;
  } catch (e) {
    return false;
  }
  return true;
};
