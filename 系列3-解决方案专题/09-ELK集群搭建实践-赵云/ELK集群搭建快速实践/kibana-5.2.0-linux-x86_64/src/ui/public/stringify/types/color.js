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

import 'ui/stringify/editors/color.less';
import _ from 'lodash';
import IndexPatternsFieldFormatProvider from 'ui/index_patterns/_field_format/field_format';
import colorTemplate from 'ui/stringify/editors/color.html';
export default function ColorFormatProvider(Private) {

  const FieldFormat = Private(IndexPatternsFieldFormatProvider);
  const convertTemplate = _.template('<span style="<%- style %>"><%- val %></span>');
  const DEFAULT_COLOR = {
    range: `${Number.NEGATIVE_INFINITY}:${Number.POSITIVE_INFINITY}`,
    regex: '<insert regex>',
    text: '#000000',
    background: '#ffffff'
  };

  _.class(_Color).inherits(FieldFormat);
  function _Color(params) {
    _Color.Super.call(this, params);
  }

  _Color.id = 'color';
  _Color.title = 'Color';
  _Color.fieldType = [
    'number',
    'string'
  ];

  _Color.editor = {
    template: colorTemplate,
    controller($scope) {
      $scope.$watch('editor.field.type', type => {
        $scope.editor.formatParams.fieldType = type;
      });

      $scope.addColor = function () {
        $scope.editor.formatParams.colors.push(_.cloneDeep(DEFAULT_COLOR));
      };

      $scope.removeColor = function (index) {
        $scope.editor.formatParams.colors.splice(index, 1);
      };
    }
  };


  _Color.paramDefaults = {
    fieldType: null, // populated by editor, see controller below
    colors: [_.cloneDeep(DEFAULT_COLOR)]
  };

  _Color.prototype.findColorRuleForVal = function (val) {
    switch (this.param('fieldType')) {
      case 'string':
        return _.findLast(this.param('colors'), (colorParam) => {
          return new RegExp(colorParam.regex).test(val);
        });

      case 'number':
        return _.findLast(this.param('colors'), ({ range }) => {
          if (!range) return;
          const [start, end] = range.split(':');
          return val >= Number(start) && val <= Number(end);
        });

      default:
        return null;
    }
  };

  _Color.prototype._convert = {
    html(val) {
      const color = this.findColorRuleForVal(val);
      if (!color) return _.asPrettyString(val);

      let style = '';
      if (color.text) style += `color: ${color.text};`;
      if (color.background) style += `background-color: ${color.background};`;
      return convertTemplate({ val, style });
    }
  };


  return _Color;
};
