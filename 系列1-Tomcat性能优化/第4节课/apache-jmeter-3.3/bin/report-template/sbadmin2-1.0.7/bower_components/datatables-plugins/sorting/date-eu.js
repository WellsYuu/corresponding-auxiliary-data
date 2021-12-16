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
 * Similar to the Date (dd/mm/YY) data sorting plug-in, this plug-in offers 
 * additional  flexibility with support for spaces between the values and
 * either . or / notation for the separators.
 *
 * Please note that this plug-in is **deprecated*. The
 * [datetime](//datatables.net/blog/2014-12-18) plug-in provides enhanced
 * functionality and flexibility.
 *
 *  @name Date (dd . mm[ . YYYY]) 
 *  @summary Sort dates in the format `dd/mm/YY[YY]` (with optional spaces)
 *  @author [Robert Sedovšek](http://galjot.si/)
 *  @deprecated
 *
 *  @example
 *    $('#example').dataTable( {
 *       columnDefs: [
 *         { type: 'date-eu', targets: 0 }
 *       ]
 *    } );
 */

jQuery.extend( jQuery.fn.dataTableExt.oSort, {
	"date-eu-pre": function ( date ) {
		date = date.replace(" ", "");
		var eu_date, year;
		
		if (date == '') {
			return 0;
		}

		if (date.indexOf('.') > 0) {
			/*date a, format dd.mn.(yyyy) ; (year is optional)*/
			eu_date = date.split('.');
		} else {
			/*date a, format dd/mn/(yyyy) ; (year is optional)*/
			eu_date = date.split('/');
		}

		/*year (optional)*/
		if (eu_date[2]) {
			year = eu_date[2];
		} else {
			year = 0;
		}

		/*month*/
		var month = eu_date[1];
		if (month.length == 1) {
			month = 0+month;
		}

		/*day*/
		var day = eu_date[0];
		if (day.length == 1) {
			day = 0+day;
		}

		return (year + month + day) * 1;
	},

	"date-eu-asc": function ( a, b ) {
		return ((a < b) ? -1 : ((a > b) ? 1 : 0));
	},

	"date-eu-desc": function ( a, b ) {
		return ((a < b) ? 1 : ((a > b) ? -1 : 0));
	}
} );
