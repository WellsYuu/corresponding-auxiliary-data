/*
 * Copyright 2021-2021 the original author or authors.
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
  var mode = CodeMirror.getMode({}, "shell");
  function MT(name) { test.mode(name, mode, Array.prototype.slice.call(arguments, 1)); }

  MT("var",
     "text [def $var] text");
  MT("varBraces",
     "text[def ${var}]text");
  MT("varVar",
     "text [def $a$b] text");
  MT("varBracesVarBraces",
     "text[def ${a}${b}]text");

  MT("singleQuotedVar",
     "[string 'text $var text']");
  MT("singleQuotedVarBraces",
     "[string 'text ${var} text']");

  MT("doubleQuotedVar",
     '[string "text ][def $var][string  text"]');
  MT("doubleQuotedVarBraces",
     '[string "text][def ${var}][string text"]');
  MT("doubleQuotedVarPunct",
     '[string "text ][def $@][string  text"]');
  MT("doubleQuotedVarVar",
     '[string "][def $a$b][string "]');
  MT("doubleQuotedVarBracesVarBraces",
     '[string "][def ${a}${b}][string "]');

  MT("notAString",
     "text\\'text");
  MT("escapes",
     "outside\\'\\\"\\`\\\\[string \"inside\\`\\'\\\"\\\\`\\$notAVar\"]outside\\$\\(notASubShell\\)");

  MT("subshell",
     "[builtin echo] [quote $(whoami)] s log, stardate [quote `date`].");
  MT("doubleQuotedSubshell",
     "[builtin echo] [string \"][quote $(whoami)][string 's log, stardate `date`.\"]");

  MT("hashbang",
     "[meta #!/bin/bash]");
  MT("comment",
     "text [comment # Blurb]");

  MT("numbers",
     "[number 0] [number 1] [number 2]");
  MT("keywords",
     "[keyword while] [atom true]; [keyword do]",
     "  [builtin sleep] [number 3]",
     "[keyword done]");
  MT("options",
     "[builtin ls] [attribute -l] [attribute --human-readable]");
  MT("operator",
     "[def var][operator =]value");
})();
