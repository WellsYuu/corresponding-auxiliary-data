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

"use strict";

var _ = require("lodash");

'use strict';

/**
 *
 * @param name
 */
function Api(name) {
  this.globalRules = {};
  this.endpoints = {};
  this.name = name;
}

(function (cls) {
  cls.addGlobalAutocompleteRules = function (parentNode, rules) {
    this.globalRules[parentNode] = rules;
  };

  cls.addEndpointDescription = function (endpoint, description) {
    if (this.endpoints[endpoint]) {
      throw new Error("endpoint [" + endpoint + "] is already registered");
    }

    var copiedDescription = {};
    _.extend(copiedDescription, description || {});
    _.defaults(copiedDescription, {
      id: endpoint,
      patterns: [endpoint],
      methods: ['GET']
    });
    this.endpoints[endpoint] = copiedDescription;
  };

  cls.asJson = function () {
    return {
      "name": this.name,
      "globals": this.globalRules,
      "endpoints": this.endpoints
    };
  };
})(Api.prototype);

module.exports = Api;
