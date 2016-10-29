/**
 * Created by lenovo on 2016-09-17.
 */
require(["jquery", "datatables.responsive"], function ($, dt) {

    function getAjaxUrl() {
        return {
            smses: '/web/system/sms/data'
        }
    }

    var responsiveHelper = undefined;
    var breakpointDefinition = {
        tablet: 1024,
        phone: 480
    };
    var tableElement = $('#example');

    var myTable = tableElement.DataTable({
        autoWidth: false,
        preDrawCallback: function () {
            // Initialize the responsive datatables helper once.
            if (!responsiveHelper) {
                responsiveHelper = new ResponsiveDatatablesHelper(tableElement, breakpointDefinition);
            }
        },
        rowCallback: function (nRow) {
            responsiveHelper.createExpandIcon(nRow);
        },
        drawCallback: function (oSettings) {
            responsiveHelper.respond();
        },
        searching: false,
        "processing": true, // 打开数据加载时的等待效果
        "serverSide": true,// 打开后台分页
        "aaSorting": [[1, 'desc']],// 排序
        "ajax": {
            "url": web_path + getAjaxUrl().smses,
            "dataSrc": "data",
            "data": function (d) {
                // 添加额外的参数传给服务器
                var searchParam = initParam();
                d.extra_search = JSON.stringify(searchParam);
            }
        },
        "columns": [
            {"data": "acceptPhone"},
            {"data": "sendTimeNew"}
        ],
        "language": {
            "sProcessing": "处理中...",
            "sLengthMenu": "显示 _MENU_ 项结果",
            "sZeroRecords": "没有匹配结果",
            "sInfo": "显示第 _START_ 至 _END_ 项结果，共 _TOTAL_ 项",
            "sInfoEmpty": "显示第 0 至 0 项结果，共 0 项",
            "sInfoFiltered": "(由 _MAX_ 项结果过滤)",
            "sInfoPostFix": "",
            "sSearch": "搜索:",
            "sUrl": "",
            "sEmptyTable": "表中数据为空",
            "sLoadingRecords": "载入中...",
            "sInfoThousands": ",",
            "oPaginate": {
                "sFirst": "首页",
                "sPrevious": "上页",
                "sNext": "下页",
                "sLast": "末页"
            },
            "oAria": {
                "sSortAscending": ": 以升序排列此列",
                "sSortDescending": ": 以降序排列此列"
            }
        },
        "dom": "<'row'<'col-sm-3'l><'#global_button.col-sm-2'><'col-sm-7'<'#mytoolbox'>>r>" +
        "t" +
        "<'row'<'col-sm-5'i><'col-sm-7'p>>"
    });

    var html = '<input type="text" id="search_phone" class="form-control input-sm" placeholder="手机号" />' +
        '  <button type="button" id="search" class="btn btn-outline btn-default btn-sm"><i class="fa fa-search"></i>搜索</button>' +
        '  <button type="button" id="reset_search" class="btn btn-outline btn-default btn-sm"><i class="fa fa-repeat"></i>重置</button>';
    $('#mytoolbox').append(html);

    var global_button = '<button type="button" id="refresh" class="btn btn-outline btn-default btn-sm"><i class="fa fa-refresh"></i>刷新</button>';
    $('#global_button').append(global_button);

    function getParamId() {
        return {
            acceptPhone: '#search_phone'
        };
    }


    function initParam() {
        return {
            acceptPhone: $(getParamId().acceptPhone).val()
        };
    }

    function cleanParam() {
        $(getParamId().acceptPhone).val('');
    }

    $(getParamId().acceptPhone).keyup(function (event) {
        if (event.keyCode == 13) {
            myTable.ajax.reload();
        }
    });

    $('#search').click(function(){
        myTable.ajax.reload();
    });

    $('#reset_search').click(function(){
        cleanParam();
        myTable.ajax.reload();
    });

    $('#refresh').click(function(){
        myTable.ajax.reload();
    });
});