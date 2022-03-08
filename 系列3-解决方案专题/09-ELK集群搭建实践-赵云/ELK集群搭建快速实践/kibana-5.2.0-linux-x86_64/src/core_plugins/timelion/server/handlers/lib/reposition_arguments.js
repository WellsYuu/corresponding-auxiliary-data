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

// Applies to unresolved arguments in the AST
module.exports = function repositionArguments(functionDef, unorderedArgs) {
  var args = [];

  _.each(unorderedArgs, function (unorderedArg, i) {
    var argDef;
    var targetIndex;
    var value;
    var storeAsArray;

    if (_.isObject(unorderedArg) && unorderedArg.type === 'namedArg') {
      argDef = functionDef.argsByName[unorderedArg.name];

      if (!argDef) {
        if (functionDef.extended) {
          var namesIndex = functionDef.args.length;
          targetIndex = functionDef.args.length + 1;

          args[namesIndex] = args[namesIndex] || [];
          args[namesIndex].push(unorderedArg.name);

          argDef = functionDef.extended;
          storeAsArray = true;
        }
      } else {
        targetIndex = _.findIndex(functionDef.args, function (orderedArg) {
          return unorderedArg.name === orderedArg.name;
        });
        storeAsArray = argDef.multi;
      }
      value = unorderedArg.value;
    } else {
      argDef = functionDef.args[i];
      storeAsArray = argDef.multi;
      targetIndex = i;
      value = unorderedArg;
    }

    if (!argDef) throw new Error('Unknown argument to ' + functionDef.name + ': ' + (unorderedArg.name || '#' + i));

    if (storeAsArray) {
      args[targetIndex] = args[targetIndex] || [];
      args[targetIndex].push(value);
    } else {
      args[targetIndex] = value;
    }
  });

  return args;
};
