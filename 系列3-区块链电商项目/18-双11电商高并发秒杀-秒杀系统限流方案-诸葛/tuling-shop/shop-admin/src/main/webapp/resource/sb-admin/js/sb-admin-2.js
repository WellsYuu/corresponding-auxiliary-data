//Loads the correct sidebar on window load,
//collapses the sidebar on window resize.
// Sets the min-height of #page-wrapper to window size
$(function() {
    $(window).bind("load resize", function() {
        topOffset = 50;
        width = (this.window.innerWidth > 0) ? this.window.innerWidth : this.screen.width;
        if (width < 768) {
            $('div.navbar-collapse').addClass('collapse');
            topOffset = 100; // 2-row-menu
        } else {
            $('div.navbar-collapse').removeClass('collapse');
        }

        height = ((this.window.innerHeight > 0) ? this.window.innerHeight : this.screen.height) - 1;
        height = height - topOffset;
        if (height < 1) height = 1;
        if (height > topOffset) {
            $("#page-wrapper").css("min-height", (height) + "px");
        }
    });

    //var url = window.location;
    //var element = $('ul.nav a').filter(function() {
    //    return this.href == url || url.href.indexOf(this.href) == 0;
    //}).addClass('active').parent().parent().addClass('in').parent();
    //if (element.is('li')) {
    //    element.addClass('active');
    //}


    $.fn._dataTable= $.fn.dataTable;
    $.fn.dataTable = function(options){
        options = $.extend({
            responsive: true,
                scrollY: 300,
            searching: false,
            "serverSide": true,
            "processing": true,
            "ordering":false,
            pagingType:"full_numbers",//simple,simple_numbers,full,full_numbers
            lengthMenu:[1,10,20,50,100],
            pageLength:10,
            "language": {
                "lengthMenu": "每页 _MENU_ 条记录",
                "zeroRecords": "没有找到记录",
                //"info": "第 _PAGE_ / _PAGES_ 页， 共 _TOTAL_ 条记录，当前_START_ - _END_",
                "info": "第 _PAGE_ / _PAGES_ 页， 共 _TOTAL_ 条",
                "infoEmpty": "无记录",
                "emptyTable": "无记录",
                "infoFiltered": "(从 _MAX_ 条记录过滤)",
                "infoPostFix": "",
                "thousands": ",",
                "loadingRecords": "加载中...",
                "processing": "处理中...",
                "search": "搜索:",
                "paginate": {
                    "first": "|<",
                    "last": ">|",
                    "next": ">>",
                    "previous": "<<"
                },
                "aria": {
                    "sortAscending": ": 正向排序",
                    "sortDescending": ": 反向排序"
                },
//                "emptyTable":     "No data available in table",
//                "info":           "Showing _START_ to _END_ of _TOTAL_ entries",
//                "infoEmpty":      "Showing 0 to 0 of 0 entries",
//                "infoFiltered":   "(filtered from _MAX_ total entries)",
//                "infoPostFix":    "",
//                "thousands":      ",",
//                "lengthMenu":     "Show _MENU_ entries",
//                "loadingRecords": "Loading...",
//                "processing":     "Processing...",
//                "search":         "Search:",
//                "zeroRecords":    "No matching records found",
//                "paginate": {
//                    "first":      "First",
//                    "last":       "Last",
//                    "next":       "Next",
//                    "previous":   "Previous"
//                },
//                "aria": {
//                    "sortAscending":  ": activate to sort column ascending",
//                    "sortDescending": ": activate to sort column descending"
//                }

            }
        }, options || {});
        return $(this)._dataTable(options);
    }

    //对表单中的输入框增加bootstrap3风格的样式
    $("form :text,:password,select").not(".form-control").each(function(){
        var display = $(this).css("display");
        $(this).addClass("form-control").css("width","auto").css("display",display||"inline-block");
    });
});
