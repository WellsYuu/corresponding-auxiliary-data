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
import angular from 'angular';
import moment from 'moment';
import dateMath from '@elastic/datemath';
import 'ui/state_management/global_state';
import 'ui/config';
import EventsProvider from 'ui/events';
import UtilsDiffTimePickerValsProvider from 'ui/utils/diff_time_picker_vals';
import TimefilterLibDiffTimeProvider from 'ui/timefilter/lib/diff_time';
import TimefilterLibDiffIntervalProvider from 'ui/timefilter/lib/diff_interval';
import uiRoutes from 'ui/routes';
import uiModules from 'ui/modules';

uiRoutes
.addSetupWork(function (timefilter) {
  return timefilter.init();
});

uiModules
.get('kibana')
.service('timefilter', function (Private, globalState, $rootScope, config) {
  let Events = Private(EventsProvider);
  let diff = Private(UtilsDiffTimePickerValsProvider);

  function convertISO8601(stringTime) {
    let obj = moment(stringTime, 'YYYY-MM-DDTHH:mm:ss.SSSZ', true);
    return obj.isValid() ? obj : stringTime;
  }

  _.class(Timefilter).inherits(Events);
  function Timefilter() {
    Timefilter.Super.call(this);

    let self = this;
    let diffTime = Private(TimefilterLibDiffTimeProvider)(self);
    let diffInterval = Private(TimefilterLibDiffIntervalProvider)(self);

    self.enabled = false;

    self.init = _.once(function () {
      let timeDefaults = config.get('timepicker:timeDefaults');
      let refreshIntervalDefaults = config.get('timepicker:refreshIntervalDefaults');

      // These can be date math strings or moments.
      self.time = _.defaults(globalState.time || {}, timeDefaults);
      self.refreshInterval = _.defaults(globalState.refreshInterval || {}, refreshIntervalDefaults);

      globalState.on('fetch_with_changes', function () {
        // clone and default to {} in one
        let newTime = _.defaults({}, globalState.time, timeDefaults);
        let newRefreshInterval = _.defaults({}, globalState.refreshInterval, refreshIntervalDefaults);

        if (newTime) {
          if (newTime.to) newTime.to = convertISO8601(newTime.to);
          if (newTime.from) newTime.from = convertISO8601(newTime.from);
        }

        self.time = newTime;
        self.refreshInterval = newRefreshInterval;
      });
    });

    $rootScope.$$timefilter = self;

    $rootScope.$watchMulti([
      '$$timefilter.time',
      '$$timefilter.time.from',
      '$$timefilter.time.to',
      '$$timefilter.time.mode'
    ], diffTime);

    $rootScope.$watchMulti([
      '$$timefilter.refreshInterval',
      '$$timefilter.refreshInterval.pause',
      '$$timefilter.refreshInterval.value'
    ], diffInterval);
  }

  Timefilter.prototype.get = function (indexPattern) {
    let filter;
    let timefield = indexPattern.timeFieldName && _.find(indexPattern.fields, {name: indexPattern.timeFieldName});

    if (timefield) {
      let bounds = this.getBounds();
      filter = {range : {}};
      filter.range[timefield.name] = {
        gte: bounds.min.valueOf(),
        lte: bounds.max.valueOf(),
        format: 'epoch_millis'
      };
    }

    return filter;
  };

  Timefilter.prototype.getBounds = function (timefield) {
    return {
      min: dateMath.parse(this.time.from),
      max: dateMath.parse(this.time.to, true)
    };
  };

  Timefilter.prototype.getActiveBounds = function () {
    if (this.enabled) return this.getBounds();
  };

  return new Timefilter();
});
