/*
 * Copyright 2021-2022 the original author or authors
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

(function(mod) {
  if (typeof exports == "object" && typeof module == "object") // CommonJS
    mod(require("../../lib/codemirror"));
  else if (typeof define == "function" && define.amd) // AMD
    define(["../../lib/codemirror"], mod);
  else // Plain browser env
    mod(CodeMirror);
})(function(CodeMirror) {
  "use strict";

  CodeMirror.defineMode("jinja2", function() {
    var keywords = ["and", "as", "block", "endblock", "by", "cycle", "debug", "else", "elif",
      "extends", "filter", "endfilter", "firstof", "for",
      "endfor", "if", "endif", "ifchanged", "endifchanged",
      "ifequal", "endifequal", "ifnotequal",
      "endifnotequal", "in", "include", "load", "not", "now", "or",
      "parsed", "regroup", "reversed", "spaceless",
      "endspaceless", "ssi", "templatetag", "openblock",
      "closeblock", "openvariable", "closevariable",
      "openbrace", "closebrace", "opencomment",
      "closecomment", "widthratio", "url", "with", "endwith",
      "get_current_language", "trans", "endtrans", "noop", "blocktrans",
      "endblocktrans", "get_available_languages",
      "get_current_language_bidi", "plural"],
    operator = /^[+\-*&%=<>!?|~^]/,
    sign = /^[:\[\(\{]/,
    atom = ["true", "false"],
    number = /^(\d[+\-\*\/])?\d+(\.\d+)?/;

    keywords = new RegExp("((" + keywords.join(")|(") + "))\\b");
    atom = new RegExp("((" + atom.join(")|(") + "))\\b");

    function tokenBase (stream, state) {
      var ch = stream.peek();

      //Comment
      if (state.incomment) {
        if(!stream.skipTo("#}")) {
          stream.skipToEnd();
        } else {
          stream.eatWhile(/\#|}/);
          state.incomment = false;
        }
        return "comment";
      //Tag
      } else if (state.intag) {
        //After operator
        if(state.operator) {
          state.operator = false;
          if(stream.match(atom)) {
            return "atom";
          }
          if(stream.match(number)) {
            return "number";
          }
        }
        //After sign
        if(state.sign) {
          state.sign = false;
          if(stream.match(atom)) {
            return "atom";
          }
          if(stream.match(number)) {
            return "number";
          }
        }

        if(state.instring) {
          if(ch == state.instring) {
            state.instring = false;
          }
          stream.next();
          return "string";
        } else if(ch == "'" || ch == '"') {
          state.instring = ch;
          stream.next();
          return "string";
        } else if(stream.match(state.intag + "}") || stream.eat("-") && stream.match(state.intag + "}")) {
          state.intag = false;
          return "tag";
        } else if(stream.match(operator)) {
          state.operator = true;
          return "operator";
        } else if(stream.match(sign)) {
          state.sign = true;
        } else {
          if(stream.eat(" ") || stream.sol()) {
            if(stream.match(keywords)) {
              return "keyword";
            }
            if(stream.match(atom)) {
              return "atom";
            }
            if(stream.match(number)) {
              return "number";
            }
            if(stream.sol()) {
              stream.next();
            }
          } else {
            stream.next();
          }

        }
        return "variable";
      } else if (stream.eat("{")) {
        if (ch = stream.eat("#")) {
          state.incomment = true;
          if(!stream.skipTo("#}")) {
            stream.skipToEnd();
          } else {
            stream.eatWhile(/\#|}/);
            state.incomment = false;
          }
          return "comment";
        //Open tag
        } else if (ch = stream.eat(/\{|%/)) {
          //Cache close tag
          state.intag = ch;
          if(ch == "{") {
            state.intag = "}";
          }
          stream.eat("-");
          return "tag";
        }
      }
      stream.next();
    };

    return {
      startState: function () {
        return {tokenize: tokenBase};
      },
      token: function (stream, state) {
        return state.tokenize(stream, state);
      }
    };
  });
});
