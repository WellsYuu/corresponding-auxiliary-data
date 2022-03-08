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

define(function (require) {
  // we also need to load the controller and used by the template
  require('plugins/timelion/vis/timelion_vis_controller');
  require('plugins/timelion/vis/timelion_vis_params_controller');

  // Stylin
  require('plugins/timelion/vis/timelion_vis.less');

  // register the provider with the visTypes registry so that other know it exists
  require('ui/registry/vis_types').register(TimelionVisProvider);

  function TimelionVisProvider(Private) {
    var TemplateVisType = Private(require('ui/template_vis_type'));

    // return the visType object, which kibana will use to display and configure new
    // Vis object of this type.
    return new TemplateVisType({
      name: 'timelion',
      title: 'Timeseries',
      icon: 'fa-clock-o',
      description: 'Create timeseries charts using the timelion expression language. ' +
        'Perfect for computing and combining timeseries sets with functions such as derivatives and moving averages',
      template: require('plugins/timelion/vis/timelion_vis.html'),
      params: {
        editor: require('plugins/timelion/vis/timelion_vis_params.html')
      },
      requiresSearch: false,
      implementsRenderComplete: true,
    });
  }

  // export the provider so that the visType can be required with Private()
  return TimelionVisProvider;
});
