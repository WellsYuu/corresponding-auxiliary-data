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

const NAMED_EDITORS = ['json', 'array', 'boolean', 'select', 'markdown'];
const NORMAL_EDITOR = ['number', 'string', 'null', 'undefined'];

/**
 * @param {object} advanced setting configuration object
 * @returns {string} the editor type to use when editing value
 */
function getEditorType(conf) {
  if (_.contains(NAMED_EDITORS, conf.type)) return conf.type;
  if (_.contains(NORMAL_EDITOR, conf.type)) return 'normal';
}

export default getEditorType;
