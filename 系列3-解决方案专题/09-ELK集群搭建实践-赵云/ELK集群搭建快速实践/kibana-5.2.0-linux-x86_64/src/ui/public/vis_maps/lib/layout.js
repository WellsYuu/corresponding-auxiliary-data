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
import MapSplitProvider from './splits/map_split';

export default function LayoutFactory(Private) {
  const mapSplit = Private(MapSplitProvider);
  class Layout {
    constructor(el, config, data) {
      this.el = el;
      this.config = config;
      this.data = data;
    }

    render() {
      this.removeAll();
      this.createLayout();
    }

    createLayout() {
      const wrapper = this.appendElem(this.el, 'div', 'vis-wrapper');
      wrapper.datum(this.data.data);
      const colWrapper = this.appendElem(wrapper.node(), 'div', 'vis-col-wrapper');
      const chartWrapper = this.appendElem(colWrapper.node(), 'div', 'chart-wrapper');
      chartWrapper.call(mapSplit, colWrapper.node(), this.config);
    }

    appendElem(el, type, className) {
      if (!el || !type || !className) {
        throw new Error('Function requires that an el, type, and class be provided');
      }

      if (typeof el === 'string') {
        // Create a DOM reference with a d3 selection
        // Need to make sure that the `el` is bound to this object
        // to prevent it from being appended to another Layout
        el = d3.select(this.el)
          .select(el)[0][0];
      }

      return d3.select(el)
      .append(type)
      .attr('class', className);
    }

    removeAll() {
      return d3.select(this.el).selectAll('*').remove();
    }
  }

  return Layout;
}
