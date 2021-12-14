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

-- The next comment line has no text after the '--' prefix.
--
-- The next comment line starts with a space.
 -- x, y, z...

insert into customer (id, name)
values	(1, 'Rod; Johnson'), (2, 'Adrian Collier');
-- This is also a comment.
insert into orders(id, order_date, customer_id)
values (1, '2008-01-02', 2);
insert into orders(id, order_date, customer_id) values (1, '2008-01-02', 2);
INSERT INTO persons( person_id--      
                   , name)
VALUES( 1      -- person_id
      , 'Name' --name
);--