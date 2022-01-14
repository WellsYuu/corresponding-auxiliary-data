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

Object.defineProperty(exports, '__esModule', {
  value: true
});
exports.linear = linear;
exports.log = log;
var _ = require('lodash');

function sum(data, fn) {
  return _.reduce(data, function (sum, d) {
    return sum + (d[1] == null ? 0 : fn(d));
  }, 0);
}

function count(data) {
  return _.filter(data, function (d) {
    return d[1] == null ? false : true;
  }).length;
}

function mapTuples(data, fn) {
  return _.map(data, function (d) {
    return [d[0], fn(d)];
  });
}

function linear(data) {
  var xSum = sum(data, function (d) {
    return d[0];
  });
  var ySum = sum(data, function (d) {
    return d[1];
  });
  var xSqSum = sum(data, function (d) {
    return d[0] * d[0];
  });
  var xySum = sum(data, function (d) {
    return d[0] * d[1];
  });
  var observations = count(data);

  var gradient = (observations * xySum - xSum * ySum) / (observations * xSqSum - xSum * xSum);

  var intercept = ySum / observations - gradient * xSum / observations;

  return mapTuples(data, function (d) {
    return d[0] * gradient + intercept;
  });
}

function log(data) {
  var logXSum = sum(data, function (d) {
    return Math.log(d[0]);
  });
  var yLogXSum = sum(data, function (d) {
    return d[1] * Math.log(d[0]);
  });
  var ySum = sum(data, function (d) {
    return d[1];
  });
  var logXsqSum = sum(data, function (d) {
    return Math.pow(Math.log(d[0]), 2);
  });
  var observations = count(data);

  var b = (observations * yLogXSum - ySum * logXSum) / (observations * logXsqSum - logXSum * logXSum);

  var a = (ySum - b * logXSum) / observations;

  return mapTuples(data, function (d) {
    return a + b * Math.log(d[0]);
  });
}
