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
import getValType from './get_val_type';
import getEditorType from './get_editor_type';

/**
 * @param {object} advanced setting definition object
 * @param {object} name of setting
 * @param {object} current value of setting
 * @returns {object} the editable config object
 */
function toEditableConfig({ def, name, value, isCustom }) {
  if (!def) {
    def = {};
  }
  const conf = {
    name,
    value,
    isCustom,
    readonly: !!def.readonly,
    defVal: def.value,
    type: getValType(def, value),
    description: def.description,
    options: def.options
  };

  const editor = getEditorType(conf);
  conf.json = editor === 'json';
  conf.select = editor === 'select';
  conf.bool = editor === 'boolean';
  conf.array = editor === 'array';
  conf.markdown = editor === 'markdown';
  conf.normal = editor === 'normal';
  conf.tooComplex = !editor;

  return conf;
}

export default toEditableConfig;
