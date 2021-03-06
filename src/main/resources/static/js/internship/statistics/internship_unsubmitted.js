/**
 * Created by lenovo on 2016-12-11.
 */
require(["jquery", "handlebars", "nav_active", "datatables.responsive", "jquery.address", "messenger"],
    function ($, Handlebars, nav_active) {

        /*
        参数
        */
        var param = {
            studentName: '',
            studentNumber: '',
            scienceName: '',
            organizeName: ''
        };

        /*
        web storage key.
        */
        var webStorageKey = {
            STUDENT_NAME: 'INTERNSHIP_STATISTICS_UNSUBMITTED_STUDENT_NAME_SEARCH_' + init_page_param.internshipReleaseId,
            STUDENT_NUMBER: 'INTERNSHIP_STATISTICS_UNSUBMITTED_STUDENT_NUMBER_SEARCH_' + init_page_param.internshipReleaseId,
            SCIENCE_NAME: 'INTERNSHIP_STATISTICS_UNSUBMITTED_SCIENCE_NAME_SEARCH_' + init_page_param.internshipReleaseId,
            ORGANIZE_NAME: 'INTERNSHIP_STATISTICS_UNSUBMITTED_ORGANIZE_NAME_SEARCH_' + init_page_param.internshipReleaseId
        };

        // 刷新时选中菜单
        nav_active(getAjaxUrl().back);

        /*
         ajax url
         */
        function getAjaxUrl() {
            return {
                unsubmitted_data_url: '/web/internship/statistical/unsubmitted/data',
                science_data_url: '/anyone/internship/sciences',
                organize_data_url: '/anyone/internship/organizes',
                back: '/web/menu/internship/statistical'
            };
        }

        var initSelectedOrganize = true;// 用于页面初始化搜索框内容时第一次选中班级

        initSearchSciences();

        /**
         * 初始化专业数据
         */
        function initSearchSciences() {
            $.post(web_path + getAjaxUrl().science_data_url, {internshipReleaseId: init_page_param.internshipReleaseId}, function (data) {
                var template = Handlebars.compile($("#science-template").html());

                Handlebars.registerHelper('science_value', function () {
                    return new Handlebars.SafeString(Handlebars.escapeExpression(this.scienceId));
                });

                Handlebars.registerHelper('science_name', function () {
                    return new Handlebars.SafeString(Handlebars.escapeExpression(this.scienceName));
                });
                $(getParamId().scienceName).html(template(data));

                var scienceName = null;
                if (typeof(Storage) !== "undefined") {
                    scienceName = sessionStorage.getItem(webStorageKey.SCIENCE_NAME);
                }
                if (scienceName !== null) {
                    $(getParamId().scienceName).val(scienceName);
                    changeOrganize(scienceName);
                }
            });
        }

        /**
         * 改变班级选项
         * @param science 专业
         */
        function changeOrganize(science) {

            if (Number(science) == 0) {
                var template = Handlebars.compile($("#organize-template").html());

                var context = {
                    listResult: [
                        {name: "请选择班级", value: 0}
                    ]
                };

                Handlebars.registerHelper('organize_value', function () {
                    return new Handlebars.SafeString(Handlebars.escapeExpression(this.value));
                });

                Handlebars.registerHelper('organize_name', function () {
                    return new Handlebars.SafeString(Handlebars.escapeExpression(this.name));
                });
                $(getParamId().organizeName).html(template(context));
            } else {
                // 根据年级查询全部班级
                $.post(web_path + getAjaxUrl().organize_data_url, {scienceId: science}, function (data) {
                    var template = Handlebars.compile($("#organize-template").html());

                    Handlebars.registerHelper('organize_value', function () {
                        return new Handlebars.SafeString(Handlebars.escapeExpression(this.organizeId));
                    });

                    Handlebars.registerHelper('organize_name', function () {
                        return new Handlebars.SafeString(Handlebars.escapeExpression(this.organizeName));
                    });
                    $(getParamId().organizeName).html(template(data));

                    if (initSelectedOrganize) {
                        var organizeName = null;
                        if (typeof(Storage) !== "undefined") {
                            organizeName = sessionStorage.getItem(webStorageKey.ORGANIZE_NAME);
                        }
                        if (organizeName !== null) {
                            $(getParamId().organizeName).val(organizeName);
                        }
                        initSelectedOrganize = false;
                    }
                });
            }
        }

        // 预编译模板
        var template = Handlebars.compile($("#operator_button").html());

        // datatables 初始化
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
            "aaSorting": [[1, 'asc']],// 排序
            "ajax": {
                "url": web_path + getAjaxUrl().unsubmitted_data_url,
                "dataSrc": "data",
                "data": function (d) {
                    // 添加额外的参数传给服务器
                    initSearchContent();
                    var searchParam = getParam();
                    d.extra_search = JSON.stringify(searchParam);
                    d.internshipReleaseId = init_page_param.internshipReleaseId;
                }
            },
            "columns": [
                {"data": "realName"},
                {"data": "studentNumber"},
                {"data": "scienceName"},
                {"data": "organizeName"}
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
            "dom": "<'row'<'col-sm-2'l><'#global_button.col-sm-5'>r>" +
            "t" +
            "<'row'<'col-sm-5'i><'col-sm-7'p>>",
            initComplete: function () {
                // 初始化搜索框中内容
                initSearchInput();
            }
        });

        var global_button = '<button type="button" id="refresh" class="btn btn-outline btn-default btn-sm"><i class="fa fa-refresh"></i>刷新</button>';
        $('#global_button').append(global_button);

        /*
         参数id
         */
        function getParamId() {
            return {
                studentName: '#search_student_name',
                studentNumber: '#search_student_number',
                scienceName: '#search_science_name',
                organizeName: '#search_organize_name'
            };
        }

        /*
         得到参数
         */
        function getParam() {
            return param;
        }

        /*
         初始化参数
         */
        function initParam() {
            param.studentName = $(getParamId().studentName).val();
            param.studentNumber = $(getParamId().studentNumber).val();
            param.scienceName = $(getParamId().scienceName).val();
            param.organizeName = $(getParamId().organizeName).val();
            if (typeof(Storage) !== "undefined") {
                sessionStorage.setItem(webStorageKey.STUDENT_NAME, param.studentName);
                sessionStorage.setItem(webStorageKey.STUDENT_NUMBER, param.studentNumber);
                sessionStorage.setItem(webStorageKey.SCIENCE_NAME, param.scienceName);
                sessionStorage.setItem(webStorageKey.ORGANIZE_NAME, param.organizeName);
            }
        }

        /*
        初始化搜索内容
        */
        function initSearchContent() {
            var studentName = null;
            var studentNumber = null;
            var scienceName = null;
            var organizeName = null;
            if (typeof(Storage) !== "undefined") {
                studentName = sessionStorage.getItem(webStorageKey.STUDENT_NAME);
                studentNumber = sessionStorage.getItem(webStorageKey.STUDENT_NUMBER);
                scienceName = sessionStorage.getItem(webStorageKey.SCIENCE_NAME);
                organizeName = sessionStorage.getItem(webStorageKey.ORGANIZE_NAME);
            }
            if (studentName !== null) {
                param.studentName = studentName;
            }

            if (studentNumber !== null) {
                param.studentNumber = studentNumber;
            }

            if (scienceName !== null) {
                param.scienceName = scienceName;
            }

            if (organizeName !== null) {
                param.organizeName = organizeName;
            }
        }

        /*
        初始化搜索框
        */
        function initSearchInput() {
            var studentName = null;
            var studentNumber = null;
            if (typeof(Storage) !== "undefined") {
                studentName = sessionStorage.getItem(webStorageKey.STUDENT_NAME);
                studentNumber = sessionStorage.getItem(webStorageKey.STUDENT_NUMBER);
            }
            if (studentName !== null) {
                $(getParamId().studentName).val(studentName);
            }

            if (studentNumber !== null) {
                $(getParamId().studentNumber).val(studentNumber);
            }
        }

        /*
         清空参数
         */
        function cleanParam() {
            $(getParamId().studentName).val('');
            $(getParamId().studentNumber).val('');
            $(getParamId().scienceName).val(0);
            $(getParamId().organizeName).val(0);
        }

        $(getParamId().studentName).keyup(function (event) {
            if (event.keyCode == 13) {
                initParam();
                myTable.ajax.reload();
            }
        });

        $(getParamId().studentNumber).keyup(function (event) {
            if (event.keyCode == 13) {
                initParam();
                myTable.ajax.reload();
            }
        });

        $(getParamId().scienceName).change(function () {
            var science = $(getParamId().scienceName).val();
            changeOrganize(science);
            initParam();
            myTable.ajax.reload();
        });

        $(getParamId().organizeName).change(function () {
            initParam();
            myTable.ajax.reload();
        });

        $('#search').click(function () {
            initParam();
            myTable.ajax.reload();
        });

        $('#reset_search').click(function () {
            cleanParam();
            initParam();
            myTable.ajax.reload();
        });

        $('#refresh').click(function () {
            myTable.ajax.reload();
        });

        /*
         返回
         */
        $('#page_back').click(function () {
            $.address.value(getAjaxUrl().back);
        });
    });