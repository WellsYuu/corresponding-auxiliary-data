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

import AbstractRequestProvider from './request';

export default function DocRequestProvider(Private) {

  const AbstractRequest = Private(AbstractRequestProvider);

  class AbstractDocRequest extends AbstractRequest {
    constructor(...args) {
      super(...args);

      this.type = 'doc';
    }

    canStart() {
      const parent = super.canStart();
      if (!parent) return false;

      const version = this.source._version;
      const storedVersion = this.source._getStoredVersion();

      // conditions that equal "fetch This DOC!"
      const unknown = !version && !storedVersion;
      const mismatch = version !== storedVersion;

      return Boolean(mismatch || (unknown && !this.started));
    }

    handleResponse(resp) {
      if (resp.found) {
        this.source._storeVersion(resp._version);
      } else {
        this.source._clearVersion();
      }

      return super.handleResponse(resp);
    }
  }


  return AbstractDocRequest;
}
