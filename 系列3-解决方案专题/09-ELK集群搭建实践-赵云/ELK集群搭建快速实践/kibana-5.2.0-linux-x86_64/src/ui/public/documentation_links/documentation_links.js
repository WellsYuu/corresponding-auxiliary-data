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

import semver from 'semver';
import metadata from '../metadata';

const major = semver.major(metadata.version);
const minor = semver.minor(metadata.version);
const urlVersion = `${major}.${minor}`;
const baseUrl = 'https://www.elastic.co/';

export default {
  filebeat: {
    installation: `${baseUrl}guide/en/beats/filebeat/${urlVersion}/filebeat-installation.html`,
    configuration: `${baseUrl}guide/en/beats/filebeat/${urlVersion}/filebeat-configuration.html`,
    elasticsearchOutput: `${baseUrl}guide/en/beats/filebeat/${urlVersion}/elasticsearch-output.html`,
    elasticsearchOutputAnchorParameters: `${baseUrl}guide/en/beats/filebeat/${urlVersion}/elasticsearch-output.html#_parameters`,
    startup: `${baseUrl}guide/en/beats/filebeat/${urlVersion}/filebeat-starting.html`,
    exportedFields: `${baseUrl}guide/en/beats/filebeat/${urlVersion}/exported-fields.html`
  },
  scriptedFields: {
    scriptFields: `${baseUrl}guide/en/elasticsearch/reference/${urlVersion}/search-request-script-fields.html`,
    scriptAggs: `${baseUrl}guide/en/elasticsearch/reference/${urlVersion}/search-aggregations.html#_values_source`,
    painless: `${baseUrl}guide/en/elasticsearch/reference/${urlVersion}/modules-scripting-painless.html`,
    painlessApi: `${baseUrl}guide/en/elasticsearch/reference/${urlVersion}/modules-scripting-painless.html#painless-api`,
    painlessSyntax: `${baseUrl}guide/en/elasticsearch/reference/${urlVersion}/modules-scripting-painless-syntax.html`,
    luceneExpressions: `${baseUrl}guide/en/elasticsearch/reference/${urlVersion}/modules-scripting-expression.html`
  }
};
