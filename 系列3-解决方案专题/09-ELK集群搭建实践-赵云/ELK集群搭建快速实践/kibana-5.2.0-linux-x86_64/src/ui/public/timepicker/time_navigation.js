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

import moment from 'moment';

export default {
  // travel forward in time based on the interval between from and to
  stepForward({min, max}) {
    const diff = max.diff(min);
    return {
      from: max.toISOString(),
      to: moment(max).add(diff).toISOString()
    };
  },

  // travel backwards in time based on the interval between from and to
  stepBackward({min, max}) {
    const diff = max.diff(min);
    return {
      from: moment(min).subtract(diff).toISOString(),
      to: min.toISOString()
    };
  },

  // zoom out, doubling the difference between start and end, keeping the same time range center
  zoomOut({min, max}) {
    const diff = max.diff(min);
    return {
      from: moment(min).subtract(diff / 2).toISOString(),
      to: moment(max).add(diff / 2).toISOString()
    };
  },

  // zoom in, halving the difference between start and end, keeping the same time range center
  zoomIn({min, max}) {
    const diff = max.diff(min);
    return {
      from: moment(min).add(diff / 4).toISOString(),
      to: moment(max).subtract(diff / 4).toISOString()
    };
  }
};
