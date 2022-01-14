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
import DecorateQueryProvider from 'ui/courier/data_source/_decorate_query';
export default function GetQueryFromUser(es, Private) {
  let decorateQuery = Private(DecorateQueryProvider);

  /**
   * Take text from the user and make it into a query object
   * @param {text} user's query input
   * @returns {object}
   */
  return function (text) {
    function getQueryStringQuery(text) {
      return decorateQuery({query_string: {query: text}});
    }

    let matchAll = getQueryStringQuery('*');

    // If we get an empty object, treat it as a *
    if (_.isObject(text)) {
      if (Object.keys(text).length) {
        return text;
      } else {
        return matchAll;
      }
    }

    // Nope, not an object.
    text = (text || '').trim();
    if (text.length === 0) return matchAll;

    if (text[0] === '{') {
      try {
        return JSON.parse(text);
      } catch (e) {
        return getQueryStringQuery(text);
      }
    } else {
      return getQueryStringQuery(text);
    }
  };
};

