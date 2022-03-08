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

/**
 * just a place to put feature detection checks
 */
export default {
  cssFilters: (function () {
    let e = document.createElement('img');
    let rules = ['webkitFilter', 'mozFilter', 'msFilter', 'filter'];
    let test = 'grayscale(1)';
    rules.forEach(function (rule) { e.style[rule] = test; });

    document.body.appendChild(e);
    let styles = window.getComputedStyle(e);
    let can = _(styles).pick(rules).includes(test);
    document.body.removeChild(e);

    return can;
  }())
};

