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

/**
* function createRequest
* @type XMLHttpRequest
* @memberOf Window
*/
Window.prototype.createRequest= function(){return new XMLHttpRequest();};
/**
* Object XMLHttpRequest
* @type constructor
*/
XMLHttpRequest.prototype=new Object();
function XMLHttpRequest(){};

/**
 * function onreadystatechange
 * @memberOf XMLHttpRequest
 */
XMLHttpRequest.prototype.onreadystatechange=function(){};
/**
 * property readyState
 * @type Number
 * @memberOf XMLHttpRequest
 */
XMLHttpRequest.prototype.readyState=0;
/**
 * property responseText
 * @type String
 * @memberOf XMLHttpRequest
 */
XMLHttpRequest.prototype.responseText="";
/**
 * property responseXML
 * @type Document
 * @memberOf XMLHttpRequest
 */
XMLHttpRequest.prototype.responseXML=new Document();
/**
 * property status
 * @type Number
 * @memberOf XMLHttpRequest
 */
XMLHttpRequest.prototype.status=0;
/**
 * property statusText
 * @type String
 * @memberOf XMLHttpRequest
 */
XMLHttpRequest.prototype.statusText="";
/**
 * function abort()
 * @memberOf XMLHttpRequest
 */
XMLHttpRequest.prototype.abort=function(){};
/**
* function getAllResponseHeaders()
* @type String
* @memberOf XMLHttpRequest
*/
XMLHttpRequest.prototype.getAllResponseHeaders=function(){return "";};
/**
* function open(method, url, async, username, password)
* @param {String} method
* @param {String} url
* @param {Boolean} optional async
* @param {String} optional username
* @param {String} optional password
* @memberOf XMLHttpRequest
*/
XMLHttpRequest.prototype.open=function(method, url, async, username, password){};
/**
* function send(body)
* @param {Object} body
* @memberOf XMLHttpRequest
*/
XMLHttpRequest.prototype.send=function(body){};
/**
* function setRequestHeader(header,value)
* @param {String} header
* @param {String} value
* @memberOf XMLHttpRequest
*/
XMLHttpRequest.prototype.setRequestHeader=function(header,value){};
/**
* function getAllResponseHeaders()
* @param {String} header
* @type String
* @memberOf XMLHttpRequest
*/
XMLHttpRequest.prototype.getResponseHeader=function(header){return "";};
