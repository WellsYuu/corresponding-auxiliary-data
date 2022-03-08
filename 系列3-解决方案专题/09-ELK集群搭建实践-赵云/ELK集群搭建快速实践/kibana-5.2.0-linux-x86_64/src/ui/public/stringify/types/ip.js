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
export default function IpFormatProvider(Private) {
  let FieldFormat = Private(IndexPatternsFieldFormatProvider);

  _.class(Ip).inherits(FieldFormat);
  function Ip(params) {
    Ip.Super.call(this, params);
  }

  Ip.id = 'ip';
  Ip.title = 'IP Address';
  Ip.fieldType = 'ip';

  Ip.prototype._convert = function (val) {
    if (val === undefined || val === null) return '-';
    if (!isFinite(val)) return val;

    // shazzam!
    return [val >>> 24, val >>> 16 & 0xFF, val >>> 8 & 0xFF, val & 0xFF].join('.');
  };

  return Ip;
};
