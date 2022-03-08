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

export default function (_) {
  return _.mixin(_, {

    /**
     * Flatten an object into a single-level object.
     * NOTE: The flatten behavior here works if you don't need to keep a reference to the original value
     *
     * set flattenArrays to traverse into arrays and create properties like:
     *  {
     *    'users.0.name': 'username1',
     *    'users.1.name': 'username2',
     *    'users.2.name': 'username3',
     *  }
     *
     * @param  {string} dot - the seperator for keys, '.' is generally preferred
     * @param  {object} nestedObj - the object to flatten
     * @param  {Boolean} flattenArrays - should arrays be travered or left alone?
     * @return {object}
     */
    flattenWith: function (dot, nestedObj, flattenArrays) {
      const stack = []; // track key stack
      const flatObj = {};

      (function flattenObj(obj) {
        _.keys(obj).forEach(function (key) {
          stack.push(key);
          if (!flattenArrays && _.isArray(obj[key])) flatObj[stack.join(dot)] = obj[key];
          else if (_.isObject(obj[key])) flattenObj(obj[key]);
          else flatObj[stack.join(dot)] = obj[key];
          stack.pop();
        });
      }(nestedObj));

      return flatObj;
    }

  });
};
