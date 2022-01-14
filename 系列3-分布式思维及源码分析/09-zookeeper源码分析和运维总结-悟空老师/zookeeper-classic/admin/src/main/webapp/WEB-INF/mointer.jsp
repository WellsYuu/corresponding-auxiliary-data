<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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

<link rel="stylesheet" href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
<link rel="stylesheet" href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">
<script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
<html>
<html>
<head>
    <title>图灵学院</title>
</head>
<body>


<body>
<h1><center>Zookeeper运维命令</center></h1>
<a class="btn" href="conf">conf</a>
<a class="btn" href="cons">cons</a>
<a class="btn" href="crst">crst</a>
<a class="btn" href="dump">dump</a>
<a class="btn" href="envi">envi</a>
<a class="btn" href="ruok">ruok</a>
<a class="btn" href="srst">srst</a>
<a class="btn" href="srvr">srvr</a>
<a class="btn" href="stat">stat</a>
<a class="btn" href="wchs">wchs</a>
<a class="btn" href="wchp">wchp</a>
<a class="btn" href="mntr">mntr</a>

<table class="table table-striped">
    <tr style="font-weight:bold">
        <td>zk1</td>
        <td>zk2</td>
        <td>zk3</td>
    </tr>

        <tr>
            <td class="warning">${zk1}</td>
            <td class="warning">${zk2}</td>
            <td class="warning">${zk3}</td>
        </tr>

</table>

</body>

</html>