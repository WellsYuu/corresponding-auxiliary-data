var SITE_PUBLIC = SITE_PUBLIC || 'http://m.iweite.com/';
var EditorCustom=window.EditorCustom||{};

EditorCustom.ueMyStyle=null;

EditorCustom.init= function (ue,callback) {
    //引导关注按钮点击执行
    ue.registerCommand('fork', {
        execCommand: function () {
            EditorCustom.renderCon(0,callback);
        }
    });

    //标题按钮点击执行
    ue.registerCommand('title', {
        execCommand: function () {
            EditorCustom.renderCon(1,callback);
        }
    });

    //内容按钮点击执行
    ue.registerCommand('con', {
        execCommand: function () {
            EditorCustom.renderCon(2,callback);
        }
    });

    //互推按钮点击执行
    ue.registerCommand('hutui', {
        execCommand: function () {
            EditorCustom.renderCon(3,callback);
        }
    });

    //分割线按钮点击执行
    ue.registerCommand('division', {
        execCommand: function () {
            EditorCustom.renderCon(4,callback);
        }
    });

    //阅读原文按钮点击执行
    ue.registerCommand('guide', {
        execCommand: function () {
            EditorCustom.renderCon(5,callback);
        }
    });

    //其他按钮点击执行
    ue.registerCommand('other', {
        execCommand: function () {
            EditorCustom.renderCon(6,callback);
        }
    });

    //我的样式
    ue.registerCommand('mystyle', {
        execCommand: function () {
            EditorCustom.renderCon(7,callback);
        }
    });
};

EditorCustom.registerAll = function (UE) {
    //关注引导
    UE.registerUI('fork', function (editor, uiName) {
        //注册按钮执行时的command命令，使用命令默认就会带有回退操作
        //创建一个button
        var btn = new UE.ui.Button({
            //按钮的名字
            name: uiName,
            //提示
            title: '关注引导样式',
            //需要添加的额外样式，指定icon图标，这里默认使用一个重复的icon
            cssRules: 'background-position: -928px -0px;width:61px!important;height:20px!important;',
            //点击时执行的命令
            onclick: function () {
                //这里可以不用执行命令,做你自己的操作也可
                editor.execCommand(uiName);
            }
        });

        //当点到编辑内容上时，按钮要做的状态反射
        editor.addListener('selectionchange', function () {
            var state = editor.queryCommandState(uiName);
            if (state == -1) {
                btn.setDisabled(true);
                btn.setChecked(false);
            } else {
                btn.setDisabled(false);
                btn.setChecked(state);
            }
        });
        //返回这个button
        return btn;
    });

    //标题
    UE.registerUI('title', function (editor, uiName) {
        //注册按钮执行时的command命令，使用命令默认就会带有回退操作
        //创建一个button
        var btn = new UE.ui.Button({
            //按钮的名字
            name: uiName,
            //提示
            title: '标题区样式',
            //需要添加的额外样式，指定icon图标，这里默认使用一个重复的icon
            cssRules: 'background-position: -887px -0px;width:39px!important;height:20px!important;',
            //点击时执行的命令
            onclick: function () {
                //这里可以不用执行命令,做你自己的操作也可
                editor.execCommand(uiName);
            }
        });

        //当点到编辑内容上时，按钮要做的状态反射
        editor.addListener('selectionchange', function () {
            var state = editor.queryCommandState(uiName);
            if (state == -1) {
                btn.setDisabled(true);
                btn.setChecked(false);
            } else {
                btn.setDisabled(false);
                btn.setChecked(state);
            }
        });
        //返回这个button
        return btn;
    });


    //内容
    UE.registerUI('con', function (editor, uiName) {
        //注册按钮执行时的command命令，使用命令默认就会带有回退操作
        //创建一个button
        var btn = new UE.ui.Button({
            //按钮的名字
            name: uiName,
            //提示
            title: '内容区样式',
            //需要添加的额外样式，指定icon图标，这里默认使用一个重复的icon
            cssRules: 'background-position: -836px -0px;width:48px!important;height:20px!important;',
            //点击时执行的命令
            onclick: function () {
                //这里可以不用执行命令,做你自己的操作也可
                editor.execCommand(uiName);
            }
        });

        //当点到编辑内容上时，按钮要做的状态反射
        editor.addListener('selectionchange', function () {
            var state = editor.queryCommandState(uiName);
            if (state == -1) {
                btn.setDisabled(true);
                btn.setChecked(false);
            } else {
                btn.setDisabled(false);
                btn.setChecked(state);
            }
        });
        //返回这个button
        return btn;
    });

    //互推
    UE.registerUI('hutui', function (editor, uiName) {
        //注册按钮执行时的command命令，使用命令默认就会带有回退操作
        //创建一个button
        var btn = new UE.ui.Button({
            //按钮的名字
            name: uiName,
            //提示
            title: '互推',
            //需要添加的额外样式，指定icon图标，这里默认使用一个重复的icon
            cssRules: 'background-position: -836px -45px;width:62px!important;height:20px!important;',
            //点击时执行的命令
            onclick: function () {
                //这里可以不用执行命令,做你自己的操作也可
                editor.execCommand(uiName);
            }
        });

        //当点到编辑内容上时，按钮要做的状态反射
        editor.addListener('selectionchange', function () {
            var state = editor.queryCommandState(uiName);
            if (state == -1) {
                btn.setDisabled(true);
                btn.setChecked(false);
            } else {
                btn.setDisabled(false);
                btn.setChecked(state);
            }
        });
        //返回这个button
        return btn;
    });

//分割
    UE.registerUI('division', function (editor, uiName) {
        //注册按钮执行时的command命令，使用命令默认就会带有回退操作
        //创建一个button
        var btn = new UE.ui.Button({
            //按钮的名字
            name: uiName,
            //提示
            title: '分割线样式',
            //需要添加的额外样式，指定icon图标，这里默认使用一个重复的icon
            cssRules: 'background-position: -903px -22px;width:50px!important;height:20px!important;',
            //点击时执行的命令
            onclick: function () {
                //这里可以不用执行命令,做你自己的操作也可
                editor.execCommand(uiName);
            }
        });

        //当点到编辑内容上时，按钮要做的状态反射
        editor.addListener('selectionchange', function () {
            var state = editor.queryCommandState(uiName);
            if (state == -1) {
                btn.setDisabled(true);
                btn.setChecked(false);
            } else {
                btn.setDisabled(false);
                btn.setChecked(state);
            }
        });
        //返回这个button
        return btn;
    });


    //原文引导
    UE.registerUI('guide', function (editor, uiName) {
        //注册按钮执行时的command命令，使用命令默认就会带有回退操作
        //创建一个button
        var btn = new UE.ui.Button({
            //按钮的名字
            name: uiName,
            //提示
            title: '引导阅读样式',
            //需要添加的额外样式，指定icon图标，这里默认使用一个重复的icon
            cssRules: 'background-position: -836px -22px;width:62px!important;height:20px!important;',
            //点击时执行的命令
            onclick: function () {
                //这里可以不用执行命令,做你自己的操作也可
                editor.execCommand(uiName);
            }
        });

        //当点到编辑内容上时，按钮要做的状态反射
        editor.addListener('selectionchange', function () {
            var state = editor.queryCommandState(uiName);
            if (state == -1) {
                btn.setDisabled(true);
                btn.setChecked(false);
            } else {
                btn.setDisabled(false);
                btn.setChecked(state);
            }
        });
        //返回这个button
        return btn;
    });

    //其他
    UE.registerUI('other', function (editor, uiName) {
        //注册按钮执行时的command命令，使用命令默认就会带有回退操作
        //创建一个button
        var btn = new UE.ui.Button({
            //按钮的名字
            name: uiName,
            //提示
            title: '其他样式',
            //需要添加的额外样式，指定icon图标，这里默认使用一个重复的icon
            cssRules: 'background-position:-955px -22px;width:41px!important;height:20px!important;',
            //点击时执行的命令
            onclick: function () {
                //这里可以不用执行命令,做你自己的操作也可
                editor.execCommand(uiName);
            }
        });

        //当点到编辑内容上时，按钮要做的状态反射
        editor.addListener('selectionchange', function () {
            var state = editor.queryCommandState(uiName);
            if (state == -1) {
                btn.setDisabled(true);
                btn.setChecked(false);
            } else {
                btn.setDisabled(false);
                btn.setChecked(state);
            }
        });
        //返回这个button
        return btn;
    });

    //我的样式
    UE.registerUI('mystyle', function (editor, uiName) {
        //注册按钮执行时的command命令，使用命令默认就会带有回退操作
        //创建一个button
        var btn = new UE.ui.Button({
            //按钮的名字
            name: uiName,
            //提示
            title: '其他样式',
            //需要添加的额外样式，指定icon图标，这里默认使用一个重复的icon
            cssRules: 'background-position:-902px -45px;width:63px!important;height:20px!important;',
            //点击时执行的命令
            onclick: function () {
                //这里可以不用执行命令,做你自己的操作也可
                editor.execCommand(uiName);
            }
        });

        //当点到编辑内容上时，按钮要做的状态反射
        editor.addListener('selectionchange', function () {
            var state = editor.queryCommandState(uiName);
            if (state == -1) {
                btn.setDisabled(true);
                btn.setChecked(false);
            } else {
                btn.setDisabled(false);
                btn.setChecked(state);
            }
        });
        //返回这个button
        return btn;
    });

}


EditorCustom.renderCon = function(num,callback) {
    //随机数，防止请求缓存
    var date = Date.now();
    //获取iframe中html，填充到win.alert的content中
    var wxStyle = Win.alert({
        title: '微特素材微信样式',
        width: 842,
        url: SITE_PUBLIC + 'style.php',
        callback: function () {
            //判断显示当前点击的tab内容
            var J_tab = $(".J_tab li").eq(num),
                J_pannel = $(".J_pannel .tab-pane").eq(num);

            if (J_tab.prop("class") !== "active") {
                J_tab.addClass('active')
                J_tab.siblings().removeClass('active')
            } else {
                J_tab.siblings().removeClass('active')
            }

            if (J_pannel.prop("class") !== "active in") {
                J_pannel.addClass('active in')
                J_pannel.siblings().removeClass('active in')
            } else {
                J_pannel.siblings().removeClass('active in')
            }

            //实例化取色器
            var t = $.farbtastic("#titPic"), //标题区取色器
                f = $.farbtastic("#picker"), //内容区取色器
                h = $.farbtastic("#htPic"); //互推区取色器

            //标题区取色器
            function titleColor() {
                t.linkTo(function () {
                    $("#colorOne").css({'border-color': this.color, 'border-width': "1px"}).val(this.color)
                    if ($(".selectedStyleoOne .bkcolor")) {
                        $(".selectedStyleoOne .bkcolor").css('background-color', arguments[0])
                    }
                    if ($(".selectedStyleoOne .brcolor")) {
                        $(".selectedStyleoOne .brcolor").css('border-color', arguments[0])
                    }
                    if ($(".selectedStyleoOne .bfcolor")) {
                        $(".selectedStyleoOne .bfcolor").css({'border-color': arguments[0], 'color': arguments[0]})
                    }
                    if ($(".selectedStyleoOne .btcolor")) {
                        val = arguments[0] + " transparent";
                        $(".selectedStyleoOne .btcolor").css('border-color', val)
                    }
                });
                $("#colorOne").keyup(function (event) {
                    var reg = new RegExp("#", "g");
                    var color = $(this).val().replace(reg, "");
                    t.setColor("#" + color);
                });
            }

            titleColor();

            //内容区取色器
            function conColor() {
                f.linkTo(function () {
                    $("#colorTwo").css({'border-color': this.color, 'border-width': "1px"}).val(this.color)
                    if ($(".selectedStyle .bkcolor")) {
                        $(".selectedStyle .bkcolor").css('background-color', arguments[0])
                    }
                    if ($(".selectedStyle .brcolor")) {
                        $(".selectedStyle .brcolor").css('border-color', arguments[0])
                    }
                    if ($(".selectedStyle .bfcolor")) {
                        $(".selectedStyle .bfcolor").css({'border-color': arguments[0], 'color': arguments[0]})
                    }

                    if ($(".selectedStyle .btcolor")) {
                        value = arguments[0] + ' transparent';
                        $(".selectedStyle .btcolor").css('border-color', value)
                    }

                });
                $("#colorTwo").keyup(function (event) {
                    var reg = new RegExp("#", "g");
                    var color = $(this).val().replace(reg, "");
                    f.setColor("#" + color);
                });
            }

            conColor();

            //互推区取色器
            function hutuiColor() {
                h.linkTo(function () {
                    $("#colorThree").css({'border-color': this.color, 'border-width': "1px"}).val(this.color)
                    if ($(".selectedStyleoTwo .bkcolor")) {
                        $(".selectedStyleoTwo .bkcolor").css('background-color', arguments[0])
                    }
                    if ($(".selectedStyleoTwo .brcolor")) {
                        $(".selectedStyleoTwo .brcolor").css('border-color', arguments[0])
                    }
                    if ($(".selectedStyleoTwo .bfcolor")) {
                        $(".selectedStyleoTwo .bfcolor").css({'border-color': arguments[0], 'color': arguments[0]})
                    }

                });
                $("#colorThree").keyup(function (event) {
                    var reg = new RegExp("#", "g");
                    var color = $(this).val().replace(reg, "");
                    h.setColor("#" + color);
                });

            }

            hutuiColor();

            //如果是我的样式tab
            function MyStyle() {

                //判断是否已经实例化，有就销毁
                if (!EditorCustom.ueMyStyle == "") {
                    EditorCustom.ueMyStyle.destroy()
                }

                //初始化编辑器
                EditorCustom.ueMyStyle = UE.getEditor('editorTwo', {
                    toolbars: [
                        [
                            'source', 'undo', 'redo',
                            'bold', 'italic', 'underline', 'fontborder', 'strikethrough', 'removeformat', 'autotypeset', 'blockquote', 'pasteplain', 'forecolor', 'insertorderedlist', 'insertunorderedlist', 'selectall', 'cleardoc', 'rowspacingtop', 'rowspacingbottom', 'lineheight', 'indent',
                            'justifyleft', 'justifycenter', 'justifyright', 'justifyjustify', 'touppercase', 'tolowercase', 'simpleupload', 'date', 'time'
                        ],
                        [
                            'con', 'title', 'fork', 'guide', 'division', 'other', 'mystyle'
                        ]
                    ],
                    autoHeightEnabled: false,
                    allowDivTransToP: false,
                    autoFloatEnabled: true,
                    enableAutoSave: false
                });

                var listData = JSON.parse(localStorage.getItem("mystyleData"));

                if (listData) {
                    $.each(listData, function (index, val) {
                        $(".J_myStyleList").append(val)
                    });
                    $("#J_styleChoseBox").html($(".J_myStyleList li .content").eq(0).html())
                    $(".no-material").hide();
                    $(".J_styleList").show();
                }

                //点击添加样式，隐藏列表，显示添加界面
                $(".J_addStyleBtn").click(function (event) {
                    $(".J_uploadStyle").show();
                    $(".J_addStyleBox").hide();
                    //清空数据
                    $(".J_uploadStyle .upload-prev-area").html("");
                    EditorCustom.ueMyStyle.execCommand('cleardoc');
                });

                //取消返回样式列表页面
                $(".J_caeStyleBtn").click(function (event) {
                    $(".J_uploadStyle").hide();
                    $(".J_addStyleBox").show();
                });

                //提交按钮
                $(".J_subStyleBtn").click(function (event) {

                    //遍历上传的图片，插入到列表,本地存储
                    mystyleData = [];
                    $.each($(".J_uploadStyle .upload-prev-area li"), function (index, val) {

                        imgsrc = $(this).find('img').attr("src");
                        var html = '<li><div class="content"><p><img src="' + imgsrc + '" style="HEIGHT: auto !important; WIDTH: auto"></p></div><span class="J_delStyleBtn">删除</span></li>';

                        $(".J_myStyleList").append(html)
                        mystyleData.push(html)
                    });

                    //获取编辑器里面的内容，插入列表
                    if (EditorCustom.ueMyStyle.hasContents()) {
                        var mystyleContent = '<li><div class="content">' + EditorCustom.ueMyStyle.getContent() + '</div><span class="J_delStyleBtn">删除</span></li>'
                        $(".J_myStyleList").append(mystyleContent);
                        mystyleData.push(mystyleContent)
                    }

                    //隐藏添加界面，显示列表页面
                    $(".J_uploadStyle").hide();
                    $(".J_addStyleBox").show();

                    //添加样式按钮显示隐藏
                    $(".no-material").hide();
                    $(".J_styleList").show();

                    //如果有本地数据，就先取出，在加上现有数据，再保存，没有就直接保存

                    if (listData) {
                        endData = [];
                        $.each($(".J_myStyleList li"), function (index, val) {
                            var saveList = '<li>' + $(val).html() + '</li>';
                            endData.push(saveList);
                        });
                        localStorage.setItem("mystyleData", JSON.stringify(endData));
                    } else {
                        localStorage.setItem("mystyleData", JSON.stringify(mystyleData));
                        listData = JSON.parse(localStorage.getItem("mystyleData"));
                    }

                    //设置默认数据，下方的选择框
                    $("#J_styleChoseBox").html($(".J_myStyleList li .content").eq(0).html());
                });
                //点击选择样式到下方
                $(".J_addStyleBox").on('click', 'li', function (event) {
                    $(this).addClass('hover').siblings().removeClass('hover');
                    var html = $(this).find(".content").html();
                    $(this).parents(".borderBox").next().html(html);
                });
                //删除功能
                $(".J_addStyleBox").on('click', '.J_delStyleBtn', function (event) {
                    var target = this;
                    if ($(".J_myStyleList li").length == 1) {
                        $(target).parent().remove();
                        $("#J_styleChoseBox").html($(".J_myStyleList li .content").eq(0).html());
                        localData.remove("mystyleData")
                        $(".J_styleList").hide();
                        $(".no-material").show();
                    } else {
                        $(target).parent().remove();//删除
                        $("#J_styleChoseBox").html($(".J_myStyleList li .content").eq(0).html());
                        //重置数据
                        endDataOne = [];
                        $.each($(".J_myStyleList li"), function (index, val) {
                            var saveList = '<li>' + $(val).html() + '</li>';
                            endDataOne.push(saveList);
                        });
                        localStorage.setItem("mystyleData", JSON.stringify(endDataOne));
                    }
                });
                //上下移动
                $("#J_sortStyle").sortable({
                    axis: 'y',
                    revert: false,
                    placeholder: "ui-state-highlight",
                    items: "li",
                    scroll: false
                }).bind('sortstop', function (event, ui) {
                    endData = [];
                    $.each($(".J_myStyleList li"), function (index, val) {
                        var saveList = '<li>' + $(val).html() + '</li>';
                        endData.push(saveList);
                    });
                    localStorage.setItem("mystyleData", JSON.stringify(endData));
                    //设置默认数据，下方的选择框
                    $("#J_styleChoseBox").html($(".J_myStyleList li .content").eq(0).html());
                });

                //禁止选中元素被选中
                $("#J_sortable").disableSelection();
            }

            MyStyle();

            //点击样式选择时,把选中的样式填充到下方div中
            $(".J_wxStyle li").click(function () {
                $(this).addClass('hover').siblings().removeClass('hover');
                var html = $(this).html();
                $(this).parents(".borderBox").next().html(html);

                conColor();

                titleColor();

                hutuiColor();
            })
            //点击确定按钮，把样式添加到编辑框中
            $(".J_conBtn").click(function () {
                UE.getEditor('editor').execCommand('inserthtml', $(this).prev().html());
                wxStyle.modal('hide');
                if(callback){
                    callback();
                }
            })
        }
    })
};