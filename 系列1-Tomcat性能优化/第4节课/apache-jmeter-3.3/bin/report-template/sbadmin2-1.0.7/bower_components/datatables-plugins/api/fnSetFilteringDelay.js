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
 * Enables filtration delay for keeping the browser more responsive while 
 * searching for a longer keyword.
 *
 * This can be particularly useful when working with server-side processing,
 * where you wouldn't typically want an Ajax request to be made with every key
 * press the user makes when searching the table.
 *
 *  @name fnSetFilteringDelay
 *  @summary Add a key debouce delay to the global filtering input of a table
 *  @author [Zygimantas Berziunas](http://www.zygimantas.com/), 
 *    [Allan Jardine](http://www.sprymedia.co.uk/) and _vex_
 *
 *  @example
 *    $(document).ready(function() {
 *        $('.dataTable').dataTable().fnSetFilteringDelay();
 *    } );
 */

jQuery.fn.dataTableExt.oApi.fnSetFilteringDelay = function ( oSettings, iDelay ) {
	var _that = this;

	if ( iDelay === undefined ) {
		iDelay = 250;
	}

	this.each( function ( i ) {
		$.fn.dataTableExt.iApiIndex = i;
		var
			$this = this,
			oTimerId = null,
			sPreviousSearch = null,
			anControl = $( 'input', _that.fnSettings().aanFeatures.f );

			anControl.unbind( 'keyup search input' ).bind( 'keyup search input', function() {
			var $$this = $this;

			if (sPreviousSearch === null || sPreviousSearch != anControl.val()) {
				window.clearTimeout(oTimerId);
				sPreviousSearch = anControl.val();
				oTimerId = window.setTimeout(function() {
					$.fn.dataTableExt.iApiIndex = i;
					_that.fnFilter( anControl.val() );
				}, iDelay);
			}
		});

		return this;
	} );
	return this;
};
