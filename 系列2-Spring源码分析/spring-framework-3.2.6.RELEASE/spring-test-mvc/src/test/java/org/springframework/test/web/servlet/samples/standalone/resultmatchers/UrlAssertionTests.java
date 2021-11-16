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

package org.springframework.test.web.servlet.samples.standalone.resultmatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import org.junit.Before;
import org.junit.Test;
import org.springframework.stereotype.Controller;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Examples of expectations on forwarded or redirected URLs.
 *
 * @author Rossen Stoyanchev
 */
public class UrlAssertionTests {

	private MockMvc mockMvc;

	@Before
	public void setup() {
		this.mockMvc = standaloneSetup(new SimpleController()).build();
	}

	@Test
	public void testRedirect() throws Exception {
		this.mockMvc.perform(get("/persons")).andExpect(redirectedUrl("/persons/1"));
	}

	@Test
	public void testForward() throws Exception {
		this.mockMvc.perform(get("/")).andExpect(forwardedUrl("/home"));
	}


	@Controller
	private static class SimpleController {

		@RequestMapping("/persons")
		public String save() {
			return "redirect:/persons/1";
		}

		@RequestMapping("/")
		public String forward() {
			return "forward:/home";
		}
	}
}
