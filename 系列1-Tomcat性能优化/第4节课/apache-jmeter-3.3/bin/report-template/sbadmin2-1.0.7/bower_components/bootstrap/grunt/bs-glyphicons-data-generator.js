/*
 * Copyright 2021-2022 the original author or authors.
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
'use strict';
var fs = require('fs');

module.exports = function generateGlyphiconsData(grunt) {
  // Pass encoding, utf8, so `readFileSync` will return a string instead of a
  // buffer
  var glyphiconsFile = fs.readFileSync('less/glyphicons.less', 'utf8');
  var glyphiconsLines = glyphiconsFile.split('\n');

  // Use any line that starts with ".glyphicon-" and capture the class name
  var iconClassName = /^\.(glyphicon-[a-zA-Z0-9-]+)/;
  var glyphiconsData = '# This file is generated via Grunt task. **Do not edit directly.**\n' +
                       '# See the \'build-glyphicons-data\' task in Gruntfile.js.\n\n';
  var glyphiconsYml = 'docs/_data/glyphicons.yml';
  for (var i = 0, len = glyphiconsLines.length; i < len; i++) {
    var match = glyphiconsLines[i].match(iconClassName);

    if (match !== null) {
      glyphiconsData += '- ' + match[1] + '\n';
    }
  }

  // Create the `_data` directory if it doesn't already exist
  if (!fs.existsSync('docs/_data')) {
    fs.mkdirSync('docs/_data');
  }

  try {
    fs.writeFileSync(glyphiconsYml, glyphiconsData);
  }
  catch (err) {
    grunt.fail.warn(err);
  }
  grunt.log.writeln('File ' + glyphiconsYml.cyan + ' created.');
};
