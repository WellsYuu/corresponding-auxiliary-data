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
    tpl = require('./tpl.js'),
    LOCAL_DIR = 'local/',
    OUT_DIR = '../local/',
    compiler = tpl( fs.readFileSync( LOCAL_DIR + '_lang.tpl' ).toString() );

console.log('building...');
task(LOCAL_DIR);

function task(src){
    fs.readdirSync(src).forEach(function(f){
        var name = path.basename(f);
        if ( /^[a-z]{2}(?:_[A-Z]{2})?\.js/.test(name) ) {
            build( name );
        }
    });
}

function build(name) {
    var obj = require('../' + LOCAL_DIR + name),
        data = obj.lang,
        outfile = path.join(OUT_DIR, name),
        str;

        data.local_string = obj.local;
        data.rules = obj.rules;
        str = compiler.render(data);

        fs.writeFileSync(outfile, str);
        console.log( 'ok: '+outfile );
}