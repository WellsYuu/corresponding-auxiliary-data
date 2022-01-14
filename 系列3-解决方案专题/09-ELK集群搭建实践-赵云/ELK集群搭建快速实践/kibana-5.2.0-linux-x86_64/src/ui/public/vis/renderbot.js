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

export default function RenderbotFactory(Private) {

  /**
   * "Abstract" renderbot class which just defines the expected API
   *
   * @param {Vis} vis - the vis object that contains all configuration data required to render the vis
   * @param {jQuery<DOMElement>} $el - a jQuery wrapped element to render into
   */
  function Renderbot(vis, $el, uiState) {
    this.vis = vis;
    this.$el = $el;
    this.uiState = uiState;
  }

  /**
   * Each renderbot should implement a #render() method which
   * should accept an elasticsearch response and update the underlying visualization
   *
   * @override
   * @param {object} esResp - The raw elasticsearch response
   * @return {undefined}
   */
  Renderbot.prototype.render = function (esResp) {
    throw new Error('not implemented');
  };

  /**
   * Each renderbot should implement the #destroy() method which
   * should tear down the owned element, remove event listeners, etc.
   *
   * @override
   * @return {undefined}
   */
  Renderbot.prototype.destroy = function () {
    throw new Error('not implemented');
  };

  /**
   * Each renderbot can optionally implement the #updateParams() method which
   * is used to pass in new vis params. It should not re-render the vis
   *
   * @override
   * @return {undefined}
   */
  Renderbot.prototype.updateParams = _.noop;

  return Renderbot;
};
