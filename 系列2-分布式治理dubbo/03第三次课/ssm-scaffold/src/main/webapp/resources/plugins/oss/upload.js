function random_string(len) {
    len = len || 32;
    var chars = 'ABCDEFGHJKMNPQRSTWXYZabcdefhijkmnprstwxyz2345678';
    var maxPos = chars.length;
    var pwd = '';
    for (i = 0; i < len; i++) {
      pwd += chars.charAt(Math.floor(Math.random() * maxPos));
    }
    return pwd;
}

function get_suffix(filename) {
    var pos = filename.lastIndexOf('.')
    var suffix = ''
    if (pos != -1) {
        suffix = filename.substring(pos)
    }
    return suffix;
}

function getFileName(filename){
    return random_string(10)+get_suffix(filename);
}

function set_upload_param(up, filename, ret){
    TOOLS.doPostAsSync({type:1},"/upload/generatorOssKey.shtml",function(response){
        if(response.status==0) {
            var data=response.data;
            key=data.dir;
            if(filename!=null){
                key=data.dir+getFileName(filename);
            }
            var new_multipart_params = {
                'key': key,
                'policy': data.policy,
                'OSSAccessKeyId': data.accessId,
                'success_action_status': '200', //让服务端返回200,不然，默认会返回204
                'signature': data.signature
            };
            up.setOption({
                'url': data.host,
                'multipart_params': new_multipart_params
            });
            up.start();
        }else{
            swal("提示","系统繁忙,请稍后重试");
        }
    })
}

var uploader = new plupload.Uploader({
	runtimes : 'html5,flash,silverlight,html4',
	browse_button : 'fileBtn',
	container: document.getElementById('images'),
	flash_swf_url :flash_swf_url,
	silverlight_xap_url : silverlight_xap_url,
    url : 'http://oss.aliyuncs.com',
    multi_selection:false,
    auto_start: true,
    filters: {
        mime_types : [ //只允许上传图片和zip文件
            { title : "Image files", extensions : "jpg,png" }
        ],
        max_file_size : '1024kb', //最大只能上传400kb的文件
        prevent_duplicates : true //不允许选取重复文件
    },
	init: {
		FilesAdded: function(up, files) {
            plupload.each(files, function(file) {
				document.getElementById('ossfile').innerHTML += '<div id="' + file.id + '">' + file.name + ' (' + plupload.formatSize(file.size) + ')<b></b>'
				+'<div class="progress"><div class="progress-bar" style="width: 0%"></div></div></div>';
                set_upload_param(uploader,file.name, true);
			});
		},
		UploadProgress: function(up, file) {
			var d = document.getElementById(file.id);
			d.getElementsByTagName('b')[0].innerHTML = '<span>' + file.percent + "%</span>";
            var prog = d.getElementsByTagName('div')[0];
			var progBar = prog.getElementsByTagName('div')[0]
			progBar.style.width= file.percent+'%';
			progBar.setAttribute('aria-valuenow', file.percent);
		},
		FileUploaded: function(up, file, info) {
            var dom = '<a href="' +imageOssUrl+ key + '" data-lightbox="images"><img src="' +imageOssUrl+ key + '" height="100px" width="100%" /></a>';
            $("#images").html(dom);
            $("#bannerUrl").val(key);
            $.each(file, function(idx, item){
                up.removeFile(item);
            });
		},
		Error: function(up, err) {
		},
        UploadComplete:function(uploader,files){

        }
	}
});
uploader.init();
