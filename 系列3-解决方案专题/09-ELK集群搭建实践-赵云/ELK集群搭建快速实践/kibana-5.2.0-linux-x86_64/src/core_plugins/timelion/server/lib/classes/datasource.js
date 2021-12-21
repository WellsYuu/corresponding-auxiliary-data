'use strict';

var _get = function get(_x, _x2, _x3) { var _again = true; _function: while (_again) { var object = _x, property = _x2, receiver = _x3; _again = false; if (object === null) object = Function.prototype; var desc = Object.getOwnPropertyDescriptor(object, property); if (desc === undefined) { var parent = Object.getPrototypeOf(object); if (parent === null) { return undefined; } else { _x = parent; _x2 = property; _x3 = receiver; _again = true; desc = parent = undefined; continue _function; } } else if ('value' in desc) { return desc.value; } else { var getter = desc.get; if (getter === undefined) { return undefined; } return getter.call(receiver); } } };

function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError('Cannot call a class as a function'); } }

function _inherits(subClass, superClass) { if (typeof superClass !== 'function' && superClass !== null) { throw new TypeError('Super expression must either be null or a function, not ' + typeof superClass); } subClass.prototype = Object.create(superClass && superClass.prototype, { constructor: { value: subClass, enumerable: false, writable: true, configurable: true } }); if (superClass) Object.setPrototypeOf ? Object.setPrototypeOf(subClass, superClass) : subClass.__proto__ = superClass; }

var loadFunctions = require('../load_functions.js');
var fitFunctions = loadFunctions('fit_functions');
var TimelionFunction = require('./timelion_function');
var offsetTime = require('../offset_time');

var _ = require('lodash');
var moment = require('moment');

function offsetSeries(response, offset) {
  if (offset) {
    response = _.map(response, function (point) {
      return [offsetTime(point[0], offset, true), point[1]];
    });
  }
  return response;
}

module.exports = (function (_TimelionFunction) {
  _inherits(Datasource, _TimelionFunction);

  function Datasource(name, config) {
    _classCallCheck(this, Datasource);

    // Additional arguments that every dataSource take
    config.args.push({
      name: 'offset',
      types: ['string', 'null'],
      help: 'Offset the series retrieval by a date expression. Eg -1M to make events from one month ago appear as if they are happening now'
    });

    config.args.push({
      name: 'fit',
      types: ['string', 'null'],
      help: 'Algorithm to use for fitting series to the target time span and interval. Available: ' + _.keys(fitFunctions).join(', ')
    });

    // Wrap the original function so we can modify inputs/outputs with offset & fit
    var originalFunction = config.fn;
    config.fn = function (args, tlConfig) {
      var config = _.clone(tlConfig);
      if (args.byName.offset) {
        config.time = _.cloneDeep(tlConfig.time);
        config.time.from = offsetTime(config.time.from, args.byName.offset);
        config.time.to = offsetTime(config.time.to, args.byName.offset);
      }

      return Promise.resolve(originalFunction(args, config)).then(function (seriesList) {
        seriesList.list = _.map(seriesList.list, function (series) {
          if (series.data.length === 0) throw new Error(name + '() returned no results');
          series.data = offsetSeries(series.data, args.byName.offset);
          series.fit = args.byName.fit || series.fit || 'nearest';
          return series;
        });
        return seriesList;
      });
    };

    _get(Object.getPrototypeOf(Datasource.prototype), 'constructor', this).call(this, name, config);

    // You  need to call timelionFn if calling up a datasource from another datasource,
    // otherwise teh series will end up being offset twice.
    this.timelionFn = originalFunction;
    this.datasource = true;
    this.cacheKey = function (item) {
      return item.text;
    };
    Object.freeze(this);
  }

  return Datasource;
})(TimelionFunction);
