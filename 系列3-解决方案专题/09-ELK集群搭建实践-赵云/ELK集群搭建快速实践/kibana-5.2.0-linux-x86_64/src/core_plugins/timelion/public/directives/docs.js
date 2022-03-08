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

define(function (require) {
  var html = require('../partials/docs/tutorial.html');
  var app = require('ui/modules').get('apps/timelion', []);
  var _ = require('lodash');
  var moment = require('moment');

  app.directive('timelionDocs', function (config, $http) {
    return {
      restrict: 'E',
      template: html,
      controller: function ($scope, config) {
        $scope.section = config.get('timelion:showTutorial', true) ? 'tutorial' : 'functions';
        $scope.page = 1;
        $scope.functions = {
          list: [],
          details: null
        };

        function init() {
          $scope.es = {
            invalidCount: 0
          };
          getFunctions();
          checkElasticsearch();
        };

        function getFunctions() {
          return $http.get('../api/timelion/functions').then(function (resp) {
            $scope.functions.list = resp.data;
          });
        }
        $scope.recheckElasticsearch = function () {
          $scope.es.valid = null;
          checkElasticsearch().then(function (valid) {
            if (!valid) $scope.es.invalidCount++;
          });
        };

        function checkElasticsearch() {
          return $http.get('../api/timelion/validate/es').then(function (resp) {
            if (resp.data.ok) {

              $scope.es.valid = true;
              $scope.es.stats = {
                min: moment(resp.data.min).format('LLL'),
                max: moment(resp.data.max).format('LLL'),
                field: resp.data.field
              };
            } else {
              $scope.es.valid = false;
              $scope.es.invalidReason = (function () {
                try {
                  var esResp = JSON.parse(resp.data.resp.response);
                  return _.get(esResp, 'error.root_cause[0].reason');
                } catch (e) {
                  if (_.get(resp, 'data.resp.message')) return _.get(resp, 'data.resp.message');
                  if (_.get(resp, 'data.resp.output.payload.message')) return _.get(resp, 'data.resp.output.payload.message');
                  return 'Unknown error';
                }
              }());
            }
            return $scope.es.valid;
          });
        };
        init();
      }
    };
  });

});
