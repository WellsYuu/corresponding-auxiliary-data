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

import VislibVisTypeVislibVisTypeProvider from 'ui/vislib_vis_type/vislib_vis_type';
import VisSchemasProvider from 'ui/vis/schemas';
import areaTemplate from 'plugins/kbn_vislib_vis_types/editors/area.html';

export default function HistogramVisType(Private) {
  const VislibVisType = Private(VislibVisTypeVislibVisTypeProvider);
  const Schemas = Private(VisSchemasProvider);

  return new VislibVisType({
    name: 'area',
    title: 'Area chart',
    icon: 'fa-area-chart',
    description: 'Great for stacked timelines in which the total of all series is more important ' +
      'than comparing any two or more series. Less useful for assessing the relative change of ' +
      'unrelated data points as changes in a series lower down the stack will have a difficult to gauge ' +
      'effect on the series above it.',
    params: {
      defaults: {
        addTooltip: true,
        addLegend: true,
        legendPosition: 'right',
        scale: 'linear',
        interpolate: 'linear',
        mode: 'stacked',
        times: [],
        addTimeMarker: false,
        defaultYExtents: false,
        setYExtents: false
      },
      legendPositions: [{
        value: 'left',
        text: 'left',
      }, {
        value: 'right',
        text: 'right',
      }, {
        value: 'top',
        text: 'top',
      }, {
        value: 'bottom',
        text: 'bottom',
      }],
      interpolationModes: [{
        value: 'linear',
        text: 'straight',
      }, {
        value: 'cardinal',
        text: 'smoothed',
      }, {
        value: 'step-after',
        text: 'stepped',
      }],
      scales: ['linear', 'log', 'square root'],
      modes: ['stacked', 'overlap', 'percentage', 'wiggle', 'silhouette'],
      editor: areaTemplate
    },
    implementsRenderComplete: true,
    schemas: new Schemas([
      {
        group: 'metrics',
        name: 'metric',
        title: 'Y-Axis',
        min: 1,
        aggFilter: '!std_dev',
        defaults: [
          { schema: 'metric', type: 'count' }
        ]
      },
      {
        group: 'buckets',
        name: 'segment',
        title: 'X-Axis',
        min: 0,
        max: 1,
        aggFilter: '!geohash_grid'
      },
      {
        group: 'buckets',
        name: 'group',
        title: 'Split Area',
        min: 0,
        max: 1,
        aggFilter: '!geohash_grid'
      },
      {
        group: 'buckets',
        name: 'split',
        title: 'Split Chart',
        min: 0,
        max: 1,
        aggFilter: '!geohash_grid'
      }
    ])
  });
};
