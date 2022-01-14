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

/**
 * It can sometimes be useful to get the average of data in an API result set,
 * be it from a column, or a collection of cells. This method provides exactly
 * that ability.
 *
 *  @name average()
 *  @summary Average the values in a data set.
 *  @author [Allan Jardine](http://sprymedia.co.uk)
 *  @requires DataTables 1.10+
 *
 * @returns {Number} Calculated average
 *
 *  @example
 *    // Average a column
 *    var table = $('#example').DataTable();
 *    table.column( 3 ).data().average();
 *
 *  @example
 *    // Average two cells
 *    var table = $('#example').DataTable();
 *    table.cells( 0, [3,4] ).data().average();
 */

jQuery.fn.dataTable.Api.register( 'average()', function () {
    var data = this.flatten();
    var sum = data.reduce( function ( a, b ) {
        return (a*1) + (b*1); // cast values in-case they are strings
    } );
 
    return sum / data.length;
} );

