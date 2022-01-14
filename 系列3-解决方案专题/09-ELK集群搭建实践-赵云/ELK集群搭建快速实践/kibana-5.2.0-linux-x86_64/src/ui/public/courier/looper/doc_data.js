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

import FetchProvider from '../fetch';
import LooperProvider from './_looper';
import DocStrategyProvider from '../fetch/strategy/doc_data';

export default function DocLooperService(Private) {
  let fetch = Private(FetchProvider);
  let Looper = Private(LooperProvider);
  let DocStrategy = Private(DocStrategyProvider);

  /**
   * The Looper which will manage the doc fetch interval
   * @type {Looper}
   */
  let docLooper = new Looper(1500, function () {
    fetch.fetchQueued(DocStrategy);
  });

  return docLooper;
};
