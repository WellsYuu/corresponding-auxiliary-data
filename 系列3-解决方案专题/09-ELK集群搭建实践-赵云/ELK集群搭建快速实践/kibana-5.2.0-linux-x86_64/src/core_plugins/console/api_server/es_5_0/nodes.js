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

'use strict';

module.exports = function (api) {
  api.addEndpointDescription('_nodes/hot_threads', {
    methods: ['GET'],
    patterns: ["_nodes/hot_threads", "_nodes/{nodes}/hot_threads"]
  });
  api.addEndpointDescription('_nodes/info', {
    patterns: ["_nodes", "_nodes/{metrics}", "_nodes/{nodes}", "_nodes/{nodes}/{metrics}", "_nodes/{nodes}/info/{metrics}"],
    url_components: {
      "metrics": ["settings", "os", "process", "jvm", "thread_pool", "network", "transport", "http", "plugins", "ingest", "_all"]
    }
  });
  api.addEndpointDescription('_nodes/stats', {
    patterns: ["_nodes/stats", "_nodes/stats/{metrics}", "_nodes/stats/{metrics}/{index_metric}", "_nodes/{nodes}/stats", "_nodes/{nodes}/stats/{metrics}", "_nodes/{nodes}/stats/{metrics}/{index_metric}"],
    url_components: {
      "metrics": ["os", "jvm", "thread_pool", "network", "fs", "transport", "http", "indices", "process", "breaker", "ingest", "_all"],
      "index_metric": ["store", "indexing", "get", "search", "merge", "flush", "refresh", "filter_cache", "fielddata", "docs", "warmer", "percolate", "completion", "segments", "translog", "query_cache", "_all"]
    }
  });
};
