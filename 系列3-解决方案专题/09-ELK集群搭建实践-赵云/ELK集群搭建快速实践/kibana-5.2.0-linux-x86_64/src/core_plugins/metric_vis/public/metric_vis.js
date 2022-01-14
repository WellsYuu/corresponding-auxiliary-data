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

import 'plugins/metric_vis/metric_vis.less';
import 'plugins/metric_vis/metric_vis_controller';
import TemplateVisTypeTemplateVisTypeProvider from 'ui/template_vis_type/template_vis_type';
import VisSchemasProvider from 'ui/vis/schemas';
import metricVisTemplate from 'plugins/metric_vis/metric_vis.html';
import metricVisParamsTemplate from 'plugins/metric_vis/metric_vis_params.html';
// we need to load the css ourselves

// we also need to load the controller and used by the template

// register the provider with the visTypes registry
require('ui/registry/vis_types').register(MetricVisProvider);

function MetricVisProvider(Private) {
  const TemplateVisType = Private(TemplateVisTypeTemplateVisTypeProvider);
  const Schemas = Private(VisSchemasProvider);

  // return the visType object, which kibana will use to display and configure new
  // Vis object of this type.
  return new TemplateVisType({
    name: 'metric',
    title: 'Metric',
    description: 'One big number for all of your one big number needs. Perfect for showing ' +
      'a count of hits, or the exact average of a numeric field.',
    icon: 'fa-calculator',
    template: metricVisTemplate,
    params: {
      defaults: {
        handleNoResults: true,
        fontSize: 60
      },
      editor: metricVisParamsTemplate
    },
    implementsRenderComplete: true,
    schemas: new Schemas([
      {
        group: 'metrics',
        name: 'metric',
        title: 'Metric',
        min: 1,
        defaults: [
          { type: 'count', schema: 'metric' }
        ]
      }
    ])
  });
}

// export the provider so that the visType can be required with Private()
export default MetricVisProvider;
