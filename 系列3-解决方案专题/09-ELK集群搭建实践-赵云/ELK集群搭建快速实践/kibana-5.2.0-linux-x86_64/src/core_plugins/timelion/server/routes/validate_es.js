'use strict';

module.exports = function (server) {
  server.route({
    method: 'GET',
    path: '/api/timelion/validate/es',
    handler: function handler(request, reply) {
      return server.uiSettings().getAll(request).then(function (uiSettings) {
        var _server$plugins$elasticsearch$getCluster = server.plugins.elasticsearch.getCluster('data');

        var callWithRequest = _server$plugins$elasticsearch$getCluster.callWithRequest;

        var timefield = uiSettings['timelion:es.timefield'];

        var body = {
          index: uiSettings['es.default_index'],
          fields: timefield
        };

        callWithRequest(request, 'fieldStats', body).then(function (resp) {
          reply({
            ok: true,
            field: timefield,
            min: resp.indices._all.fields[timefield].min_value,
            max: resp.indices._all.fields[timefield].max_value
          });
        })['catch'](function (resp) {
          reply({
            ok: false,
            resp: resp
          });
        });
      });
    }
  });
};
