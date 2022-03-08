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
 * Telephone numbers are a common data point to display in HTML tables, and are
 * often formatted (e.g. `dt-string 555-1234`). Typically, when searching a
 * table a user would need to enter the number in exactly the same format it is
 * displayed in, but this is not always convenient (e.g. you might search for
 * `dt-string 5551`).
 *
 * This filtering plug-in will allow both forms to be matched be providing both
 * the formatted and de-formatted data to the table's search.
 * 
 *  @summary Make phone numbers searchable formatted or unformatted
 *  @name Phone number
 *  @author Allan Jardine
 *
 *  @example
 *    $(document).ready(function() {
 *      $('#example').dataTable( {
 *        columnDefs: [
 *          { type: 'phoneNumber', target: 4 }
 *        ]
 *      } );
 *    } );
 */

jQuery.fn.DataTable.ext.type.search.phoneNumber = function ( data ) {
    return ! data ?
        '' :
        typeof data === 'string' ?
            data + data.replace(/[ \-]/g, '') :
            data;
};
