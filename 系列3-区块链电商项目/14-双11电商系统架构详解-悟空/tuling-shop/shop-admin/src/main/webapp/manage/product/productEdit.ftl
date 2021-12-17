<#import "/manage/tpl/pageBase.ftl" as page>
<@page.pageBase currentMenu="商品管理">
<form action="${basepath}/manage/product" id="form" name="form" namespace="/manage" theme="simple" enctype="multipart/form-data" method="post">

    <span id="pifeSpan" class="input-group-addon" style="display:none">${systemSetting().imageRootPath}</span>
    <input type="hidden" value="${e.id!""}" id="productID"/>
    <input type="hidden" value="${e.catalogID!""}" id="catalogID"/>

    <div style="text-align: center;">
        <div id="updateMsg"><font color='red'>${updateMsg!""}</font></div>
		<#if e.id??>
            商品ID：<span class="badge badge-success">${e.id!""}</span>
			<#if e.activityID??>
                活动ID：<span class="badge badge-success">${e.activityID!""}</span>
			</#if>
            <button method="update" class="btn btn-success">
                <i class="icon-ok icon-white"></i> 保存
            </button>

			<#if e.status??&&e.status!=2>
                <button method="updateUpProduct?id=${e.id!""}" class="btn btn-warning" onclick="return confirm('确定上架商品吗?');">
                    <i class="icon-arrow-up icon-white"></i> 上架
                </button>
			<#else>
                <button method="updateDownProduct?id=${e.id!""}" class="btn btn-warning" onclick="return confirm('确定下架商品吗?');">
                    <i class="icon-arrow-down icon-white"></i> 下架
                </button>
			</#if>

            <a class="btn btn-info" target="_blank" href="${systemSetting().www}/product/${e.id!""}.html">
                <i class="icon-eye-open icon-white"></i> 查看</a>
            <a target="_blank" href="${basepath}/freemarker/create?method=staticProductByID&id=${e.id!""}" class="btn btn-warning">
                <i class="icon-refresh icon-white"></i> 静态化</a>
		<#else>
            <button method="insert" class="btn btn-success">
                <i class="icon-ok icon-white"></i> 新增
            </button>
		</#if>

        <!-- 			<a href="selectList?init=y" class="btn btn-inverse">返回</a> -->
    </div>

    <div id="tabs">
        <ul>
            <li><a href="#tabs-1">基本信息</a></li>
            <li><a href="#tabs-2">商品介绍</a></li>
            <li><a href="#tabs-3">商品图片</a></li>
            <li><a href="#tabs-4">商品属性</a></li>
            <li><a href="#tabs-5">商品参数</a></li>
            <li><a href="#tabs-6">商品规格</a></li>
            <li><a href="#tabs-7">绑定商品赠品</a></li>
        </ul>
        <div id="tabs-1">
            <input type="hidden" value="${e.id!""}" name="id" label="id" id="id"/>
            <div class="row form-horizontal"-role="form">

                <div class="form-group">
                    <label class="col-md-2 control-label">名称</label>
                    <div class="col-md-10">
                        <input type="text" value="${e.name!""}" name="name"  data-rule="商品名称;required;name;length[0~44];" size="44" maxlength="44" style="width: 80%;"
                               id="name" />
                    </div>
                </div>
                <div class="form-group col-md-6">
                    <label class="col-md-4 control-label">类别</label>
                    <div class="col-md-8">
                        <select onchange="catalogChange(this)" name="catalogID" id="catalogSelect">
                            <option></option>
							<#list catalogs as item>
                                <option pid="0" value="${item.id!""}"><font color='red'>${item.name!""}</font></option>
								<#if item.children??>
									<#list item.children as item>
                                        <option value="${item.id!""}">&nbsp;&nbsp;&nbsp;&nbsp;${item.name!""}</option>
									</#list>
								</#if>
							</#list>
                        </select>(请选择子类别)
                    </div>
                </div>

                <div class="form-group col-md-6">
                    <label class="col-md-4 control-label">单位</label>
                    <div class="col-md-8">
						<#assign map = {'item':'件'}>
                        <select id="unit" name="unit" class="input-medium">
							<#list map?keys as key>
                                <option value="${key}" <#if e.unit?? && e.unit==key>selected="selected" </#if>>${map[key]}</option>
							</#list>
                        </select>
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-md-2 control-label">简介</label>
                    <div class="col-md-10">
							<textarea name="introduce" class="form-control" rows="3" id="introduce"
                                      data-rule="商品简介;required;introduce;length[4~500];">${e.introduce!""}</textarea>
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-md-2 control-label">主图</label>
                    <div class="col-md-10">
                        <input type="button" name="filemanager" value="浏览图片" class="btn btn-success"/>
                        <input type="text"  value="${e.picture!""}" name="picture" type="text" id="picture"  ccc="imagesInput" style="width: 600px;"
                               data-rule="小图;required;maxPicture;"/>
						<#if e.picture??>
                            <a target="_blank" href="${systemSetting().imageRootPath}${e.picture!""}">
                                <img style="max-width: 50px;max-height: 50px;" alt="" src="${systemSetting().imageRootPath}${e.picture!""}">
                            </a>
						</#if>
                    </div>
                </div>
                <div class="form-group col-md-6">
                    <label class="col-md-4 control-label">定价</label>
                    <div class="col-md-8"><input type="text"  value="${e.price!""}" name="price"  data-rule="定价;required;price;" size="10" maxlength="10"
                                                 id="price" />
                    </div>
                </div>

                <div class="form-group col-md-6">
                    <label class="col-md-4 control-label">现价</label>
                    <div class="col-md-8"><input type="text"  value="${e.nowPrice!""}" name="nowPrice"  data-rule="现价;required;nowPrice;" size="10" maxlength="10"
                                                 id="nowPrice" />
                    </div>
                </div>

                <div class="form-group col-md-6">
                    <label class="col-md-4 control-label">库存</label>
                    <div class="col-md-8"><input type="text"  value="${e.stock!""}" name="stock"  data-rule="库存;required;integer;stock;"
                                                 id="stock" />
                    </div>
                </div>

                <div class="form-group col-md-6">
                    <label class="col-md-4 control-label">销量</label>
                    <div class="col-md-8"><input type="text"  value="${e.sellcount!""}" name="sellcount"  data-rule="销量;required;integer;sellcount;"
                                                 id="sellcount" />
                    </div>
                </div>

                <div class="form-group col-md-6">
                    <label class="col-md-4 control-label">是否新品</label>
                    <div class="col-md-8">
						<#assign map = {'n':'否','y':'是'}>
                        <select id="isnew" name="isnew" class="input-medium">
							<#list map?keys as key>
                                <option value="${key}" <#if e.isnew?? && e.isnew==key>selected="selected" </#if>>${map[key]}</option>
							</#list>
                        </select>
                    </div>
                </div>

                <div class="form-group col-md-6">
                    <label class="col-md-4 control-label">是否特价</label>
                    <div class="col-md-8">
						<#assign map = {'n':'否','y':'是'}>
                        <select id="sale" name="sale" class="input-medium">
							<#list map?keys as key>
                                <option value="${key}" <#if e.sale?? && e.sale==key>selected="selected" </#if>>${map[key]}</option>
							</#list>
                        </select>
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-md-2 control-label">送积分</label>
                    <div class="col-md-10">
                        <input type="text"  value="${e.score!""}" name="score" type="text"  id="score" maxlength="20" data-rule="销量;required;integer;score;"/>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-md-2 control-label">页面标题</label>
                    <div class="col-md-10">
                        <input type="text"  value="${e.title!""}" name="title" type="text" class="form-control"/>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-md-2 control-label">页面描述</label>
                    <div class="col-md-10">
                        <input type="text"  value="${e.description!""}" name="description" type="text" class="form-control" />
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-md-2 control-label">页面关键字</label>
                    <div class="col-md-10">
                        <input type="text"  value="${e.keywords!""}" name="keywords" type="text" class="form-control" />
                    </div>
                </div>
				<#if e.id??>
                    <div class="form-group">
                        <label class="col-md-2 control-label">其他信息</label>
                        <div class="col-md-10">
                            录入人：<a style="text-decoration: underline;" target="_blank" href="${basepath}/manage/user/show?account=${e.createAccount!""}">${e.createAccount!""}</a>
                            录入时间：${e.createtime!""}<br>
                            最后修改人：<a style="text-decoration: underline;" target="_blank" href="${basepath}/manage/user/show?account=${e.updateAccount!""}">${e.updateAccount!""}</a>
                            最后修改时间：${e.updatetime!""}
                        </div>
                    </div>
				</#if>
            </div> <!--end form-->
        </div>
        <div id="tabs-2">
            <textarea data-rule="商品介绍;required;productHTML;" id="productHTML" name="productHTML" style="width:100%;height:500px;visibility:hidden;">${e.productHTML!""}</textarea>
        </div>
        <div id="tabs-3">
            <div>
                <h4><div class="alert alert-info">图片列表</div></h4>
                <table class="table table-bordered">
                    <tr>
                        <td colspan="11">
                            <button method="deleteImageByImgPaths" onclick="return deleteImageByImgPaths();"
                                    class="btn btn-primary">删除</button>
                        </td>
                    </tr>
                    <tr style="background-color: #dff0d8">
                        <th width="20"><input type="checkbox" id="firstCheckbox" /></th>
                        <th>图片地址</th>
                        <!-- 					<th>设为封面</th> -->
                    </tr>
					<#if e.imagesList??>
						<#list e.imagesList as item>
                            <tr>
                                <td><input type="checkbox" name="imagePaths"
                                           value="${item!""}" /></td>
                                <td>
                                    <a href="${systemSetting().imageRootPath}${item!""}" target="_blank">
                                        <img style="max-width: 100px;max-height: 100px;" alt="" src="${systemSetting().imageRootPath}${item!""}">
                                    </a>
                                </td>
                            </tr>
						</#list>
					</#if>
                </table>
            </div>
            <br>
            <table class="table table-bordered">
                <tr style="background-color: #dff0d8">
                    <td>新增图片 </td>
                </tr>
                <tr>
                    <td>
                        <input id="uploadify" name="uploadify" value="添加" class="btn btn-warning" type="button"/></td>
                </tr>
                <tr id="firstTr" style="display:none">
                    <td>
					<#--<input type="button" name="filemanager" value="浏览图片" class="btn btn-warning"/>-->
                        <img name="img"  style="width:50px;height:50px;max-width: 50px;max-height: 50px;">
                        <input type="text" ccc="imagesInput" name="images" style="width: 260px;" readonly="readonly"/>
                    </td>
                </tr>
            </table>
        </div>

        <!-- 商品属性 -->
        <div id="tabs-4">
            <table class="table table-bordered">
				<#if e.attrList?? && e.attrList?size gt 0>
					<#list e.attrList as attr>
                        <tr>
                            <td nowrap="nowrap" style="text-align: right;">${attr.name!""}</td>
                            <td>
                                <select id="attrSelectIds_${attr_index}" name="attrSelectIds">
                                    <option value="">--请选择--</option>
									<#list attr.attrList as item>
                                        <option value="${item.id!""}" <#if attr.selectedID==item.id?eval>selected="selected" </#if>>${item.name!""}</option>
									</#list>
                                </select>
                            </td>
                        </tr>
					</#list>
				</#if>
            </table>
        </div>

        <!-- 商品参数 -->
        <div id="tabs-5">
            <table class="table">
				<#if e.parameterList?? && e.parameterList?size gt 0 >
					<#list e.parameterList as param>
                        <tr>
                            <th style="display: none;"><input type="hidden" value="${param.id!""}" name="parameterIds"/></th>
                            <th style="text-align: right;">${param.name!""}</th>
                            <th><input type="text"  value="${param.parameterValue!""}" name="parameterNames" /></th>
                        </tr>
					</#list>
				</#if>
            </table>
        </div>

        <!-- 商品规格 -->
        <div id="tabs-6">
            <div class="row">
                <table class="table">
                    <tr>
                        <th style="display: none;">id</th>
                        <th>尺寸</th>
                        <th>颜色</th>
                        <th>规格库存数</th>
                        <th>价格</th>
                        <th>是否显示</th>
                    </tr>
					<#if e.specList?? && e.specList?size gt 0>
						<#list e.specList as item>
                            <tr>
                                <td style="display: none;"><input type="hidden" value="${item.id!""}" name="specList[${item_index}].id"/></td>
                                <td><input type="text"  value="${e.specList[item_index].specSize!""}" name="specList[${item_index}].specSize"  class="search-query input-small"/></td>
                                <td><input type="text"  value="${e.specList[item_index].specColor!""}" name="specList[${item_index}].specColor"  class="search-query input-small"/></td>
                                <td><input type="text"  value="${e.specList[item_index].specStock!""}" name="specList[${item_index}].specStock"  class="search-query input-small"/></td>
                                <td><input type="text"  value="${e.specList[item_index].specPrice!""}" name="specList[${item_index}].specPrice"  class="search-query input-small"/></td>
                                <td>
									<#assign map = {'n':'不显示','y':'显示'}>
                                    <select id="e_spec_specStatus" name="specList[${item_index}].specStatus" class="search-query input-medium">
										<#list map?keys as key>
                                            <option value="${key}" <#if item.specStatus?? && item.specStatus==key>selected="selected" </#if>>${map[key]}</option>
										</#list>
                                    </select>
                                </td>
                            </tr>
						</#list>
					<#else>
						<#list [1,2] as item>
                            <tr>
                                <td style="display: none;"><input type="hidden" value="${e.id!""}" name="specList[${item_index}].id"/></td>
                                <td><input type="text" name="specList[${item_index}].specColor"  class="search-query input-small"/></td>
                                <td><input type="text" name="specList[${item_index}].specSize"  class="search-query input-small"/></td>
                                <td><input type="text" name="specList[${item_index}].specStock"  class="search-query input-small"/></td>
                                <td><input type="text" name="specList[${item_index}].specPrice"  class="search-query input-small"/></td>
                                <td>
									<#assign map = {'n':'不显示','y':'显示'}>
                                    <select id="e_spec_specStatus" name="specList[${item_index}].specStatus" class="search-query input-medium">
										<#list map?keys as key>
                                            <option value="${key}" >${map[key]}</option>
										</#list>
                                    </select>
                                </td>
                            </tr>
						</#list>
					</#if>
                </table>
            </div>
        </div>

        <div id="tabs-7">
            商品赠品:
            <select name="giftID">
                <option></option>
				<#if giftList??>
					<#list giftList as item>
                        <option value="${item.id}" <#if e.giftID?? && e.giftID == item.id>selected="selected"</#if>>${item.giftName!""}</option>
					</#list>
				</#if>
            </select>
        </div>

    </div>
</form>

<script>
    $(function() {
        $( "#tabs" ).tabs({
            //event: "mouseover"
        });
        //alert($("#insertOrUpdateMsg").html());
//	if($("#insertOrUpdateMsg").html()!='' && $("#insertOrUpdateMsg").html().trim().length>0){
//		$("#insertOrUpdateMsg").slideDown(1000).delay(1500).slideUp(1000);
//	}

        selectDefaultCatalog();

        $("#removePife").click(function(){
            clearRootImagePath();
        });
    });
    //删除图片主路径
    function deleteImageByImgPaths(){
        if ($("input:checked").size() == 0) {
            alert("请选择要删除的图片！");
            return false;
        }
        return confirm("确定删除选择的图片吗?");
    }

    function selectDefaultCatalog(){
        var _catalogID = $("#catalogID").val();
        if(_catalogID!='' && _catalogID>0){
            //$("#catalogSelect").attr("value",_catalogID);
            $("#catalogSelect").val(_catalogID);
        }
    }

    function catalogChange(obj){
        var _pid = $(obj).find("option:selected").attr("pid");
        if(_pid==0){
            alert("不能选择大类!");
            selectDefaultCatalog();
            return false;
        }
        var _productID = $("#productID").val();

        if(confirm("修改商品类别会清空该商品的属性和参数，确认要这样做吗？")){
            $.blockUI({ message: "正在切换商品目录，请稍候...",css: {
                border: 'none',
                padding: '15px',
                backgroundColor: '#000',
                '-webkit-border-radius': '10px',
                '-moz-border-radius': '10px',
                opacity: .5,
                color: '#fff'
            }});

            //alert($(obj).val());
            if(_productID==''){
                //alert(3);
                document.form.action = "toAdd?chanageCatalog=true&catalog="+$(obj).val();
            }else{
                document.form.action = "updateProductCatalog?id="+_productID+"&chanageCatalog=true&catalog="+$(obj).val();
            }
            document.form.submit();
        }else{
            selectDefaultCatalog();
        }
    }
</script>

<script>
    var editor;
    KindEditor.ready(function(K) {
        editor = K.create('textarea[name="productHTML"]', {
            allowFileManager : true,
            uploadJson : '${basepath}/editor/upload',
            fileManagerJson : '${basepath}/editor/fileManager'
        });
        K('input[name=getHtml]').click(function(e) {
            alert(editor.html());
        });
        K('input[name=isEmpty]').click(function(e) {
            alert(editor.isEmpty());
        });
        K('input[name=getText]').click(function(e) {
            alert(editor.text());
        });
        K('input[name=selectedHtml]').click(function(e) {
            alert(editor.selectedHtml());
        });
        K('input[name=setHtml]').click(function(e) {
            editor.html('<h3>Hello KindEditor</h3>');
        });
        K('input[name=setText]').click(function(e) {
            editor.text('<h3>Hello KindEditor</h3>');
        });
        K('input[name=insertHtml]').click(function(e) {
            editor.insertHtml('<strong>插入HTML</strong>');
        });
        K('input[name=appendHtml]').click(function(e) {
            editor.appendHtml('<strong>添加HTML</strong>');
        });
        K('input[name=clear]').click(function(e) {
            editor.html('');
        });
    });

    function addTrFunc(){
        var cc = $("#firstTr").clone();
        $("#firstTr").after(cc);

        cc.find("a").show();
    }

    function removeThis(t){
        $(t).parent().parent().remove();
        return false;
    }
</script>

<script>
    KindEditor.ready(function(K) {
        var editor = K.editor({
            fileManagerJson : '${basepath}/editor/fileManager'
        });
        K('input[name=filemanager]').click(function() {
            var imagesInputObj = $(this).parent().children("input[ccc=imagesInput]");
            editor.loadPlugin('filemanager', function() {
                editor.plugin.filemanagerDialog({
                    viewType : 'VIEW',
                    dirName : 'image',
                    clickFn : function(url, title) {
                      //  K('#picture').val(url);
                        url=url.replace("/shop-admin","");
                       imagesInputObj.val(url);
                        editor.hideDialog();
                        clearRootImagePath(imagesInputObj);//$("#picture"));
                    }
                });
            });
        });

    });
</script>

<link rel="stylesheet" href="${basepath}/resource/uploadify/uploadify.css"  type="text/css">
<script type="text/javascript" src="${basepath}/resource/uploadify/jquery.uploadify.min.js"></script>

<script type="text/javascript">
    $(document).ready(function() {

        ajaxLoadImgList();
        var url = '${basepath}/common/uploadify.do';
        //alert(url);
        $("#uploadify").uploadify({
            //'auto'           : false,
            'swf'       	 : '${basepath}/resource/uploadify/uploadify.swf',
            'uploader'       : url,//后台处理的请求
            'queueID'        : 'fileQueue',//与下面的id对应
            //'queueSizeLimit' :100,
            //'fileTypeDesc'   : 'rar文件或zip文件',
            //'fileTypeExts' 	 : '*.jpg;*.jpg', //控制可上传文件的扩展名，启用本项时需同时声明fileDesc
            //'fileTypeExts'   : '*.rar;*.zip', //控制可上传文件的扩展名，启用本项时需同时声明fileDesc


            //'fileTypeDesc' : '图片文件' , //出现在上传对话框中的文件类型描述
//'fileTypeExts' : '*.jpg;*.bmp;*.png;*.gif', //控制可上传文件的扩展名，启用本项时需同时声明filedesc

            'multi'          : true,
            'buttonText'     : '本地上传',

            onUploadSuccess:function(file, data, response){
                //alert("上传成功,data="+data+",file="+file+",response="+response);
//				ajaxLoadImgList();
                data = $.parseJSON(data);
                if(data.error == '1') {
                    alert("上传失败：\n失败原因:" + data.msg);
                } else {
                    var $tr = $("#firstTr").clone();
                    $tr.find("img[name=img]").attr("src", "${systemSetting().imageRootPath}" + data.filePath);
                    $tr.find(":input[name=images]").val(data.filePath);
                    $("#firstTr").parent().append($tr);
                    $tr.show();
                }
            },
            onUploadError:function(file, errorCode, errorMsg) {
                alert("上传失败,data="+data+",file="+file+",response="+response);
            }
        });
    });

    //ajax加载内容图片列表
    function ajaxLoadImgList(){
        if($("#id").val()==''){
            $("#fileListDiv").html("");
            return;
        }

        $("#fileListDiv").html("");
        var _url = "ajaxLoadImgList?id="+$("#id").val();
        $.ajax({
            type: 'POST',
            url: _url,
            data: {},
            success: function(data){
                var _tableHtml = "<table class='table table-bordered' style='border:0px solid red;'>";
                _tableHtml += "<tr style='background-color: #dff0'>";
                _tableHtml += "<td>图片地址</td><td>设为默认图片</td><td>操作</td>";
                _tableHtml += "</tr>";
                $.each(data,function(i,row){
                    _tableHtml += "<tr>";
                    var str = "<a target='_blank' href='"+row+"'>"+row+"</a>";
                    _tableHtml += "<td>"+str+"</td><td><input type='radio' onclick='setProductImageToDefault(\""+row+"\")' name='abcdef123'/></td><td><input type='button' Class='btn btn-danger' value='删除' onclick='deleteImageByProductID(\""+row+"\")'/></td>";
                    _tableHtml += "</tr>";
                    //$("#fileListDiv").append("<a target='_blank' href='"+row+"'>"+row+"</a><br>");
                });
                _tableHtml += "</table>";
                $("#fileListDiv").append(_tableHtml);
            },
            dataType: "json",
            error:function(){
                alert("加载图片列表失败！");
            }
        });
    }

    //产品图片设置为默认图片
    function setProductImageToDefault(imageUrl){
        var _url = "setProductImageToDefault?id="+$("#id").val()+"&imageUrl="+imageUrl;
        $.ajax({
            type: 'POST',
            url: _url,
            data: {},
            success: function(data){
                //alert("设置成功!");
                $("#showMessage").append("设置成功！").fadeTo(2000, 1, function(){
                    //alert("Animation Done.");
                    $("#showMessage").html("").hide();
                });
            },
            dataType: "text",
            error:function(){
                alert("设置失败！");
            }
        });
    }

    //产品图片设置为默认图片
    function deleteImageByProductID(imageUrl){
        if(!confirm("确定删除选择的记录?")){
            return ;
        }
        var _url = "deleteImageByProductID?id="+$("#id").val()+"&imageUrl="+imageUrl;
        $.ajax({
            type: 'POST',
            url: _url,
            data: {},
            success: function(data){
                ajaxLoadImgList();
                //$("#showMessage").append("删除成功！").fadeTo(2000, 1, function(){
                //   $("#showMessage").html("").hide();
                //});

            },
            dataType: "text",
            error:function(){
                alert("删除失败！");
            }
        });
    }
</script>
</@page.pageBase>