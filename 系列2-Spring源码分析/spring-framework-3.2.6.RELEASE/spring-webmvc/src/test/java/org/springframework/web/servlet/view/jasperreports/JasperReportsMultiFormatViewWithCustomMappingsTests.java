/*
 * Copyright 2002-2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.web.servlet.view.jasperreports;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Rob Harrop
 * @author Juergen Hoeller
 */
public class JasperReportsMultiFormatViewWithCustomMappingsTests extends JasperReportsMultiFormatViewTests {

	@Override
	protected AbstractJasperReportsView getViewImplementation() {
		JasperReportsMultiFormatView view = new JasperReportsMultiFormatView();
		view.setFormatKey("fmt");
		Map<String, Class<? extends AbstractJasperReportsView>> mappings =
				new HashMap<String, Class<? extends AbstractJasperReportsView>>();
		mappings.put("csv", JasperReportsCsvView.class);
		mappings.put("comma-separated", JasperReportsCsvView.class);
		mappings.put("html", JasperReportsHtmlView.class);
		view.setFormatMappings(mappings);
		return view;
	}

	@Override
	protected String getDiscriminatorKey() {
		return "fmt";
	}

	@Override
	protected void extendModel(Map<String, Object> model) {
		model.put(getDiscriminatorKey(), "comma-separated");
	}

}
