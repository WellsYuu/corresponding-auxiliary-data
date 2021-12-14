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
 * When DataTables removes columns from the display (`bVisible` or
 * `fnSetColumnVis`) it removes these elements from the DOM, effecting the index
 * value for the column positions. This function converts the data column index
 * (i.e. all columns regardless of visibility) into a visible column index.
 *
 * DataTables 1.10+ has this ability built-in through the
 * `dt-api column.index()` method. As such this method is marked deprecated, but
 * is available for use with legacy version of DataTables.
 *
 *  @name fnColumnIndexToVisible
 *  @summary Convert a column data index to a visible index.
 *  @author [Allan Jardine](http://sprymedia.co.uk)
 *  @deprecated
 *
 *  @param {integer} iMatch Column data index to convert to visible index
 *  @returns {integer} Visible column index
 *
 *  @example
 *    var table = $('#example').dataTable( {
 *      aoColumnDefs: [
 *        { bVisible: false, aTargets: [1] }
 *      ]
 *    } );
 *
 *    // This will show 1
 *    alert( 'Column 2 visible index: '+table.fnColumnIndexToVisible(2) );
 */

jQuery.fn.dataTableExt.oApi.fnColumnIndexToVisible = function ( oSettings, iMatch )
{
	return oSettings.oApi._fnColumnIndexToVisible( oSettings, iMatch );
};
