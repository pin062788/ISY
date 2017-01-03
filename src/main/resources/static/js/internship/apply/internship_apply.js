/**
 * Created by lenovo on 2016/11/25.
 */
//# sourceURL=internship_apply.js
require(["jquery", "handlebars", "messenger", "jquery.address", "jquery.simple-pagination", "jquery.showLoading", "bootstrap", "jquery.fileupload-validate"],
    function ($, Handlebars) {

        /*
         ajax url.
         */
        var ajax_url = {
            internship_apply_data_url: '/web/internship/apply/data',
            my_internship_apply_data_url: '/web/internship/apply/my/data',
            recall_apply_url: '/web/internship/apply/recall',
            change_state_url: '/web/internship/apply/state',
            access_url: '/web/internship/apply/access',
            valid_is_student: '/anyone/valid/cur/is/student',
            valid_student: '/web/internship/apply/valid/student',
            access_condition_url: '/web/internship/apply/condition',
            file_upload_url: '/web/internship/apply/upload',
            download_file: '/anyone/users/download/file'
        };

        /*
         参数id
         */
        var paramId = {
            internshipTitle: '#search_internship_title',
            studentUsername: '#studentUsername',
            studentNumber: '#studentNumber'
        };

        /*
         我的实习参数id
         */
        var myParamId = {
            internshipTitle: '#my_search_internship_title',
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
         参数
         */
        var myParam = {
            searchParams: '',
            pageNum: 0,
            pageSize: 2,
            displayedPages: 3
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
         * 检验成功
         * @param validId
         * @param errorMsgId
         */
        function validSuccessDom(validId, errorMsgId) {
            $(validId).addClass('has-success').removeClass('has-error');
            $(errorMsgId).addClass('hidden').text('');
        }

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

        /*
         清除验证
         */
        function validCleanDom(inputId, errorId) {
            $(inputId).removeClass('has-error').removeClass('has-success');
            $(errorId).addClass('hidden').text('');
        }

        var tableData = '#tableData';
        var myTableData = '#myTableData';

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

        /*
         清空我的实习参数
         */
        function cleanMyParam() {
            $(myParamId.internshipTitle).val('');
        }

        /**
         * 刷新查询参数
         */
        function refreshSearch() {
            var params = {
                internshipTitle: $(paramId.internshipTitle).val()
            };
            param.pageNum = 0;
            param.searchParams = JSON.stringify(params);
        }

        /**
         * 刷新查询参数
         */
        function refreshMySearch() {
            var params = {
                internshipTitle: $(myParamId.internshipTitle).val()
            };
            myParam.pageNum = 0;
            myParam.searchParams = JSON.stringify(params);
        }

        /*
         搜索
         */
        $('#search').click(function () {
            refreshSearch();
            init();
        });

        /*
         我的申请搜索
         */
        $('#my_search').click(function () {
            refreshMySearch();
            initMyData();
        });

        /*
         重置
         */
        $('#reset_search').click(function () {
            cleanParam();
            refreshSearch();
            init();
        });

        /*
         我的申请重置
         */
        $('#my_reset_search').click(function () {
            cleanMyParam();
            refreshMySearch();
            initMyData();
        });

        $('#refresh').click(function () {
            init();
        });

        $('#my_refresh').click(function () {
            initMyData();
        });

        $(paramId.internshipTitle).keyup(function (event) {
            if (event.keyCode == 13) {
                refreshSearch();
                init();
            }
        });

        $(myParamId.internshipTitle).keyup(function (event) {
            if (event.keyCode == 13) {
                refreshMySearch();
                initMyData();
            }
        });

        /**
         * 列表数据
         * @param data 数据
         */
        function listData(data) {
            var source = $("#internship-release-template").html();
            var template = Handlebars.compile(source);

            Handlebars.registerHelper('internship_title', function () {
                var value = Handlebars.escapeExpression(this.internshipTitle);
                return new Handlebars.SafeString(value);
            });

            Handlebars.registerHelper('school_name', function () {
                var value = Handlebars.escapeExpression(this.schoolName);
                return new Handlebars.SafeString(value);
            });

            Handlebars.registerHelper('college_name', function () {
                var value = Handlebars.escapeExpression(this.collegeName);
                return new Handlebars.SafeString(value);
            });

            Handlebars.registerHelper('department_name', function () {
                var value = Handlebars.escapeExpression(this.departmentName);
                return new Handlebars.SafeString(value);
            });

            Handlebars.registerHelper('real_name', function () {
                var value = Handlebars.escapeExpression(this.realName);
                return new Handlebars.SafeString(value);
            });

            var html = template(data);
            $(tableData).html(html);
        }

        /**
         * 我的申请列表数据
         * @param data 数据
         */
        function myListData(data) {
            var source = $("#my-internship-release-template").html();
            var template = Handlebars.compile(source);

            Handlebars.registerHelper('internship_title', function () {
                var value = Handlebars.escapeExpression(this.internshipTitle);
                return new Handlebars.SafeString(value);
            });

            Handlebars.registerHelper('school_name', function () {
                var value = Handlebars.escapeExpression(this.schoolName);
                return new Handlebars.SafeString(value);
            });

            Handlebars.registerHelper('college_name', function () {
                var value = Handlebars.escapeExpression(this.collegeName);
                return new Handlebars.SafeString(value);
            });

            Handlebars.registerHelper('department_name', function () {
                var value = Handlebars.escapeExpression(this.departmentName);
                return new Handlebars.SafeString(value);
            });

            Handlebars.registerHelper('real_name', function () {
                var value = Handlebars.escapeExpression(this.realName);
                return new Handlebars.SafeString(value);
            });

            Handlebars.registerHelper('internship_apply_state', function () {
                var value = Handlebars.escapeExpression(internshipApplyStateCode(this.internshipApplyState));
                return new Handlebars.SafeString(value);
            });

            var html = template(data);
            $(myTableData).html(html);
        }

        /**
         * 状态码表
         * @param state 状态码
         * @returns {string}
         */
        function internshipApplyStateCode(state) {
            var msg = '';
            switch (state) {
                case 0:
                    msg = '未提交';
                    break;
                case 1:
                    msg = '审核中...';
                    break;
                case 2:
                    msg = '已通过';
                    break;
                case 3:
                    msg = '未通过';
                    break;
                case 4:
                    msg = '基本信息变更审核中...';
                    break;
                case 5:
                    msg = '基本信息变更填写中...';
                    break;
                case 6:
                    msg = '单位信息变更申请中...';
                    break;
                case 7:
                    msg = '单位信息变更填写中...';
                    break;
            }
            return msg;
        }

        /*
         进行申请
         */
        $(tableData).delegate('.apply', "click", function () {
            var id = $(this).attr('data-id');
            // 如果用户类型不是学生，则这里需要一个弹窗，填写学生账号或学生学号以获取学生id
            $.get(web_path + ajax_url.valid_is_student, function (data) {
                if (data.state) {
                    accessApply(id, data.objectResult);
                } else {
                    $('#studentInfoInternshipReleaseId').val(id);
                    $('#studentModal').modal('show');
                }
            });
        });

        /*
         进入申请
         */
        $(myTableData).delegate('.myApply', "click", function () {
            var id = $(this).attr('data-id');
            var studentId = $(this).attr('data-student');
            // 如果用户类型不是学生，则这里需要一个弹窗，填写学生账号或学生学号以获取学生id
            accessApply(id, studentId);
        });

        /*
         撤消申请
         */
        $(myTableData).delegate('.recallApply', "click", function () {
            var id = $(this).attr('data-id');
            var studentId = $(this).attr('data-student');
            recall(id, studentId);
        });

        /*
         基础信息变更申请
         */
        $(myTableData).delegate('.basisApply', "click", function () {
            var id = $(this).attr('data-id');
            var studentId = $(this).attr('data-student');
            showStateModal(4, id, studentId, '基础信息变更申请');
        });

        /*
         单位信息变更申请
         */
        $(myTableData).delegate('.firmApply', "click", function () {
            var id = $(this).attr('data-id');
            var studentId = $(this).attr('data-student');
            showStateModal(6, id, studentId, '单位信息变更申请');
        });

        /*
         上传电子资料
         */
        $(myTableData).delegate('.uploadFile', "click", function () {
            var id = $(this).attr('data-id');
            var studentId = $(this).attr('data-student');
            showUploadModal(id, studentId);
        });

        /*
         下载电子资料
         */
        $(myTableData).delegate('.downloadFile', "click", function () {
            var id = $(this).attr('data-id');
            window.location.href = web_path + ajax_url.download_file + '?fileId=' + id;
        });

        /**
         * 展开上传文件modal
         * @param id
         * @param studentId
         */
        function showUploadModal(id, studentId) {
            $('#uploadInternshipReleaseId').val(id);
            $('#uploadStudentId').val(studentId);
            $('#uploadModal').modal('show');
        }

        /**
         * 关闭文件上传modal
         */
        function closeUploadModal() {
            $('#uploadInternshipReleaseId').val('');
            $('#uploadStudentId').val('');
            $('#fileName').text('');
            $('#fileSize').text('');
            $('#uploadModal').modal('hide');
        }

        /**
         * 撤消询问
         * @param id
         * @param studentId
         */
        function recall(id, studentId) {
            var msg;
            msg = Messenger().post({
                message: "确定撤消申请吗?",
                actions: {
                    retry: {
                        label: '确定',
                        phrase: 'Retrying TIME',
                        action: function () {
                            msg.cancel();
                            sendRecallAjax(id, studentId);
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
        }

        /**
         * 撤消ajax
         * @param id
         * @param studentId
         */
        function sendRecallAjax(id, studentId) {
            $.post(web_path + ajax_url.recall_apply_url, {id: id, studentId: studentId}, function (data) {
                if (data.state) {
                    initMyData();
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
         * 展示变更模态框
         * @param state
         * @param internshipReleaseId
         * @param studentId
         * @param title
         */
        function showStateModal(state, internshipReleaseId, studentId, title) {
            $('#applyState').val(state);
            $('#applyInternshipReleaseId').val(internshipReleaseId);
            $('#applyStudentId').val(studentId);
            $('#stateModalLabel').text(title);
            $('#stateModal').modal('show');
        }

        /*
         检验原因字数
         */
        $('#reason').blur(function () {
            var reason = $('#reason').val();
            if (reason.length <= 0 || reason.length > 500) {
                validErrorDom('#valid_reason', '#reason_error_msg', '原因500个字符以内');
            } else {
                validSuccessDom('#valid_reason', '#reason_error_msg');
            }
        });

        /**
         * 隐藏变更模态框
         */
        function hideStateModal() {
            $('#applyState').val('');
            $('#applyInternshipReleaseId').val('');
            $('#applyStudentId').val('');
            $('#reason').val('');
            $('#stateModalLabel').text('');
            validCleanDom('#valid_reason', '#reason_error_msg');
            $('#stateModal').modal('hide');
        }

        /*
         提交变更申请
         */
        $('#stateOk').click(function () {
            stateAdd();
        });

        /*
         取消变更申请
         */
        $('#stateCancel').click(function () {
            hideStateModal();
        });

        /*
         状态申请提交询问
         */
        function stateAdd() {
            var msg;
            msg = Messenger().post({
                message: '确定申请吗?',
                actions: {
                    retry: {
                        label: '确定',
                        phrase: 'Retrying TIME',
                        action: function () {
                            msg.cancel();
                            validReason();
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
        }

        function validReason() {
            var reason = $('#reason').val();
            if (reason.length <= 0 || reason.length > 500) {
                validErrorDom('#valid_reason', '#reason_error_msg', '原因500个字符以内');
            } else {
                sendStateAjax();
            }
        }

        /**
         * 发送状态申请
         */
        function sendStateAjax() {
            $.post(web_path + ajax_url.change_state_url, $('#state_form').serialize(), function (data) {
                if (data.state) {
                    hideStateModal();
                    initMyData();
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
         * 进入申请页面
         * @param id
         * @param studentId
         */
        function accessApply(id, studentId) {
            // 进入判断
            $.post(web_path + ajax_url.access_condition_url, {id: id, studentId: studentId}, function (data) {
                if (data.state) {
                    $.address.value(ajax_url.access_url + "?id=" + id + "&studentId=" + studentId);
                } else {
                    Messenger().post({
                        message: data.msg,
                        type: 'error',
                        showCloseButton: true
                    });
                }
            });
        }

        // 用于可以去申请页
        var to_apply = false;
        var to_apply_data = '';

        $('#studentModal').on('hidden.bs.modal', function (e) {
            // do something...
            if (to_apply) {
                to_apply = false;
                accessApply($('#studentInfoInternshipReleaseId').val(), to_apply_data);
            }
        });

        /**
         * 检验学生信息
         */
        function validStudent() {
            var studentUsername = $(paramId.studentUsername).val();
            var studentNumber = $(paramId.studentNumber).val();
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
                    data: {student: student, type: type},
                    success: function (data) {
                        if (data.state) {
                            to_apply_data = data.objectResult;
                            to_apply = true;
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

        /*
         学生 form 确定
         */
        $('#studentInfo').click(function () {
            validStudent();
        });

        // tab
        $('#myTab').find('a').click(function (e) {
            e.preventDefault();
            var t = $(this).text();
            if (t === '申请列表') {
                init();
            } else if (t === '我的申请') {
                initMyData();
            }
        });

        init();

        /**
         * 初始化数据
         */
        function init() {
            startLoading();
            $.get(web_path + ajax_url.internship_apply_data_url, param, function (data) {
                endLoading();
                if (data.listResult.length > 0) {
                    createPage(data);
                }
                listData(data);
            });
        }

        /**
         * 初始化数据
         */
        function initMyData() {
            startLoading();
            $.get(web_path + ajax_url.my_internship_apply_data_url, myParam, function (data) {
                endLoading();
                if (data.listResult.length > 0) {
                    myCreatePage(data);
                }
                myListData(data);
            });
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
         * 我的申请创建分页
         * @param data 数据
         */
        function myCreatePage(data) {
            $('#myPagination').pagination({
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
                    myNextPage(pageNumber);
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
            $.get(web_path + ajax_url.internship_apply_data_url, param, function (data) {
                endLoading();
                listData(data);
            });
        }

        /**
         * 我的申请下一页
         * @param pageNumber 当前页
         */
        function myNextPage(pageNumber) {
            myParam.pageNum = pageNumber;
            startLoading();
            $.get(web_path + ajax_url.my_internship_apply_data_url, myParam, function (data) {
                endLoading();
                myListData(data);
            });
        }

        var startUpload = null; // 开始上传

        // 上传组件
        $('#fileupload').fileupload({
            url: web_path + ajax_url.file_upload_url,
            dataType: 'json',
            maxFileSize: 100000000,// 100MB
            formAcceptCharset: 'utf-8',
            autoUpload: false,// 关闭自动上传
            maxNumberOfFiles: 1,
            add: function (e, data) {
                $('#fileName').text(data.files[0].name);
                $('#fileSize').text(data.files[0].size);
                startUpload = data;
            },
            submit: function (e, data) {
                if (validUpload()) {
                    var internshipReleaseId = $('#uploadInternshipReleaseId').val();
                    var studentId = $('#uploadStudentId').val();
                    data.formData = {
                        'internshipReleaseId': internshipReleaseId,
                        'studentId': studentId
                    };
                }
            },
            done: function (e, data) {
                initMyData();// 刷新我的申请
                closeUploadModal();// 清空信息
            }
        });

        function validUpload() {
            var internshipReleaseId = $('#uploadInternshipReleaseId').val();
            var studentId = $('#uploadStudentId').val();
            var fileName = $('#fileName').text();
            if (internshipReleaseId !== '' && Number(studentId) > 0) {
                if (fileName !== '') {
                    return true;
                } else {
                    Messenger().post({
                        message: '请选择文件',
                        type: 'error',
                        showCloseButton: true
                    });
                    return false;
                }
            } else {
                Messenger().post({
                    message: '缺失重要参数不能上传',
                    type: 'error',
                    showCloseButton: true
                });
                return false;
            }
        }

        // 确认上传
        $('#confirmUpload').click(function () {
            if (validUpload()) {
                startUpload.submit();
            }
        });

        // 取消上传
        $('#cancelUpload').click(function () {
            closeUploadModal();// 清空信息
        });

    });