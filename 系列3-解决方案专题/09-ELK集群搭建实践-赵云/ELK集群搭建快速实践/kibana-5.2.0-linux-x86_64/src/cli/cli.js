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

var _utilsPackage_json = require('../utils/package_json');

var _utilsPackage_json2 = _interopRequireDefault(_utilsPackage_json);

var _command = require('./command');

var _command2 = _interopRequireDefault(_command);

var _serveServe = require('./serve/serve');

var _serveServe2 = _interopRequireDefault(_serveServe);

var argv = process.env.kbnWorkerArgv ? JSON.parse(process.env.kbnWorkerArgv) : process.argv.slice();
var program = new _command2['default']('bin/kibana');

program.version(_utilsPackage_json2['default'].version).description('Kibana is an open source (Apache Licensed), browser ' + 'based analytics and search dashboard for Elasticsearch.');

// attach commands
(0, _serveServe2['default'])(program);

program.command('help <command>').description('Get the help for a specific command').action(function (cmdName) {
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
  if (_lodash2['default'].intersection(argv.slice(2), ['-h', '--help']).length) {
    program.defaultHelp();
  } else {
    argv.splice(2, 0, ['serve']);
  }
}

program.parse(argv);
