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
    }
}