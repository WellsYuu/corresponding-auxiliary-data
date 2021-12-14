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

(function() {
  var mode = CodeMirror.getMode({indentUnit: 2}, "xml"), mname = "xml";
  function MT(name) { test.mode(name, mode, Array.prototype.slice.call(arguments, 1), mname); }

  MT("matching",
     "[tag&bracket <][tag top][tag&bracket >]",
     "  text",
     "  [tag&bracket <][tag inner][tag&bracket />]",
     "[tag&bracket </][tag top][tag&bracket >]");

  MT("nonmatching",
     "[tag&bracket <][tag top][tag&bracket >]",
     "  [tag&bracket <][tag inner][tag&bracket />]",
     "  [tag&bracket </][tag&error tip][tag&bracket&error >]");

  MT("doctype",
     "[meta <!doctype foobar>]",
     "[tag&bracket <][tag top][tag&bracket />]");

  MT("cdata",
     "[tag&bracket <][tag top][tag&bracket >]",
     "  [atom <![CDATA[foo]",
     "[atom barbazguh]]]]>]",
     "[tag&bracket </][tag top][tag&bracket >]");

  // HTML tests
  mode = CodeMirror.getMode({indentUnit: 2}, "text/html");

  MT("selfclose",
     "[tag&bracket <][tag html][tag&bracket >]",
     "  [tag&bracket <][tag link] [attribute rel]=[string stylesheet] [attribute href]=[string \"/foobar\"][tag&bracket >]",
     "[tag&bracket </][tag html][tag&bracket >]");

  MT("list",
     "[tag&bracket <][tag ol][tag&bracket >]",
     "  [tag&bracket <][tag li][tag&bracket >]one",
     "  [tag&bracket <][tag li][tag&bracket >]two",
     "[tag&bracket </][tag ol][tag&bracket >]");

  MT("valueless",
     "[tag&bracket <][tag input] [attribute type]=[string checkbox] [attribute checked][tag&bracket />]");

  MT("pThenArticle",
     "[tag&bracket <][tag p][tag&bracket >]",
     "  foo",
     "[tag&bracket <][tag article][tag&bracket >]bar");

})();
