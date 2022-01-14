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

'use strict';

var _ = require("lodash");
var Api = require('./api');
var parts = [require('./es_5_0/aliases'), require('./es_5_0/aggregations'), require('./es_5_0/cat'), require('./es_5_0/cluster'), require('./es_5_0/count'), require('./es_5_0/document'), require('./es_5_0/field_stats'), require('./es_5_0/filter'), require('./es_5_0/nodes'), require('./es_5_0/globals'), require('./es_5_0/indices'), require('./es_5_0/mappings'), require('./es_5_0/percolator'), require('./es_5_0/query'), require('./es_5_0/snapshot_restore'), require('./es_5_0/search'), require('./es_5_0/settings'), require('./es_5_0/templates')];

function ES_5_0() {
  Api.call(this, "es_5_0");
  _.each(parts, function (apiSection) {
    apiSection(this);
  }, this);
}

ES_5_0.prototype = _.create(Api.prototype, { 'constructor': ES_5_0 });

(function (cls) {
  cls.addEndpointDescription = function (endpoint, description) {
    if (description) {
      var url_params_def = {};
      _.each(description.patterns || [], function (p) {
        if (p.indexOf("{indices}") >= 0) {
          url_params_def["ignore_unavailable"] = "__flag__";
          url_params_def["allow_no_indices"] = "__flag__";
          url_params_def["expand_wildcards"] = ["open", "closed"];
        }
      });

      if (url_params_def) {
        description.url_params = description.url_params || {};
        _.defaults(description.url_params, url_params_def);
      }
    }
    Object.getPrototypeOf(cls).addEndpointDescription.call(this, endpoint, description);
  };
})(ES_5_0.prototype);

module.exports = new ES_5_0();
