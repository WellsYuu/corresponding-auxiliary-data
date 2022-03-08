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

import _ from 'lodash';

/**
 * Extension of Angular's FormController class
 * that provides helpers for error handling/validation.
 *
 * @param {$scope} $scope
 */
function KbnFormController($scope, $element) {
  let self = this;

  self.errorCount = function (predicate) {
    return self.$$invalidModels().length;
  };

  // same as error count, but filters out untouched and pristine models
  self.softErrorCount = function () {
    return self.$$invalidModels(function (model) {
      return model.$touched || model.$dirty;
    }).length;
  };

  self.describeErrors = function () {
    let count = self.softErrorCount();
    return count + ' Error' + (count === 1 ? '' : 's');
  };

  self.$$invalidModels = function (predicate) {
    predicate = _.callback(predicate);

    let invalid = [];

    _.forOwn(self.$error, function collect(models) {
      if (!models) return;

      models.forEach(function (model) {
        if (model.$$invalidModels) {
          // recurse into child form
          _.forOwn(model.$error, collect);
        } else {
          if (predicate(model)) {
            // prevent dups
            let len = invalid.length;
            while (len--) if (invalid[len] === model) return;

            invalid.push(model);
          }
        }
      });
    });

    return invalid;
  };

  self.$setTouched = function () {
    self.$$invalidModels().forEach(function (model) {
      // only kbnModels have $setTouched
      if (model.$setTouched) model.$setTouched();
    });
  };

  function filterSubmits(event) {
    if (self.errorCount()) {
      event.preventDefault();
      event.stopImmediatePropagation();
      self.$setTouched();
    }
  }

  $element.on('submit', filterSubmits);
  $scope.$on('$destroy', function () {
    $element.off('submit', filterSubmits);
  });
}

export default KbnFormController;
