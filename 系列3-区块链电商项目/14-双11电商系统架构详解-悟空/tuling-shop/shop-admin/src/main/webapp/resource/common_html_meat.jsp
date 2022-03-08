<%@ page contentType="text/html; charset=UTF-8"%>
<%@page import="SystemManager"%>
<%@page import="FrontContainer"%>
<%--
  ~ Copyright 2021-2022 the original author or authors
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

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>


<%

Object responsive_session2 = request.getSession().getAttribute("responsive");
boolean non_responsive2 = true;
if(responsive_session2!=null){
	if(responsive_session2.toString().equals("y")){
		non_responsive2 = false;
	}
}else{
	if(SystemManager.getInstance().getSystemSetting().getOpenResponsive().equals("n")){
		non_responsive2 = true;
	}else{
		non_responsive2 = false;
	}
}
if(!non_responsive2){
%>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<%} %>

<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title><%=SystemManager.getInstance().getSystemSetting().getTitle()%></title>
<meta name="description" content="<%=SystemManager.getInstance().getSystemSetting().getDescription()%>" />
<meta name="keywords"  content="<%=SystemManager.getInstance().getSystemSetting().getKeywords()%>" />
<link rel="shortcut icon" type="image/x-icon" href="<%=SystemManager.getInstance().getSystemSetting().getShortcuticon()%>">

