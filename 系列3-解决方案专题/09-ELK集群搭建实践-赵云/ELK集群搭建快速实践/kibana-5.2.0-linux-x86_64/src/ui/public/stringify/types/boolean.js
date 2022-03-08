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

import IndexPatternsFieldFormatProvider from 'ui/index_patterns/_field_format/field_format';
import _ from 'lodash';

export default function TruncateFormatProvider(Private) {

  let FieldFormat = Private(IndexPatternsFieldFormatProvider);

  class Bool extends FieldFormat {

    constructor(params) {
      super(params);
    }

    _convert(value) {

      if (typeof value === 'string') {
        value = value.trim().toLowerCase();
      }

      switch (value) {
        case false:
        case 0:
        case 'false':
        case 'no':
          return 'false';
        case true:
        case 1:
        case 'true':
        case 'yes':
          return 'true';
        default:
          return _.asPrettyString(value);
      }
    }
  }

  Bool.id = 'boolean';
  Bool.title = 'Boolean';
  Bool.fieldType = ['boolean', 'number', 'string'];

  return Bool;
};
