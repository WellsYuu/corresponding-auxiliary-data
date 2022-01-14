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

export default function (_) {
  _.mixin({

    /**
     * Create a method that wraps another method which expects a callback as it's last
     * argument. The wrapper method will call the wrapped function only once (the first
     * time it is called), but will always call the callbacks passed to it. This has a
     * similar effect to calling a promise-returning function that is wrapped with _.once
     * but can be used outside of angular.
     *
     * @param  {Function} fn - the function that should only be executed once and accepts
     *                       a callback as it's last arg
     * @return {Function} - the wrapper method
     */
    onceWithCb: function (fn) {
      const callbacks = [];

      // on initial flush, call the init function, but ensure
      // that it only happens once
      let flush = _.once(function (cntx, args) {
        args.push(function finishedOnce() {
          // override flush to simply schedule an asynchronous clear
          flush = function () {
            setTimeout(function () {
              _.callEach(callbacks.splice(0));
            }, 0);
          };

          flush();
        });

        fn.apply(cntx, args);
      });

      return function runOnceWithCb() {
        let args = [].slice.call(arguments, 0);
        const cb = args[args.length - 1];

        if (typeof cb === 'function') {
          callbacks.push(cb);
          // trim the arg list so the other callback can
          // be pushed if needed
          args = args.slice(0, -1);
        }

        // always call flush, it might not do anything
        flush(this, args);
      };
    },


    /**
     * Call all of the function in an array
     *
     * @param  {array[functions]} arr
     * @return {undefined}
     */
    callEach: function (arr) {
      return _.map(arr, function (fn) {
        return _.isFunction(fn) ? fn() : undefined;
      });
    }

  });
};
