/*
 * Copyright [$tody.year] [Wales Yu of copyright owner]
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