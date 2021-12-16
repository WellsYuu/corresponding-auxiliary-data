/*
 * Copyright 2021-2021 the original author or authors.
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

/**
 * Patched version as per https://github.com/flot/flot/issues/326
 */
(function ($) {
    var options = {
        series: { stack: null } // or number/string
    };

    function init(plot) {

        // will be built up dynamically as a hash from x-value to
        var stackBases = {};

        function stackData(plot, s, datapoints) {
            if (s.stack == null || s.stack === false)
                return;

            var newPoints = [];
    
            for (var i=0; i <  datapoints.points.length; i += 3) {

                if (!stackBases[datapoints.points[i]]) {
                    stackBases[datapoints.points[i]] = 0;
                }

                // note that the values need to be turned into absolute y-values.
                // in other words, if you were to stack (x, y1), (x, y2), and (x, y3), (each from different series, which is where stackBases comes in),
                // you'd want the new points to be (x, y1, 0), (x, y1+y2, y1), (x,y1+y2+y3, y1+y2)
                // generally, (x, thisValue + (base up to this point), + (base up tothis point))
                newPoints[i] = datapoints.points[i];
                newPoints[i+1] = datapoints.points[i+1] + stackBases[datapoints.points[i]];
                newPoints[i+2] = stackBases[datapoints.points[i]];
                stackBases[datapoints.points[i]] += datapoints.points[i+1];
            }
            datapoints.points = newPoints;
        }
        plot.hooks.processDatapoints.push(stackData);
    }

    $.plot.plugins.push({
        init: init,
        options: options,
        name: 'stack',
        version: '1.2-patch-326'
    });
})(jQuery);
