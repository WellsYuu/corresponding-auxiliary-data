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
 * This plug-in will provide numeric sorting for numeric columns which have
 * extra formatting, such as thousands separators, currency symbols or any other
 * non-numeric data.
 * 
 * By default when a cell is found to have no numeric data its value is sorted
 * numerically as if its value were 0. This could also be altered to be Inifnity
 * or -Infinity as required.
 *
 * DataTables 1.10+ has formatted number detection and sorting abilities built-
 * in. As such this plug-in is marked as deprecated, but might be useful when
 * working with old versions of DataTables.
 *
 *  @name Formatted numbers
 *  @summary Sort numbers which are displayed with thousand separators
 *  @deprecated
 *  @author [Allan Jardine](http://sprymedia.co.uk)
 *
 *  @example
 *    $('#example').dataTable( {
 *       columnDefs: [
 *         { type: 'formatted-num', targets: 0 }
 *       ]
 *    } );
 */

jQuery.extend( jQuery.fn.dataTableExt.oSort, {
	"formatted-num-pre": function ( a ) {
		a = (a === "-" || a === "") ? 0 : a.replace( /[^\d\-\.]/g, "" );
		return parseFloat( a );
	},

	"formatted-num-asc": function ( a, b ) {
		return a - b;
	},

	"formatted-num-desc": function ( a, b ) {
		return b - a;
	}
} );
