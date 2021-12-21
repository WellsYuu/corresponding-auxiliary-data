'use strict';

var _ = require('lodash');

var buildTarget = require('../../lib/build_target.js');

module.exports = function (setup) {
  var targetSeries;

  var tlConfig = {
    getTargetSeries: function getTargetSeries() {
      return _.map(targetSeries, function (bucket) {
        // eslint-disable-line no-use-before-define
        return [bucket, null];
      });
    },
    setTargetSeries: function setTargetSeries() {
      targetSeries = buildTarget(this);
    },
    writeTargetSeries: function writeTargetSeries(series) {
      targetSeries = _.map(series, function (p) {
        return p[0];
      });
    }
  };

  tlConfig = _.extend(tlConfig, setup);
  return tlConfig;
};
