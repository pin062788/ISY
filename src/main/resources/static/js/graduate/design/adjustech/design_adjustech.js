/**
 * Created by lenovo on 2017-05-21.
 */
//# sourceURL=graduate_design_adjustech.js
require(["jquery", "handlebars", "messenger", "jquery.address", "jquery.simple-pagination", "jquery.showLoading"],
    function ($, Handlebars) {

        /*
         ajax url.
         */
        var ajax_url = {
            release_data_url: '/web/graduate/design/adjustech/data',
            sync_data: '/web/graduate/design/adjustech/sync/data',
            adjust_url: '/web/graduate/design/adjustech/adjust',
            apply_state_url: '/web/graduate/design/adjustech/apply',
            yes_fill: '/web/graduate/design/adjustech/student/submit',
            not_fill: '/web/graduate/design/adjustech/student/unsubmit',
            is_ok: '/web/graduate/design/adjustech/ok'
        };

        /*
         参数
         */
        var param = {
            searchParams: '',
            pageNum: 0,
            pageSize: 2,
            displayedPages: 3
        };

        /*
        web storage key.
        */
        var webStorageKey = {
            GRADUATION_DESIGN_TITLE: 'GRADUATE_DESIGN_ADJUSTECH_TITLE_SEARCH'
        };

        /*
         参数id
         */
        var paramId = {
            graduationDesignTitle: '#search_title'
        };

        var tableData = '#tableData';

        function startLoading() {
            // 显示遮罩
            $('#page-wrapper').showLoading();
        }

        function endLoading() {
            // 去除遮罩
            $('#page-wrapper').hideLoading();
        }

        /*
         清空参数
         */
        function cleanParam() {
            $(paramId.graduationDesignTitle).val('');
        }

        /**
         * 刷新查询参数
         */
        function refreshSearch() {
            if (typeof(Storage) !== "undefined") {
                sessionStorage.setItem(webStorageKey.GRADUATION_DESIGN_TITLE, $(paramId.graduationDesignTitle).val());
            }
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

        $('#refresh').click(function () {
            init();
        });

        $(paramId.graduationDesignTitle).keyup(function (event) {
            if (event.keyCode === 13) {
                refreshSearch();
                init();
            }
        });

        /**
         * 列表数据
         * @param data 数据
         */
        function listData(data) {
            var template = Handlebars.compile($("#graduation-design-template").html());

            Handlebars.registerHelper('graduation_design_title', function () {
                return new Handlebars.SafeString(Handlebars.escapeExpression(this.graduationDesignTitle));
            });

            Handlebars.registerHelper('school_name', function () {
                return new Handlebars.SafeString(Handlebars.escapeExpression(this.schoolName));
            });

            Handlebars.registerHelper('college_name', function () {
                return new Handlebars.SafeString(Handlebars.escapeExpression(this.collegeName));
            });

            Handlebars.registerHelper('department_name', function () {
                return new Handlebars.SafeString(Handlebars.escapeExpression(this.departmentName));
            });

            Handlebars.registerHelper('science_name', function () {
                return new Handlebars.SafeString(Handlebars.escapeExpression(this.scienceName));
            });

            Handlebars.registerHelper('real_name', function () {
                return new Handlebars.SafeString(Handlebars.escapeExpression(this.realName));
            });

            $(tableData).html(template(data));
        }

        /*
         同步数据
         */
        $(tableData).delegate('.design_sync_adjust', "click", function () {
            startLoading();
            $.post(ajax_url.sync_data, {id: $(this).attr('data-id')}, function (data) {
                endLoading();
                if (data.state) {
                    Messenger().post({
                        message: data.msg,
                        type: 'success',
                        showCloseButton: true
                    });
                    init();
                } else {
                    Messenger().post({
                        message: data.msg,
                        type: 'error',
                        showCloseButton: true
                    });
                }
            });
        });

        /*
        填报情况
        */
        $(tableData).delegate('.apply_state', "click", function () {
            $.address.value(ajax_url.apply_state_url + '?id=' + $(this).attr('data-id'));
        });

        /*
         调整
         */
        $(tableData).delegate('.design_adjust', "click", function () {
            $.address.value(ajax_url.adjust_url + '?id=' + $(this).attr('data-id'));
        });

        /*
         已填报学生
         */
        $(tableData).delegate('.design_fill', "click", function () {
            $.address.value(ajax_url.yes_fill + '?id=' + $(this).attr('data-id'));
        });

        /*
         未填报学生
         */
        $(tableData).delegate('.design_not_fill', "click", function () {
            $.address.value(ajax_url.not_fill + '?id=' + $(this).attr('data-id'));
        });

        /*
         确认
         */
        $(tableData).delegate('.design_teacher_ok_adjust', "click", function () {
            var id = $(this).attr('data-id');
            var msg;
            msg = Messenger().post({
                message: "确认调整后，相关操作将无法进行",
                actions: {
                    retry: {
                        label: '确定',
                        phrase: 'Retrying TIME',
                        action: function () {
                            msg.cancel();
                            sendOkAjax(id);
                        }
                    },
                    cancel: {
                        label: '取消',
                        action: function () {
                            return msg.cancel();
                        }
                    }
                }
            });

        });

        /**
         * 发送确认请求
         * @param id 发布id
         */
        function sendOkAjax(id) {
            $.post(web_path + ajax_url.is_ok, {id: id}, function (data) {
                if (data.state) {
                    Messenger().post({
                        message: data.msg,
                        type: 'success',
                        showCloseButton: true
                    });
                    init();
                } else {
                    Messenger().post({
                        message: data.msg,
                        type: 'error',
                        showCloseButton: true
                    });
                }
            });
        }

        init();
        initSearchInput();

        /**
         * 初始化数据
         */
        function init() {
            initSearchContent();
            startLoading();
            $.get(web_path + ajax_url.release_data_url, param, function (data) {
                endLoading();
                createPage(data);
                listData(data);
            });
        }

        /*
       初始化搜索内容
       */
        function initSearchContent() {
            var graduationDesignTitle = null;
            var params = {
                graduationDesignTitle: ''
            };
            if (typeof(Storage) !== "undefined") {
                graduationDesignTitle = sessionStorage.getItem(webStorageKey.GRADUATION_DESIGN_TITLE);
            }
            if (graduationDesignTitle !== null) {
                params.graduationDesignTitle = graduationDesignTitle;
            } else {
                params.graduationDesignTitle = $(paramId.graduationDesignTitle).val();
            }
            param.pageNum = 0;
            param.searchParams = JSON.stringify(params);
        }

        /*
        初始化搜索框
        */
        function initSearchInput() {
            var graduationDesignTitle = null;
            if (typeof(Storage) !== "undefined") {
                graduationDesignTitle = sessionStorage.getItem(webStorageKey.GRADUATION_DESIGN_TITLE);
            }
            if (graduationDesignTitle !== null) {
                $(paramId.graduationDesignTitle).val(graduationDesignTitle);
            }
        }

        /**
         * 创建分页
         * @param data 数据
         */
        function createPage(data) {
            $('#pagination').pagination({
                pages: data.paginationUtils.totalPages,
                displayedPages: data.paginationUtils.displayedPages,
                hrefTextPrefix: '',
                prevText: '上一页',
                nextText: '下一页',
                cssStyle: '',
                listStyle: 'pagination',
                onPageClick: function (pageNumber, event) {
                    // Callback triggered when a page is clicked
                    // Page number is given as an optional parameter
                    console.log(pageNumber);
                    nextPage(pageNumber);
                }
            });
        }

        /**
         * 下一页
         * @param pageNumber 当前页
         */
        function nextPage(pageNumber) {
            param.pageNum = pageNumber;
            startLoading();
            $.get(web_path + ajax_url.release_data_url, param, function (data) {
                endLoading();
                listData(data);
            });
        }

    });