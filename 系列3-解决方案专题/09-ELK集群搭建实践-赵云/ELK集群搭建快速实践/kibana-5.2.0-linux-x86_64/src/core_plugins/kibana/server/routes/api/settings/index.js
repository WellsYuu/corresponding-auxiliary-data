'use strict';

Object.defineProperty(exports, '__esModule', {
  value: true
});

exports['default'] = function (server) {
  require('./register_get')(server);
  require('./register_set')(server);
  require('./register_set_many')(server);
  require('./register_delete')(server);
};

module.exports = exports['default'];
