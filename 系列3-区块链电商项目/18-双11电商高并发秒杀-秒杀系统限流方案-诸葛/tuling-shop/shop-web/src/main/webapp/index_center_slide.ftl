
<!-- 首页中间位置图片轮播 -->
<div class="zhuanti_box">
  <div id="slideBox">
    <div class="J_slide" style="height: 100%;">
      <div class="J_slide_clip">
        <ul class="J_slide_list">
			<#list systemManager().indexImages as item>
		          <li class="J_slide_item">
		          <#if item.link??>
			          <a href="${item.link!""}" target="_blank">
			          	<img style="max-width: 100%;" 
			          	src="${systemSetting().imageRootPath}/${item.picture!""}" >
			          </a>
		          <#else>
                      <img style="max-width: 100%;"
                           src="${systemSetting().imageRootPath}/${item.picture!""}" >
		          </#if>
		          </li>
			</#list>
        </ul>
      </div>
      <ul class="J_slide_trigger">
		<#list systemManager().indexImages as item>
	        <li class="">
	        	<a href="javascript:" title="${item.title!""}">
				${item.title!""}
	        	</a>
	        </li>
			</#list>
      </ul>
    </div>
  </div>
</div>
  <script type="text/javascript">
   new Tab('.J_tab',{auto:false});
   new Slide('#slideBox',{index: 1 ,effect:'slide', firstDelay:8});
  </script>
