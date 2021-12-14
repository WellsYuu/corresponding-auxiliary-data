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
 * Automatically detect British (`dd/mm/yyyy`) date types. Goes with the UK 
 * date sorting plug-in.
 *
 *  @name Date (`dd/mm/yyyy`)
 *  @summary Detect data which is in the date format `dd/mm/yyyy`
 *  @author Andy McMaster
 */

jQuery.fn.dataTableExt.aTypes.unshift(
	function ( sData )
	{
		if (sData !== null && sData.match(/^(0[1-9]|[12][0-9]|3[01])\/(0[1-9]|1[012])\/(19|20|21)\d\d$/))
		{
			return 'date-uk';
		}
		return null;
	}
);
