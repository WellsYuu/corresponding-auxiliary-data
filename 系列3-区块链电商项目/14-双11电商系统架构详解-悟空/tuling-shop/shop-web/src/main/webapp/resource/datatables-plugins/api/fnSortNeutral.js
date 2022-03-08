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
 * This function will restore the order in which data was read into a DataTable
 * (for example from an HTML source). Although you can set aaSorting to be an
 * empty array (`[ ]`) in order to prevent sorting during initialisation, it can
 * sometimes be useful to restore the original order after sorting has already
 * occurred - which is exactly what this function does.
 *
 *  @name fnSortNeutral
 *  @summary Change ordering of the table to its data load order
 *  @author [Allan Jardine](http://sprymedia.co.uk)
 *
 *  @example
 *    $(document).ready(function() {
 *        var table = $('#example').dataTable();
 *         
 *        // Sort in the order that was originally in the HTML
 *        table.fnSortNeutral();
 *    } );
 */

jQuery.fn.dataTableExt.oApi.fnSortNeutral = function ( oSettings )
{
	/* Remove any current sorting */
	oSettings.aaSorting = [];

	/* Sort display arrays so we get them in numerical order */
	oSettings.aiDisplay.sort( function (x,y) {
		return x-y;
	} );
	oSettings.aiDisplayMaster.sort( function (x,y) {
		return x-y;
	} );

	/* Redraw */
	oSettings.oApi._fnReDraw( oSettings );
};
