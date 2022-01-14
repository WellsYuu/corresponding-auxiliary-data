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
import 'ui/field_format_editor/samples/samples';
import IndexPatternsFieldFormatProvider from 'ui/index_patterns/_field_format/field_format';
export default function StringFormatProvider(Private) {
  let FieldFormat = Private(IndexPatternsFieldFormatProvider);


  _.class(_String).inherits(FieldFormat);
  function _String(params) {
    _String.Super.call(this, params);
  }

  _String.id = 'string';
  _String.title = 'String';
  _String.fieldType = [
    'number',
    'boolean',
    'date',
    'ip',
    'attachment',
    'geo_point',
    'geo_shape',
    'string',
    'murmur3',
    'unknown',
    'conflict'
  ];

  _String.paramDefaults = {
    transform: false
  };

  _String.editor = require('ui/stringify/editors/string.html');

  _String.transformOpts = [
    { id: false, name: '- none -' },
    { id: 'lower', name: 'Lower Case' },
    { id: 'upper', name: 'Upper Case' },
    { id: 'title', name: 'Title Case' },
    { id: 'short', name: 'Short Dots' },
    { id: 'base64', name: 'Base64 Decode'}
  ];

  _String.sampleInputs = [
    'A Quick Brown Fox.',
    'STAY CALM!',
    'com.organizations.project.ClassName',
    'hostname.net',
    'SGVsbG8gd29ybGQ='
  ];

  _String.prototype._base64Decode = function (val) {
    try {
      return window.atob(val);
    } catch (e) {
      return _.asPrettyString(val);
    }
  };

  _String.prototype._toTitleCase = function (val) {
    return val.replace(/\w\S*/g, txt => { return txt.charAt(0).toUpperCase() + txt.substr(1).toLowerCase(); });
  };

  _String.prototype._convert = function (val) {
    switch (this.param('transform')) {
      case 'lower': return String(val).toLowerCase();
      case 'upper': return String(val).toUpperCase();
      case 'title': return this._toTitleCase(val);
      case 'short': return _.shortenDottedString(val);
      case 'base64': return this._base64Decode(val);
      default: return _.asPrettyString(val);
    }
  };

  return _String;
};
