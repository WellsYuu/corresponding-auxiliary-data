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
 * This sorting plug-in will sort, in calendar order, data which
 * is in the format "MMM yyyy" or "MMMM yyyy". Inspired by forum discussion:
 * http://datatables.net/forums/discussion/1242/sorting-dates-with-only-month-and-year
 *
 * Please note that this plug-in is **deprecated*. The
 * [datetime](//datatables.net/blog/2014-12-18) plug-in provides enhanced
 * functionality and flexibility.
 *
 *  @name Date (MMM yyyy) or (MMMM yyyy)
 *  @anchor Sort dates in the format `MMM yyyy` or `MMMM yyyy`
 *  @author Phil Hurwitz
 *  @deprecated
 *
 *  @example
 *    $('#example').DataTable( {
 *       columnDefs: [
 *         { type: 'stringMonthYear', targets: 0 }
 *       ]
 *    } );
 */

jQuery.extend(jQuery.fn.dataTableExt.oSort, {
    "stringMonthYear-pre": function (s) {
        var months = ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"];

        var dateComponents = s.split(" ");
        dateComponents[0] = dateComponents[0].replace(",", "");
        dateComponents[1] = jQuery.trim(dateComponents[1]);

        var year = dateComponents[1];

        var month = 0;
        for (var i = 0; i < months.length; i++) {
            if (months[i].toLowerCase() == dateComponents[0].toLowerCase().substring(0,3)) {
                month = i;
                break;
            }
        }

        return new Date(year, month, 1);
    },

    "stringMonthYear-asc": function (a, b) {
        return ((a < b) ? -1 : ((a > b) ? 1 : 0));
    },

    "stringMonthYear-desc": function (a, b) {
        return ((a < b) ? 1 : ((a > b) ? -1 : 0));
    }
});