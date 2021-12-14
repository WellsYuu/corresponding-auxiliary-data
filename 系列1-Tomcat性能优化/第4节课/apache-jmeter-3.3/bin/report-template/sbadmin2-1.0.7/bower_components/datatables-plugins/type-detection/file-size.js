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
 * Detect "file size" type columns automatically. Commonly used for computer
 * file sizes, this can allow sorting to take the order of magnitude indicated
 * by the label (GB etc) into account.
 *
 *  @name File size
 *  @summary Detect abbreviated file size data (8MB, 4KB etc)
 *  @author _anjibman_
 */

jQuery.fn.dataTableExt.aTypes.unshift(
	function ( sData )
	{
		var sValidChars = "0123456789";
		var Char;

		/* Check the numeric part */
		for ( var i=0 ; i<(sData.length - 3) ; i++ )
		{
			Char = sData.charAt(i);
			if (sValidChars.indexOf(Char) == -1)
			{
				return null;
			}
		}

		/* Check for size unit KB, MB or GB */
		if ( sData.substring(sData.length - 2, sData.length) == "KB"
			|| sData.substring(sData.length - 2, sData.length) == "MB"
			|| sData.substring(sData.length - 2, sData.length) == "GB" )
		{
			return 'file-size';
		}
		return null;
	}
);
