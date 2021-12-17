<#macro htmlBase title="" jsFiles=[] cssFiles=[] staticJsFiles=[] staticCssFiles=[] checkLogin=true>
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
    <script>
        var basepath = "${basepath}";
        var staticpath = "${staticpath}";
        var imageRootPath = "${systemSetting().imageRootPath}";
        var non_responsive2 = "${non_responsive2}";
        var systemCode = "${systemSetting().systemCode}"
        <#if currentUser()??>
            var login = true;
        var currentUser = "${currentUser().username}";
        <#else >
        var login = false;
        var currentUser = "";
            <#if checkLogin>
                top.location = "${basepath}/manage/user/logout";
            </#if>
        </#if>
    </script>
    <#if non_responsive2 != "y">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
    </#if>
    <meta name="description" content="${systemSetting().description}"/>
    <meta name="keywords" content="${systemSetting().keywords}"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>${(title?? && title!="")?string("${systemSetting().systemCode} - "+ title , "${systemSetting().systemCode} - JAVA开源电商系统")}</title>
    <link rel="shortcut icon" type="image/x-icon" href="${systemSetting().shortcuticon}">

    <link rel="stylesheet" href="${staticpath}/zTree3.5/css/zTreeStyle/zTreeStyle.css" type="text/css">
    <link rel="stylesheet" href="${staticpath}/bootstrap3.3.4/css/bootstrap.min.css"  type="text/css">
    <#--<link rel="stylesheet" href="${staticpath}/bootstrap3.3.4/css/bootstrap-theme.min.css"  type="text/css">-->
    <link rel="stylesheet" href="${staticpath}/jquery-ui-1.11.2/jquery-ui.css">
    <#--<link rel="stylesheet" href="${staticpath}/jquery-ui-1.11.2/jquery-ui.theme.css">-->
    <link rel="stylesheet" href="${staticpath}/validator-0.7.0/jquery.validator.css" />

<#--<script type="text/javascript" src="${staticpath}/js/jquery-1.4.2.min.js"></script>-->
    <script type="text/javascript" src="${staticpath}/js/jquery-1.9.1.min.js"></script>
    <script type="text/javascript" src="${staticpath}/zTree3.5/js/jquery.ztree.all-3.5.min.js"></script>

    <script type="text/javascript" src="${staticpath}/js/jquery.blockUI.js"></script>
    <script type="text/javascript" src="${staticpath}/bootstrap3.3.4/js/bootstrap.min.js"></script>
    <!-- sb admin -->
    <link rel="stylesheet" href="${staticpath}/sb-admin/css/sb-admin-2.css" />
    <script src="${staticpath}/sb-admin/js/sb-admin-2.js" ></script>

    <link href="${staticpath}/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">

    <script src="${staticpath}/jquery-ui-1.11.2/jquery-ui.js"></script>
    <!-- jquery validator -->

    <script type="text/javascript" src="${staticpath}/validator-0.7.0/jquery.validator.js"></script>
    <script type="text/javascript" src="${staticpath}/validator-0.7.0/local/zh_CN.js"></script>

    <script type="text/javascript" src="${staticpath}/My97DatePicker/WdatePicker.js"></script>

    <link rel="stylesheet" href="${staticpath}/kindeditor-4.1.7/themes/default/default.css" />
    <script charset="utf-8" src="${staticpath}/kindeditor-4.1.7/kindeditor-min.js"></script>
    <script charset="utf-8" src="${staticpath}/kindeditor-4.1.7/lang/zh_CN.js"></script>

    <!-- datatables -->
    <link rel="stylesheet" href="${staticpath}/datatables/css/jquery.dataTables.css" />
    <script charset="utf-8" src="${staticpath}/datatables/js/jquery.dataTables.js"></script>
    <link rel="stylesheet" href="${staticpath}/datatables-plugins/integration/bootstrap/3/dataTables.bootstrap.css" />
    <script charset="utf-8" src="${staticpath}/datatables-plugins/integration/bootstrap/3/dataTables.bootstrap.js"></script>

    <!-- metisMenu -->
    <link href="${staticpath}/metisMenu/metisMenu.min.css" rel="stylesheet">
    <script src="${staticpath}/metisMenu/metisMenu.min.js"></script>
    <#--<link rel="stylesheet" href="${staticpath}/datatables-responsive/css/dataTables.responsive.css" />-->
    <#--<script charset="utf-8" src="${staticpath}/datatables-responsive/js/dataTables.responsive.js"></script>-->
    <script type="text/javascript" src="${basepath}/manage/manage.js"></script>
    <#list staticJsFiles as jsFile>
        <script src="${staticpath}/${jsFile}"></script>
    </#list>
    <#list staticCssFiles as cssFile>
        <link rel="stylesheet" href="${staticpath}/${cssFile}" />
    </#list>

    <#list jsFiles as jsFile>
        <script src="${basepath}/manage/${jsFile}"></script>
    </#list>
    <#list cssFiles as cssFile>
        <link rel="stylesheet" href="${basepath}/manage/${cssFile}" />
    </#list>
</head>
<#nested />
</html>
</#macro>
