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
 * Search through a table looking for a given string (optionally the search
 * can be restricted to a single column). The return value is an array with
 * the data indexes (from DataTables' internal data store) for any rows which
 * match.
 *
 *  @name fnFindCellRowIndexes
 *  @summary Search for data, returning row indexes
 *  @author [Allan Jardine](http://sprymedia.co.uk)
 *
 *  @param {string} sSearch Data to search for
 *  @param {integer} [iColumn=null] Limit search to this column
 *  @returns {array} Array of row indexes with this data
 *
 *  @example
 *    $(document).ready(function() {
 *        var table = $('#example').dataTable();
 * 
 *        var a = table.fnFindCellRowIndexes( '1.7' ); // Search all columns
 *
 *        var b = table.fnFindCellRowIndexes( '1.7', 3 );  // Search only column 3
 *    } );
 */

jQuery.fn.dataTableExt.oApi.fnFindCellRowIndexes = function ( oSettings, sSearch, iColumn )
{
	var
		i,iLen, j, jLen, val,
		aOut = [], aData,
		columns = oSettings.aoColumns;

	for ( i=0, iLen=oSettings.aoData.length ; i<iLen ; i++ )
	{
		aData = oSettings.aoData[i]._aData;

		if ( iColumn === undefined )
		{
			for ( j=0, jLen=columns.length ; j<jLen ; j++ )
			{
				val = this.fnGetData(i, j);

				if ( val == sSearch )
				{
					aOut.push( i );
				}
			}
		}
		else if (this.fnGetData(i, iColumn) == sSearch )
		{
			aOut.push( i );
		}
	}

	return aOut;
};
