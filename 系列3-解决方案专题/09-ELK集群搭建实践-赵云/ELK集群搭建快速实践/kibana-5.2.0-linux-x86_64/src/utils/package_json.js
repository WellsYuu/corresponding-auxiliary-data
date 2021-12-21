'use strict';

var _fs = require('fs');

var _path = require('path');

var packageDir = undefined;
var packagePath = undefined;

while (!packagePath || !(0, _fs.existsSync)(packagePath)) {
  var prev = packageDir;
  packageDir = prev ? (0, _path.join)(prev, '..') : __dirname;
  packagePath = (0, _path.join)(packageDir, 'package.json');

  if (prev === packageDir) {
    // if going up a directory doesn't work, we
    // are already at the root of the filesystem
    throw new Error('unable to find package.json');
  }
}

module.exports = require(packagePath);
module.exports.__filename = packagePath;
module.exports.__dirname = packageDir;
