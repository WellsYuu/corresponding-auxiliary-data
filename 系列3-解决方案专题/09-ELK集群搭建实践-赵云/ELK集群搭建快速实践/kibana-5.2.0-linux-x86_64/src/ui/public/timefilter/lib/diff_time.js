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
import UtilsDiffTimePickerValsProvider from 'ui/utils/diff_time_picker_vals';
export default function diffTimeProvider(Private) {
  let diff = Private(UtilsDiffTimePickerValsProvider);

  return function (self) {
    let oldTime = _.clone(self.time);
    return function () {
      if (diff(self.time, oldTime)) {
        self.emit('update');
        self.emit('fetch');
      }
      oldTime = _.clone(self.time);
    };
  };
};
