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
import editorHtml from 'ui/agg_types/controls/string.html';
import AggTypesParamTypesBaseProvider from 'ui/agg_types/param_types/base';
export default function FieldAggParamFactory(Private) {

  let BaseAggParam = Private(AggTypesParamTypesBaseProvider);

  _.class(ScriptAggParam).inherits(BaseAggParam);
  function ScriptAggParam(config) {
    ScriptAggParam.Super.call(this, config);
  }

  ScriptAggParam.prototype.editor = editorHtml;

  /**
   * Write the aggregation parameter.
   *
   * @param  {AggConfig} aggConfig - the entire configuration for this agg
   * @param  {object} output - the result of calling write on all of the aggregations
   *                         parameters.
   * @param  {object} output.params - the final object that will be included as the params
   *                               for the agg
   * @return {undefined}
   */
  ScriptAggParam.prototype.write = function (aggConfig, output) {
    if (aggConfig.params[this.name] && aggConfig.params[this.name].length) {
      output.params[this.name] = aggConfig.params[this.name];
    }
  };

  return ScriptAggParam;
};
