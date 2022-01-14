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
 * DataTables has a built in type called `html` which will strip HTML tags 
 * from a search string, but it doesn't cope with nested HTML inside another 
 * element's attributes (for example DOM0 events with have HTML in them). This
 * plug-in function overrules the built-in method and provides complete HTML
 * tag removal.
 * 
 * Note that this function is not included in DataTables by
 * default because it is slightly slower than the built-in method, which is
 * good enough for by far the majority of use cases.
 *
 *  @summary Strip HTML using DOM methods
 *  @name html
 *  @author _guillimon_
 *
 *  @example
 *    $(document).ready(function() {
 *        $('#example').dataTable({
 *            "columnDefs": [
 *                { type: "html", target: 0 }
 *            ]
 *        });
 *    } );
 */

(function () {

var _div = document.createElement('div');

jQuery.fn.dataTable.ext.type.search.html = function ( data ) {
	_div.innerHTML = data;

	return _div.textContent ?
		_div.textContent.replace(/\n/g," ") :
		_div.innerText.replace(/\n/g," ");
};

})();
