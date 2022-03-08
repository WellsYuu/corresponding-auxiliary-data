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

import collectKeys from 'ui/agg_response/hierarchical/_collect_keys';
import AggResponseHierarchicalTransformAggregationProvider from 'ui/agg_response/hierarchical/_transform_aggregation';
export default function biuldSplitProvider(Private) {
  let transformer = Private(AggResponseHierarchicalTransformAggregationProvider);
  return function (agg, metric, aggData) {
    // Ceate the split structure
    let split = { label: '', slices: { children: [] } };

    // Transform the aggData into splits
    split.slices.children = transformer(agg, metric, aggData);

    // Collect all the keys
    split.names = collectKeys(split.slices.children);
    return split;
  };
};
