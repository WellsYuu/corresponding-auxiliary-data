/****************************************
 * Modified clone of https://git.io/v1pul
 ****************************************

/*
  MIT License http://www.opensource.org/licenses/mit-license.php
  Author Tobias Koppers @sokra
*/
var basename = require('path').basename;

function DirectoryDefaultFilePlugin() {}
module.exports = DirectoryDefaultFilePlugin;

DirectoryDefaultFilePlugin.prototype.apply = function (resolver) {
  resolver.plugin('directory', function (req, done) {
    var directory = resolver.join(req.path, req.request);

    resolver.fileSystem.stat(directory, function (err, stat) {
      if (err || !stat) return done();
      if (!stat.isDirectory()) return done();

      var index = resolver.join(directory, 'index.js');

      resolver.fileSystem.stat(index, function (err, stat) {
        if (!err && stat && stat.isFile()) {
          // ignore directories containing index.js files
          return done();
        }

        resolver.doResolve('file', {
          path: req.path,
          query: req.query,
          request: resolver.join(directory, basename(directory))
        }, function (err, result) {
          return done(undefined, result || undefined);
        });
      });
    });
  });
};
