/*
 * Copyright 2002-2013 the original author or authors.
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

package org.springframework.core.env;

public class DummyEnvironment implements Environment {

	public boolean containsProperty(String key) {
		return false;
	}

	public String getProperty(String key) {
		return null;
	}

	public String getProperty(String key, String defaultValue) {
		return null;
	}

	public <T> T getProperty(String key, Class<T> targetType) {
		return null;
	}

	public <T> T getProperty(String key, Class<T> targetType, T defaultValue) {
		return null;
	}

	public <T> Class<T> getPropertyAsClass(String key, Class<T> targetType) {
		return null;
	}

	public String getRequiredProperty(String key) throws IllegalStateException {
		return null;
	}

	public <T> T getRequiredProperty(String key, Class<T> targetType)
			throws IllegalStateException {
		return null;
	}

	public String resolvePlaceholders(String text) {
		return null;
	}

	public String resolveRequiredPlaceholders(String text)
			throws IllegalArgumentException {
		return null;
	}

	public String[] getActiveProfiles() {
		return null;
	}

	public String[] getDefaultProfiles() {
		return null;
	}

	public boolean acceptsProfiles(String... profiles) {
		return false;
	}

}
