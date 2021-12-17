
<#macro html currentMenu>
    <#import "/resource/common_html_front.ftl" as html>
    <#import "/indexMenu.ftl" as menu>
    <#import "/account/accountMenu.ftl" as accountMenu>
<@html.htmlBase>
    <@menu.menu selectMenu=""/>
<div class="container">
    <div class="row">
        <div class="col-xs-3">
            <@accountMenu.accountMenu currentMenu=currentMenu/>
        </div>
        <#nested/>
    </div>
</div>
</@html.htmlBase>
</#macro>