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

import 'plugins/markdown_vis/markdown_vis.less';
import 'plugins/markdown_vis/markdown_vis_controller';
import TemplateVisTypeTemplateVisTypeProvider from 'ui/template_vis_type/template_vis_type';
import markdownVisTemplate from 'plugins/markdown_vis/markdown_vis.html';
import markdownVisParamsTemplate from 'plugins/markdown_vis/markdown_vis_params.html';
// we need to load the css ourselves

// we also need to load the controller and used by the template

// register the provider with the visTypes registry so that other know it exists
require('ui/registry/vis_types').register(MarkdownVisProvider);

function MarkdownVisProvider(Private) {
  const TemplateVisType = Private(TemplateVisTypeTemplateVisTypeProvider);

  // return the visType object, which kibana will use to display and configure new
  // Vis object of this type.
  return new TemplateVisType({
    name: 'markdown',
    title: 'Markdown widget',
    icon: 'fa-code',
    description: 'Useful for displaying explanations or instructions for dashboards.',
    template: markdownVisTemplate,
    params: {
      editor: markdownVisParamsTemplate
    },
    requiresSearch: false,
    implementsRenderComplete: true,
  });
}

// export the provider so that the visType can be required with Private()
export default MarkdownVisProvider;
