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

import _ from 'lodash';
import uiRegistry from 'ui/registry/_registry';

export default uiRegistry({
  name: 'fieldFormats',
  index: ['id'],
  group: ['fieldType'],

  constructor: function (config) {
    let self = this;
    let defaultMap;

    function init() {
      config.watch('format:defaultTypeMap', parseDefaultTypeMap);
    }


    /**
     * Get the id of the default type for this field type
     * using the format:defaultTypeMap config map
     *
     * @param  {String} fieldType - the field type
     * @return {String}
     */
    self.getDefaultConfig = function (fieldType) {
      return defaultMap[fieldType] || defaultMap._default_;
    };

    /**
     * Get a FieldFormat type (class) by it's id.
     *
     * @param  {String} formatId - the format id
     * @return {Function}
     */
    self.getType = function (formatId) {
      return self.byId[formatId];
    };

    /**
     * Get the default FieldFormat type (class) for
     * a field type, using the format:defaultTypeMap.
     *
     * @param  {String} fieldType
     * @return {Function}
     */
    self.getDefaultType = function (fieldType) {
      return self.byId[self.getDefaultConfig(fieldType).id];
    };

    /**
     * Get the singleton instance of the FieldFormat type by it's id.
     *
     * @param  {String} formatId
     * @return {FieldFormat}
     */
    self.getInstance = _.memoize(function (formatId) {
      let FieldFormat = self.byId[formatId];
      return new FieldFormat();
    });

    /**
     * Get the default fieldFormat instance for a field format.
     *
     * @param  {String} fieldType
     * @return {FieldFormat}
     */
    self.getDefaultInstance = _.memoize(function (fieldType) {
      let conf = self.getDefaultConfig(fieldType);
      let FieldFormat = self.byId[conf.id];
      return new FieldFormat(conf.params);
    });


    function parseDefaultTypeMap(value) {
      defaultMap = value;
      _.forOwn(self, function (fn) {
        if (_.isFunction(fn) && fn.cache) {
          // clear all memoize caches
          fn.cache = new _.memoize.Cache();
        }
      });
    }

    init();
  }
});
