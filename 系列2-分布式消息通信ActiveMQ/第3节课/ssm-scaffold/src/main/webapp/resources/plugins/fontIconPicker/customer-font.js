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

$.fn.initIconPicker=function(options){
    options=$.extend({},$.fn.initIconPicker.options,options);
    var _icon=$(this).fontIconPicker();
    $.ajax({
        url:options.url,
        type:'get',
        async:false,
        dataType:"json"
    }).done(function(response){
        var classPrefix = response.preferences.fontPref.prefix;
        var icomoon_json_icons = [];
        var icomoon_json_search = [];
        $.each(response.icons, function(i, v) {
            icomoon_json_icons.push( classPrefix + v.properties.name );
            if ( v.icon && v.icon.tags && v.icon.tags.length ) {
                icomoon_json_search.push( v.properties.name + ' ' + v.icon.tags.join(' ') );
            } else {
                icomoon_json_search.push( v.properties.name );
            }
        });
        _icon.setIcons(icomoon_json_icons, icomoon_json_search);
    }).fail(function(){
        
    });
}
