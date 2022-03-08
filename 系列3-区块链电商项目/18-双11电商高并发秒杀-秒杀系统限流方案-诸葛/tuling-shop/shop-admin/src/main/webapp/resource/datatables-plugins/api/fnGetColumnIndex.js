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
 * Maintenance of web-sites can often cause unexpected headaches, particularly
 * if the hardcoded index of an array (the columns in a DataTables instance)
 * needs to change due to an added or removed column. This plug-in function
 * will match a given string to the title of a column in the table and return
 * the column index, helping to overcome this problem.
 *
 *  @name fnGetColumnIndex
 *  @summary Get the column index by searching the column titles
 *  @author [Michael Ross](http://www.rosstechassociates.com/)
 *
 *  @param {string} sCol Column title to search for
 *  @returns {integer} Column index, or -1 if not found
 *
 *  @example
 *    var table = $('#example').dataTable();
 *    table.fnGetColumnIndex( 'Browser' );
 */

jQuery.fn.dataTableExt.oApi.fnGetColumnIndex = function ( oSettings, sCol )
{
	var cols = oSettings.aoColumns;
	for ( var x=0, xLen=cols.length ; x<xLen ; x++ )
	{
		if ( cols[x].sTitle.toLowerCase() == sCol.toLowerCase() )
		{
			return x;
		}
	}
	return -1;
};
