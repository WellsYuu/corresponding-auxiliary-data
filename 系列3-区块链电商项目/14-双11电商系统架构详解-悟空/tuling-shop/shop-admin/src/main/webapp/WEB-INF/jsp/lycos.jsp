<%@ page session="false" %>
<%@ taglib uri="http://jsptags.com/tags/navigation/pager" prefix="pg" %>
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

<font face="verdana" color="#aaaaaa" size="-1">
<pg:prev export="pageUrl" ifnull="<%= true %>">
  <% if (pageUrl == null) { %>
    <b>&laquo; Previous</b>
  <% } else { %>
    <font color="#ff0000"><b>&laquo;</b></font>
    <a href="<%= pageUrl %>"><b>Previous</b></a>
  <% } %>
</pg:prev>
<pg:pages export="pageUrl,firstItem,lastItem">
     <a href="<%= pageUrl %>"><b><%= firstItem %> thru <%= lastItem %></b></a> 
</pg:pages>
<pg:next export="pageUrl" ifnull="<%= true %>">
  <% if (pageUrl == null) { %>
    <b>Next &raquo;</b>
  <% } else { %>
    <a href="<%= pageUrl %>"><b>Next</b></a>
    <font color="#ff0000"><b>&raquo;</b></font>
  <% } %>
</pg:next>
</font>
