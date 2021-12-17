<#macro menu menus=[] topMenu="" currentMenu="首页">
<script>
    $(function(){
        $('#side-menu').metisMenu();
        $("a.menu-item").each(function(){
            var href = $(this).data("href");
            $(this).attr("href", href==""?"#":basepath + href);
        })
        var parent = $("a.menu-item.active").parent().parent();
        if(parent.is("ul.nav")){
            parent.addClass("in").parent().addClass("active");
        }
    });
</script>
<div class="navbar-default sidebar" role="navigation">
    <div class="sidebar-nav navbar-collapse">
        <!-- /.navbar-top-links -->
        <ul class="nav" id="side-menu">
            <li class="sidebar-search" style="margin-top: 10px;">
                <#--<div class="input-group custom-search-form" >-->
                    <#--<input type="text" class="form-control" placeholder="Search...">-->
                                <#--<span class="input-group-btn">-->
                                <#--<button class="btn btn-default" type="button">-->
                                    <#--<i class="fa fa-search"></i>-->
                                <#--</button>-->
                            <#--</span>-->
                <#--</div>-->
                <!-- /input-group -->
            </li>
                <#list menus as menu>
                    <li>
                        <a id="item_${menu.pid!"0"}_${menu.id!""}" data-href="${menu.url}" class="menu-item ${(currentMenu==menu.name)?string("active","")}"><i class="fa fa-folder fa-fw"></i> ${menu.name!""}<#if menu.children?? && menu.children?size gt 0><span class="fa arrow"></span></#if></a>
                        <#if menu.children?? && menu.children?size gt 0>
                            <ul class="nav nav-second-level">
                            <#list menu.children as menu>
                                <li>
                                    <a id="item_${menu.pid!"0"}_${menu.id!""}" data-href="${menu.url}"  class="menu-item ${(currentMenu==menu.name)?string("active","")}"><i class="fa fa-files-o fa-fw"></i> ${menu.name!""}</a>
                                </li>
                            </#list>
                            </ul>
                        </#if>
                    </li>
                </#list>
        </ul>
    </div>
    <!-- /.sidebar-collapse -->
</div>
<!-- /.navbar-static-side -->
</#macro>