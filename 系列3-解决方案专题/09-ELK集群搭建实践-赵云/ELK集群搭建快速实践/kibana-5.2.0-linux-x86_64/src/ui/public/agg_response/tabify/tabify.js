/*
 * Copyright 2021-2022 the original author or authors.
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

import _ from 'lodash';
import VisAggConfigProvider from 'ui/vis/agg_config';
import AggResponseTabifyResponseWriterProvider from 'ui/agg_response/tabify/_response_writer';
import AggResponseTabifyBucketsProvider from 'ui/agg_response/tabify/_buckets';
export default function tabifyAggResponseProvider(Private, Notifier) {

  let AggConfig = Private(VisAggConfigProvider);
  let TabbedAggResponseWriter = Private(AggResponseTabifyResponseWriterProvider);
  let Buckets = Private(AggResponseTabifyBucketsProvider);
  let notify = new Notifier({ location: 'agg_response/tabify'});

  function tabifyAggResponse(vis, esResponse, respOpts) {
    let write = new TabbedAggResponseWriter(vis, respOpts);

    let topLevelBucket = _.assign({}, esResponse.aggregations, {
      doc_count: esResponse.hits.total
    });

    collectBucket(write, topLevelBucket);

    return write.response();
  }

  /**
   * read an aggregation from a bucket, which is *might* be found at key (if
   * the response came in object form), and will recurse down the aggregation
   * tree and will pass the read values to the ResponseWriter.
   *
   * @param {object} bucket - a bucket from the aggResponse
   * @param {undefined|string} key - the key where the bucket was found
   * @returns {undefined}
   */
  function collectBucket(write, bucket, key) {
    let agg = write.aggStack.shift();

    switch (agg.schema.group) {
      case 'buckets':
        let buckets = new Buckets(bucket[agg.id]);
        if (buckets.length) {
          let splitting = write.canSplit && agg.schema.name === 'split';
          if (splitting) {
            write.split(agg, buckets, function forEachBucket(subBucket, key) {
              collectBucket(write, subBucket, agg.getKey(subBucket), key);
            });
          } else {
            buckets.forEach(function (subBucket, key) {
              write.cell(agg, agg.getKey(subBucket, key), function () {
                collectBucket(write, subBucket, agg.getKey(subBucket, key));
              });
            });
          }
        } else if (write.partialRows && write.metricsForAllBuckets && write.minimalColumns) {
          // we don't have any buckets, but we do have metrics at this
          // level, then pass all the empty buckets and jump back in for
          // the metrics.
          write.aggStack.unshift(agg);
          passEmptyBuckets(write, bucket, key);
          write.aggStack.shift();
        } else {
          // we don't have any buckets, and we don't have isHierarchical
          // data, so no metrics, just try to write the row
          write.row();
        }
        break;
      case 'metrics':
        let value = agg.getValue(bucket);
        write.cell(agg, value, function () {
          if (!write.aggStack.length) {
            // row complete
            write.row();
          } else {
            // process the next agg at this same level
            collectBucket(write, bucket, key);
          }
        });
        break;
    }

    write.aggStack.unshift(agg);
  }

  // write empty values for each bucket agg, then write
  // the metrics from the initial bucket using collectBucket()
  function passEmptyBuckets(write, bucket, key) {
    let agg = write.aggStack.shift();

    switch (agg.schema.group) {
      case 'metrics':
        // pass control back to collectBucket()
        write.aggStack.unshift(agg);
        collectBucket(write, bucket, key);
        return;

      case 'buckets':
        write.cell(agg, '', function () {
          passEmptyBuckets(write, bucket, key);
        });
    }

    write.aggStack.unshift(agg);
  }

  return notify.timed('tabify agg response', tabifyAggResponse);
};
