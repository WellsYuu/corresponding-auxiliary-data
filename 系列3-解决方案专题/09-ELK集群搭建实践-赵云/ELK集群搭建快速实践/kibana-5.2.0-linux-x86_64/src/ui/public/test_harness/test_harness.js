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

// chrome expects to be loaded first, let it get its way
import chrome from 'ui/chrome';

import sinon from 'sinon';
import Notifier from 'ui/notify/notifier';
import { setupAutoRelease } from 'auto-release-sinon';

import './test_harness.less';
import 'ng_mock';
import { setupTestSharding } from './test_sharding';

// Setup auto releasing stubs and spys
setupAutoRelease(sinon, window.afterEach);
setupTestSharding();

// allows test_harness.less to have higher priority selectors
document.body.setAttribute('id', 'test-harness-body');

// prevent accidental ajax requests
before(() => {
  sinon.useFakeXMLHttpRequest();
});

beforeEach(function () {
  if (Notifier.prototype._notifs.length) {
    Notifier.prototype._notifs.length = 0;
    throw new Error('notifications were left in the notifier');
  }
});

// Kick off mocha, called at the end of test entry files
exports.bootstrap = () => {
  chrome.setupAngular();
};
