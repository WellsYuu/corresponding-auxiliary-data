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

module.exports = function (_ref) {
  var env = _ref.env;
  var bundle = _ref.bundle;

  var pluginSlug = env.pluginInfo.sort().map(function (p) {
    return ' *  - ' + p;
  }).join('\n');

  var requires = bundle.modules.map(function (m) {
    return 'require(\'' + m + '\');';
  }).join('\n');

  return '\n/**\n * Test entry file\n *\n * This is programatically created and updated, do not modify\n *\n * context: ' + JSON.stringify(env.context) + '\n * includes code from:\n' + pluginSlug + '\n *\n */\n\nrequire(\'ui/chrome\');\n' + requires + '\nrequire(\'ui/chrome\').bootstrap(/* xoxo */);\n\n';
};
