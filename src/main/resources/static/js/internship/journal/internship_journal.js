/**
 * Created by lenovo on 2016/12/14.
 */
//# sourceURL=internship_journal.js
require(["jquery", "handlebars", "messenger", "jquery.address", "jquery.simple-pagination", "jquery.showLoading"],
    function ($, Handlebars) {

        /*
         ajax url.
         */
        var ajax_url = {
            internship_journal_data_url: '/anyone/internship/data',
            journal_url: '/web/internship/journal/list',
            team_journal: '/web/internship/journal/team',
            my_journal: '/web/internship/journal/my/list',
            my_condition: '/web/internship/journal/my/list/condition',
            add: '/web/internship/journal/list/add',
            valid_is_student: '/anyone/valid/cur/is/student',
            valid_student: '/web/internship/journal/valid/student'
        };

        /*
         参数id
         */
        var paramId = {
            internshipTitle: '#search_internship_title'
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
            INTERNSHIP_TITLE: 'INTERNSHIP_JOURNAL_INTERNSHIP_TITLE_SEARCH'
        };

        /*
         检验id
         */
        var validId = {
            student: '#valid_student'
        };

        /*
         错误消息id
         */
        var errorMsgId = {
            student: '#student_error_msg'
        };

        /**
         * 检验失败
         * @param validId
         * @param errorMsgId
         * @param msg
         */
        function validErrorDom(validId, errorMsgId, msg) {
            $(validId).addClass('has-error').removeClass('has-success');
            $(errorMsgId).removeClass('hidden').text(msg);
        }

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
            $(paramId.internshipTitle).val('');
        }

        /**
         * 刷新查询参数
         */
        function refreshSearch() {
            if (typeof(Storage) !== "undefined") {
                sessionStorage.setItem(webStorageKey.INTERNSHIP_TITLE, $(paramId.internshipTitle).val());
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

        $(paramId.internshipTitle).keyup(function (event) {
            if (event.keyCode == 13) {
                refreshSearch();
                init();
            }
        });

        /**
         * 列表数据
         * @param data 数据
         */
        function listData(data) {
            var template = Handlebars.compile($("#internship-release-template").html());

            Handlebars.registerHelper('internship_title', function () {
                return new Handlebars.SafeString(Handlebars.escapeExpression(this.internshipTitle));
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

            Handlebars.registerHelper('real_name', function () {
                return new Handlebars.SafeString(Handlebars.escapeExpression(this.realName));
            });

            $(tableData).html(template(data));
        }

        /*
         日志列表
         */
        $(tableData).delegate('.journal_list', "click", function () {
            var id = $(this).attr('data-id');
            $.address.value(ajax_url.journal_url + "?id=" + id);
        });

        /*
         小组日志
         */
        $(tableData).delegate('.team_journal', "click", function () {
            var id = $(this).attr('data-id');
            $.address.value(ajax_url.team_journal + "?id=" + id);
        });

        /*
         我的日志
         */
        $(tableData).delegate('.my_journal', "click", function () {
            var id = $(this).attr('data-id');
            $.post(web_path + ajax_url.my_condition, {id: id}, function (data) {
                if (data.state) {
                    $.address.value(ajax_url.my_journal + "?id=" + id);
                } else {
                    Messenger().post({
                        message: data.msg,
                        type: 'error',
                        showCloseButton: true
                    });
                }
            })
        });

        /*
         写日志
         */
        $(tableData).delegate('.write_journal', "click", function () {
            var id = $(this).attr('data-id');
            // 如果用户类型不是学生，则这里需要一个弹窗，填写学生账号或学生学号以获取学生id
            $.get(web_path + ajax_url.valid_is_student, function (data) {
                if (data.state) {
                    accessAdd(id, data.objectResult);
                } else {
                    $('#studentInfoInternshipReleaseId').val(id);
                    $('#studentModal').modal('show');
                }
            });
        });

        /*
         学生 form 确定
         */
        $('#studentInfo').click(function () {
            validStudent($('#studentInfoInternshipReleaseId').val());
        });

        // 用于可以去添加页
        var to_add = false;
        var to_add_data = '';

        $('#studentModal').on('hidden.bs.modal', function (e) {
            // do something...
            if (to_add) {
                to_add = false;
                accessAdd($('#studentInfoInternshipReleaseId').val(), to_add_data);
            }
        });

        /**
         * 检验学生信息
         */
        function validStudent(id) {
            var studentUsername = $('#studentUsername').val();
            var studentNumber = $('#studentNumber').val();
            if (studentUsername.length <= 0 && studentNumber.length <= 0) {
                validErrorDom(validId.student, errorMsgId.student, '请至少填写一项学生信息');
            } else {
                var student = "";
                var type = -1;
                if (studentUsername.length > 0) {
                    student = studentUsername;
                    type = 0;
                }

                if (studentNumber.length > 0) {
                    student = studentNumber;
                    type = 1;
                }

                // 检验学生信息
                Messenger().run({
                    errorMessage: '请求失败'
                }, {
                    url: web_path + ajax_url.valid_student,
                    type: 'post',
                    data: {student: student, internshipReleaseId: id, type: type},
                    success: function (data) {
                        if (data.state) {
                            to_add_data = data.objectResult;
                            to_add = true;
                            $('#studentModal').modal('hide');
                        } else {
                            validErrorDom(validId.student, errorMsgId.student, data.msg);
                        }
                    },
                    error: function (xhr) {
                        if ((xhr != null ? xhr.status : void 0) === 404) {
                            return "请求失败";
                        }
                        return true;
                    }
                });
            }
        }

        /**
         * 进入添加
         * @param internshipReleaseId
         * @param studentId
         */
        function accessAdd(internshipReleaseId, studentId) {
            $.address.value(ajax_url.add + '?id=' + internshipReleaseId + '&studentId=' + studentId);
        }

        init();
        initSearchInput();

        /**
         * 初始化数据
         */
        function init() {
            initSearchContent();
            startLoading();
            $.get(web_path + ajax_url.internship_journal_data_url, param, function (data) {
                endLoading();
                createPage(data);
                listData(data);
            });
        }

        /*
        初始化搜索内容
       */
        function initSearchContent() {
            var internshipTitle = null;
            var params = {
                internshipTitle: ''
            };
            if (typeof(Storage) !== "undefined") {
                internshipTitle = sessionStorage.getItem(webStorageKey.INTERNSHIP_TITLE);
            }
            if (internshipTitle !== null) {
                params.internshipTitle = internshipTitle;
            } else {
                params.internshipTitle = $(paramId.internshipTitle).val();
            }
            param.pageNum = 0;
            param.searchParams = JSON.stringify(params);
        }

        /*
        初始化搜索框
        */
        function initSearchInput() {
            var internshipTitle = null;
            if (typeof(Storage) !== "undefined") {
                internshipTitle = sessionStorage.getItem(webStorageKey.INTERNSHIP_TITLE);
            }
            if (internshipTitle !== null) {
                $(paramId.internshipTitle).val(internshipTitle);
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
            $.get(web_path + ajax_url.internship_journal_data_url, param, function (data) {
                endLoading();
                listData(data);
            });
        }

    });