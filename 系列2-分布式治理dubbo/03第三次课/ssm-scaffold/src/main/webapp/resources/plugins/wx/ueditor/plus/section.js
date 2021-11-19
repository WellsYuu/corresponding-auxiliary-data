UE.plugins["sectionwxb"] = function() {
    var me = this
        , editor = this;
    var utils = baidu.editor.utils
        , Popup = baidu.editor.ui.Popup
        , Stateful = baidu.editor.ui.Stateful
        , uiUtils = baidu.editor.ui.uiUtils
        , UIBase = baidu.editor.ui.UIBase;
    var domUtils = baidu.editor.dom.domUtils;
    if(window.top.wxb){
        var clipboard=window.top.wxb.clipboard;
    }
    var clickPop = new baidu.editor.ui.Popup({
        content: "",
        editor: me,
        _remove: function() {
            wxbEditor.mainUE.undoManger.save();
            $(clickPop.anchorEl).remove();
            clickPop.hide();
            wxbEditor.mainUE.undoManger.save();
        },
        _copy: function() {
            var html=$(clickPop.anchorEl).prop('outerHTML');
            clipboard.set(html,'text');
            clickPop.hide();
        },
        _cut: function() {
            var html=$(clickPop.anchorEl).prop('outerHTML');
            clipboard.set(html,'text');
            $(clickPop.anchorEl).remove();
            clickPop.hide();
            wxbEditor.mainUE.undoManger.save();
        },
        select: function() {
            var range = wxbEditor.mainUE.selection.getRange();
            range.selectNode(clickPop.anchorEl);
            range.select();
        },
        _blank: function() {
            $('<p><br/></p>').insertAfter(clickPop.anchorEl);
            wxbEditor.mainUE.undoManger.save();
        },
        _preblank: function() {
            $('<p><br/></p>').insertBefore(clickPop.anchorEl);
            wxbEditor.mainUE.undoManger.save();
        },
        _wxbitem: null ,
        className: 'edui-bubble'
    });
    var editor_document = null ;
    var innerBody=me.body;

    var getInterval=setInterval(function(){
        if(innerBody!=undefined){
            innerBody.onpaste = function(e) {
                var pastedText = undefined;
                pastedText = e.clipboardData.getData('text/plain');
                alert(pastedText);
                return false;
            };
            clearInterval(getInterval);
        }
    },300);

    me.addListener("beforepaste", function (eventName, html, root) {
        var _html=html.html;
        if(_html.match('wxb-style')!=null){
            _html=_html.replace(/&lt;/gim,"<");
            _html=_html.replace(/&gt;/gim,">");
            _html=_html.replace(/&quot;/gim,'"');
        }

        var parser = $('<div>' + _html + '</div>');

        parser.find('img').each(function () {
            var url = this.src;
            /*url=window.top.Material.transforImgUrl(url);*/
            $(this).attr('src', url);
            $(this).attr('data-src', url);
            $(this).attr('_src', url);
        });
        html.html = parser.html();
    });

    me.addListener("click", function(t, evt) {
            evt = evt || window.event;
            var el = evt.target || evt.srcElement;
            var tagName=el.tagName;
            var $el=$(el);
            if(tagName=='body'){
                $el.find('.wxb-style.active').removeClass('active');
            }
            var body=$el.parents('body');
            body.find('.wxb-style.active').removeClass('active');
            if (tagName == 'AREA') {
                var usemap = $el.parent('map').attr('id');
                var imgel = $("img[usemap='#" + usemap + "']", editor_document).get(0);
                var range = wxbEditor.mainUE.selection.getRange();
                range.selectNode(imgel);
                range.select();
                return;
                if (wxbEditor.mainUE.ui._dialogs['insertimageDialog']) {
                    var range = wxbEditor.mainUE.selection.getRange();
                    range.selectNode(imgel);
                    range.select();
                    wxbEditor.mainUE.ui._dialogs['insertimageDialog'].open();
                }
                return;
            }
            if (tagName == "IMG") {

                return;
            }

            var match=false;
            if ($el.parent()[0].tagName !== 'BODY') {
                $el.parents().each(function(){
                    if ($(this).parent()[0].tagName == 'BODY') {
                        el=$(this)[0];//非 body > * 的节点，向上查找到 body 下的那一级的节点
                        if(el.tagName=="SECTION"){
                            match=true;
                            $el=$(el);
                        }
                    }
                });
            }else{
                if(el.tagName=="SECTION"){
                    match=true;
                }
            }

            if(match==false){
                return;
            }

            //135 会在最外层加上 <section class="135article"> content... </section>
            if($el.hasClass('135article')){
                $el.replaceWith($el.html());
            }
            $el.addClass('active');
            clickPop.render();
            var html = clickPop.formatHtml('<nobr class="otf-poptools">' +
            '<span class="copy" onclick="$$._copy()" stateful>' + '复制</span>' +
            '<span class="cut" onclick="$$._cut()" stateful>' + '剪切</span>' +
            '<span class="select" title="选中后，可以更换为文字样式" onclick="$$.select()" stateful>' + '选中</span>' +
            '<span onclick="$$._remove()" stateful>' + '删除</span>' +
            '<span class="_wxbbg hidden" onclick="$$._wxbbg()" stateful>' + '背景图</span>' +
            '<span class="_wxbvideo hidden" onclick="$$._wxbvideo()" stateful>' + '视频</span>' +
            '<span class="_wxbmusic hidden" onclick="$$._wxbmusic()" stateful>' + '音乐</span>' +
            '<span class="_wxbbdbg hidden" onclick="$$._wxbbdbg()" stateful>' + '边框</span>' +
            '<span onclick="$$._blank()" stateful>' + '下面添加空行</span>' +
            '<span onclick="$$._preblank()" stateful>' + '上面添加空行</span>' +
            '</nobr>');
            var content = clickPop.getDom('content');
            content.innerHTML = html;

            if ($el.find('.wxbbg').size()) {
                $(content).find('._wxbbg').removeClass('hidden');
            }
            if ($el.find('audio').size()) {
                $(content).find('._wxbmusic').removeClass('hidden');
            }
            if ($el.find('.video_iframe').size()) {
                $(content).find('._wxbvideo').removeClass('hidden');
            }
            var bdbg_flag = false;
            $el.find('*').each(function () {
                    if (this.style.background || this.style.border || this.style.borderBottom || this.style.borderTop || this.style.borderLeft || this.style.borderRight) {
                        bdbg_flag = true;
                    }
                }
            );

            clickPop.anchorEl = el;
            clickPop.showAnchor(clickPop.anchorEl);

        }
    );
};