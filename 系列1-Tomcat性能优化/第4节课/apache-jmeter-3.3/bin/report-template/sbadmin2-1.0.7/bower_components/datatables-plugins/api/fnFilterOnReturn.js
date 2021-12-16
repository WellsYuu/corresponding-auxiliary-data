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
 * This plug-in removes the default behaviour of DataTables to filter on each
 * keypress, and replaces with it the requirement to press the enter key to
 * perform the filter.
 *
 *  @name fnFilterOnReturn
 *  @summary Require the return key to be pressed to filter a table
 *  @author [Jon Ranes](http://www.mvccms.com/)
 *
 *  @returns {jQuery} jQuery instance
 *
 *  @example
 *    $(document).ready(function() {
 *        $('.dataTable').dataTable().fnFilterOnReturn();
 *    } );
 */

jQuery.fn.dataTableExt.oApi.fnFilterOnReturn = function (oSettings) {
	var _that = this;

	this.each(function (i) {
		$.fn.dataTableExt.iApiIndex = i;
		var $this = this;
		var anControl = $('input', _that.fnSettings().aanFeatures.f);
		anControl
			.unbind('keyup search input')
			.bind('keypress', function (e) {
				if (e.which == 13) {
					$.fn.dataTableExt.iApiIndex = i;
					_that.fnFilter(anControl.val());
				}
			});
		return this;
	});
	return this;
};
