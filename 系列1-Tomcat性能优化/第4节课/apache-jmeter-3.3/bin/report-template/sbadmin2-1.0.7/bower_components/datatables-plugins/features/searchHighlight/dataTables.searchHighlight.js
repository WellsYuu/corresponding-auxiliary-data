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

/*! SearchHighlight for DataTables v1.0.1
 * 2014 SpryMedia Ltd - datatables.net/license
 */

(function(window, document, $){


// Listen for DataTables initialisations
$(document).on( 'init.dt.dth', function (e, settings, json) {
	var table = new $.fn.dataTable.Api( settings );
	var body = $( table.table().body() );

	if (
		$( table.table().node() ).hasClass( 'searchHighlight' ) || // table has class
		settings.oInit.searchHighlight                          || // option specified
		$.fn.dataTable.defaults.searchHighlight                    // default set
	) {
		table
			.on( 'draw.dt.dth column-visibility.dt.dth', function () {
				// On each draw highlight search results, removing the old ones
				body.unhighlight();

				// Don't highlight the "not found" row
				if ( table.rows( { filter: 'applied' } ).data().length ) {
					body.highlight( table.search().split(' ') );
				}
			} )
			.on( 'destroy', function () {
				// Remove event handler
				table.off( 'draw.dt.dth column-visibility.dt.dth' );
			} );
	}
} );


})(window, document, jQuery);
