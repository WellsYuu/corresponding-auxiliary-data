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
// Takes a hit, merges it with any stored/scripted fields, and with the metaFields
// returns a flattened version
export default function FlattenHitProvider(config) {
  let metaFields = config.get('metaFields');

  config.watch('metaFields', value => {
    metaFields = value;
  });

  function flattenHit(indexPattern, hit) {
    let flat = {};

    // recursively merge _source
    let fields = indexPattern.fields.byName;
    (function flatten(obj, keyPrefix) {
      keyPrefix = keyPrefix ? keyPrefix + '.' : '';
      _.forOwn(obj, function (val, key) {
        key = keyPrefix + key;

        if (flat[key] !== void 0) return;

        let hasValidMapping = (fields[key] && fields[key].type !== 'conflict');
        let isValue = !_.isPlainObject(val);

        if (hasValidMapping || isValue) {
          flat[key] = val;
          return;
        }

        flatten(val, key);
      });
    }(hit._source));

    // assign the meta fields
    _.each(metaFields, function (meta) {
      if (meta === '_source') return;
      flat[meta] = hit[meta];
    });

    // unwrap computed fields
    _.forOwn(hit.fields, function (val, key) {
      if (key[0] === '_' && !_.contains(metaFields, key)) return;
      flat[key] = _.isArray(val) && val.length === 1 ? val[0] : val;
    });

    return flat;
  }

  return function flattenHitWrapper(indexPattern) {
    function cachedFlatten(hit) {
      return hit.$$_flattened || (hit.$$_flattened = flattenHit(indexPattern, hit));
    }

    cachedFlatten.uncached = _.partial(flattenHit, indexPattern);

    return cachedFlatten;
  };
};

