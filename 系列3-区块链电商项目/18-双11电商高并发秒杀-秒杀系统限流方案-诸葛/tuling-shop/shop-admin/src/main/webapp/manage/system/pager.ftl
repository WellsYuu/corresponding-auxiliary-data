<#assign pg = JspTaglibs["/WEB-INF/jsp/pager-taglib.tld"]/>
<style type="text/css">
.pageLink {
	border: 1px solid #dddddd;
	padding: 4px 12px;
	text-decoration: none;
}

.selectPageLink {
	border: 1px solid #0088cc;
	padding: 4px 12px;
	color: #0088cc;
	background-color: #dddddd;
	text-decoration: none;
}
</style>
	<!-- 分页标签 -->
	<div style="text-align: right; border: 0;padding: 4px 12px;" class="pageDiv">
		<@pg.pager url="${pager.pagerUrl}" items=pager.total
			export="currentPageNumber=pageNumber"
			maxPageItems=pager.pageSize maxIndexPages=10 isOffset=true>

					总共：${pager.total}条,共:${pager.pagerSize}页
                <@pg.param name="cc"/>
			<@pg.first>
				<a href="${pageUrl}" class="pageLink">首页</a>
			</@pg.first>
			<@pg.prev>
				<a href="${pageUrl}" class="pageLink">上一页</a>
			</@pg.prev>
			<@pg.pages>
            <#if currentPageNumber==pageNumber>
                <span class="selectPageLink">${pageNumber}</span>
                <#else >
                    <a href="${pageUrl}" class="pageLink">${pageNumber}</a>
            </#if>
			</@pg.pages>
			<@pg.next>
				<a href="${pageUrl}" class="pageLink">下一页</a>
			</@pg.next>
			<@pg.last>
				<a href="${pageUrl}" class="pageLink">尾页</a>
			</@pg.last>
		</@pg.pager>
	</div>