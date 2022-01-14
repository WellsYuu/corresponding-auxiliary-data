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
import angular from 'angular';
import 'ui/highlight';
export default function contentTypesProvider(highlightFilter) {

  let types = {
    html: function (format, convert) {
      return function recurse(value, field, hit) {
        if (value == null) {
          return _.asPrettyString(value);
        }

        if (!value || typeof value.map !== 'function') {
          return convert.call(format, value, field, hit);
        }

        let subVals = value.map(function (v) {
          return recurse(v, field, hit);
        });
        let useMultiLine = subVals.some(function (sub) {
          return sub.indexOf('\n') > -1;
        });

        return subVals.join(',' + (useMultiLine ? '\n' : ' '));
      };
    },

    text: function (format, convert) {
      return function recurse(value) {
        if (!value || typeof value.map !== 'function') {
          return convert.call(format, value);
        }

        // format a list of values. In text contexts we just use JSON encoding
        return angular.toJson(value.map(recurse), true);
      };
    }
  };

  function fallbackText(value) {
    return _.asPrettyString(value);
  }

  function fallbackHtml(value, field, hit) {
    let formatted = _.escape(this.convert(value, 'text'));

    if (!hit || !hit.highlight || !hit.highlight[field.name]) {
      return formatted;
    } else {
      return highlightFilter(formatted, hit.highlight[field.name]);
    }
  }

  function setup(format) {
    let src = format._convert || {};
    let converters = format._convert = {};

    converters.text = types.text(format, src.text || fallbackText);
    converters.html = types.html(format, src.html || fallbackHtml);

    return format._convert;
  }

  return {
    types: types,
    setup: setup
  };
};
