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
 * This plug-in will strip out non-numeric formatting characters such that a
 * formatted number (for example 1,000,000) can be detected automatically and
 * sorted numerically. Note that characters a-z are not automatically removed,
 * otherwise there is a risk of detecting columns as numeric which should not
 * be.
 *
 * DataTables 1.10+ has formatted number type detection and sorting abilities
 * built-in. As such this plug-in is marked as deprecated, but might be useful
 * when working with old versions of DataTables.
 *
 *  @name Formatted numbers
 *  @summary formatted_numbers
 *  @deprecated
 *  @author [Allan Jardine](http://sprymedia.co.uk)
 */

jQuery.fn.dataTableExt.aTypes.unshift(
	function ( sData )
	{
		var deformatted = sData.replace(/[^\d\-\.\/a-zA-Z]/g,'');
		if ( $.isNumeric( deformatted ) || deformatted === "-" ) {
			return 'formatted-num';
		}
		return null;
	}
);
