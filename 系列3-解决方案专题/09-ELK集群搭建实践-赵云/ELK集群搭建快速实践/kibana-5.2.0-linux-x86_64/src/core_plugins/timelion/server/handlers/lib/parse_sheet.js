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

var fs = require('fs');
var path = require('path');
var _ = require('lodash');
var grammar = fs.readFileSync(path.resolve(__dirname, '../../../public/chain.peg'), 'utf8');
var PEG = require('pegjs');
var Parser = PEG.buildParser(grammar);

module.exports = function parseSheet(sheet) {
  return _.map(sheet, function (plot) {
    try {
      return Parser.parse(plot).tree;
    } catch (e) {
      var message;
      if (e.expected) {
        throw new Error('Expected: ' + e.expected[0].description + ' @ character ' + e.column);
      } else {
        throw e;
      }
    }
  });
};
