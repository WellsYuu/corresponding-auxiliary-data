
'use strict';

var urllib = OSS.urllib;
var Buffer = OSS.Buffer;
var OSS = OSS.Wrapper;
var STS = OSS.STS;

// Play without STS. NOT SAFE! Because access key id/secret are
// exposed in web page.

var fileClient = new OSS({
    region: 'oss-cn-qingdao',
    accessKeyId: 'LTAIWG4uiXTFvLMM',
    accessKeySecret: '08bf6PioTO1tIoSL2PHRxLGZhnc8jn',
    bucket: 'zhuoyue-image-bucket'
});


var applyTokenDo = function (func,id,btn,input,size,dir) {
    return  func(fileClient,id,btn,input,size,dir);
};

 var progress=function(p) {
    return function (done) {
        done();
    }
};

var uploadFile = function (client,id,btn,input,size,dir) {
    var file = document.getElementById(id).files[0];
    if(file==null||file==undefined){
        swal("提示","请选择文件");
        return;
    }
    var key = dir+file.name;
    $("#"+btn).ladda( 'start' );
    return client.multipartUpload(key, file, {
        progress: progress
    }).then(function (res) {
        key=imageOssUrl+key;
        $("#"+input).val(key);
        $("#"+btn).ladda( 'stop' );
        $("#"+size).val(res.size);
        console.log('upload success: %j', res);
    });
};
