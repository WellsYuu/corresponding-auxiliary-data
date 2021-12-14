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
 * Creates `rowspan` cells in a column when there are two or more cells in a 
 * row with the same content, effectively grouping them together visually. 
 * 
 * **Note** - this plug-in currently only operates correctly with 
 * **server-side processing**.
 *
 *  @name fnFakeRowspan
 *  @summary Create a rowspan for cells which share data
 *  @author Fredrik Wendel
 *
 *  @param {interger} iColumn Column index to have row span
 *  @param {boolean} [bCaseSensitive=true] If the data check should be case
 *    sensitive or not.
 *  @returns {jQuery} jQuery instance
 *
 *  @example
 *    $('#example').dataTable().fnFakeRowspan(3);
 */

jQuery.fn.dataTableExt.oApi.fnFakeRowspan = function ( oSettings, iColumn, bCaseSensitive ) {
	/* Fail silently on missing/errorenous parameter data. */
	if (isNaN(iColumn)) {
		return false;
	}

	if (iColumn < 0 || iColumn > oSettings.aoColumns.length-1) {
		alert ('Invalid column number choosen, must be between 0 and ' + (oSettings.aoColumns.length-1));
		return false;
	}

	bCaseSensitive = (typeof(bCaseSensitive) != 'boolean' ? true : bCaseSensitive);

	function fakeRowspan () {
		var firstOccurance = null,
			value = null,
			rowspan = 0;
		jQuery.each(oSettings.aoData, function (i, oData) {
			var val = oData._aData[iColumn],
				cell = oData.nTr.childNodes[iColumn];
			/* Use lowercase comparison if not case-sensitive. */
			if (!bCaseSensitive) {
				val = val.toLowerCase();
			}
			/* Reset values on new cell data. */
			if (val != value) {
				value = val;
				firstOccurance = cell;
				rowspan = 0;
			}

			if (val == value) {
				rowspan++;
			}

			if (firstOccurance !== null && val == value && rowspan > 1) {
				oData.nTr.removeChild(cell);
				firstOccurance.rowSpan = rowspan;
			}
		});
	}

	oSettings.aoDrawCallback.push({ "fn": fakeRowspan, "sName": "fnFakeRowspan" });

	return this;
};
