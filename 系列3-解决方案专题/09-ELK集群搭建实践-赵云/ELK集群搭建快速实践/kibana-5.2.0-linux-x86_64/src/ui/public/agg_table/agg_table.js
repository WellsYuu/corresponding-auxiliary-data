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

import 'ui/paginated_table';
import 'ui/compile_recursive_directive';
import 'ui/agg_table/agg_table.less';
import _ from 'lodash';
import uiModules from 'ui/modules';
import aggTableTemplate from 'ui/agg_table/agg_table.html';

uiModules
.get('kibana')
.directive('kbnAggTable', function ($filter, config, Private, compileRecursiveDirective) {

  return {
    restrict: 'E',
    template: aggTableTemplate,
    scope: {
      table: '=',
      perPage: '=?',
      sort: '=?',
      exportTitle: '=?',
      showTotal: '=',
      totalFunc: '='
    },
    controllerAs: 'aggTable',
    compile: function ($el) {
      // Use the compile function from the RecursionHelper,
      // And return the linking function(s) which it returns
      return compileRecursiveDirective.compile($el);
    },
    controller: function ($scope) {
      let self = this;

      self._saveAs = require('@spalger/filesaver').saveAs;
      self.csv = {
        separator: config.get('csv:separator'),
        quoteValues: config.get('csv:quoteValues')
      };

      self.exportAsCsv = function (formatted) {
        let csv = new Blob([self.toCsv(formatted)], { type: 'text/plain;charset=utf-8' });
        self._saveAs(csv, self.csv.filename);
      };

      self.toCsv = function (formatted) {
        let rows = $scope.table.rows;
        let columns = formatted ? $scope.formattedColumns : $scope.table.columns;
        let nonAlphaNumRE = /[^a-zA-Z0-9]/;
        let allDoubleQuoteRE = /"/g;

        function escape(val) {
          if (!formatted && _.isObject(val)) val = val.valueOf();
          val = String(val);
          if (self.csv.quoteValues && nonAlphaNumRE.test(val)) {
            val = '"' + val.replace(allDoubleQuoteRE, '""') + '"';
          }
          return val;
        }

        // escape each cell in each row
        let csvRows = rows.map(function (row) {
          return row.map(escape);
        });

        // add the columns to the rows
        csvRows.unshift(columns.map(function (col) {
          return escape(col.title);
        }));

        return csvRows.map(function (row) {
          return row.join(self.csv.separator) + '\r\n';
        }).join('');
      };

      $scope.$watch('table', function () {
        let table = $scope.table;

        if (!table) {
          $scope.rows = null;
          $scope.formattedColumns = null;
          return;
        }

        self.csv.filename = ($scope.exportTitle || table.title() || 'table') + '.csv';
        $scope.rows = table.rows;
        $scope.formattedColumns = table.columns.map(function (col, i) {
          let agg = $scope.table.aggConfig(col);
          let field = agg.getField();
          let formattedColumn = {
            title: col.title,
            filterable: field && field.filterable && agg.schema.group === 'buckets'
          };

          let last = i === (table.columns.length - 1);

          if (last || (agg.schema.group === 'metrics')) {
            formattedColumn.class = 'visualize-table-right';
          }

          const isFieldNumeric = (field && field.type === 'number');
          const isFirstValueNumeric = _.isNumber(_.get(table, `rows[0][${i}].value`));

          if (isFieldNumeric || isFirstValueNumeric) {
            function sum(tableRows) {
              return _.reduce(tableRows, function (prev, curr, n, all) {return prev + curr[i].value; }, 0);
            }

            switch ($scope.totalFunc) {
              case 'sum':
                formattedColumn.total = sum(table.rows);
                break;
              case 'avg':
                formattedColumn.total = sum(table.rows) / table.rows.length;
                break;
              case 'min':
                formattedColumn.total = _.chain(table.rows).map(i).map('value').min().value();
                break;
              case 'max':
                formattedColumn.total = _.chain(table.rows).map(i).map('value').max().value();
                break;
              case 'count':
                formattedColumn.total = table.rows.length;
                break;
              default:
                break;
            }
          }

          return formattedColumn;
        });
      });
    }
  };
});
