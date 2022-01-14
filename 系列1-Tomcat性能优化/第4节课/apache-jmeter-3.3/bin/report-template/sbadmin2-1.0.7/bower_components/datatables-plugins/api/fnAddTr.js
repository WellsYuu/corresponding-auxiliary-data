/*
 * Copyright 2021-2022 the original author or authors.
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
 * This method will add an existing `dt-tag tr` element to a DataTable. This can
 * be useful for maintaining custom classes and other attributes which have
 * been explicitly assigned to the row.
 *
 * DataTables 1.10+ has `dt-api row.add()` and `dt-api rows.add()` which have
 * this ability built in, and extend it to be able to use jQuery objects as well
 * as plain `dt-tag tr` elements. As such this method is marked deprecated, but
 * is available for use with legacy version of DataTables. Please use the
 * new API if you are used DataTables 1.10 or newer.
 *
 *  @name fnAddTr
 *  @summary Add a `dt-tag tr` element to the table
 *  @author [Allan Jardine](http://sprymedia.co.uk)
 *  @deprecated
 *
 *  @param {node} nTr `dt-tag tr` element to add to the table
 *  @param {boolean} [bRedraw=false] Indicate if the table should do a redraw or not.
 *
 *  @example
 *    var table = $('#example').dataTable();
 *    table.fnAddTr( $('<tr>'+
 *         '<td>1</td>'+
 *         '<td>2</td>'+
 *         '<td>3</td>'+
 *      '</tr>')[0]
 *    );
 */

jQuery.fn.dataTableExt.oApi.fnAddTr = function ( oSettings, nTr, bRedraw ) {
    if ( typeof bRedraw == 'undefined' )
    {
        bRedraw = true;
    }

    var nTds = nTr.getElementsByTagName('td');
    if ( nTds.length != oSettings.aoColumns.length )
    {
        alert( 'Warning: not adding new TR - columns and TD elements must match' );
        return;
    }

    var aData = [];
    var aInvisible = [];
    var i;
    for ( i=0 ; i<nTds.length ; i++ )
    {
        aData.push( nTds[i].innerHTML );
        if (!oSettings.aoColumns[i].bVisible)
        {
            aInvisible.push( i );
        }
    }

    /* Add the data and then replace DataTable's generated TR with ours */
    var iIndex = this.oApi._fnAddData( oSettings, aData );
    nTr._DT_RowIndex = iIndex;
    oSettings.aoData[ iIndex ].nTr = nTr;

    oSettings.aiDisplay = oSettings.aiDisplayMaster.slice();

    // Hidding invisible columns
    for ( i = (aInvisible.length - 1) ; i >= 0 ; i-- )
    {
		oSettings.aoData[iIndex]._anHidden[ i ] = nTds[aInvisible[i]];
		nTr.removeChild( nTds[aInvisible[i]] );
    }

	// Redraw
    if ( bRedraw )
    {
        this.oApi._fnReDraw( oSettings );
    }
};
