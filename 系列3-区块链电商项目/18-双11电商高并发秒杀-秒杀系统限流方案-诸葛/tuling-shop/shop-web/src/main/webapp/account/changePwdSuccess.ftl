<#import "/resource/common_html_front.ftl" as html>
<#import "/indexMenu.ftl" as menu>
<#import "/account/accountMenu.ftl" as accountMenu>
<@html.htmlBase>
    <@menu.menu selectMenu=""/>
<div class="container">
    <div class="row">
        <div class="col-xs-3">
            <@accountMenu.accountMenu currentMenu="topwd"/>
        </div>

        <div class="col-xs-9">
            <div class="row">
                <div class="col-xs-12">
                    <ol class="breadcrumb">
                        <li class="active">修改密码</li>
                    </ol>
                </div>
            </div>

            <hr>

            <div class="row">
                <div class="col-xs-12" style="font-size: 14px;font-weight: normal;">
                    <div class="panel panel-default">
                        <div class="panel-body" style="font-size: 16px;font-weight: normal;text-align: center;">
                            <span class="glyphicon glyphicon-ok"></span>恭喜您：新密码修改成功！
                        </div>
                    </div>
                    <hr>
                </div>
            </div>

        </div>
    </div>
</div>

</@html.htmlBase>
