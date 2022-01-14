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

var utils = {};

utils.textFromRequest = function (request) {
  var data = request.data;
  if (typeof data != "string") {
    data = data.join("\n");
  }
  return request.method + " " + request.url + "\n" + data;
};

utils.jsonToString = function (data, indent) {
  return JSON.stringify(data, null, indent ? 2 : 0);
};

utils.reformatData = function (data, indent) {
  var changed = false;
  var formatted_data = [];
  for (var i = 0; i < data.length; i++) {
    var cur_doc = data[i];
    try {
      var new_doc = utils.jsonToString(JSON.parse(utils.collapseLiteralStrings(cur_doc)), indent ? 2 : 0);
      if (indent) {
        new_doc = utils.expandLiteralStrings(new_doc);
      }
      changed = changed || new_doc != cur_doc;
      formatted_data.push(new_doc);
    }
    catch (e) {
      console.log(e);
      formatted_data.push(cur_doc);
    }
  }

  return {
    changed: changed,
    data: formatted_data
  };
};

utils.collapseLiteralStrings = function (data) {
  return data.replace(/"""(?:\s*\n)?((?:.|\n)*?)(?:\n\s*)?"""/g,function (match, literal) {
      return JSON.stringify(literal);
  });
}

utils.expandLiteralStrings = function (data) {
  return data.replace(/("(?:\\"|[^"])*?")/g, function (match, string) {
    // expand things with two slashes or more
    if (string.split(/\\./).length > 2) {
      string = JSON.parse(string).replace("^\s*\n", "").replace("\n\s*^", "");
      var append = string.includes("\n") ? "\n" : ""; // only go multi line if the string has multiline
      return '"""' + append + string + append + '"""';
    } else {
      return string;
    }
  });
}

module.exports = utils;
