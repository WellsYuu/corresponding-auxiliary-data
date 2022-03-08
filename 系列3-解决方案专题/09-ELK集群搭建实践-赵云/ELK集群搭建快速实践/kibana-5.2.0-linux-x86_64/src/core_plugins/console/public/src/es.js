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

let _ = require('lodash');
let $ = require('jquery');

let esVersion = [];

module.exports.getVersion = function () {
  return esVersion;
};

module.exports.send = function (method, path, data, server, disable_auth_alert) {
  var wrappedDfd = $.Deferred();

  console.log("Calling " + path);
  if (data && method == "GET") {
    method = "POST";
  }

  // delayed loading for circular references
  var settings = require("./settings");

  let contentType;
  if (data) {
    try {
      JSON.parse(data);
      contentType = 'application/json';
    } catch (e) {
      contentType = 'text/plain';
    }
  }

  var options = {
    url: '../api/console/proxy?uri=' + encodeURIComponent(path),
    data: method == "GET" ? null : data,
    contentType,
    cache: false,
    crossDomain: true,
    type: method,
    dataType: "text", // disable automatic guessing
  };


  $.ajax(options).then(
    function (data, textStatus, jqXHR) {
      wrappedDfd.resolveWith(this, [data, textStatus, jqXHR]);
    },
    function (jqXHR, textStatus, errorThrown) {
      if (jqXHR.status == 0) {
        jqXHR.responseText = "\n\nFailed to connect to Console's backend.\nPlease check the Kibana server is up and running";
      }
      wrappedDfd.rejectWith(this, [jqXHR, textStatus, errorThrown]);
    });
  return wrappedDfd;
};

module.exports.constructESUrl = function (baseUri, path) {
  baseUri = baseUri.replace(/\/+$/, '');
  path = path.replace(/^\/+/, '');
  return baseUri + '/' + path;
};
