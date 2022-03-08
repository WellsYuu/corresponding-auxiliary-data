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

/**
 * Regexp portion that matches our number
 *
 * supports:
 *   -100
 *   -100.0
 *   0
 *   0.10
 *   Infinity
 *   -Infinity
 *
 * @type {String}
 */
let _RE_NUMBER = '(\\-?(?:\\d+(?:\\.\\d+)?|Infinity))';

/**
 * Regexp for the interval notation
 *
 * supports:
 *   [num, num]
 *   ( num , num ]
 *   [Infinity,num)
 *
 * @type {RegExp}
 */
let RANGE_RE = new RegExp('^\\s*([\\[|\\(])\\s*' + _RE_NUMBER + '\\s*,\\s*' + _RE_NUMBER + '\\s*([\\]|\\)])\\s*$');

function parse(input) {

  let match = String(input).match(RANGE_RE);
  if (!match) {
    throw new TypeError('expected input to be in interval notation eg. (100, 200]');
  }

  return new Range(
    match[1] === '[',
    parseFloat(match[2]),
    parseFloat(match[3]),
    match[4] === ']'
  );
}

function Range(/* minIncl, min, max, maxIncl */) {
  let args = _.toArray(arguments);
  if (args[1] > args[2]) args.reverse();

  this.minInclusive = args[0];
  this.min = args[1];
  this.max = args[2];
  this.maxInclusive = args[3];
}

Range.prototype.within = function (n) {
  if (this.min === n && !this.minInclusive) return false;
  if (this.min > n) return false;

  if (this.max === n && !this.maxInclusive) return false;
  if (this.max < n) return false;

  return true;
};

export default parse;

