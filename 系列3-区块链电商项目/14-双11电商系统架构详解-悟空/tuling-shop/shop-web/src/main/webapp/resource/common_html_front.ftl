<#macro htmlBase title="${systemSetting().systemCode}" jsFiles=[] cssFiels=[] nobody=false>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <#assign non_responsive2>y</#assign>
    <#assign responsive>${Session["responsive"]!""}</#assign>
    <#if responsive == "y">
        <#assign non_responsive2>n</#assign>
    <#elseif systemSetting().openResponsive == "n">
        <#assign non_responsive2>y</#assign>
    <#else >
        <#assign non_responsive2>n</#assign>
    </#if>
    <#assign style>${RequestParameters.style!""}</#assign>
    <#if style=="">
        <#assign style>${systemSetting().style}</#assign>
    </#if>

    <!-- <link rel="stylesheet" href="http://v3.bootcss.com/dist/css/bootstrap.css"  type="text/css"> -->
    <script>
        var basepath = "${basepath}";
        var staticpath = "${staticpath}";
        var non_responsive2 = "${non_responsive2}";
        <#if currentUser()??>
            var login = true;
        var currentUser = "${currentUser().username}";
        <#else >
        var login = false;
        var currentUser = "";
        </#if>
    </script>
    <#if non_responsive2 != "y">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
    </#if>
    <meta name="description" content="${systemSetting().description}"/>
    <meta name="keywords" content="${systemSetting().keywords}"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>${title!"${systemSetting().systemCode}"}</title>
    <link rel="shortcut icon" type="image/x-icon" href="${systemSetting().shortcuticon}">
    <link rel="stylesheet" href="${basepath}/resource/css/sticky-footer.css"  type="text/css">
    <link rel="stylesheet" href="${basepath}/resource/css/base.css"  type="text/css">
    <#--<link rel="stylesheet" href="${basepath}/resource/zTree3.5/css/zTreeStyle/zTreeStyle.css" type="text/css">-->
    <link rel="stylesheet" href="${basepath}/resource/bootstrap3.3.4/css/${style}/bootstrap.min.css"  type="text/css">
    <link rel="stylesheet" href="${basepath}/resource/bootstrap3.3.4/css/docs.css"  type="text/css">
    <#--<link rel="stylesheet" href="${basepath}/resource/jquery-jquery-ui/themes/base/jquery.ui.all.css">-->
    <link rel="stylesheet" href="${basepath}/resource/validator-0.7.0/jquery.validator.css" />
    <#if non_responsive2 == "y">
        <link rel="stylesheet" href="${basepath}/resource/bootstrap3.3.4/css/non-responsive.css"  type="text/css">
    </#if>

<#--<script type="text/javascript" src="${basepath}/resource/js/jquery-1.4.2.min.js"></script>-->
    <script type="text/javascript" src="${basepath}/resource/js/jquery-1.9.1.min.js"></script>
    <#--<script type="text/javascript" src="${basepath}/resource/zTree3.5/js/jquery.ztree.all-3.5.min.js"></script>-->

    <script type="text/javascript" src="${basepath}/resource/bootstrap/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="${basepath}/resource/js/jquery.blockUI.js"></script>
    <#--<script type="text/javascript" src="${basepath}/resource/js/manage.js"></script>-->

    <#--<script src="${basepath}/resource/jquery-jquery-ui/jquery-1.5.1.js"></script>-->
    <#--<script src="${basepath}/resource/jquery-jquery-ui/ui/jquery.ui.core.js"></script>-->
    <#--<script src="${basepath}/resource/jquery-jquery-ui/ui/jquery.ui.widget.js"></script>-->
    <#--<script src="${basepath}/resource/jquery-jquery-ui/ui/jquery.ui.tabs.js"></script>-->
    <!-- jquery validator -->

    <script type="text/javascript" src="${basepath}/resource/validator-0.7.0/jquery.validator.js"></script>
    <script type="text/javascript" src="${basepath}/resource/validator-0.7.0/local/zh_CN.js"></script>

    <#--<script type="text/javascript" src="${basepath}/resource/My97DatePicker/WdatePicker.js"></script>-->

    <#--<link rel="stylesheet" href="${basepath}/resource/kindeditor-4.1.7/themes/default/default.css" />-->
    <#--<script charset="utf-8" src="${basepath}/resource/kindeditor-4.1.7/kindeditor-min.js"></script>-->
    <#--<script charset="utf-8" src="${basepath}/resource/kindeditor-4.1.7/lang/zh_CN.js"></script>-->
    <script type="text/javascript" src="${basepath}/resource/js/jquery.lazyload.min.js"></script>
    <script type="text/javascript" src="${basepath}/resource/js/superMenu/js/new.js"></script>
    <link href="${basepath}/resource/js/slideTab2/css/lanrenzhijia.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="${basepath}/resource/js/slideTab2/js/lanrenzhijia.js"></script>

    <#include "/resource/common_css.ftl"/>
</head>
    <#if nobody>
        <#if systemSetting().isopen=="false">
        ${systemSetting().closeMsg!"系统关闭，请联系管理员"}
            <#return />
        </#if>
        <#nested />
    <#else >
    <body>
        <#if systemSetting().isopen=="false">
        ${systemSetting().closeMsg!"系统关闭，请联系管理员"}
            <#return />
        </#if>
        <#nested />
        <#include "/foot.ftl">
        <#include "/index_superSlide_js.ftl">
    </body>
    </#if>
</html>
</#macro>
