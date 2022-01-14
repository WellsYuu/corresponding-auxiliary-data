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

/**
 * THESE ARE AUTOMATICALLY INCLUDED IN LODASH
 *
 * use:
 * var _ = require('lodash');
 */

'use strict';

var _ = require('node_modules/lodash/index.js').runInContext();
require('ui/utils/lodash-mixins/string')(_);
require('ui/utils/lodash-mixins/lang')(_);
require('ui/utils/lodash-mixins/object')(_);
require('ui/utils/lodash-mixins/collection')(_);
require('ui/utils/lodash-mixins/function')(_);
require('ui/utils/lodash-mixins/oop')(_);
module.exports = _;
