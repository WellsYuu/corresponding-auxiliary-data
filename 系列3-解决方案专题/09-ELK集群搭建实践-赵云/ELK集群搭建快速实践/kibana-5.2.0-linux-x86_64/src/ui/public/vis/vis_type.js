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

import VisSchemasProvider from './schemas';

export default function VisTypeFactory(Private) {
  let VisTypeSchemas = Private(VisSchemasProvider);

  function VisType(opts) {
    opts = opts || {};

    this.name = opts.name;
    this.title = opts.title;
    this.responseConverter = opts.responseConverter;
    this.hierarchicalData = opts.hierarchicalData || false;
    this.icon = opts.icon;
    this.description = opts.description;
    this.schemas = opts.schemas || new VisTypeSchemas();
    this.params = opts.params || {};
    this.requiresSearch = opts.requiresSearch == null ? true : opts.requiresSearch; // Default to true unless otherwise specified
    this.implementsRenderComplete = opts.implementsRenderComplete || false;
  }

  VisType.prototype.createRenderbot = function (vis, $el, uiState) {
    throw new Error('not implemented');
  };

  return VisType;
};
