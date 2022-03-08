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
 * Sorts a column containing IP addresses in typical dot notation. This can 
 * be most useful when using DataTables for a networking application, and 
 * reporting information containing IP address. Also has a matching type 
 * detection plug-in for automatic type detection.
 *
 *  @name IP addresses 
 *  @summary Sort IP addresses numerically
 *  @author Brad Wasson
 *
 *  @example
 *    $('#example').dataTable( {
 *       columnDefs: [
 *         { type: 'ip-address', targets: 0 }
 *       ]
 *    } );
 */

jQuery.extend( jQuery.fn.dataTableExt.oSort, {
	"ip-address-pre": function ( a ) {
		var m = a.split("."), x = "";

		for(var i = 0; i < m.length; i++) {
			var item = m[i];
			if(item.length == 1) {
				x += "00" + item;
			} else if(item.length == 2) {
				x += "0" + item;
			} else {
				x += item;
			}
		}

		return x;
	},

	"ip-address-asc": function ( a, b ) {
		return ((a < b) ? -1 : ((a > b) ? 1 : 0));
	},

	"ip-address-desc": function ( a, b ) {
		return ((a < b) ? 1 : ((a > b) ? -1 : 0));
	}
} );
