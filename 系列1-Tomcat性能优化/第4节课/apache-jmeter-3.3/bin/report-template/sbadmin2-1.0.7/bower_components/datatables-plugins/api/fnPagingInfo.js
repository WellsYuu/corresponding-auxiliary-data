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
 * Get information about the paging settings that DataTables is currently 
 * using to display each page, including the number of records shown, start
 * and end points in the data set etc.
 *
 * DataTables 1.10+ provides the `dt-api page.info()` method, built-in, provide
 * the same information as this method. As such this method is marked
 * deprecated, but is available for use with legacy version of DataTables.
 * Please use the new API if you are used DataTables 1.10 or newer.
 *
 *  @name fnPagingInfo
 *  @summary Get information about the paging state of the table
 *  @author [Allan Jardine](http://sprymedia.co.uk)
 *  @deprecated
 *
 *  @example
 *    $(document).ready(function() {
 *        $('#example').dataTable( {
 *            "fnDrawCallback": function () {
 *            alert( 'Now on page'+ this.fnPagingInfo().iPage );
 *          }
 *        } );
 *    } );
 */

jQuery.fn.dataTableExt.oApi.fnPagingInfo = function ( oSettings )
{
	return {
		"iStart":         oSettings._iDisplayStart,
		"iEnd":           oSettings.fnDisplayEnd(),
		"iLength":        oSettings._iDisplayLength,
		"iTotal":         oSettings.fnRecordsTotal(),
		"iFilteredTotal": oSettings.fnRecordsDisplay(),
		"iPage":          oSettings._iDisplayLength === -1 ?
			0 : Math.ceil( oSettings._iDisplayStart / oSettings._iDisplayLength ),
		"iTotalPages":    oSettings._iDisplayLength === -1 ?
			0 : Math.ceil( oSettings.fnRecordsDisplay() / oSettings._iDisplayLength )
	};
};
