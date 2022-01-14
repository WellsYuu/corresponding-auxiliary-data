<%@ page contentType="text/html; charset=UTF-8"%>

<%--
  ~ Copyright 2021-2022 the original author or authors.
  ~
  ~ Licensed under the Apache License, Version 2.0 (the "License");
  ~ you may not use this file except in compliance with the License.
  ~ You may obtain a copy of the License at
  ~
  ~     http://www.apache.org/licenses/LICENSE-2.0
  ~
  ~ Unless required by applicable law or agreed to in writing, software
  ~ distributed under the License is distributed on an "AS IS" BASIS,
  ~ WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  ~ See the License for the specific language governing permissions and
  ~ limitations under the License.
  --%>

<!DOCTYPE html>
<html>
<head>
<script src="http://cdnjs.bootcss.com/ajax/libs/jquery/1.9.1/jquery.min.js" > </script>

</head>

<body>
<!-- 新浪微博登陆 -->
						<div id="wb_connect_btn"></div>
						<input type="button" value="新浪微博登陆" onclick="_login3213123()"/>
						<input type="button" value="新浪微博退出登陆" onclick="_logout321312323()"/>
						<script src="http://tjs.sjs.sinajs.cn/open/api/js/wb.js?appkey=3176592960" type="text/javascript" charset="utf-8"></script>
						<script type="text/javascript">
						(function(){
							if(WB2.checkLogin()){
								console.log("已经登陆");
							}else{
								console.log("未登陆");
							}
						})();
						function _login3213123(){
							WB2.login(function(){
								console.log("登陆成功");
							});
						}
						function _logout321312323(){
							WB2.logout(function(){
								console.log("退出成功");
							});
						}
						
						WB2.anyWhere(function(W){
						    W.widget.connectButton({
						        id: "wb_connect_btn",
						        type: '3,2',
						        callback : {
						            login:function(o){
						                console.log("logout,screen_name"+o.screen_name);
						                $.each(o,function(index,value){
						                	console.log("index="+index+",value="+value);
						                });
						                //window.location.href="http://www.itelse.com";
						            },
						            logout:function(){
						            	console.log("logout");
						            }
						        }
						    });
						});
						</script>
</body>
</html>