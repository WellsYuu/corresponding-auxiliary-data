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
import moment from 'moment';
import 'ui/field_format_editor/pattern/pattern';
import IndexPatternsFieldFormatProvider from 'ui/index_patterns/_field_format/field_format';
import BoundToConfigObjProvider from 'ui/bound_to_config_obj';
import dateTemplate from 'ui/stringify/editors/date.html';
export default function DateTimeFormatProvider(Private) {
  let FieldFormat = Private(IndexPatternsFieldFormatProvider);
  let BoundToConfigObj = Private(BoundToConfigObjProvider);


  _.class(DateTime).inherits(FieldFormat);
  function DateTime(params) {
    DateTime.Super.call(this, params);
  }

  DateTime.id = 'date';
  DateTime.title = 'Date';
  DateTime.fieldType = 'date';

  DateTime.paramDefaults = new BoundToConfigObj({
    pattern: '=dateFormat',
    timezone: '=dateFormat:tz'
  });

  DateTime.editor = {
    template: dateTemplate,
    controllerAs: 'cntrl',
    controller: function ($interval, $scope) {
      let self = this;
      self.sampleInputs = [
        Date.now(),
        +moment().startOf('year'),
        +moment().endOf('year')
      ];

      $scope.$on('$destroy', $interval(function () {
        self.sampleInputs[0] = Date.now();
      }, 1000));
    }
  };

  DateTime.prototype._convert = function (val) {
    // don't give away our ref to converter so
    // we can hot-swap when config changes
    let pattern = this.param('pattern');
    let timezone = this.param('timezone');

    let timezoneChanged = this._timeZone !== timezone;
    let datePatternChanged = this._memoizedPattern !== pattern;
    if (timezoneChanged || datePatternChanged) {
      this._timeZone = timezone;
      this._memoizedPattern = pattern;

      this._memoizedConverter = _.memoize(function converter(val) {
        if (val === null || val === undefined) {
          return '-';
        }
        return moment(val).format(pattern);
      });
    }

    return this._memoizedConverter(val);
  };

  return DateTime;
};
