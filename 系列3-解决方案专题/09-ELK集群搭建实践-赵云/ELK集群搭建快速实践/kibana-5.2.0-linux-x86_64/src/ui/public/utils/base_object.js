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
import rison from 'rison-node';
import angular from 'angular';

function BaseObject(attributes) {
  // Set the attributes or default to an empty object
  _.assign(this, attributes);
}

/**
 * Returns the attirbutes for the objct
 * @returns {object}
 */
BaseObject.prototype.toObject = function () {
  // return just the data.
  return _.omit(this, function (value, key) {
    return key.charAt(0) === '$' || key.charAt(0) === '_' || _.isFunction(value);
  });
};

/**
 * Serialize the model to RISON
 * @returns {string}
 */
BaseObject.prototype.toRISON = function () {
  // Use Angular to remove the private vars, and JSON.stringify to serialize
  return rison.encode(JSON.parse(angular.toJson(this)));
};

/**
 * Serialize the model to JSON
 * @returns {object}
 */
BaseObject.prototype.toJSON = function () {
  return this.toObject();
};

export default BaseObject;
