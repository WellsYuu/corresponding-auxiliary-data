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

import d3 from 'd3';
import _ from 'lodash';
import VislibComponentsColorSeedColorsProvider from './seed_colors';
export default function ColorPaletteUtilService(Private) {

  const seedColors = Private(VislibComponentsColorSeedColorsProvider);


  /*
   * Generates an array of hex colors the length of the input number.
   * If the number is greater than the length of seed colors available,
   * new colors are generated up to the value of the input number.
   */

  const offset = 300; // Hue offset to start at

  const fraction = function (goal) {
    const walkTree = function (numerator, denominator, bytes) {
      if (bytes.length) {
        return walkTree(
          (numerator * 2) + (bytes.pop() ? 1 : -1),
          denominator * 2,
          bytes
        );

      } else {
        return numerator / denominator;
      }
    };

    const b = (goal + 2)
      .toString(2)
      .split('')
      .map(function (num) {
        return parseInt(num, 10);
      });
    b.shift();

    return walkTree(1, 2, b);

  };

  return function (num) {
    if (!_.isNumber(num)) {
      throw new TypeError('ColorPaletteUtilService expects a number');
    }

    const colors = seedColors;

    const seedLength = seedColors.length;

    _.times(num - seedLength, function (i) {
      colors.push(d3.hsl((fraction(i + seedLength + 1) * 360 + offset) % 360, 0.5, 0.5).toString());
    });

    return colors;

  };

};
