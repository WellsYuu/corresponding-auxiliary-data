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

import Notifier from 'ui/notify/notifier';

import ErrorHandlersProvider from '../../_error_handlers';

export default function RequestErrorHandlerFactory(Private) {
  const errHandlers = Private(ErrorHandlersProvider);

  const notify = new Notifier({
    location: 'Courier Fetch Error'
  });

  function handleError(req, error) {
    const myHandlers = [];

    errHandlers.splice(0).forEach(function (handler) {
      (handler.source === req.source ? myHandlers : errHandlers).push(handler);
    });

    if (!myHandlers.length) {
      notify.fatal(new Error(`unhandled courier request error: ${ notify.describeError(error) }`));
    } else {
      myHandlers.forEach(function (handler) {
        handler.defer.resolve(error);
      });
    }
  }

  return handleError;
};
