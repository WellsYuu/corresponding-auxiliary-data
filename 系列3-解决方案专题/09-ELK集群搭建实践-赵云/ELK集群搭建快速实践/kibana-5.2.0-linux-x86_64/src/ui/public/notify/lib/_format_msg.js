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
import formatESMsg from 'ui/notify/lib/_format_es_msg';
let has = _.has;

/**
 * Formats the error message from an error object, extended elasticsearch
 * object or simple string; prepends optional second parameter to the message
 * @param  {Error|String} err
 * @param  {String} from - Prefix for message indicating source (optional)
 * @returns {string}
 */
function formatMsg(err, from) {
  let rtn = '';
  if (from) {
    rtn += from + ': ';
  }

  let esMsg = formatESMsg(err);

  if (typeof err === 'string') {
    rtn += err;
  } else if (esMsg) {
    rtn += esMsg;
  } else if (err instanceof Error) {
    rtn += formatMsg.describeError(err);
  } else if (has(err, 'status') && has(err, 'data')) {
    // is an Angular $http "error object"
    rtn += 'Error ' + err.status + ' ' + err.statusText + ': ' + err.data.message;
  }

  return rtn;
};

formatMsg.describeError = function (err) {
  if (!err) return undefined;
  if (err.body && err.body.message) return err.body.message;
  if (err.message) return err.message;
  return '' + err;
};

export default formatMsg;
