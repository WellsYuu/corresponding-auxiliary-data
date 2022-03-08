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

@/*
    表格标签的参数说明:

    id : table表格的id
@*/
<table id="${id}" data-mobile-responsive="true" data-click-to-select="true">
    <thead>
        <tr>
            <th data-field="selectItem" data-checkbox="true"></th>
        </tr>
    </thead>
</table>