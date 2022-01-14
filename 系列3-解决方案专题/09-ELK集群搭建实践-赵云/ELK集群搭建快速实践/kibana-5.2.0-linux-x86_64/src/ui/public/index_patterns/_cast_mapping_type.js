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

import IndexedArray from 'ui/indexed_array';
export default function CastMappingTypeFn() {

  castMappingType.types = new IndexedArray({
    index: ['name'],
    group: ['type'],
    immutable: true,
    initialSet: [
      { name: 'string',       type: 'string',     group: 'base'   },
      { name: 'text',         type: 'string',     group: 'base'   },
      { name: 'keyword',      type: 'string',     group: 'base'   },
      { name: 'date',         type: 'date',       group: 'base'   },
      { name: 'boolean',      type: 'boolean',    group: 'base'   },
      { name: 'float',        type: 'number',     group: 'number' },
      { name: 'half_float',   type: 'number',     group: 'number' },
      { name: 'scaled_float', type: 'number',     group: 'number' },
      { name: 'double',       type: 'number',     group: 'number' },
      { name: 'integer',      type: 'number',     group: 'number' },
      { name: 'long',         type: 'number',     group: 'number' },
      { name: 'short',        type: 'number',     group: 'number' },
      { name: 'byte',         type: 'number',     group: 'number' },
      { name: 'token_count',  type: 'number',     group: 'number' },
      { name: 'geo_point',    type: 'geo_point',  group: 'geo'    },
      { name: 'geo_shape',    type: 'geo_shape',  group: 'geo'    },
      { name: 'ip',           type: 'ip',         group: 'other'  },
      { name: 'attachment',   type: 'attachment', group: 'other'  },
      { name: 'murmur3',      type: 'murmur3',    group: 'hash'   }
    ]
  });

  /**
   * Accepts a mapping type, and converts it into it's js equivilent
   * @param  {String} type - the type from the mapping's 'type' field
   * @return {String} - the most specific type that we care for
   */
  function castMappingType(name) {
    if (!name) return 'unknown';

    let match = castMappingType.types.byName[name];
    return match ? match.type : 'string';
  }

  return castMappingType;
};
