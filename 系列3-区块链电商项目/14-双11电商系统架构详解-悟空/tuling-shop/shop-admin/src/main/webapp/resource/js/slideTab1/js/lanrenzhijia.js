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

/* 代码整理：懒人之家 www.lanrenzhijia.com */
$(function(){		
	//设计案例切换
	$('.title-list li').mouseover(function(){
		var liindex = $('.title-list li').index(this);
		$(this).addClass('on').siblings().removeClass('on');
		$('.product-wrap div.product').eq(liindex).fadeIn(150).siblings('div.product').hide();
		var liWidth = $('.title-list li').width();
		$('.lanrenzhijia .title-list p').stop(false,true).animate({'left' : liindex * liWidth + 'px'},300);
	});
	
	//设计案例hover效果
	$('.product-wrap .product li').hover(function(){
		$(this).css("border-color","#ff6600");
		$(this).find('p > a').css('color','#ff6600');
	},function(){
		$(this).css("border-color","#fafafa");
		$(this).find('p > a').css('color','#666666');
	});
	});


/* 代码整理：懒人之家 www.lanrenzhijia.com */