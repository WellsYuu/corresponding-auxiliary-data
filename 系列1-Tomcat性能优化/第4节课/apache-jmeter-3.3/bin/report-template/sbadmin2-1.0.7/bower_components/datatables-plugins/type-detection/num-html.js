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
 * This type-detection plug-in will look at an HTML string from a data cell,
 * strip the HTML tags and then check to see if the remaining data is numeric.
 * If it is, then the data can be sorted numerically with the Numbers with HTML
 * sorting plug-in.
 *
 * DataTables 1.10+ has numeric HTML data type and sorting abilities built-in.
 * As such this plug-in is marked as deprecated, but might be useful when
 * working with old versions of DataTables.
 *
 *  @name Numbers with HTML
 *  @summary Detect data which is a mix of HTML and numeric data.
 *  @deprecated
 *  @author [Allan Jardine](http://sprymedia.co.uk)
 */

jQuery.fn.dataTableExt.aTypes.unshift( function ( sData )
{
	sData = typeof sData.replace == 'function' ?
		sData.replace( /<[\s\S]*?>/g, "" ) : sData;
	sData = $.trim(sData);

	var sValidFirstChars = "0123456789-";
	var sValidChars = "0123456789.";
	var Char;
	var bDecimal = false;

	/* Check for a valid first char (no period and allow negatives) */
	Char = sData.charAt(0);
	if (sValidFirstChars.indexOf(Char) == -1)
	{
		return null;
	}

	/* Check all the other characters are valid */
	for ( var i=1 ; i<sData.length ; i++ )
	{
		Char = sData.charAt(i);
		if (sValidChars.indexOf(Char) == -1)
		{
			return null;
		}

		/* Only allowed one decimal place... */
		if ( Char == "." )
		{
			if ( bDecimal )
			{
				return null;
			}
			bDecimal = true;
		}
	}

	return 'num-html';
} );
