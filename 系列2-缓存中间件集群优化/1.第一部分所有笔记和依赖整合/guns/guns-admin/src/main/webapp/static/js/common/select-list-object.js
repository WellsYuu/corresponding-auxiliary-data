/*
 * Copyright [$tody.year] [Wales Yu of copyright owner]
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

var SelectList = {};
SelectList.singelSelect = function (eleName, attr, data) {
    var result = false;
    $("#" + eleName).find("a").each(function (i, d) {
        if ($(this).attr(attr) == data) {
            if ($(this).hasClass('active')) {
                $(this).removeClass('active');
            } else {
                $(this).addClass('active');
                result = true;
            }
        } else {
            $(this).removeClass('active');
        }
    });
    return result;
};
SelectList.mutiSelect = function (eleName, attr, data) {
    var result = false;
    $("#" + eleName).find("a").each(function (i, d) {
        if ($(this).attr(attr) == data) {
            if ($(this).hasClass('active')) {
                $(this).removeClass('active');
            } else {
                $(this).addClass('active');
                result = true;
            }
        }
    });
    return result;
};
SelectList.clearSelect = function (eleName) {
    $("#" + eleName).find("a").each(function (i, d) {
        $(this).removeClass('active');
    });
};