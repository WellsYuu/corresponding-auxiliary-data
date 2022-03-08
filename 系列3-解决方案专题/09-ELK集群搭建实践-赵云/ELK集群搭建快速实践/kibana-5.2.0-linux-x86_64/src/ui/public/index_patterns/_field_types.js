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

import IndexedArray from 'ui/indexed_array';
export default function IndexPatternFieldTypes() {

  return new IndexedArray({
    index: ['name'],
    group: ['sortable', 'filterable'],
    immutable: true,
    initialSet: [
      { name: 'ip',         sortable: true,   filterable: true  },
      { name: 'date',       sortable: true,   filterable: true  },
      { name: 'string',     sortable: true,   filterable: true  },
      { name: 'number',     sortable: true,   filterable: true  },
      { name: 'boolean',    sortable: true,   filterable: true  },
      { name: 'conflict',   sortable: false,  filterable: false },
      { name: 'geo_point',  sortable: false,  filterable: false },
      { name: 'geo_shape',  sortable: false,  filterable: false },
      { name: 'attachment', sortable: false,  filterable: false },
      { name: 'murmur3',    sortable: false,  filterable: false },
      { name: 'unknown',    sortable: false,  filterable: false },
      { name: '_source',    sortable: false,  filterable: false },
    ]
  });
};
