'use strict';
/**
 * @description
 * 1.拖放文件到编辑区域，自动上传并插入到选区
 * 2.插入粘贴板的图片，自动上传并插入到选区
 * @author Nero
 * @date 2015-07-20
 */

UE.plugin.register('autoupload', function (){
    var domUtils = baidu.editor.dom.domUtils;
    function sendAndInsertFile(file, editor) {
        var me  = editor;
        //模拟数据
        var fieldName, urlPrefix, maxSize, allowFiles, actionUrl,
            loadingHtml, errorHandler, successHandler,
            filetype = /image\/\w+/i.test(file.type) ? 'image':'file',
            loadingId = 'loading_' + (+new Date()).toString(36);

        fieldName = me.getOpt(filetype + 'FieldName');
        urlPrefix = '';
        maxSize = me.getOpt(filetype + 'MaxSize');
        allowFiles = me.getOpt(filetype + 'AllowFiles');
        actionUrl = me.getActionUrl(me.getOpt(filetype + 'ActionName'));
        errorHandler = function(title) {
            var loader = me.document.getElementById(loadingId);
            loader && domUtils.remove(loader);
            me.fireEvent('showmessage', {
                'id': loadingId,
                'content': title,
                'type': 'error',
                'timeout': 4000
            });
            var store=wxbEditor.store;
            if(store!=null) {
                wxbEditor.store.storeContent();
            }
        };

        if (filetype == 'image') {
            loadingHtml = '<img class="loadingclass" id="' + loadingId + '" src="' +
            me.options.themePath + me.options.theme +
            '/images/spacer.gif" title="' + (me.getLang('autoupload.loading') || '') + '" >';
            successHandler = function(response) {
                var imgPath =  response.data.url;
                var imgWxPath =  response.data.weixin_url;
                var loader = me.document.getElementById(loadingId);
                if (loader) {
                    loader.setAttribute('src', imgPath);
                    loader.setAttribute('wx-url', imgWxPath);
                    loader.removeAttribute('id');
                    loader.removeAttribute('title');
                    loader.removeAttribute("_src");
                    loader.removeAttribute("data-src");
                    domUtils.removeClasses(loader, 'loadingclass');
                }
                var store=wxbEditor.store;
                if(store!=null) {
                    wxbEditor.store.storeContent();
                }
            };
        } else {
            loadingHtml = '<p>' +
            '<img class="loadingclass" id="' + loadingId + '" src="' +
            me.options.themePath + me.options.theme +
            '/images/spacer.gif" title="' + (me.getLang('autoupload.loading') || '') + '" >' +
            '</p>';
            successHandler = function(data) {
                var link = urlPrefix + data.url,
                    loader = me.document.getElementById(loadingId);

                var rng = me.selection.getRange(),
                    bk = rng.createBookmark();
                rng.selectNode(loader).select();
                me.execCommand('insertfile', {'url': link});
                rng.moveToBookmark(bk).select();
            };
        }

        /* 插入loading的占位符 */
        me.execCommand('inserthtml', loadingHtml);

        /* 判断文件大小是否超出限制 */
        if(file.size > maxSize) {
            errorHandler(me.getLang('autoupload.exceedSizeError'));
            return;
        }
        /* 判断文件格式是否超出允许 */
        var fileext = file.name ? file.name.substr(file.name.lastIndexOf('.')):'';
        if ((fileext && filetype != 'image') || (allowFiles && (allowFiles.join('') + '.').indexOf(fileext.toLowerCase() + '.') == -1)) {
            errorHandler(me.getLang('autoupload.exceedTypeError'));
            return;
        }

        /* 创建Ajax并提交 */
        var xhr = new XMLHttpRequest(),
            fd = new FormData(),
            url = wxbEditor.contentUploadPath;

        fd.append('files', file, file.name || ('blob.' + file.type.substr('image/'.length)));
        fd.append('type', 'ajax');
        fd.append('authorizer_id', wxbEditor.authorizerId);
        xhr.open("post", url, true);
        xhr.setRequestHeader("X-Requested-With", "XMLHttpRequest");
        xhr.addEventListener('load', function (e) {
            var response=e.target.response;
            try{
                var json = JSON.parse(e.target.response);
                if (json.error == 0) {
                    successHandler(json);
                } else {
                    errorHandler(json.message);
                }
            }catch(er){
                errorHandler(me.getLang('autoupload.loadError'));
            }
        });
        xhr.send(fd);
    }

    function getPasteImage(e){
        return e.clipboardData && e.clipboardData.items && e.clipboardData.items.length == 1 && /^image\//.test(e.clipboardData.items[0].type) ? e.clipboardData.items:null;
    }
    function getDropImage(e){
        return  e.dataTransfer && e.dataTransfer.files ? e.dataTransfer.files:null;
    }

    return {
        outputRule: function(root){
            UE.utils.each(root.getNodesByTagName('img'),function(n){
                if (/\b(loaderrorclass)|(bloaderrorclass)\b/.test(n.getAttr('class'))) {
                    n.parentNode.removeChild(n);
                }
            });
            UE.utils.each(root.getNodesByTagName('p'),function(n){
                if (/\bloadpara\b/.test(n.getAttr('class'))) {
                    n.parentNode.removeChild(n);
                }
            });
        },
        bindEvents:{
            //插入粘贴板的图片，拖放插入图片
            'ready':function(e){
                var me = this;
                if(window.FormData && window.FileReader) {
                    domUtils.on(me.body, 'paste drop', function(e){
                        var hasImg = false,
                            items;
                        //获取粘贴板文件列表或者拖放文件列表
                        items = e.type == 'paste' ? getPasteImage(e):getDropImage(e);
                        if(items){
                            var len = items.length,
                                file;
                            while (len--){
                                file = items[len];
                                if(file.getAsFile) file = file.getAsFile();
                                if(file && file.size > 0) {
                                    sendAndInsertFile(file, me);
                                    hasImg = true;
                                }
                            }
                            hasImg && e.preventDefault();
                        }

                    });
                    //取消拖放图片时出现的文字光标位置提示
                    domUtils.on(me.body, 'dragover', function (e) {
                        if(e.dataTransfer.types[0] == 'Files') {
                            e.preventDefault();
                        }
                    });

                    //设置loading的样式
                    UE.utils.cssRule('loading',
                        '.loadingclass{display:inline-block;cursor:default;background: url(\''
                        + this.options.themePath
                        + this.options.theme +'/images/loading.gif\') no-repeat center center transparent;border:1px solid #cccccc;margin-left:1px;height: 22px;width: 22px;}\n' +
                        '.loaderrorclass{display:inline-block;cursor:default;background: url(\''
                        + this.options.themePath
                        + this.options.theme +'/images/loaderror.png\') no-repeat center center transparent;border:1px solid #cccccc;margin-right:1px;height: 22px;width: 22px;' +
                        '}',
                        this.document);
                }
            }
        }
    }
});


// plugins/simpleupload.js
/**
 * @description
 * 简单上传:点击按钮,直接选择文件上传
 * @author Nero
 * @date 2015-07-24
 */
UE.plugin.register('simpleupload', function (){
    var me = this,
        isLoaded = false,
        containerBtn;
    var domUtils = baidu.editor.dom.domUtils;
    var uploadPath=wxbEditor.contentUploadPath;

    function initUploadBtn(){
        var w = containerBtn.offsetWidth || 20,
            h = containerBtn.offsetHeight || 20,
            btnIframe = document.createElement('iframe'),
            btnStyle = 'display:block;width:' + w + 'px;height:' + h + 'px;overflow:hidden;border:0;margin:0;padding:0;position:absolute;top:0;left:0;filter:alpha(opacity=0);-moz-opacity:0;-khtml-opacity: 0;opacity: 0;cursor:pointer;';

        domUtils.on(btnIframe, 'load', function(){

            var timestrap = (+new Date()).toString(36),
                wrapper,
                btnIframeDoc,
                btnIframeBody;

            btnIframeDoc = (btnIframe.contentDocument || btnIframe.contentWindow.document);
            btnIframeBody = btnIframeDoc.body;
            wrapper = btnIframeDoc.createElement('div');

            wrapper.innerHTML = '<form id="edui_form_' + timestrap + '" target="edui_iframe_' + timestrap + '" method="POST" enctype="multipart/form-data" action="' + uploadPath + '" ' +
            'style="' + btnStyle + '">' +
            '<input type="hidden" name="authorizer_id" value="'+wxbEditor.authorizerId+'"/>' +
            '<input id="edui_input_' + timestrap + '" type="file" accept="image/*" name="files" ' +
            'style="' + btnStyle + '">' +
            '</form>' +
            '<iframe id="edui_iframe_' + timestrap + '" name="edui_iframe_' + timestrap + '" style="display:none;width:0;height:0;border:0;margin:0;padding:0;position:absolute;"></iframe>';

            wrapper.className = 'edui-' + me.options.theme;
            wrapper.id = me.ui.id + '_iframeupload';
            btnIframeBody.style.cssText = btnStyle;
            btnIframeBody.style.width = w + 'px';
            btnIframeBody.style.height = h + 'px';
            btnIframeBody.appendChild(wrapper);

            if (btnIframeBody.parentNode) {
                btnIframeBody.parentNode.style.width = w + 'px';
                btnIframeBody.parentNode.style.height = w + 'px';
            }

            var form = btnIframeDoc.getElementById('edui_form_' + timestrap);
            var input = btnIframeDoc.getElementById('edui_input_' + timestrap);
            var iframe = btnIframeDoc.getElementById('edui_iframe_' + timestrap);

            domUtils.on(input, 'change', function(){
                if(!input.value) return;
                var loadingId = 'loading_' + (+new Date()).toString(36);
                var params = UE.utils.serializeParam(me.queryCommandValue('serverparam')) || '';

                var imageActionUrl = uploadPath;
                var allowFiles = me.getOpt('imageAllowFiles');

                me.focus();
                me.execCommand('inserthtml', '<img class="loadingclass" id="' + loadingId + '" src="' + me.options.themePath + me.options.theme +'/images/spacer.gif" title="' + (me.getLang('simpleupload.loading') || '') + '" >');

                function callback(){
                    var imgPath,imgWxPath, json, loader,
                        body = (iframe.contentDocument || iframe.contentWindow.document).body,
                        result = body.innerText || body.textContent || '';
                    try{
                        json = (new Function("return " + result))();

                        if(json.error == 0) {
                            imgPath = json.data.url;
                            imgWxPath = json.data.weixin_url;
                            loader = me.document.getElementById(loadingId);
                            loader.setAttribute('src', imgPath);
                            loader.setAttribute('_src', imgPath);
                            loader.setAttribute('wx-src', imgWxPath);
                            loader.removeAttribute('id');
                            loader.removeAttribute('title');
                            domUtils.removeClasses(loader, 'loadingclass');
                        } else {
                            showErrorLoader && showErrorLoader(json.message);
                        }
                    }catch(er){
                        console.log(result);
                        showErrorLoader && showErrorLoader(me.getLang('simpleupload.loadError'));
                    }
                    form.reset();
                    domUtils.un(iframe, 'load', callback);
                    var store=wxbEditor.store;
                    if(store!=null) {
                        wxbEditor.store.storeContent();
                    }
                }
                function showErrorLoader(title){
                    if(loadingId) {
                        var loader = me.document.getElementById(loadingId);
                        loader && domUtils.remove(loader);
                        me.fireEvent('showmessage', {
                            'id': loadingId,
                            'content': title,
                            'type': 'error',
                            'timeout': 4000
                        });
                    }

                }


                // 判断文件格式是否错误
                var filename = input.value,
                    fileext = filename ? filename.substr(filename.lastIndexOf('.')):'';
                if (!fileext || (allowFiles && (allowFiles.join('') + '.').indexOf(fileext.toLowerCase() + '.') == -1)) {
                    showErrorLoader(me.getLang('simpleupload.exceedTypeError'));
                    return;
                }

                domUtils.on(iframe, 'load', callback);
                form.action = UE.utils.formatUrl(imageActionUrl + (imageActionUrl.indexOf('?') == -1 ? '?':'&') + params);
                form.submit();
            });

            var stateTimer;
            me.addListener('selectionchange', function () {
                clearTimeout(stateTimer);
                stateTimer = setTimeout(function() {
                    var state = me.queryCommandState('simpleupload');
                    if (state == -1) {
                        input.disabled = 'disabled';
                    } else {
                        input.disabled = false;
                    }
                }, 400);
            });
            isLoaded = true;
        });

        btnIframe.style.cssText = btnStyle;
        containerBtn.appendChild(btnIframe);
    }

    return {
        bindEvents:{
            'ready': function() {
                //设置loading的样式
                UE.utils.cssRule('loading',
                    '.loadingclass{display:inline-block;cursor:default;background: url(\''
                    + this.options.themePath
                    + this.options.theme +'/images/loading.gif\') no-repeat center center transparent;border:1px solid #cccccc;margin-right:1px;height: 22px;width: 22px;}\n' +
                    '.loaderrorclass{display:inline-block;cursor:default;background: url(\''
                    + this.options.themePath
                    + this.options.theme +'/images/loaderror.png\') no-repeat center center transparent;border:1px solid #cccccc;margin-right:1px;height: 22px;width: 22px;' +
                    '}',
                    this.document);
            },
            /* 初始化简单上传按钮 */
            'simpleuploadbtnready': function(type, container) {
                containerBtn = container;
                me.afterConfigReady(initUploadBtn);
            }
        },
        outputRule: function(root){
            UE.utils.each(root.getNodesByTagName('img'),function(n){
                if (/\b(loaderrorclass)|(bloaderrorclass)\b/.test(n.getAttr('class'))) {
                    n.parentNode.removeChild(n);
                }
            });
        },
        commands: {
            'simpleupload': {
                queryCommandState: function () {
                    return isLoaded ? 0:-1;
                }
            }
        }
    }
});
