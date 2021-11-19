/**
 * Created by Administrator on 2016/9/3.
 */
(function($){
    $.fn.extend({
        imgupload:function(params,callback){
            var selector=this.selector;
            if(params.selfDefinedUrl!=""&&params.selfDefinedUrl!=undefined){
                uploadUrl=params.selfDefinedUrl;
            }
            $(selector).uploadify({
                method:'post',
                auto:'true',
                buttonText:'上传图片',
                height:30,
                width:100,
                checkExisting:false,
                fileObjName:'files',
                fileSizeLimit:'1024KB',
                fileTypeDesc:'支持的格式：',
                fileTypeExts: '*.jpg;*.jpge;*.gif;*.png',
                formData:params.data, //TODO
                queueID:params.queue,
                queueSizeLimit: 20,                   //一个队列上传文件数限制
                removeCompleted : true,               //完成时是否清除队列 默认true
                removeTimeout   : 3,                  //完成时清除队列显示秒数,默认3秒
                requeueErrors   : false,              //队列上传出错，是否继续回滚队列
                successTimeout  : 8,                  //上传超时
                uploadLimit     : 5,                 //允许上传的最多张数
                swf  :swfUrl, //swfUpload
                uploader: uploadUrl,
                onUploadSuccess:function(file, data, response){
                    if(callback){
                        callback(file,data,response);
                    }else {
                        data=JSON.parse(data);
                        if (data.status == 0) {
                        	if(typeof(params.imageHeight) == "undefined"||params.imageHeight==""){
                        		params.imageHeight = "100px";
                        	}
                        	if(typeof(params.imageWidth) == "undefined"||params.imageWidth==""){
                        		params.imageWidth = "100%";
                        	}
                            var dom = '<a href="' +imageOssUrl+ data.data.key + '" data-lightbox="images"><img src="' +imageOssUrl+ data.data.key + '" height="'+params.imageHeight+'" width="'+params.imageWidth+'" /></a>';
                            $(params.targetKey).html(dom);
                            $(params.targetVal).val(data.data.key);
                        }else{
                            swal("提示","图片上传失败");
                        }
                    }
                },
                onUploadError:function(file, errorCode, errorMsg, errorString) {
                    swal("提示","图片上传失败");
                }
            });
        },
        bindTypeHead:function(parameter,callback){
            var selector=this.selector;
            var bloodhound = new Bloodhound({
                datumTokenizer: Bloodhound.tokenizers.obj.whitespace('selValue'),
                queryTokenizer: Bloodhound.tokenizers.whitespace,
                prefetch: parameter.loadUrl
            });
            $(selector).typeahead(null, {
                name: parameter.name,
                display: 'selValue',
                source: bloodhound,
                templates: {
                    empty: [
                        '<div class="empty-message">',
                        '未找到匹配的数据',
                        '</div>'
                    ].join('\n'),
                    suggestion: Handlebars.compile('<div><strong>{{selKey}}</strong> – {{selValue}}</div>')
                }
            });
            if(callback) {
                $(selector).bind('typeahead:select', callback);
            }
        }
    });
})(jQuery)