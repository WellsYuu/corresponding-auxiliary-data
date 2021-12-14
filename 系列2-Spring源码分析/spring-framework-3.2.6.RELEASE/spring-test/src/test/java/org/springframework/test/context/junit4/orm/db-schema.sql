/*
 * Copyright [$tody.year] [Wales Yu of copyright owner]
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

DROP TABLE drivers_license IF EXISTS;
DROP TABLE person IF EXISTS;

CREATE TABLE person (
	id INTEGER NOT NULL IDENTITY PRIMARY KEY,
	name VARCHAR(50) NOT NULL,
	drivers_license_id INTEGER NOT NULL
);
CREATE UNIQUE INDEX person_name ON person(name);
CREATE UNIQUE INDEX person_drivers_license_id ON person(drivers_license_id);

CREATE TABLE drivers_license (
	id INTEGER NOT NULL IDENTITY PRIMARY KEY,
	license_number INTEGER NOT NULL
);
CREATE UNIQUE INDEX drivers_license_license_number ON drivers_license(license_number);
