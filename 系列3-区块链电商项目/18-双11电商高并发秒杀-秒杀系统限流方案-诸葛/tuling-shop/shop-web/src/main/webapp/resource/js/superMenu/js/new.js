/*
 * Copyright 2021-2022 the original author or authors
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

$(document).ready(function(){
    $('.sidelist').mousemove(function(){
    	//console.log("wwww = " + $("#sidebar").width());
    	var ww = $("#sidebar").width();
    	//var cc = $("#sidebar").width()+"px";
    	$(this).find('.i-list').css("left",ww+"px")
    	//.css("width",ww+"px")
    	.show();
		$(this).find('h3').addClass('hover').css("width",ww+1+"px");
	});
	$('.sidelist').mouseleave(function(){
	$(this).find('.i-list').hide();
	$(this).find('h3').removeClass('hover');
	});
});

//slideUp