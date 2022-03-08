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

'use strict';

Object.defineProperty(exports, '__esModule', {
  value: true
});
exports.rewriteLegacyConfig = rewriteLegacyConfig;

var _lodash = require('lodash');

// legacySettings allow kibana 4.2+ to accept the same config file that people
// used for kibana 4.0 and 4.1. These settings are transformed to their modern
// equivalents at the very begining of the process
var legacySettings = {
  // server
  port: 'server.port',
  host: 'server.host',
  pid_file: 'pid.file',
  ssl_cert_file: 'server.ssl.cert',
  ssl_key_file: 'server.ssl.key',

  // logging
  log_file: 'logging.dest',

  // kibana
  kibana_index: 'kibana.index',
  default_app_id: 'kibana.defaultAppId',

  // es
  ca: 'elasticsearch.ssl.ca',
  elasticsearch_preserve_host: 'elasticsearch.preserveHost',
  elasticsearch_url: 'elasticsearch.url',
  kibana_elasticsearch_client_crt: 'elasticsearch.ssl.cert',
  kibana_elasticsearch_client_key: 'elasticsearch.ssl.key',
  kibana_elasticsearch_password: 'elasticsearch.password',
  kibana_elasticsearch_username: 'elasticsearch.username',
  ping_timeout: 'elasticsearch.pingTimeout',
  request_timeout: 'elasticsearch.requestTimeout',
  shard_timeout: 'elasticsearch.shardTimeout',
  startup_timeout: 'elasticsearch.startupTimeout',
  tilemap_url: 'tilemap.url',
  tilemap_min_zoom: 'tilemap.options.minZoom',
  tilemap_max_zoom: 'tilemap.options.maxZoom',
  tilemap_attribution: 'tilemap.options.attribution',
  tilemap_subdomains: 'tilemap.options.subdomains',
  verify_ssl: 'elasticsearch.ssl.verify'
};

exports.legacySettings = legacySettings;
// transform legacy options into new namespaced versions

function rewriteLegacyConfig(object) {
  var log = arguments.length <= 1 || arguments[1] === undefined ? _lodash.noop : arguments[1];

  return (0, _lodash.transform)(object, function (clone, val, key) {
    if (legacySettings.hasOwnProperty(key)) {
      var replacement = legacySettings[key];
      log('Config key "' + key + '" is deprecated. It has been replaced with "' + replacement + '"');
      clone[replacement] = val;
    } else {
      clone[key] = val;
    }
  }, {});
}
