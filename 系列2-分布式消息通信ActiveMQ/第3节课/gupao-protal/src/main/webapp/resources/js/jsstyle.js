$(document).ready(function(){	

	$('#top #top_main .topright ul li .vip').hover(function(){
		$(this).css('background-position','0px -15px');
	},
	function(){
		$(this).css('background-position','0px 0px');
	}
	);
	$('#top #top_main .topright ul li').hover(function(){
		$(this).find('.phonetu').css('background-position','-128px -399px');
	},
	function(){
		$(this).find('.phonetu').css('background-position','-128px -360px');
	}
	);
	$('#serach .sou .kuang').click(function(){
		$(this).val('');
	});
	
	setInterval(auto, 2000);
	var n=1;
	function auto(){
		var newn = n % 2;
		$('#daohang .dhright .dhtu ul li').eq(newn).addClass('on').siblings().removeClass('on');
		n++;
	}

  // 大图轮播区域效果
	$('.nav li').mouseover(function(){
		clearInterval(time);
		index = $(this).index();
		nowzt(index);
	});
	$('.nav li').mouseout(function(){
		time = setInterval(lubo, 3000);
	});
	$('.pic li').mouseover(function(){
		clearInterval(time);
		index = $(this).index();
		nowzt(index);
	});
	$('.pic li').mouseout(function(){
		time = setInterval(lubo, 3000);
	});
	//时钟轮播
	var time = setInterval(lubo, 3000);
	//索引
	index = 0;
	function lubo(){
		//判断索引 如果index已经到最后一个,就把它赋值0
		//结果 = 条件表达式?真:假;
		index = index == 5 ? 0 : ++index;
		//当前状态
		nowzt(index);
	}

	function nowzt(index){
		//更新轮标class
		$('.nav li').eq(index).addClass('on').siblings().removeClass('on');
		//轮播图片
		$('.pic li').eq(index).fadeIn().siblings().hide();
	}

	  // 大图轮播区域结束

	   $('#main .mainleft .liulan .liuleft').hover(function(){
	   		$('#main .mainleft .liulan .liuleft .lefttu').css(
	  		{'background-position':'1px -6px','background-image':'url(images/15.png)'});
	  		$(this).css('background','#F3F3F3');
	   },
	   function(){
	   		$('#main .mainleft .liulan .liuleft .lefttu').css(
	  		{'background-position':'-83px -51px','background-image':'url(images/16.png)'});
	  		$(this).css('background','white');
	   }
	   );

	 $('#main .mainleft .liulan .liuright').hover(function(){
	 		$('#main .mainleft .liulan .liuright .righttu').css(
	  		{'background-position':'5px -42px','background-image':'url(images/15.png)'});
	  		$(this).css('background','#F3F3F3');
	 },
	 function(){
	 		$('#main .mainleft .liulan .liuright .righttu').css(
	  		{'background-position':'-50px -51px','background-image':'url(images/16.png)'});
	  		$(this).css('background','white');
	 }
	 ); 

	 // 点击移动
	 var width=606;
	 var w=0;
	 $('#main .mainleft .liulan .liuleft .lefttu').click(function(){
	 	w--;
	 	w = w == -1 ? 3 : w;
	 	// stop(0,1)立刻完成前面的动画
	 	$('#main .mainleft .liulan .liucenter ul').stop(0,1).animate({left:-w*width});
	 });
	  $('#main .mainleft .liulan .liuright .righttu').click(function(){
	 	w++;
		w %= 4;
		$('#main .mainleft .liulan .liucenter ul').stop(0,1).animate({left:-w*width});
	 });

	  $('#main .mainright .tubiao ul li.xiala').mouseover(function(){
	  	$('#main .mainright .tubiao ul').animate({'top':'-168px'});
	  })
	  $('#main .mainright .tubiao .tubiaola .close').click(function(){
	  	$('#main .mainright .tubiao ul').animate({'top':'0px'});
	  })

	$('#main .mainright .tubiao .tubiaola .close_menu ul li').mouseover(function(){
		var index1=$(this).index();
		$('#main .mainright .tubiao .tubiaola .c_main .box').eq(
			index1).show().siblings('').hide();	
		$(this).css('border-bottom','1px solid white').siblings().css(
			'border-bottom','1px solid #ccc');
	});
	$('#main .mainright .tubiao .tubiaola .c_main .box > ul > li').mouseover(function(){
		var index2=$(this).index();
		$('#main .mainright .tubiao .tubiaola .c_main .box .jpbox').eq(
			index2).show().siblings('.jpbox').hide();
			$(this).css('border-bottom','1px solid #E4393C').siblings().css(
			'border-bottom','1px solid #ccc');	
	});
	// 设置鼠标移入图片，图片向左移动10PX
	$('#lifewindow .lifemain .tuarea1').hover(
	function(){

		$('#lifewindow .lifemain .tuarea1 img').stop().animate({'left':'-10px'},300);
	},
	function(){
		$('#lifewindow .lifemain .tuarea1 img').stop().animate({'left':'0px'},300);
	}
	);
	$('#lifewindow .lifemain .tuarea2').hover(
	function(){

		$('#lifewindow .lifemain .tuarea2 img').stop().animate({'left':'-10px'},300);
	},
	function(){
		$('#lifewindow .lifemain .tuarea2 img').stop().animate({'left':'0px'},300);
	}
	);
	$('#lifewindow .lifemain .tuarea3').hover(
	function(){

		$('#lifewindow .lifemain .tuarea3 img').stop().animate({'left':'-10px'},300);
	},
	function(){
		$('#lifewindow .lifemain .tuarea3 img').stop().animate({'left':'0px'},300);
	}
	);
	$('#lifewindow .lifemain .tuarea4').hover(
	function(){

		$('#lifewindow .lifemain .tuarea4 img').stop().animate({'left':'-10px'},300);
	},
	function(){
		$('#lifewindow .lifemain .tuarea4 img').stop().animate({'left':'0px'},300);
	}
	);
	$('#lifewindow .lifemain .tuarea5').hover(
	function(){

		$('#lifewindow .lifemain .tuarea5 img').stop().animate({'left':'-10px'},300);
	},
	function(){
		$('#lifewindow .lifemain .tuarea5 img').stop().animate({'left':'0px'},300);
	}
	);
	$('#lifewindow .lifemain .tuarea6').hover(
	function(){

		$('#lifewindow .lifemain .tuarea6 img').stop().animate({'left':'-10px'},300);
	},
	function(){
		$('#lifewindow .lifemain .tuarea6 img').stop().animate({'left':'0px'},300);
	}
	);
	$('#lifewindow .lifemain .tuarea7').hover(
	function(){

		$('#lifewindow .lifemain .tuarea7 img').stop().animate({'left':'-10px'},300);
	},
	function(){
		$('#lifewindow .lifemain .tuarea7 img').stop().animate({'left':'0px'},300);
	}
	);
	$('#lifewindow .lifemain .tuarea8').hover(
	function(){

		$('#lifewindow .lifemain .tuarea8 img').stop().animate({'left':'-10px'},300);
	},
	function(){
		$('#lifewindow .lifemain .tuarea8 img').stop().animate({'left':'0px'},300);
	}
	);
	// 设置鼠标移入图片，图片向左移动结束

	// 1F滑块效果开始
	$('#onefloor .one_top ul li').mouseover(function(){
			var index=$(this).index();
			var f1_left=index*158;
			$('#onefloor .one_top ul .huakuai').stop().animate({'left':f1_left+'px'});
			$(this).find('h3').css('color','#E4393C');
			$(this).siblings('li').find('h3').css('color','#666');

			$('#onefloor .onemain .maincenter .cent1').eq(index).show().siblings('.cent1').hide();
	});
	// 1F滑块效果结束
	// 2F滑块效果开始
	$('#twofloor .one_top ul li').mouseover(function(){
			var index=$(this).index();
			var f2_left=index*158;
			$('#twofloor .one_top ul .huakuai').stop().animate({'left':f2_left+'px'});
			$(this).find('h3').css('color','#E4393C');
			$(this).siblings('li').find('h3').css('color','#666');

			$('#twofloor .onemain .maincenter .cent1').eq(index).show().siblings('.cent1').hide();
	});
	// 2F滑块效果结束
	// 3F滑块效果开始
	$('#threefloor .one_top ul li').mouseover(function(){
			var index=$(this).index();
			var f3_left=index*158;
			$('#threefloor .one_top ul .huakuai').stop().animate({'left':f3_left+'px'});
			$(this).find('h3').css('color','#E4393C');
			$(this).siblings('li').find('h3').css('color','#666');

			$('#threefloor .onemain .maincenter .cent1').eq(index).show().siblings('.cent1').hide();
	});
	// 3F滑块效果结束

	// 4F滑块效果开始
	$('#fourfloor .one_top ul li').mouseover(function(){
			var index=$(this).index();
			var f3_left=index*158;
			$('#fourfloor .one_top ul .huakuai').stop().animate({'left':f3_left+'px'});
			$(this).find('h3').css('color','#E4393C');
			$(this).siblings('li').find('h3').css('color','#666');

			$('#fourfloor .onemain .maincenter .cent1').eq(index).show().siblings('.cent1').hide();
	});
	// 4F滑块效果结束

	// 5F滑块效果开始
	$('#fivefloor .one_top ul li').mouseover(function(){
			var index=$(this).index();
			var f3_left=index*158;
			$('#fivefloor .one_top ul .huakuai').stop().animate({'left':f3_left+'px'});
			$(this).find('h3').css('color','#E4393C');
			$(this).siblings('li').find('h3').css('color','#666');

			$('#fivefloor .onemain .maincenter .cent1').eq(index).show().siblings('.cent1').hide();
	});
	// 5F滑块效果结束

	// 6F滑块效果开始
	$('#sixfloor .one_top ul li').mouseover(function(){
			var index=$(this).index();
			var f3_left=index*158;
			$('#sixfloor .one_top ul .huakuai').stop().animate({'left':f3_left+'px'});
			$(this).find('h3').css('color','#E4393C');
			$(this).siblings('li').find('h3').css('color','#666');

			$('#sixfloor .onemain .maincenter .cent1').eq(index).show().siblings('.cent1').hide();
	});
	// 6F滑块效果结束

	// 7F滑块效果开始
	$('#sevenfloor .one_top ul li').mouseover(function(){
			var index=$(this).index();
			var f3_left=index*158;
			$('#sevenfloor .one_top ul .huakuai').stop().animate({'left':f3_left+'px'});
			$(this).find('h3').css('color','#E4393C');
			$(this).siblings('li').find('h3').css('color','#666');

			$('#sevenfloor .onemain .maincenter .cent1').eq(index).show().siblings('.cent1').hide();
	});
	// 7F滑块效果结束

	// 8F滑块效果开始
	$('#eightfloor .one_top ul li').mouseover(function(){
			var index=$(this).index();
			var f3_left=index*158;
			$('#eightfloor .one_top ul .huakuai').stop().animate({'left':f3_left+'px'});
			$(this).find('h3').css('color','#E4393C');
			$(this).siblings('li').find('h3').css('color','#666');

			$('#eightfloor .onemain .maincenter .cent1').eq(index).show().siblings('.cent1').hide();
	});
	// 8F滑块效果结束

	// 1F轮播图效果开始
	$('#onefloor .onemain .maincenter .cent1 .cenlunbo ul li').mouseover(function(){
		var index=$(this).index();
		var tuleft=-index*473;
		$('#onefloor .onemain .maincenter .cent1 .cenlunbo .luntu').stop().animate(
			{'left':tuleft+'px'});
		$(this).css('background','#7ABD54').siblings('li').css('background','#ccc');
	});
	// 1F轮播图效果结束

	// 2F轮播图效果开始
	$('#twofloor .onemain .maincenter .cent1 .cenlunbo ul li').mouseover(function(){
		var index=$(this).index();
		var tuleft=-index*473;
		$('#twofloor .onemain .maincenter .cent1 .cenlunbo .luntu').stop().animate(
			{'left':tuleft+'px'});
		$(this).css('background','#7ABD54').siblings('li').css('background','#ccc');
	});
	// 2F轮播图效果结束

	// 4F轮播图效果开始
	$('#fourfloor .onemain .maincenter .cent1 .cenlunbo ul li').mouseover(function(){
		var index=$(this).index();
		var tuleft=-index*473;
		$('#fourfloor .onemain .maincenter .cent1 .cenlunbo .luntu').stop().animate(
			{'left':tuleft+'px'});
		$(this).css('background','#7ABD54').siblings('li').css('background','#ccc');
	});
	// 4F轮播图效果结束

	// 5F轮播图效果开始
	$('#fivefloor .onemain .maincenter .cent1 .cenlunbo ul li').mouseover(function(){
		var index=$(this).index();
		var tuleft=-index*473;
		$('#fivefloor .onemain .maincenter .cent1 .cenlunbo .luntu').stop().animate(
			{'left':tuleft+'px'});
		$(this).css('background','#7ABD54').siblings('li').css('background','#ccc');
	});
	// 5F轮播图效果结束

	// 6F轮播图效果开始
	$('#sixfloor .onemain .maincenter .cent1 .cenlunbo ul li').mouseover(function(){
		var index=$(this).index();
		var tuleft=-index*473;
		$('#sixfloor .onemain .maincenter .cent1 .cenlunbo .luntu').stop().animate(
			{'left':tuleft+'px'});
		$(this).css('background','#7ABD54').siblings('li').css('background','#ccc');
	});
	// 6F轮播图效果结束

	// 7F轮播图效果开始
	$('#sevenfloor .onemain .maincenter .cent1 .cenlunbo ul li').mouseover(function(){
		var index=$(this).index();
		var tuleft=-index*473;
		$('#sevenfloor .onemain .maincenter .cent1 .cenlunbo .luntu').stop().animate(
			{'left':tuleft+'px'});
		$(this).css('background','#7ABD54').siblings('li').css('background','#ccc');
	});
	// 7F轮播图效果结束

	// 8F轮播图效果开始
	$('#eightfloor .onemain .maincenter .cent1 .cenlunbo ul li').mouseover(function(){
		var index=$(this).index();
		var tuleft=-index*473;
		$('#eightfloor .onemain .maincenter .cent1 .cenlunbo .luntu').stop().animate(
			{'left':tuleft+'px'});
		$(this).css('background','#7ABD54').siblings('li').css('background','#ccc');
	});
	// 8F轮播图效果结束


	// 1F右侧轮播图效果开始
	$('#onefloor .onemain .mainright .righttu ul li').mouseover(function(){
		var index=$(this).index();
		var tuleft=-index*209;
		$('#onefloor .onemain .mainright .righttu .rightlun').stop().animate(
			{'left':tuleft+'px'});
		$(this).css('background','#7ABD54').siblings('li').css('background','#ccc');
	});
	// 2F右侧轮播图效果开始
	$('#twofloor .onemain .mainright .righttu ul li').mouseover(function(){
		var index=$(this).index();
		var tuleft=-index*209;
		$('#twofloor .onemain .mainright .righttu .rightlun').stop().animate(
			{'left':tuleft+'px'});
		$(this).css('background','#7ABD54').siblings('li').css('background','#ccc');
	});
	// 2F右侧轮播图效果结束
	// 3F右侧轮播图效果开始
	$('#threefloor .onemain .mainright .righttu ul li').mouseover(function(){
		var index=$(this).index();
		var tuleft=-index*209;
		$('#threefloor .onemain .mainright .righttu .rightlun').stop().animate(
			{'left':tuleft+'px'});
		$(this).css('background','#7ABD54').siblings('li').css('background','#ccc');
	});
	// 3F右侧轮播图效果结束

	// 4F右侧轮播图效果开始
	$('#fourfloor .onemain .mainright .righttu ul li').mouseover(function(){
		var index=$(this).index();
		var tuleft=-index*209;
		$('#fourfloor .onemain .mainright .righttu .rightlun').stop().animate(
			{'left':tuleft+'px'});
		$(this).css('background','#7ABD54').siblings('li').css('background','#ccc');
	});
	//4F右侧轮播图效果结束

	// 5F右侧轮播图效果开始
	$('#fivefloor .onemain .mainright .righttu ul li').mouseover(function(){
		var index=$(this).index();
		var tuleft=-index*209;
		$('#fivefloor .onemain .mainright .righttu .rightlun').stop().animate(
			{'left':tuleft+'px'});
		$(this).css('background','#7ABD54').siblings('li').css('background','#ccc');
	});
	//5F右侧轮播图效果结束

	// 6F右侧轮播图效果开始
	$('#sixfloor .onemain .mainright .righttu ul li').mouseover(function(){
		var index=$(this).index();
		var tuleft=-index*209;
		$('#sixfloor .onemain .mainright .righttu .rightlun').stop().animate(
			{'left':tuleft+'px'});
		$(this).css('background','#7ABD54').siblings('li').css('background','#ccc');
	});
	//6F右侧轮播图效果结束

	// 7F右侧轮播图效果开始
	$('#sevenfloor .onemain .mainright .righttu ul li').mouseover(function(){
		var index=$(this).index();
		var tuleft=-index*209;
		$('#sevenfloor .onemain .mainright .righttu .rightlun').stop().animate(
			{'left':tuleft+'px'});
		$(this).css('background','#7ABD54').siblings('li').css('background','#ccc');
	});
	//7F右侧轮播图效果结束

	// 8F右侧轮播图效果开始
	$('#eightfloor .onemain .mainright .righttu ul li').mouseover(function(){
		var index=$(this).index();
		var tuleft=-index*209;
		$('#eightfloor .onemain .mainright .righttu .rightlun').stop().animate(
			{'left':tuleft+'px'});
		$(this).css('background','#7ABD54').siblings('li').css('background','#ccc');
	});
	//8F右侧轮播图效果结束

	// 3F图片滑动效果开始
	$('.onemain .maincenter .cent1 .f3tu1 .f3tu1_1').hover(
	function(){

		$('.onemain .maincenter .cent1 .f3tu1 .f3tu1_1 img').stop().animate({'left':'-10px'},300);
	},
	function(){
		$('.onemain .maincenter .cent1 .f3tu1 .f3tu1_1 img').stop().animate({'left':'0px'},300);
	}
	);

	$('.onemain .maincenter .cent1 .f3tu1 .f3tu1_2').hover(
	function(){

		$('.onemain .maincenter .cent1 .f3tu1 .f3tu1_2 img').stop().animate({'left':'-10px'},300);
	},
	function(){
		$('.onemain .maincenter .cent1 .f3tu1 .f3tu1_2 img').stop().animate({'left':'0px'},300);
	}
	);

	$('.onemain .maincenter .cent1 .f3tu2').hover(
	function(){

		$('.onemain .maincenter .cent1 .f3tu2 img').stop().animate({'left':'-10px'},300);
	},
	function(){
		$('.onemain .maincenter .cent1 .f3tu2 img').stop().animate({'left':'0px'},300);
	}
	);

	$('.onemain .maincenter .cent1 .f3tu3 .f3tu1_3').hover(
	function(){

		$('.onemain .maincenter .cent1 .f3tu3 .f3tu1_3 img').stop().animate({'left':'-10px'},300);
	},
	function(){
		$('.onemain .maincenter .cent1 .f3tu3 .f3tu1_3 img').stop().animate({'left':'0px'},300);
	}
	);

	$('.onemain .maincenter .cent1 .f3tu3 .f3tu1_4').hover(
	function(){

		$('.onemain .maincenter .cent1 .f3tu3 .f3tu1_4 img').stop().animate({'left':'-10px'},300);
	},
	function(){
		$('.onemain .maincenter .cent1 .f3tu3 .f3tu1_4 img').stop().animate({'left':'0px'},300);
	}
	);

	$('.onemain .maincenter .cent1 .f3tu4').hover(
	function(){

		$('.onemain .maincenter .cent1 .f3tu4 img').stop().animate({'left':'-10px'},300);
	},
	function(){
		$('.onemain .maincenter .cent1 .f3tu4 img').stop().animate({'left':'0px'},300);
	}
	);

	$('.onemain .maincenter .cent1 .f3tu5 .f3tu1_5').hover(
	function(){

		$('.onemain .maincenter .cent1 .f3tu5 .f3tu1_5 img').stop().animate({'left':'-10px'},300);
	},
	function(){
		$('.onemain .maincenter .cent1 .f3tu5 .f3tu1_5 img').stop().animate({'left':'0px'},300);
	}
	);

	$('.onemain .maincenter .cent1 .f3tu5 .f3tu1_6').hover(
	function(){

		$('.onemain .maincenter .cent1 .f3tu5 .f3tu1_6 img').stop().animate({'left':'-10px'},300);
	},
	function(){
		$('.onemain .maincenter .cent1 .f3tu5 .f3tu1_6 img').stop().animate({'left':'0px'},300);
	}
	);
	// 3F图片滑动效果结束

	// 京东晒单效果
	$(function(){
		setInterval(function(){
			$('#hot .hotleft .shaidan .shaimain ul li').last().css('height','0');
			$('#hot .hotleft .shaidan .shaimain ul').prepend($('#hot .hotleft .shaidan .shaimain ul li').last());
			$('#hot .hotleft .shaidan .shaimain ul li').first().animate({'height':'80px'},500);
		},5000)
	})
	// 京东晒单效果

	// 京东热门效果
	$(function(){
		setInterval(function(){
			$('#hot .hotleft .shaidan1 .shaimain ul li').last().css('height','0');
			$('#hot .hotleft .shaidan1 .shaimain ul').prepend($('#hot .hotleft .shaidan1 .shaimain ul li').last());
			$('#hot .hotleft .shaidan1 .shaimain ul li').first().animate({'height':'80px'},500);
		},5000)
	});
	// 京东热门效果

// 判断输入框的内容
	$('#hot .hotright .readbottom .shuru').click(function(){
		if($(this).val()=="请输入您的Email地址")
		{
			$(this).val('');
		}	
	});
	$('#hot .hotright .readbottom .shuru').blur(function(){
		if($(this).val()==''){
			$(this).val('请输入您的Email地址');
			$(this).css('color','#999');
		}		
	});
// 判断输入框的内容

// 跳转页面开始
	$(function(){

		$('a').click(function(){
			if(!$(this).attr('href')){
				//打开新页面
				window.open('404.html','_blank');
				return false;
			}
		})
	});
// 跳转页面结束
$(".fu ul li a").click(function(){
        var href = $(this).attr("href");
        var pos = $(href).offset().top;
        $("html,body").animate({scrollTop: pos}, 500);
        return false;
    });

// 悬浮开始

window.onscroll=function(){
		if($(document).scrollTop()>0){
			$('.fu').show();
		}else{
			$('.fu').hide();
		}
		var res=$(document).scrollTop();
		$('.futuhide').css('display','none');
		if(res>1000 && res<1400){
			index = 0;
		}else if (res>1400 && res<1800) {
			index = 1;
		}else if (res>1800 && res<2200) {
			index = 2;
		}else if (res>2200 && res<2600) {
			index = 3;
		}else if (res>2600 && res<3000) {
			index = 4;
		}else if (res>3000 && res<3400) {
			index = 5;
		}else if (res>3400 && res<3800) {
			index = 6;
		}else if (res>3800 && res<4200) {
			index = 7;
		}else{
			index = false;
		}
		index !== false?$('.fu ul li').eq(index).find(
			'.futuhide').css('display','block'):true;
		// $('title').text(res);
}
// 悬浮结束
})
