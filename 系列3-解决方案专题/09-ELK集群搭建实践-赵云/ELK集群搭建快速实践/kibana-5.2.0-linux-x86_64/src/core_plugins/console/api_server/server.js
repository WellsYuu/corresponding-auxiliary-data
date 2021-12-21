"use strict";

var _ = require("lodash");

module.exports.resolveApi = function (sense_version, apis, reply) {
  var result = {};
  _.each(apis, function (name) {
    {
      // for now we ignore sense_version. might add it in the api name later
      var api = require('./' + name);
      result[name] = api.asJson();
    }
  });

  return reply(result).type("application/json");
};
