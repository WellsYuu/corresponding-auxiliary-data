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
import noWhiteSpace from 'ui/utils/no_white_space';
import angular from 'angular';
import IndexPatternsFieldFormatProvider from 'ui/index_patterns/_field_format/field_format';
export default function _SourceFormatProvider(Private, shortDotsFilter) {
  let FieldFormat = Private(IndexPatternsFieldFormatProvider);
  let template = _.template(noWhiteSpace(require('ui/stringify/types/_source.html')));

  _.class(Source).inherits(FieldFormat);
  function Source(params) {
    Source.Super.call(this, params);
  }

  Source.id = '_source';
  Source.title = '_source';
  Source.fieldType = '_source';

  Source.prototype._convert = {
    text: angular.toJson,
    html: function sourceToHtml(source, field, hit) {
      if (!field) return this.getConverter('text')(source, field, hit);

      let highlights = (hit && hit.highlight) || {};
      let formatted = field.indexPattern.formatHit(hit);
      let highlightPairs = [];
      let sourcePairs = [];

      _.keys(formatted).forEach(function (key) {
        let pairs = highlights[key] ? highlightPairs : sourcePairs;
        let field = shortDotsFilter(key);
        let val = formatted[key];
        pairs.push([field, val]);
      }, []);

      return template({ defPairs: highlightPairs.concat(sourcePairs) });
    }
  };

  return Source;
};
