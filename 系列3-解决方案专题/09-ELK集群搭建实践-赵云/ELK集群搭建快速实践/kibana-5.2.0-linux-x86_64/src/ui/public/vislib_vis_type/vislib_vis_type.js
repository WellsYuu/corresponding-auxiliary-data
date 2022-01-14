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
import 'ui/vislib';
import 'plugins/kbn_vislib_vis_types/controls/vislib_basic_options';
import 'plugins/kbn_vislib_vis_types/controls/point_series_options';
import 'plugins/kbn_vislib_vis_types/controls/line_interpolation_option';
import 'plugins/kbn_vislib_vis_types/controls/heatmap_options';
import VisSchemasProvider from 'ui/vis/schemas';
import VisVisTypeProvider from 'ui/vis/vis_type';
import AggResponsePointSeriesPointSeriesProvider from 'ui/agg_response/point_series/point_series';
import VislibVisTypeVislibRenderbotProvider from 'ui/vislib_vis_type/vislib_renderbot';
export default function VislibVisTypeFactory(Private) {

  let VisTypeSchemas = Private(VisSchemasProvider);
  let VisType = Private(VisVisTypeProvider);
  let pointSeries = Private(AggResponsePointSeriesPointSeriesProvider);
  let VislibRenderbot = Private(VislibVisTypeVislibRenderbotProvider);


  _.class(VislibVisType).inherits(VisType);
  function VislibVisType(opts = {}) {
    VislibVisType.Super.call(this, opts);

    if (this.responseConverter == null) {
      this.responseConverter = pointSeries;
    }

    this.listeners = opts.listeners || {};
  }

  VislibVisType.prototype.createRenderbot = function (vis, $el, uiState) {
    return new VislibRenderbot(vis, $el, uiState);
  };

  return VislibVisType;
};
