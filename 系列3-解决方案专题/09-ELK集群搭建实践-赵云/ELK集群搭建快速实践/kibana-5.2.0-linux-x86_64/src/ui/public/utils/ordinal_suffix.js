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
// adopted from http://stackoverflow.com/questions/3109978/php-display-number-with-ordinal-suffix
export default function addOrdinalSuffix(num) {
  return num + '' + suffix(num);
};

function suffix(num) {
  let int = Math.floor(parseFloat(num));

  let hunth = int % 100;
  if (hunth >= 11 && hunth <= 13) return 'th';

  let tenth = int % 10;
  if (tenth === 1) return 'st';
  if (tenth === 2) return 'nd';
  if (tenth === 3) return 'rd';
  return 'th';
}
