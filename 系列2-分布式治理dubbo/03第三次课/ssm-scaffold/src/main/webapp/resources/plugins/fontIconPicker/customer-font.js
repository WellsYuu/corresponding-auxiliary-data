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
