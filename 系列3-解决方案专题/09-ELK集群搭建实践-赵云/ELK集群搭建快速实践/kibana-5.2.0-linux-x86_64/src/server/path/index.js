'use strict';

Object.defineProperty(exports, '__esModule', {
  value: true
});

var _fs = require('fs');

var _lodash = require('lodash');

var _utils = require('../../utils');

var CONFIG_PATHS = [process.env.CONFIG_PATH, (0, _utils.fromRoot)('config/kibana.yml'), '/etc/kibana/kibana.yml'].filter(Boolean);

var DATA_PATHS = [process.env.DATA_PATH, (0, _utils.fromRoot)('data'), '/var/lib/kibana'].filter(Boolean);

function findFile(paths) {
  var availablePath = (0, _lodash.find)(paths, function (configPath) {
    try {
      (0, _fs.accessSync)(configPath, _fs.R_OK);
      return true;
    } catch (e) {
      //Check the next path
    }
  });
  return availablePath || paths[0];
}

exports['default'] = {
  getConfig: function getConfig() {
    return findFile(CONFIG_PATHS);
  },
  getData: function getData() {
    return findFile(DATA_PATHS);
  }
};
module.exports = exports['default'];
