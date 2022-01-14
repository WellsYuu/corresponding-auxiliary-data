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

export default function (indexPattern) {
  const dateScripts = {};
  const scripts = {
    __dayOfMonth:   'dayOfMonth',
    __dayOfWeek:    'dayOfWeek',
    __dayOfYear:    'dayOfYear',
    __hourOfDay:    'hourOfDay',
    __minuteOfDay:  'minuteOfDay',
    __minuteOfHour: 'minuteOfHour',
    __monthOfYear:  'monthOfYear',
    __weekOfYear:   'weekOfWeekyear',
    __year:         'year'
  };

  _.each(indexPattern.fields.byType.date, function (field) {
    if (field.indexed) {
      _.each(scripts, function (value, key) {
        dateScripts[field.name + '.' + key] = 'doc["' + field.name + '"].date.' + value;
      });
    }
  });

  return dateScripts;
};
