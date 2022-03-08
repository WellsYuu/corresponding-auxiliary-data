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

<table width=100% cellpadding=2 cellspacing=0 border=0 bgcolor=e3e9f8>
<tr><td><font face=arial size=2>&nbsp;<b>Matches</b></font>
</td><td align=right nowrap width=1%><font face=arial size=-1>
<pg:index export="total=itemCount">
<pg:page export="first,last">
    <%= first %> - <%= last %> of <%= total %>
</pg:page>
<pg:first export="url" unless="current">
&nbsp;<b><a href="<%= url %>">First Page</a></b>&nbsp;|
</pg:first>
<pg:prev export="url,first,last">
<% int prevItems = (last.intValue() - first.intValue()) + 1; %>
<b><a href="<%= url %>">Previous <%= prevItems %></a></b>
</pg:prev>
<pg:next export="url,first,last">
<% int nextItems = (last.intValue() - first.intValue()) + 1; %>
|&nbsp;<b><a href="<%= url %>">Next <%= nextItems %></a></b>
</pg:next>
 &nbsp;&nbsp;</font></td></tr>
</table>
</pg:index>
