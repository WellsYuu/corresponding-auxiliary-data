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

var Promise = require('bluebird');
var _ = require('lodash');

/* @param {Array} args
 * - args[0] must be a seriesList

 * @params {Function} fn - Function to apply to each series in the seriesList
 * @return {seriesList}
 */

module.exports = function alter(args, fn) {
  // In theory none of the args should ever be promises. This is probably a waste.
  return Promise.all(args).then(function (args) {

    var seriesList = args.shift();

    if (seriesList.type !== 'seriesList') {
      throw new Error('args[0] must be a seriesList');
    }

    var list = _.chain(seriesList.list).map(function (series) {
      return fn.apply(this, [series].concat(args));
    }).flatten().value();

    seriesList.list = list;
    return seriesList;
  })['catch'](function (e) {
    throw e;
  });
};
