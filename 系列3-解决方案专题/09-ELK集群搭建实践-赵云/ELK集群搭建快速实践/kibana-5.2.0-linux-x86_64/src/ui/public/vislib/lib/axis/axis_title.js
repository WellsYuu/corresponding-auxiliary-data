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

import d3 from 'd3';
import $ from 'jquery';
export default function AxisTitleFactory(Private) {

  class AxisTitle {
    constructor(axisConfig) {
      this.axisConfig = axisConfig;
      this.elSelector = this.axisConfig.get('title.elSelector').replace('{pos}', this.axisConfig.get('position'));
    }

    render() {
      d3.select(this.axisConfig.get('rootEl')).selectAll(this.elSelector).call(this.draw());
    };

    draw() {
      const config = this.axisConfig;

      return function (selection) {
        selection.each(function () {
          if (!config.get('show') && !config.get('title.show', false)) return;

          const el = this;
          const div = d3.select(el);
          const width = $(el).width();
          const height = $(el).height();

          const svg = div.append('svg')
          .attr('width', width)
          .attr('height', height);

          const bbox = svg.append('text')
          .attr('transform', function () {
            if (config.isHorizontal()) {
              return 'translate(' + width / 2 + ',11)';
            }
            return 'translate(11,' + height / 2 + ') rotate(270)';
          })
          .attr('text-anchor', 'middle')
          .text(config.get('title.text'))
          .node()
          .getBBox();

          if (config.isHorizontal()) {
            svg.attr('height', bbox.height);
          } else {
            svg.attr('width', bbox.height);
          }
        });
      };
    };
  }
  return AxisTitle;
};
