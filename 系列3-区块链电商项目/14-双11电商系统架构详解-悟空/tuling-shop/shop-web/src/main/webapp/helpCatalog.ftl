<div class="panel panel-primary">
	<div class="panel-heading">帮助中心</div>
  <div class="panel-body">
    	<ul>
			<#list newsCatalogs as item>
			<li class="list-item0">
				<h5>
					${item.name!""}
				</h5>
				<#if item.news??>
				<#list item.news as item>
					<div style="margin-left: 20px;">
						<a href="${basepath}/help/${item.code!""}.html">${item.title!""}</a>
					</div>
				</#list>
				</#if>
			</li>
			</#list>
    	</ul>
  </div>
</div>


