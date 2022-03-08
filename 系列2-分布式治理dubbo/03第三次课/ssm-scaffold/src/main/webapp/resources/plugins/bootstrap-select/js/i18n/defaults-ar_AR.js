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

(function (root, factory) {
  if (typeof define === 'function' && define.amd) {
    // AMD. Register as an anonymous module unless amdModuleId is set
    define(["jquery"], function (a0) {
      return (factory(a0));
    });
  } else if (typeof exports === 'object') {
    // Node. Does not work with strict CommonJS, but
    // only CommonJS-like environments that support module.exports,
    // like Node.
    module.exports = factory(require("jquery"));
  } else {
    factory(jQuery);
  }
}(this, function (jQuery) {

/*!
 * Translated default messages for bootstrap-select.
 * Locale: AR (Arabic)
 * Author: Yasser Lotfy <y_l@alive.com>
 */
(function ($) {
  $.fn.selectpicker.defaults = {
    noneSelectedText: 'لم يتم إختيار شئ',
    noneResultsText: 'لا توجد نتائج مطابقة لـ {0}',
    countSelectedText: function (numSelected, numTotal) {
      return (numSelected == 1) ? "{0} خيار تم إختياره" : "{0} خيارات تمت إختيارها";
    },
    maxOptionsText: function (numAll, numGroup) {
      return [
        (numAll == 1) ? 'تخطى الحد المسموح ({n} خيار بحد أقصى)' : 'تخطى الحد المسموح ({n} خيارات بحد أقصى)',
        (numGroup == 1) ? 'تخطى الحد المسموح للمجموعة ({n} خيار بحد أقصى)' : 'تخطى الحد المسموح للمجموعة ({n} خيارات بحد أقصى)'
      ];
    },
    selectAllText: 'إختيار الجميع',
    deselectAllText: 'إلغاء إختيار الجميع',
    multipleSeparator: '، '
  };
})(jQuery);


}));
