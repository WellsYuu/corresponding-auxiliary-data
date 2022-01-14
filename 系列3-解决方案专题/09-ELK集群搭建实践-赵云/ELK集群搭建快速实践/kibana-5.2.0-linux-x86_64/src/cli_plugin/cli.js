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

'use strict';

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { 'default': obj }; }

var _lodash = require('lodash');

var _lodash2 = _interopRequireDefault(_lodash);

var _utilsPackage_json = require('../utils/package_json');

var _utilsPackage_json2 = _interopRequireDefault(_utilsPackage_json);

var _cliCommand = require('../cli/command');

var _cliCommand2 = _interopRequireDefault(_cliCommand);

var _list = require('./list');

var _list2 = _interopRequireDefault(_list);

var _install = require('./install');

var _install2 = _interopRequireDefault(_install);

var _remove = require('./remove');

var _remove2 = _interopRequireDefault(_remove);

var argv = process.env.kbnWorkerArgv ? JSON.parse(process.env.kbnWorkerArgv) : process.argv.slice();
var program = new _cliCommand2['default']('bin/kibana-plugin');

program.version(_utilsPackage_json2['default'].version).description('The Kibana plugin manager enables you to install and remove plugins that ' + 'provide additional functionality to Kibana');

(0, _list2['default'])(program);
(0, _install2['default'])(program);
(0, _remove2['default'])(program);

program.command('help <command>').description('get the help for a specific command').action(function (cmdName) {
  var cmd = _lodash2['default'].find(program.commands, { _name: cmdName });
  if (!cmd) return program.error('unknown command ' + cmdName);
  cmd.help();
});

program.command('*', null, { noHelp: true }).action(function (cmd, options) {
  program.error('unknown command ' + cmd);
});

// check for no command name
var subCommand = argv[2] && !String(argv[2][0]).match(/^-|^\.|\//);
if (!subCommand) {
  program.defaultHelp();
}

program.parse(argv);
