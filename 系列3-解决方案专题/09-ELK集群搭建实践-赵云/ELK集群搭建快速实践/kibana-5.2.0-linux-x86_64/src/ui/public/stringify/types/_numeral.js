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
import 'ui/field_format_editor/numeral/numeral';
import IndexPatternsFieldFormatProvider from 'ui/index_patterns/_field_format/field_format';
import BoundToConfigObjProvider from 'ui/bound_to_config_obj';
export default function AbstractNumeralFormatProvider(Private) {
  let FieldFormat = Private(IndexPatternsFieldFormatProvider);
  let BoundToConfigObj = Private(BoundToConfigObjProvider);
  let numeral = require('numeral')();

  _.class(Numeral).inherits(FieldFormat);
  function Numeral(params) {
    Numeral.Super.call(this, params);
  }

  Numeral.prototype._convert = function (val) {
    if (val === -Infinity) return '-∞';
    if (val === +Infinity) return '+∞';
    if (typeof val !== 'number') {
      val = parseFloat(val);
    }

    if (isNaN(val)) return '';

    return numeral.set(val).format(this.param('pattern'));
  };


  Numeral.factory = function (opts) {
    _.class(Class).inherits(Numeral);
    function Class(params) {
      Class.Super.call(this, params);
    }

    Class.id = opts.id;
    Class.title = opts.title;
    Class.fieldType = 'number';

    Class.paramDefaults = opts.paramDefaults || new BoundToConfigObj({
      pattern: '=format:' + opts.id + ':defaultPattern',
    });

    Class.editor = {
      template: opts.editorTemplate || require('ui/field_format_editor/numeral/numeral.html'),
      controllerAs: 'cntrl',
      controller: opts.controller || function () {
        this.sampleInputs = opts.sampleInputs;
      }
    };

    if (opts.prototype) {
      _.assign(Class.prototype, opts.prototype);
    }

    return Class;
  };

  return Numeral;
};
