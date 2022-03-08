/*
 * Copyright 2021-2022 the original author or authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

module.exports = function calculateInterval(from, to, size, interval) {
  if (interval !== 'auto') return interval;
  return roundInterval((to - from) / size);
};

// Totally cribbed this from Kibana 3.
// I bet there's something similar in the Kibana 4 code. Somewhere. Somehow.
function roundInterval(interval) {
  switch (true) {
    case (interval <= 500):         // <= 0.5s
      return '100ms';
    case (interval <= 5000):        // <= 5s
      return '1s';
    case (interval <= 7500):        // <= 7.5s
      return '5s';
    case (interval <= 15000):       // <= 15s
      return '10s';
    case (interval <= 45000):       // <= 45s
      return '30s';
    case (interval <= 180000):      // <= 3m
      return '1m';
    case (interval <= 450000):      // <= 9m
      return '5m';
    case (interval <= 1200000):     // <= 20m
      return '10m';
    case (interval <= 2700000):     // <= 45m
      return '30m';
    case (interval <= 7200000):     // <= 2h
      return '1h';
    case (interval <= 21600000):    // <= 6h
      return '3h';
    case (interval <= 86400000):    // <= 24h
      return '12h';
    case (interval <= 604800000):   // <= 1w
      return '24h';
    case (interval <= 1814400000):  // <= 3w
      return '1w';
    case (interval < 3628800000):   // <  2y
      return '30d';
    default:
      return '1y';
  }
};
