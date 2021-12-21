'use strict';

var _path = require('path');

module.exports = function pathContains(root, child) {
  return (0, _path.relative)(child, root).slice(0, 2) !== '..';
};
