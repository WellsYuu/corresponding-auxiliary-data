<?xml version="1.0" encoding="UTF-8"?>
<!--
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
  -->

<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<xsl:output method="xml" indent="yes" media-type="text/html"/>
	<xsl:param name="title"/>

	<xsl:template match="/">
		<html>
			<head>
				<title><xsl:value-of select="$title"/></title>
			</head>
			<body>
				<table>
					<xsl:apply-templates select="/products/product"/>
				</table>
			</body>
		</html>
	</xsl:template>

</xsl:stylesheet>
