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

export default function (_) {

  const DOT_PREFIX_RE = /(.).+?\./g;

  _.mixin({

    /**
     * Convert a value to a presentable string
     * @param  {any} val - the value to transform
     * @return {string}
     */
    asPrettyString: function (val) {
      if (val === null || val === undefined) return ' - ';
      switch (typeof val) {
        case 'string': return val;
        case 'object': return JSON.stringify(val, null, '  ');
        default: return '' + val;
      }
    },

    /**
     * Convert a dot.notated.string into a short
     * version (d.n.string)
     *
     * @param {string} str - the long string to convert
     * @return {string}
     */
    shortenDottedString: function (input) {
      return typeof input !== 'string' ? input : input.replace(DOT_PREFIX_RE, '$1.');
    },

    /**
     * Parse a comma-seperated list into an array
     * efficiently, or just return if already an array
     *
     * @param {string|array} input  - the comma-seperated list
     * @return {array}
     */
    commaSeperatedList: function (input) {
      if (_.isArray(input)) return input;

      const source = String(input || '').split(',');
      const list = [];
      while (source.length) {
        const item = source.shift().trim();
        if (item) list.push(item);
      }

      return list;
    }

  });
};
