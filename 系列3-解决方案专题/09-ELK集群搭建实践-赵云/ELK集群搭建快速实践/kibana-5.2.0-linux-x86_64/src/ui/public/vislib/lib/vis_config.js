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

/**
 * Provides vislib configuration, throws error if invalid property is accessed without providing defaults
 */
import _ from 'lodash';
import VisTypesProvider from './types';
import VislibLibDataProvider from './data';

export default function VisConfigFactory(Private) {

  const Data = Private(VislibLibDataProvider);
  const visTypes = Private(VisTypesProvider);
  const DEFAULT_VIS_CONFIG = {
    style: {
      margin : { top: 10, right: 3, bottom: 5, left: 3 }
    },
    alerts: [],
    categoryAxes: [],
    valueAxes: []
  };


  class VisConfig {
    constructor(visConfigArgs, data, uiState, el) {
      this.data = new Data(data, uiState);

      const visType = visTypes[visConfigArgs.type];
      const typeDefaults = visType(visConfigArgs, this.data);
      this._values = _.defaultsDeep({}, typeDefaults, DEFAULT_VIS_CONFIG);
      this._values.el = el;
    }

    get(property, defaults) {
      if (_.has(this._values, property) || typeof defaults !== 'undefined') {
        return _.get(this._values, property, defaults);
      } else {
        throw new Error(`Accessing invalid config property: ${property}`);
        return defaults;
      }
    }

    set(property, value) {
      return _.set(this._values, property, value);
    }
  }

  return VisConfig;
}
