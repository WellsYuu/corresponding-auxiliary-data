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

var _ = require('lodash');
var glob = require('glob');
var path = require('path');
var processFunctionDefinition = require('./process_function_definition');

module.exports = function (directory) {

  function getTuple(directory, name) {
    var func = require('../' + directory + '/' + name);
    return [name, require('../' + directory + '/' + name)];
  }

  // Get a list of all files and use the filename as the object key
  var files = _.map(glob.sync(path.resolve(__dirname, '../' + directory + '/*.js')), function (file) {
    var name = file.substring(file.lastIndexOf('/') + 1, file.lastIndexOf('.'));
    return getTuple(directory, name);
  });

  // Get a list of all directories with an index.js, use the directory name as the key in the object
  var directories = _.chain(glob.sync(path.resolve(__dirname, '../' + directory + '/*/index.js'))).filter(function (file) {
    return file.match(/__test__/) == null;
  }).map(function (file) {
    var parts = file.split('/');
    var name = parts[parts.length - 2];
    return getTuple(directory, parts[parts.length - 2]);
  }).value();

  var functions = _.zipObject(files.concat(directories));

  _.each(functions, function (func) {
    _.assign(functions, processFunctionDefinition(func));
  });

  return functions;
};
