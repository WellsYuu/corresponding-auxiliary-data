/*
 * Copyright 2021-2021 the original author or authors.
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

/**
 * Apply the same filter to all DataTable instances on a particular page. The
 * function call exactly matches that used by `fnFilter()` so regular expression
 * and individual column sorting can be used.
 *
 * DataTables 1.10+ provides this ability through its new API, which is able to
 * to control multiple tables at a time.
 * `$('.dataTable').DataTable().search( ... )` for example will apply the same
 * filter to all tables on the page. The new API should be used in preference
 * to this older method if at all possible.
 *
 *  @name fnFilterAll
 *  @summary Apply a common filter to all DataTables on a page
 *  @author [Kristoffer Karlström](http://www.kmmtiming.se/)
 *  @deprecated
 *
 *  @param {string} sInput Filtering input
 *  @param {integer} [iColumn=null] Column to apply the filter to
 *  @param {boolean} [bRegex] Regular expression flag
 *  @param {boolean} [bSmart] Smart filtering flag
 *
 *  @example
 *    $(document).ready(function() {
 *      var table = $(".dataTable").dataTable();
 *       
 *      $("#search").keyup( function () {
 *        // Filter on the column (the index) of this element
 *        table.fnFilterAll(this.value);
 *      } );
 *    });
 */

jQuery.fn.dataTableExt.oApi.fnFilterAll = function(oSettings, sInput, iColumn, bRegex, bSmart) {
    var settings = $.fn.dataTableSettings;

    for ( var i=0 ; i<settings.length ; i++ ) {
      settings[i].oInstance.fnFilter( sInput, iColumn, bRegex, bSmart);
    }
};
