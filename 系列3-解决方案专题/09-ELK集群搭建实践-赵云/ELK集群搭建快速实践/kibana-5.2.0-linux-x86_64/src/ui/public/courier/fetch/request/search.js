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

import SearchStrategyProvider from '../strategy/search';
import AbstractRequestProvider from './request';

export default function SearchReqProvider(Private) {

  const searchStrategy = Private(SearchStrategyProvider);
  const AbstractRequest = Private(AbstractRequestProvider);

  return class SearchReq extends AbstractRequest {
    constructor(...args) {
      super(...args);

      this.type = 'search';
      this.strategy = searchStrategy;
    }
  };
};
