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
  _.mixin(_, {


    /**
     * Checks to see if an input value is number-like, this
     * includes strings that parse into valid numbers and objects
     * that don't have a type of number but still parse properly
     * via-some sort of valueOf magic
     *
     * @param  {any} v - the value to check
     * @return {Boolean}
     */
    isNumeric: function (v) {
      return !_.isNaN(v) && (typeof v === 'number' || (!_.isArray(v) && !_.isNaN(parseFloat(v))));
    },

  });
};
