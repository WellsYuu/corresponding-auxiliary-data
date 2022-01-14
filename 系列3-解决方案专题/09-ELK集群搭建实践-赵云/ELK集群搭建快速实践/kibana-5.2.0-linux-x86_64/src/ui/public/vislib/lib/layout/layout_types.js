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

import VislibLibLayoutTypesColumnLayoutProvider from './types/column_layout';
import VislibLibLayoutTypesPieLayoutProvider from './types/pie_layout';

export default function LayoutTypeFactory(Private) {

  /**
   * Provides the HTML layouts for each visualization class
   *
   * @module vislib
   * @submodule LayoutTypeFactory
   * @param Private {Service} Loads any function as an angular module
   * @return {Function} Returns an Object of HTML layouts for each visualization class
   */
  return {
    pie: Private(VislibLibLayoutTypesPieLayoutProvider),
    point_series: Private(VislibLibLayoutTypesColumnLayoutProvider)
  };
};
