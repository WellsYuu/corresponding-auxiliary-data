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

import moment from 'moment';
import dateMath from '@elastic/datemath';
export default function () {

  let unitsDesc = dateMath.unitsDesc;
  let largeMax = unitsDesc.indexOf('M');

  /**
   * Convert a moment.duration into an es
   * compatible expression, and provide
   * associated metadata
   *
   * @param  {moment.duration} duration
   * @return {object}
   */
  function esDuration(duration) {
    for (let i = 0; i < unitsDesc.length; i++) {
      let unit = unitsDesc[i];
      let val = duration.as(unit);
      // find a unit that rounds neatly
      if (val >= 1 && Math.floor(val) === val) {

        // if the unit is "large", like years, but
        // isn't set to 1 ES will puke. So keep going until
        // we get out of the "large" units
        if (i <= largeMax && val !== 1) {
          continue;
        }

        return {
          value: val,
          unit: unit,
          expression: val + unit
        };
      }
    }

    let ms = duration.as('ms');
    return {
      value: ms,
      unit: 'ms',
      expression: ms + 'ms'
    };
  }


  return esDuration;
};
