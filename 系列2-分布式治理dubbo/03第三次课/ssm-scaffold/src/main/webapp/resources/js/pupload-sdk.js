;(function (global) {
    function createCookie(key, value, exp) {
        var date = new Date();
        date.setTime(date.getTime() + (exp * 24 * 60 * 60 * 1000));
        var expires = "; expires=" + date.toGMTString();
        document.cookie = key + "=" + value + expires + "; path=/";
    }

    function readCookie(key) {
        var nameEQ = key + "=";
        var ca = document.cookie.split(';');
        for (var i = 0, max = ca.length; i < max; i++) {
            var c = ca[i];
            while (c.charAt(0) === ' ') {
                c = c.substring(1, c.length);
            }
            if (c.indexOf(nameEQ) === 0) {
                return c.substring(nameEQ.length, c.length);
            }
        }
        return null;
    }

    if (!window.localStorage) {
        window.localStorage = {
            setItem: function (key, value) {
                createCookie(key, value, 30);
            },
            getItem: function (key) {
                return readCookie(key);
            },
            removeItem: function (key) {
                createCookie(key, '', -1);
            }
        };
    }

    function eduUploadSdk() {
        var that = this;

        /**
         * 创建ajax请求
         * @param argument
         * @returns {{}}
         */
        this.createAjax = function (argument) {
            var xmlhttp = {};
            if (window.XMLHttpRequest) {
                xmlhttp = new XMLHttpRequest();
            } else {
                xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
            }
            return xmlhttp;
        };

        /**
         * 判断文件类型
         * @param  {String}  url of a file
         * @return {Boolean} file is a image or not
         */
        this.isImage = function (url) {
            url = url.split(/[?#]/)[0];
            return (/\.(png|jpg|jpeg|gif|bmp)$/i).test(url);
        };
        var ossUploadUrl = 'http://miaotu1.img-cn-beijing.aliyuncs.com';
        /**
         * 获取文件扩展名
         * get file extension
         * @param  {String} filename
         * @return {String} file extension
         * @example
         *     input: test.txt
         *     output: txt
         */
        this.getFileExtension = function (filename) {
            var tempArr = filename.split(".");
            var ext;
            if (tempArr.length === 1 || (tempArr[0] === "" && tempArr.length === 2)) {
                ext = "";
            } else {
                ext = tempArr.pop().toLowerCase(); //get the extension and make it lower-case
            }
            return ext;
        };

        this.uploader = function (op) {
            var getAccessToken = function (file) {
                if (op.uptoken) {
                    that.token = op.uptoken;
                    return;
                } else if (op.uptoken_url) {
                    var ajax = that.createAjax();
                    ajax.open('POST', that.uptoken_url, false);
                    ajax.setRequestHeader("If-Modified-Since", "0");
                    ajax.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
                    ajax.send("tag=" + op.data.tag);
                    if (ajax.status === 200) {
                        var res = JSON.parse(ajax.responseText);
                        that.token = res.data;
                    } else {
                        //TODO 获取token失败
                    }
                    return;
                } else {
                    throw new error("[uptoken, uptoken_url] 必须包含其中一个");
                }
            }
            //为每个文件生成唯一的key
            var getFileKey = function (up, file, func) {
                var key = '', unique_names = false;
                if (!op.save_key) { //若在服务端生成密钥的上传策略中指定了sava_key，则开启，SDK在前端将不对key进行任何处理
                    unique_names = up.getOption && up.getOption('unique_names');
                    unique_names = unique_names || (up.settings && up.settings.unique_names);
                    if (unique_names) {
                        var ext = that.getFileExtension(file.name);
                        key = ext ? file.id + '.' + ext : file.id;
                    } else if (typeof func === 'function') {
                        key = func(up, file);
                    } else {
                        key = file.name;
                    }
                }
                return key;
            };
            if (!op.domain) { //bucket+endpoint
                throw 'domain setting in options is required!';
            }
            if (!op.browse_button) {
                throw 'browse_button setting in options is required!';
            }
            if (!op.uptoken && !op.uptoken_url && !op.uptoken_func) {
                throw 'one of [uptoken, uptoken_url] settings in options is required!';
            }
            var option = {};

            var _Error_Handler = op.init && op.init.Error;
            var _FileUploaded_Handler = op.init && op.init.FileUploaded;

            // replace the handler for intercept
            op.init.Error = function () {
            };
            op.init.FileUploaded = function () {
            };

            that.uptoken_url = op.uptoken_url;
            that.token = '';
            that.key_handler = typeof op.init.Key === 'function' ? op.init.Key : '';
            this.domain = op.domain;

            var ctx = '';
            var speedCalInfo = {
                isResumeUpload: false,
                resumeFilesize: 0,
                startTime: '',
                currentTime: ''
            };

            var defaultSetting = {
                url: ossUploadUrl,
                multipart_params: {
                    token: ''
                }
            };

            plupload.extend(option, op, defaultSetting);

            var uploader = new plupload.Uploader(option);

            uploader.bind('Init', function (up, params) {
                if (!op.get_new_uptoken) {
                    getAccessToken(null);
                }
            });
            uploader.bind('FilesAdded', function (up, files) {
                var auto_start = up.getOption && up.getOption('auto_start');
                auto_start = auto_start || (up.settings && up.settings.auto_start);
                if (auto_start) {
                    setTimeout(function () {
                        up.start();
                    }, 0);
                }
                up.refresh(); // Reposition Flash/Silverlight
            });
            //bind BeforeUpload event
            uploader.bind('BeforeUpload', function (up, file) {
                file.speed = file.speed || 0;
                if (op.get_new_uptoken) {
                    getAccessToken(file);
                }
                var directUpload = function (up, file, func) {
                    speedCalInfo.startTime = new Date().getTime();
                    var multipart_params_obj;
                    if (op.save_key) {
                        multipart_params_obj = {
                            'token': that.token
                        };
                    } else {
                        multipart_params_obj = {
                            'key': that.token.dir + getFileKey(up, file, func),
                            'policy': that.token.policy,
                            'OSSAccessKeyId': that.token.accessId,
                            'success_action_status': '200', //让服务端返回200,不然，默认会返回204
                            'signature': that.token.signature
                        };
                    }
                    up.setOption({
                        'url': that.token.host,
                        'multipart': true,
                        'multipart_params': multipart_params_obj
                    });
                };
                directUpload(up, file, that.key_handler);
            });
            //bind UploadProgress event
            uploader.bind('UploadProgress', function (up, file) {
                speedCalInfo.currentTime = new Date().getTime();
                var timeUsed = speedCalInfo.currentTime - speedCalInfo.startTime; // ms
                var fileUploaded = file.loaded || 0;
                if (speedCalInfo.isResumeUpload) {
                    fileUploaded = file.loaded - speedCalInfo.resumeFilesize;
                }
                file.speed = (fileUploaded / timeUsed * 1000).toFixed(0) || 0; // unit: byte/s
            });
            var retries = 3; //重试次数
            var unknow_error_retry = function (file) {
                if (retries-- > 0) {
                    setTimeout(function () {
                        file.status = plupload.QUEUED;
                        uploader.stop();
                        uploader.start();
                    }, 0);
                    return true;
                } else {
                    return false;
                }
            };

            uploader.bind('Error', (function (_Error_Handler) {
                return function (up, err) {
                    var errTip = '';
                    var file = err.file;
                    if (file) {
                        switch (err.code) {
                            case plupload.FAILED:
                                errTip = '上传失败。请稍后再试。';
                                break;
                            case plupload.FILE_SIZE_ERROR:
                                var max_file_size = up.getOption && up.getOption('max_file_size');
                                max_file_size = max_file_size || (up.settings && up.settings.max_file_size);
                                errTip = '浏览器最大可上传' + max_file_size + '。更大文件请使用命令行工具。';
                                break;
                            case plupload.FILE_EXTENSION_ERROR:
                                errTip = '文件验证失败。请稍后重试。';
                                break;
                            case plupload.HTTP_ERROR:
                                if (err.response === '') {
                                    errTip = err.message || '未知网络错误。';
                                    if (!unknow_error_retry(file)) {
                                        return;
                                    }
                                    break;
                                }
                                throw err.response;
                            case plupload.SECURITY_ERROR:
                                errTip = '安全配置错误。请联系网站管理员。';
                                break;
                            case plupload.GENERIC_ERROR:
                                errTip = '上传失败。请稍后再试。';
                                break;
                            case plupload.IO_ERROR:
                                errTip = '上传失败。请稍后再试。';
                                break;
                            case plupload.INIT_ERROR:
                                errTip = '网站配置错误。请联系网站管理员。';
                                uploader.destroy();
                                break;
                            default:
                                errTip = err.message + err.details;
                                if (!unknow_error_retry(file)) {
                                    return;
                                }
                                break;
                        }
                        if (_Error_Handler) {
                            _Error_Handler(up, err, errTip);
                        }
                    }
                    up.refresh(); // Reposition Flash/Silverlight
                };
            })(_Error_Handler));


            uploader.bind('FileUploaded', (function (_FileUploaded_Handler) {
                return function (up, file, info) {
                    _FileUploaded_Handler(up, file, info);
                }
            })(_FileUploaded_Handler));

            uploader.init();

            return uploader;
        };
        this.getUrl = function (key) {
            if (!key) {
                return false;
            }
            key = encodeURI(key);
            var domain = this.domain;
            if (domain.slice(domain.length - 1) !== '/') {
                domain = domain + '/';
            }
            return domain + key;
        };
    }

    var EduUpload = new eduUploadSdk();

    global.EduUpload = EduUpload;

    global.EduUploadJsSDK = eduUploadSdk;
})(window);


(function($) {
    $.fn.extend({
        eduUpload:function(params){  //TODO 这里可以扩展下. 增加callback函数来自定义处理上传结果处理
            var selector=this.selector;
            var uploader=EduUpload.uploader({
                runtimes: 'html5,flash,html4',    //上传模式,依次退化
                browse_button: $(selector).attr("id"),       //上传选择的点选按钮，**必需**
                uptoken_url: uptoken_url,            //Ajax请求upToken的Url，**强烈建议设置**（服务端提供）
                data: params.data,
                // unique_names: true, // 默认 false，key为文件名。若开启该选项，SDK为自动生成上传成功后的key（文件名）。
                // save_key: true,   // 默认 false。若在服务端生成uptoken的上传策略中指定了 `sava_key`，则开启，SDK会忽略对key的处理
                domain: 'http://kefeng-image-bucket.img-cn-hangzhou.aliyuncs.com',   //bucket 域名，下载资源时用到，**必需**
                get_new_uptoken: false,  //设置上传文件的时候是否每次都重新获取新的token
                container: document.getElementById(params.container),           //上传区域DOM ID，默认是browser_button的父元素，
                flash_swf_url: flash_swf_url,  //引入flash,相对路径
                max_retries: 3,                   //上传失败最大重试次数
                filters: {
                    mime_types : [ //只允许上传图片和zip文件
                        { title : "Image files", extensions : "jpg,png" }
                    ],
                    max_file_size : '2mb', //最大只能上传2mb的文件
                    prevent_duplicates : true //不允许选取重复文件
                },
                resize:{
                    quality: 80,
                    crop: false
                },
                dragdrop: false,                   //开启可拖曳上传
                unique_names:true,
                auto_start: true,                 //选择文件后自动上传，若关闭需要自己绑定事件触发上传
                silverlight_xap_url : silverlight_xap_url,
                init: {
                    FilesAdded: function(up, files) {
                        plupload.each(files, function(file) {
                            document.getElementById(params.ossfile).innerHTML+= '<div id="' + file.id + '">' + file.name + ' (' + plupload.formatSize(file.size) + ')<b></b>'
                            +'<div class="progress"><div class="progress-bar" style="width: 0%"></div></div></div>';
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
                        var dir=params.dir;
                        var dom = '<a href="' +imageOssUrl+dir+file.target_name + '" data-lightbox="images"><img src="' +imageOssUrl+dir+ file.target_name + '" height="100px" width="100%" /></a>';
                        $(params.targetContain).html(dom);
                        $(params.targetInput).val(imageOssUrl+dir+file.target_name);
                        document.getElementById(params.ossfile).innerHTML="";
                    },
                    UploadComplete:function(uploader,files){
                    }
                }
            });
        }
    })
})(jQuery);
