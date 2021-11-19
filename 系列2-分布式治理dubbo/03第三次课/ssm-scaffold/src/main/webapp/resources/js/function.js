/**
 * Created by Administrator on 2016/8/21.
 */

var TOOLS={
    doPostAsSync:function(param, url, callFunc,errorFunc) {
        $.ajax({
            url: url,
            data: param,
            type: 'post',
            cache: false,
            async:false,
            dataType: 'json',
            success: function (data) {
                callFunc(data);
            },
            error: function (err) {
                if(errorFunc){
                    errorFunc(err);
                }else {
                    swal("错误", "系统繁忙，请稍后重试", "error");
                }
            }
        });
    },
    doPost:function(param, url, callFunc,errorFunc) {
        $.ajax({
            url: url,
            data: param,
            type: 'post',
            cache: false,
            dataType: 'json',
            success: function (data) {
                callFunc(data);
            },
            error: function (err) {
                if(errorFunc){
                    errorFunc(err);
                }else {
                    swal("错误", "系统繁忙，请稍后重试", "error");
                }
            }
        });
    },
    doGet:function (param, url, callFunc,errorFunc) {
        $.ajax({
            url: url,
            data: param,
            type: 'get',
            cache: false,
            dataType: 'json',
            success: function (data) {
                callFunc(data);
            },
            error: function (err) {
                if (errorFunc) {
                    errorFunc(err);
                } else {
                    swal("错误", "系统繁忙，请稍后重试", "error");
                }
            }
        });
    },
    loadingProgress:function(status){
        if(status=="show"){
            $("#loading").show();
        }else{
            $("#loading").hide();
        }
    },
    showComfirm:function(title,msg,callbackFunc){
        swal({
                title: title,
                text:msg,
                type: "warning",
                showCancelButton: true,
                confirmButtonColor: "#DD6B55",
                confirmButtonText: "确定",
                cancelButtonText: "取消",
                closeOnConfirm: false,closeOnCancel: true
            },callbackFunc);
    },
    formCommitWithPost:function(form,reqUrl,callFunc){
        var params = $(form).serialize();
        $.ajax({
            url: reqUrl,
            data: params,
            type: 'post',
            cache: false,
            dataType: 'json',
            success: function (data) {
                callFunc(data);
            },
            error: function (err) {
                swal("错误", "系统繁忙，请稍后重试", "error");
            }
        });
    },
    bindJsTree:function(treeName,url,checkbox,loadedfunction){
        var control=$("#"+treeName);
        control.data('jstree',false); //清空数据
        var isCheck=arguments[2]||false;
        if(isCheck){
            $.getJSON(url,function(data){
                control.jstree({
                    "core" : {
                        'data' :data,
                        "themes" : {
                            "responsive" : true
                        },
                        "multiple" : true,
                        "animation" : 200,
                        "dblclick_toggle" : true,
                        "expand_selected_onload" : true
                    },
                    "checkbox" : {
                        "keep_selected_style" : true,
                        "three_state" : false,
                        "cascade" : "up"
                    },
                    "plugins" : ["checkbox"]
                }).bind('loaded.jstree', loadedfunction);
            });
        }else{
            //普通树列表的初始化
            $.getJSON(url, function (data) {
                control.jstree({
                    'core': {
                        'data': data,
                        "themes": {
                            "responsive": false
                        }
                    }
                }).bind('loaded.jstree', loadedfunction);
            });
        }
    },
    redirectToUrl:function(url){
        window.location.href=url;
    },
    substrContent:function(content,length){
        if(content.length>length){
            content=content.substring(0,length)+"...";
        }
        return content;
    }
}