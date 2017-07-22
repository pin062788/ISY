/**
 * Created by zbeboy on 2017/7/20.
 */
require(["jquery", "nav_active", "handlebars", "messenger", "jquery.address",
    "jquery.showLoading", "tablesaw", "bootstrap"], function ($, nav_active, Handlebars) {
    /*
     ajax url.
     */
    var ajax_url = {
        data_url: '/anyone/graduate/design/defense/order/data',
        timer_url: '/web/graduate/design/reorder/timer',
        back: '/web/menu/graduate/design/reorder'
    };

    /*
     参数id
     */
    var paramId = {
        studentName: '#search_student_name',
        studentNumber: '#search_student_number'
    };

    /*
     参数
     */
    var param = {
        graduationDesignReleaseId: init_page_param.graduationDesignReleaseId,
        defenseGroupId: init_page_param.defenseGroupId,
        studentName: $(paramId.studentName).val(),
        studentNumber: $(paramId.studentNumber).val()
    };

    // 刷新时选中菜单
    nav_active(ajax_url.back);

    function startLoading() {
        // 显示遮罩
        $('#page-wrapper').showLoading();
    }

    function endLoading() {
        // 去除遮罩
        $('#page-wrapper').hideLoading();
    }

    $('#refresh').click(function () {
        init();
    });

    /*
     清空参数
     */
    function cleanParam() {
        $(paramId.studentName).val('');
        $(paramId.studentNumber).val('');
    }

    /**
     * 刷新查询参数
     */
    function refreshSearch() {
        param.studentName = $(paramId.studentName).val();
        param.studentNumber = $(paramId.studentNumber).val();
    }

    /*
     搜索
     */
    $('#search').click(function () {
        refreshSearch();
        init();
    });

    /*
     重置
     */
    $('#reset_search').click(function () {
        cleanParam();
        refreshSearch();
        init();
    });

    $(paramId.studentName).keyup(function (event) {
        if (event.keyCode === 13) {
            refreshSearch();
            init();
        }
    });

    $(paramId.studentNumber).keyup(function (event) {
        if (event.keyCode === 13) {
            refreshSearch();
            init();
        }
    });

    var tableData = '#tableData';

    /*
     返回
     */
    $('#page_back').click(function () {
        $.address.value(ajax_url.back);
    });

    init();

    function init() {
        startLoading();
        $.get(web_path + ajax_url.data_url, param, function (data) {
            endLoading();
            if (data.state) {
                listData(data);
            } else {
                Messenger().post({
                    message: data.msg,
                    type: 'error',
                    showCloseButton: true
                });
            }
        });
    }

    /**
     * 列表数据
     * @param data 数据
     */
    function listData(data) {
        var template = Handlebars.compile($("#order-template").html());
        $(tableData).html(template(data));
        $('#tablesawTable').tablesaw().data("tablesaw").refresh();
    }

    /*
     设置
     */
    $(tableData).delegate('.timer', "click", function () {
        var id = $(this).attr('data-id');
        $('#timerDefenseOrderId').val(id);
        $('#timerModal').modal('show');
    });

    // 计时
    $('#toTimer').click(function () {
        var id = $('#timerDefenseOrderId').val();
        var timer = Math.round(Number($('#timerInput').val()));
        $('#timerModal').modal('hide');
        window.open(web_path + ajax_url.timer_url + '?defenseOrderId=' + id + '&timer=' + timer);
    });

});