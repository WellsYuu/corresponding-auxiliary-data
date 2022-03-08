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

import _ from 'lodash';
import angular from 'angular';

export default function (target, source) {

  let diff = {};

  /**
   * Filter the private vars
   * @param {string} key The keys
   * @returns {boolean}
   */
  let filterPrivateAndMethods = function (obj) {
    return function (key) {
      if (_.isFunction(obj[key])) return false;
      if (key.charAt(0) === '$') return false;
      return key.charAt(0) !== '_';
    };
  };

  let targetKeys = _.keys(target).filter(filterPrivateAndMethods(target));
  let sourceKeys = _.keys(source).filter(filterPrivateAndMethods(source));

  // Find the keys to be removed
  diff.removed = _.difference(targetKeys, sourceKeys);

  // Find the keys to be added
  diff.added = _.difference(sourceKeys, targetKeys);

  // Find the keys that will be changed
  diff.changed = _.filter(sourceKeys, function (key) {
    return !angular.equals(target[key], source[key]);
  });

  // Make a list of all the keys that are changing
  diff.keys = _.union(diff.changed, diff.removed, diff.added);

  // Remove all the keys
  _.each(diff.removed, function (key) {
    delete target[key];
  });

  // Assign the changed to the source to the target
  _.assign(target, _.pick(source, diff.changed));
  // Assign the added to the source to the target
  _.assign(target, _.pick(source, diff.added));

  return diff;

};
