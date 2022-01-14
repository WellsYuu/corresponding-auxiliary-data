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

import angular from 'angular';
import $ from 'jquery';
import _ from 'lodash';
import EventsProvider from 'ui/events';
export default function ReflowWatcherService(Private, $rootScope, $http) {

  let EventEmitter = Private(EventsProvider);
  let $body = $(document.body);
  let $window = $(window);

  let MOUSE_EVENTS = 'mouseup';
  let WINDOW_EVENTS = 'resize';

  _.class(ReflowWatcher).inherits(EventEmitter);
  /**
   * Watches global activity which might hint at a change in the content, which
   * in turn provides a hint to resizers that they should check their size
   */
  function ReflowWatcher() {
    ReflowWatcher.Super.call(this);

    // bound version of trigger that can be used as a handler
    this.trigger = _.bind(this.trigger, this);
    this._emitReflow = _.bind(this._emitReflow, this);

    // list of functions to call that will unbind our watchers
    this._unwatchers = [
      $rootScope.$watchCollection(function () {
        return $http.pendingRequests;
      }, this.trigger)
    ];

    $body.on(MOUSE_EVENTS, this.trigger);
    $window.on(WINDOW_EVENTS, this.trigger);
  }

  /**
   * Simply emit reflow, but in a way that can be bound and passed to
   * other functions. Using _.bind caused extra arguments to be added, and
   * then emitted to other places. No Bueno
   *
   * @return {void}
   */
  ReflowWatcher.prototype._emitReflow = function () {
    this.emit('reflow');
  };

  /**
   * Emit the "reflow" event in the next tick of the digest cycle
   * @return {void}
   */
  ReflowWatcher.prototype.trigger = function () {
    $rootScope.$evalAsync(this._emitReflow);
  };

  /**
   * Signal to the ReflowWatcher that it should clean up it's listeners
   * @return {void}
   */
  ReflowWatcher.prototype.destroy = function () {
    $body.off(MOUSE_EVENTS, this.trigger);
    $window.off(WINDOW_EVENTS, this.trigger);
    _.callEach(this._unwatchers);
  };

  return new ReflowWatcher();
};
