/*
 @name: Material.js
 @description: 素材部分工具函数
 @require: 
 @date: 2015/7/20
 @author: Nero(Nero@Nero-zou.com)
 */
'use strict';
var Material = _material();
function _material() {

    var Material = {};//素材模块所有工具方法

    Material.transforImgUrl = transforImgUrl;//转换微信图片地址

    /**
     * 转换 微信图片地址
     * @param url
     * @returns {XML|string|*}
     */
    function transforImgUrl(url){
        url = url.replace(/^https?:\/\/mmbiz\.q(logo|pic)\.cn\/mmbiz/i, 'https://mmbiz.qlogo.cn/mmbiz');
        url = url.replace(/&wxfrom=\d+/g, '');
        url = url.replace(/wxfrom=\d+/g, '');
        url = url.replace(/&tp=[a-z]+/g, '');
        url = url.replace(/tp=[a-z]+/g, '');
        url = url.replace(/\?&/g, '?');
        url = url.replace(/&$/g, '');
        return url;
    }

    return Material;

};