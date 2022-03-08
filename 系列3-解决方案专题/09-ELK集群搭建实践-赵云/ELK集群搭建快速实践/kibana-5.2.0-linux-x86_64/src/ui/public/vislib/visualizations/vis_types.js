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

import VislibVisualizationsPointSeriesProvider from './point_series';
import VislibVisualizationsPieChartProvider from './pie_chart';

export default function VisTypeFactory(Private) {

  /**
   * Provides the visualizations for the vislib
   *
   * @module vislib
   * @submodule VisTypeFactory
   * @param Private {Object} Loads any function as an angular module
   * @return {Function} Returns an Object of Visualization classes
   */
  return {
    pie: Private(VislibVisualizationsPieChartProvider),
    point_series: Private(VislibVisualizationsPointSeriesProvider)
  };
};
