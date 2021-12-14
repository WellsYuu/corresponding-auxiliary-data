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
 * When doing some heavy processing of your own (for example using fnOpen with
 * data loading from the server) it can be useful to make use of the
 * 'processing' indicator built-into DataTables. This plug-in function exposes
 * the internal DataTables function so it can be used for exactly this.
 *
 *  @name fnProcessingIndicator
 *  @summary Show and hide the DataTables processing element through the API.
 *  @author Allan Chappell
 *
 *  @param {boolean} [onoff=true] Show (`true`) or hide (`false`) the processing
 *    element.
 *
 *  @example
 *    var table = $('#example').dataTable();
 *    table.fnProcessingIndicator();      // On
 *    table.fnProcessingIndicator(false); // Off
 */

jQuery.fn.dataTableExt.oApi.fnProcessingIndicator = function ( oSettings, onoff )
{
	if ( onoff === undefined ) {
		onoff = true;
	}
	this.oApi._fnProcessingDisplay( oSettings, onoff );
};
