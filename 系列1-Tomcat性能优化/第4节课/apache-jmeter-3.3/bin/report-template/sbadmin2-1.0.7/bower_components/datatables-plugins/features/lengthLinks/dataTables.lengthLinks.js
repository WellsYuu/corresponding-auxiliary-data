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

/*! Page length control via links for DataTables
 * 2014 SpryMedia Ltd - datatables.net/license
 */

(function(window, document, $, undefined) {


$.fn.dataTable.LengthLinks = function ( inst ) {
	var api = new $.fn.dataTable.Api( inst );
	var settings = api.settings()[0];
	var container = $('<div></div>').addClass( settings.oClasses.sLength );
	var lastLength = -1;

	// API so the feature wrapper can return the node to insert
	this.container = function () {
		return container[0];
	};

	// Listen for events to change the page length
	container.on( 'click.dtll', 'a', function (e) {
		e.preventDefault();
		api.page.len( $(this).data('length')*1 ).draw( false );
	} );

	// Update on each draw
	api.on( 'draw', function () {
		// No point in updating - nothing has changed
		if ( api.page.len() === lastLength ) {
			return;
		}

		var menu = settings.aLengthMenu;
		var lang = menu.length===2 && $.isArray(menu[0]) ? menu[1] : menu;
		var lens = menu.length===2 && $.isArray(menu[0]) ? menu[0] : menu;

		var out = $.map( lens, function (el, i) {
			return el == api.page.len() ?
				'<a class="active" data-length="'+lens[i]+'">'+lang[i]+'</a>' :
				'<a data-length="'+lens[i]+'">'+lang[i]+'</a>';
		} );

		container.html( settings.oLanguage.sLengthMenu.replace( '_MENU_', out.join(' | ') ) );
		lastLength = api.page.len();
	} );

	api.on( 'destroy', function () {
		container.off( 'click.dtll', 'a' );
	} );
};

// Subscribe the feature plug-in to DataTables, ready for use
$.fn.dataTable.ext.feature.push( {
	"fnInit": function( settings ) {
		var l = new $.fn.dataTable.LengthLinks( settings );
		return l.container();
	},
	"cFeature": "L",
	"sFeature": "LengthLinks"
} );


})(window, document, jQuery);
