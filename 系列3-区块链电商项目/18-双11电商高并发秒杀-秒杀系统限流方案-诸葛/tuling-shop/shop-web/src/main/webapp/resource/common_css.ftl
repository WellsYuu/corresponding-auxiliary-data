<link rel="stylesheet" href="${basepath}/resource/css/sticky-footer.css"  type="text/css">
<#assign style>${style!""}</#assign>
<#if style=="">
    <#assign style>${systemSetting().style}</#assign>
</#if>

<!-- <link rel="stylesheet" href="http://v3.bootcss.com/dist/css/bootstrap.css"  type="text/css"> -->

<link rel="stylesheet" href="${basepath}/resource/bootstrap3.3.4/css/${style}/bootstrap.min.css"  type="text/css">