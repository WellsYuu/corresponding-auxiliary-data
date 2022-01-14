<%@ page session="false" %>
<%@ taglib uri="http://jsptags.com/tags/navigation/pager" prefix="pg" %>
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

<jsp:useBean id="currentPageNumber" type="java.lang.Integer" scope="request"/>
<font face="fixed">
<pg:first export="firstPageUrl=pageUrl" unless="current">
  <a href="<%= firstPageUrl %>">|&lt;</a>
</pg:first>
<pg:skip export="skipBackPageUrl=pageUrl" pages="<%= -10 %>">
  <a href="<%= skipBackPageUrl %>">&lt;&lt;&lt;</a>
</pg:skip>
<pg:prev export="prevPageUrl=pageUrl">
  <a href="<%= prevPageUrl %>">&lt;&lt;</a>
</pg:prev>
<pg:pages><%
  if (pageNumber == currentPageNumber) {
    %> <b><%= pageNumber %></b> <%
  } else {
    %> <a href="<%= pageUrl %>"><%= pageNumber %></a> <%
  }
%></pg:pages>
<pg:next export="nextPageUrl=pageUrl">
  <a href="<%= nextPageUrl %>">&gt;&gt;</a>
</pg:next>
<pg:skip export="skipForwardPageUrl=pageUrl" pages="<%= 10 %>">
  <a href="<%= skipForwardPageUrl %>">&gt;&gt;&gt;</a>
</pg:skip>
<pg:last export="lastPageUrl=pageUrl" unless="current">
  <a href="<%= lastPageUrl %>">&gt;|</a>
</pg:last>
</font>
