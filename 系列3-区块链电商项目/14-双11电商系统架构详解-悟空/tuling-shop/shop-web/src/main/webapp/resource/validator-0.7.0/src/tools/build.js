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

#!/usr/local/bin/node

var fs = require('fs'),
    path = require('path'),
    //https://github.com/mishoo/UglifyJS2
    U2 = require("uglify-js"),
    //http://learnboost.github.io/stylus/
    stylus = require('stylus');

var SRC  = '',
    OUT  = '../',
    pkg = JSON.parse(fs.readFileSync(OUT + 'package.json')),
    NS = pkg.name,
    COPYRIGHT = '/*! '+ pkg.title +' '+ pkg.version +'\n'
              + ' * (c) 2012-'+ new Date().getFullYear() +' '+ pkg.author +', '+ pkg.licenses[0].type +' Licensed\n'
              + ' * '+ pkg.homepage +'\n'
              + ' */';
    
console.log('building and minifying...');
buildJS(NS);
buildStyl(NS);
console.log('done');


function buildJS(name) {
    var filename = name + '.js',
        content = fs.readFileSync(SRC+ filename).toString(),
        ast = U2.parse(content),
        compressor,
        code = '';
    
    compressor = U2.Compressor({
        unsafe: true
    });

    ast.figure_out_scope();
    ast = ast.transform(compressor);

    ast.figure_out_scope();
    ast.compute_char_frequency();
    ast.mangle_names();

    code = ast.print_to_string() + '\n';
    console.log('compiled: ' + filename);

    fs.writeFile(OUT + filename, COPYRIGHT + '\n' + code);
    fs.writeFile(SRC+ filename, content.replace(/\/\*\![\s\S]+?\*\//, COPYRIGHT));
}

function buildStyl(name) {
    var filename = name + '.styl',
        content = fs.readFileSync(SRC + filename).toString();

        stylus(content)
            .set('filename', filename)
            .set('paths', [__dirname])
            .set('compress', true)
            .render(function(err, css){
                if (err) throw err;
                console.log('compiled: ' + filename);
                fs.writeFile(OUT + name + '.css', COPYRIGHT + '\n' + css);
            });
}
