
/*
 * Copyright 2021-2022 the original author or authors
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
