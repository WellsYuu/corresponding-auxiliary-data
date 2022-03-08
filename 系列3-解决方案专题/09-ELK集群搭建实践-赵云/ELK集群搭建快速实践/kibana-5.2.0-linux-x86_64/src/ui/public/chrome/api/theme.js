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

module.exports = function (chrome, internals) {
  /**
   * ui/chrome Theme API
   *
   *   Nav Background
   *     applies to the entire nav bar and is specified as a css string.
   *     eg. 'red' or 'url(..) no-repeat left center'
   *
   *   Logo
   *     Set the background for the logo and small logo in the navbar.
   *     When the app is in the "small" category, a modified version of the
   *     logo is displayed that is 45px wide.
   *     eg. 'url(/plugins/app/logo.png) center no-repeat'
   *
   *   Brand
   *     Similar to a logo, but is just text with styles to make it stick out.
   */

  /**
   * @param {string} background - css background definition
   * @return {chrome}
   */
  chrome.setNavBackground = function (cssBackground) {
    internals.navBackground = cssBackground;
    return chrome;
  };

  /**
   * @return {string} - css background
   */
  chrome.getNavBackground = function () {
    return internals.navBackground;
  };

  /**
   * @param {string|object} item - brand key to set, or object to apply
   * @param {mixed} val - value to put on the brand item
   * @return {chrome}
   */
  chrome.setBrand = function (item, val) {
    internals.brand = internals.brand || {};

    // allow objects to be passed in
    if (_.isPlainObject(item)) {
      internals.brand = _.clone(item);
    } else {
      internals.brand[item] = val;
    }

    return chrome;
  };

  /**
   * @return {string} - the brand text
   */
  chrome.getBrand = function (item) {
    if (!internals.brand) return;
    return internals.brand[item];
  };

  /**
   * Adds a class to the application node
   * @param {string} - the class name to add
   * @return {chrome}
   */
  chrome.addApplicationClass = function (val) {
    let classes = internals.applicationClasses || [];
    classes.push(val);
    classes = _.uniq(classes);

    internals.applicationClasses = classes;
    return chrome;
  };

  /**
   * Removes a class from the application node. Note: this only
   * removes classes that were added via the addApplicationClass method
   * @param  {string|[string]} - class or classes to be removed
   * @return {chrome}
   */
  chrome.removeApplicationClass = function (val) {
    let classesToRemove = [].concat(val || []);
    let classes = internals.applicationClasses || [];
    _.pull(classes, ...classesToRemove);

    internals.applicationClasses = classes;
    return chrome;
  };

  /**
   * @return {string} - a space delimited string of the classes added by the
   * addApplicationClass method
   */
  chrome.getApplicationClasses = function () {
    return internals.applicationClasses.join(' ');
  };

};
