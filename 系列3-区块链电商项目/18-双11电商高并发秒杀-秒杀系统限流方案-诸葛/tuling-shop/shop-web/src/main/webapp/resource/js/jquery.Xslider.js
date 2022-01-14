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
 * @package Xslider - A slider plugin for jQuery
 * @version 0.5
 * @author xhowhy <http://x1989.com> 
 **/
;(function($){
    $.fn.Xslider = function(options){var settings ={
            affect: 'scrollx', //Ч��  ��scrollx|scrolly|fade|none
            speed: 1200, //�����ٶ�
            space: 6000, //ʱ����
            auto: true, //�Զ�����
            trigger: 'mouseover', //�����¼� ע����mouseover����hover
            conbox: '.conbox', //��������id��class
            ctag: 'a', //���ݱ�ǩ Ĭ��Ϊ<a>
            switcher: '.switcher', //�л�������id��class
            stag: 'a', //�л�����ǩ Ĭ��Ϊa
            current:'cur', //��ǰ�л�����ʽ����
            rand:false //�Ƿ����ָ��Ĭ�ϻõ�ͼƬ
        };
        settings = $.extend({}, settings, options);
        var index = 1;
        var last_index = 0;
        var $conbox = $(this).find(settings.conbox),$contents = $conbox.find(settings.ctag);
        var $switcher = $(this).find(settings.switcher),$stag = $switcher.find(settings.stag);
        if(settings.rand) {index = Math.floor(Math.random()*$contents.length);slide();}
        if(settings.affect == 'fade'){$.each($contents,function(k, v){(k === 0) ? $(this).css({'position':'absolute','z-index':9}):$(this).css({'position':'absolute','z-index':1,'opacity':0});
            });
        }
        function slide(){if (index >= $contents.length) index = 0;
            $stag.removeClass(settings.current).eq(index).addClass(settings.current);
            switch(settings.affect){case 'scrollx':
                    $conbox.width($contents.length*$contents.width());
                    $conbox.stop().animate({left:-$contents.width()*index},settings.speed);
                    break;
                case 'scrolly':
                    $contents.css({display:'block'});
                    $conbox.stop().animate({top:-$contents.height()*index+'px'},settings.speed);
                    break;
                case 'fade':
                    $contents.eq(last_index).stop().animate({'opacity': 0}, settings.speed/2).css('z-index',1)
                             .end()
                             .eq(index).css('z-index',9).stop().animate({'opacity': 1}, settings.speed/2)
                    break;
                case 'none':
                    $contents.hide().eq(index).show();
                    break;
            }
            last_index = index;
            index++;
        };
        if(settings.auto) var Timer = setInterval(slide, settings.space);
        $stag.bind(settings.trigger,function(){_pause()
            index = $(this).index();
            slide();
            _continue()
        });
        $conbox.hover(_pause,_continue);
        function _pause(){
            clearInterval(Timer);
        }
        function _continue(){
            if(settings.auto)Timer = setInterval(slide, settings.space);
        }    
    }
})(jQuery);