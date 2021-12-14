/*
 * Copyright [$tody.year] [Wales Yu of copyright owner]
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
 * Fairly simply, this plug-in will take the data from an API result set
 * and sum it, returning the summed value. The data can come from any data
 * source, including column data, cells or rows.
 *
 *  @name sum()
 *  @summary Sum the values in a data set.
 *  @author [Allan Jardine](http://sprymedia.co.uk)
 *  @requires DataTables 1.10+
 *
 *  @returns {Number} Summed value
 *
 *  @example
 *    // Simply get the sum of a column
 *    var table = $('#example').DataTable();
 *    table.column( 3 ).data().sum();
 *
 *  @example
 *    // Insert the sum of a column into the columns footer, for the visible
 *    // data on each draw
 *    $('#example').DataTable( {
 *      drawCallback: function () {
 *        var api = this.api();
 *        api.table().footer().to$().html(
 *          api.column( 4, {page:'current'} ).data().sum()
 *        );
 *      }
 *    } );
 */

jQuery.fn.dataTable.Api.register( 'sum()', function () {
	return this.flatten().reduce( function ( a, b ) {
		return (a*1) + (b*1); // cast values in-case they are strings
	} );
} );

