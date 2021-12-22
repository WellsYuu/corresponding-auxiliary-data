'use strict';

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { 'default': obj }; }

var _lodash = require('lodash');

var _lodash2 = _interopRequireDefault(_lodash);

function Samples(max) {
  this.vals = {};
  this.max = max || Infinity;
  this.length = 0;
}

Samples.prototype.add = function (sample) {
  var vals = this.vals;
  var length = this.length = Math.min(this.length + 1, this.max);

  _lodash2['default'].forOwn(sample, function (val, name) {
    if (val == null) val = null;

    if (!vals[name]) vals[name] = new Array(length);
    vals[name].unshift([Date.now(), val]);
    vals[name].length = length;
  });
};

Samples.prototype.toJSON = function () {
  return this.vals;
};

module.exports = Samples;
