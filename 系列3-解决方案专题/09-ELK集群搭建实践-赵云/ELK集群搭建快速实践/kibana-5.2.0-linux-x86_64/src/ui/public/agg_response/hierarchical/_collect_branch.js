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

define(function () {
  return function (leaf) {
    // walk up the branch for each parent
    function walk(item, memo) {
      // record the the depth
      let depth = item.depth - 1;

      // Using the aggConfig determine what the field name is. If the aggConfig
      // doesn't exist (which means it's an _all agg) then use the level for
      // the field name
      const { aggConfig } = item;
      let field = (aggConfig && aggConfig.getFieldDisplayName())
        || (aggConfig && aggConfig.label)
        || ('level ' + item.depth);

      // Add the row to the tooltipScope.rows
      memo.unshift({
        aggConfig,
        depth: depth,
        field: field,
        bucket: item.name,
        metric: item.size,
        item: item
      });

      // If the item has a parent and it's also a child then continue walking
      // up the branch
      if (item.parent && item.parent.parent) {
        return walk(item.parent, memo);
      } else {
        return memo;
      }
    }

    return walk(leaf, []);
  };
});
