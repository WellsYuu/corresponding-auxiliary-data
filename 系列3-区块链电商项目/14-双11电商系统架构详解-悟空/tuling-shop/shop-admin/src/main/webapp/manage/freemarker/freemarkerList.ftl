<#import "/manage/tpl/htmlBase.ftl" as html/>
<@html.htmlBase>
	<form action="${basepath}/manage/freemarker" namespace="/manage" method="post" theme="simple">
		<table class="table table-bordered">
			<tr>
				<td><a href="${basepath}/freemarker/create?method=products" class="btn btn-primary">所有商品介绍全部静态化</a></td>
			</tr>
		</table>
	</form>
</@html.htmlBase>