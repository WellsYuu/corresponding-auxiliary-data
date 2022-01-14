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
import _ from 'lodash';
import VislibLibDispatchProvider from '../lib/dispatch';
import TooltipProvider from 'ui/vis/components/tooltip';
export default function ChartBaseClass(Private) {

  const Dispatch = Private(VislibLibDispatchProvider);
  const Tooltip = Private(TooltipProvider);
  /**
   * The Base Class for all visualizations.
   *
   * @class Chart
   * @constructor
   * @param handler {Object} Reference to the Handler Class Constructor
   * @param el {HTMLElement} HTML element to which the chart will be appended
   * @param chartData {Object} Elasticsearch query results for this specific chart
   */
  class Chart {
    constructor(handler, el, chartData) {
      this.handler = handler;
      this.chartEl = el;
      this.chartData = chartData;
      this.tooltips = [];

      const events = this.events = new Dispatch(handler);

      if (this.handler.visConfig && this.handler.visConfig.get('addTooltip', false)) {
        const $el = this.handler.el;
        const formatter = this.handler.data.get('tooltipFormatter');

        // Add tooltip
        this.tooltip = new Tooltip('chart', $el, formatter, events);
        this.tooltips.push(this.tooltip);
      }
    }

    render() {
      const selection = d3.select(this.chartEl);
      selection.selectAll('*').remove();
      selection.call(this.draw());
    }


    /**
     * Removes all DOM elements from the root element
     *
     * @method destroy
     */
    destroy() {
      const selection = d3.select(this.chartEl);
      this.events.removeAllListeners();
      this.tooltips.forEach(function (tooltip) {
        tooltip.destroy();
      });
      selection.remove();
    }
  }

  return Chart;
}
