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

/**
 * This plug-in will read the text from the header cell of a column, returning
 * that value.
 *
 *  @name column().title()
 *  @summary Get the title of a column
 *  @author Alejandro Navarro
 *  @requires DataTables 1.10+
 *
 * @returns {String} Column title
 *
 *  @example
 *    // Read the title text of column index 3
 *    var table = $('#example').DataTable();
 *    table.column( 3 ).title();
 */

$.fn.dataTable.Api.register( 'column().title()', function () {
    var colheader = this.header();
    return $(colheader).text().trim();
} );