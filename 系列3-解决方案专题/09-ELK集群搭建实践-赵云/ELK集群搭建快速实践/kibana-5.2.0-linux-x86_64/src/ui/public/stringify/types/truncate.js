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

import IndexPatternsFieldFormatProvider from 'ui/index_patterns/_field_format/field_format';
export default function TruncateFormatProvider(Private) {
  let FieldFormat = Private(IndexPatternsFieldFormatProvider);
  let omission = '...';

  _.class(Truncate).inherits(FieldFormat);

  function Truncate(params) {
    Truncate.Super.call(this, params);
  }

  Truncate.id = 'truncate';
  Truncate.title = 'Truncated String';
  Truncate.fieldType = ['string'];

  Truncate.prototype._convert = function (val) {
    let length = this.param('fieldLength');
    if (length > 0) {
      return _.trunc(val, {
        'length': length + omission.length,
        'omission': omission
      });
    }

    return val;
  };

  Truncate.editor = require('ui/stringify/editors/truncate.html');

  Truncate.sampleInput = [ require('ui/stringify/samples/large.html') ];

  return Truncate;
};
