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
 * This plug-in will take a `dt-tag tr` element and alter the table's paging
 * to make that `dt-tag tr` element (i.e. that row) visible.
 *
 *  @name fnDisplayRow
 *  @summary Shift the table's paging to display a given `dt-tag tr` element
 *  @author [Allan Jardine](http://sprymedia.co.uk)
 *
 *  @param {node} nRow Row to display
 *
 *  @example
 *    // Display the 21st row in the table
 *    var table = $('#example').dataTable();
 *    table.fnDisplayRow( table.fnGetNodes()[20] );
 */

jQuery.fn.dataTableExt.oApi.fnDisplayRow = function ( oSettings, nRow )
{
	// Account for the "display" all case - row is already displayed
	if ( oSettings._iDisplayLength == -1 )
	{
		return;
	}

	// Find the node in the table
	var iPos = -1;
	for( var i=0, iLen=oSettings.aiDisplay.length ; i<iLen ; i++ )
	{
		if( oSettings.aoData[ oSettings.aiDisplay[i] ].nTr == nRow )
		{
			iPos = i;
			break;
		}
	}

	// Alter the start point of the paging display
	if( iPos >= 0 )
	{
		oSettings._iDisplayStart = ( Math.floor(i / oSettings._iDisplayLength) ) * oSettings._iDisplayLength;
		if ( this.oApi._fnCalculateEnd ) {
			this.oApi._fnCalculateEnd( oSettings );
		}
	}

	this.oApi._fnDraw( oSettings );
};
