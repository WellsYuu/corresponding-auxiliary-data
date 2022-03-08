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
 * Get a list of all `dt-tag tr` nodes in the table which are not currently
 * visible (useful for building forms).
 *
 * This function is marked as deprecated as using the `dt-api rows()` method in
 * DataTables 1.10+ is preferred to this approach.
 *
 *  @name fnGetHiddenNodes
 *  @summary Get the `dt-tag tr` elements which are not in the DOM
 *  @author [Allan Jardine](http://sprymedia.co.uk)
 *  @deprecated
 *
 *  @example
 *    var table = $('#example').dataTable();
 *    var nodes = table.fnGetHiddenNodes();
 */

jQuery.fn.dataTableExt.oApi.fnGetHiddenNodes = function ( settings )
{
	var nodes;
	var display = jQuery('tbody tr', settings.nTable);

	if ( jQuery.fn.dataTable.versionCheck ) {
		// DataTables 1.10
		var api = new jQuery.fn.dataTable.Api( settings );
		nodes = api.rows().nodes().toArray();
	}
	else {
		// 1.9-
		nodes = this.oApi._fnGetTrNodes( settings );
	}

	/* Remove nodes which are being displayed */
	for ( var i=0 ; i<display.length ; i++ ) {
		var iIndex = jQuery.inArray( display[i], nodes );

		if ( iIndex != -1 ) {
			nodes.splice( iIndex, 1 );
		}
	}

	return nodes;
};
