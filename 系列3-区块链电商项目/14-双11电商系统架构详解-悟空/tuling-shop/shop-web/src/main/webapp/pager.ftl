<#assign pg = JspTaglibs["/WEB-INF/jsp/pager-taglib.tld"]/>
<!-- 分页标签 -->
<#if pager.list?? && pager.pagerSize gt 1>
	<ul class="pagination pagination-sm" style="margin: 0px;">

		<@pg.pager url="${pager.pagerUrl}" items=pager.total
		export="currentPageNumber=pageNumber"
		maxPageItems=pager.pageSize maxIndexPages=10 isOffset=true>

            总共：${pager.total}条,共:${pager.pagerSize}页
            <#if orderBy?? && orderBy != 0>
				<@pg.param name="orderBy"/>
            </#if>
			<@pg.first>
            	<li><a href="${pageUrl}" class="pageLink">首页</a></li>
			</@pg.first>
			<@pg.prev>
                <li><a href="${pageUrl}" class="pageLink">上一页</a></li>
			</@pg.prev>
			<@pg.pages>
				<#if currentPageNumber==pageNumber>
                    <li class="disabled"><a href="#" style="background-color: red;border-color: red;cursor: default;">${pageNumber}</a></li>
				<#else >
                    <li><a href="${pageUrl}" class="pageLink">${pageNumber}</a></li>
				</#if>
			</@pg.pages>
			<@pg.next>
                <li><a href="${pageUrl}">下一页</a></li>
			</@pg.next>
			<@pg.last>
                <li><a href="${pageUrl}">尾页</a></li>
			</@pg.last>
		</@pg.pager>
	</ul>
</#if>
