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

function toPojo(obj) {
  return JSON.parse(JSON.stringify(obj));
}

function replacer(match, group) {
  return new Array(group.length + 1).join('X');
}

function apply(obj, key, action) {
  for (var k in obj) {
    if (obj.hasOwnProperty(k)) {
      var val = obj[k];
      if (k === key) {
        if (action === 'remove') {
          delete obj[k];
        } else if (action === 'censor' && typeof val === 'object') {
          delete obj[key];
        } else if (action === 'censor') {
          obj[k] = ('' + val).replace(/./g, 'X');
        } else if (/\/.+\//.test(action)) {
          var matches = action.match(/\/(.+)\//);
          if (matches) {
            var regex = new RegExp(matches[1]);
            obj[k] = ('' + val).replace(regex, replacer);
          }
        }
      } else if (typeof val === 'object') {
        val = apply(val, key, action);
      }
    }
  }
  return obj;
}

module.exports = function (obj, actionsByKey) {
  return Object.keys(actionsByKey).reduce(function (output, key) {
    return apply(output, key, actionsByKey[key]);
  }, toPojo(obj));
};
