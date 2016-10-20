/**
 * Created by lenovo on 2016-09-12.
 */
requirejs.config({
    // pathsオプションの設定。"module/name": "path"を指定します。拡張子（.js）は指定しません。
    paths: {
        "check.all": web_path + "/plugin/checkall/checkall",
        "datatables.responsive": web_path + "/plugin/datatables/js/datatables.responsive",
        "datatables.net": web_path + "/plugin/datatables/js/jquery.dataTables.min",
        "datatables.bootstrap": web_path + "/plugin/datatables/js/dataTables.bootstrap.min",
        "csrf": web_path + "/js/util/csrf",
        "com": web_path + "/js/util/com",
        "nav": web_path + "/js/util/nav"
    },
    // shimオプションの設定。モジュール間の依存関係を定義します。
    shim: {
        "check.all": {
            // jQueryに依存するのでpathsで設定した"module/name"を指定します。
            deps: ["jquery"]
        },
        "datatables.responsive": {
            // jQueryに依存するのでpathsで設定した"module/name"を指定します。
            deps: ["datatables.bootstrap"]
        },
        "messenger": {
            deps: ["jquery"]
        }
    }
});
// require(["module/name", ...], function(params){ ... });
require(["jquery", "requirejs-domready", "messenger", "handlebars", "datatables.responsive", "csrf", "com", "check.all", "nav"], function ($, domready, messenger, Handlebars, loading, dt, csrf, com, checkall, nav) {
    domready(function () {
        //This function is called once the DOM is ready.
        //It will be safe to query the DOM and manipulate
        //DOM nodes in this function.

        /*
         初始化消息机制
         */
        Messenger.options = {
            extraClasses: 'messenger-fixed messenger-on-bottom messenger-on-right',
            theme: 'flat'
        };

        /*
         ajax url
         */
        function getAjaxUrl() {
            return {
                applications: '/web/system/application/data',
                updateDel: '/web/system/application/update/del',
                add:'/web/system/application/add',
                edit:'/web/system/application/edit'
            };
        }

        var operator_button = $("#operator_button").html();
        // 预编译模板
        var template = Handlebars.compile(operator_button);

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
                $('#checkall').prop('checked', false);
                // 调用全选插件
                $.fn.check({checkall_name: "checkall", checkbox_name: "check"});
            },
            searching: false,
            "processing": true, // 打开数据加载时的等待效果
            "serverSide": true,// 打开后台分页
            "ajax": {
                "url": web_path + getAjaxUrl().applications,
                "dataSrc": "data",
                "data": function (d) {
                    // 添加额外的参数传给服务器
                    var searchParam = initParam();
                    d.extra_search = JSON.stringify(searchParam);
                }
            },
            "columns": [
                {"data": null},
                {"data": "applicationName"},
                {"data": "applicationEnName"},
                {"data": "applicationPidName"},
                {"data": "applicationUrl"},
                {"data": "applicationTypeName"},
                {"data": "icon"},
                {"data": "applicationSort"},
                {"data": "applicationCode"},
                {"data": "applicationDataUrlStartWith"},
                {"data": null}
            ],
            columnDefs: [
                {
                    targets: 0,
                    orderable: false,
                    render: function (a, b, c, d) {
                        return '<input type="checkbox" value="' + c.applicationId + '" name="check"/>';
                    }
                },
                {
                    targets: 10,
                    orderable: false,
                    render: function (a, b, c, d) {

                        var context =
                            {
                                func: [
                                    {
                                        "name": "编辑",
                                        "css": "edit",
                                        "type": "primary",
                                        "id": c.applicationId,
                                        "application": c.applicationName
                                    },
                                    {
                                        "name": "删除",
                                        "css": "del",
                                        "type": "danger",
                                        "id": c.applicationId,
                                        "application": c.applicationName
                                    }
                                ]
                            };
                        var html = template(context);
                        return html;
                    }
                }

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
            "dom": "<'row'<'col-sm-2'l><'#global_button.col-sm-2'><'col-sm-8'<'#mytoolbox'>>r>" +
            "t" +
            "<'row'<'col-sm-5'i><'col-sm-7'p>>",
            initComplete: function () {
                $(document).on("click", ".edit", function () {
                    edit($(this).attr('data-id'));
                });

                $(document).on("click", ".del", function () {
                    application_del($(this).attr('data-id'), $(this).attr('data-application'));
                });
            }
        });

        var html = '<input type="text" id="search_application" class="form-control input-sm" placeholder="应用" />' +
            ' <input type="text" id="search_en_application" class="form-control input-sm" placeholder="英文" />' +
            ' <input type="text" id="search_application_code" class="form-control input-sm" placeholder="识别码" />' +
            ' <button type="button" id="search" class="btn btn-outline btn-default btn-sm"><i class="fa fa-search"></i>搜索</button>' +
            ' <button type="button" id="reset_search" class="btn btn-outline btn-default btn-sm"><i class="fa fa-repeat"></i>重置</button>';
        $('#mytoolbox').append(html);

        var global_button = '<button type="button" id="application_add" class="btn btn-outline btn-primary btn-sm">添加</button>' +
            '  <button type="button" id="application_dels" class="btn btn-outline btn-danger btn-sm">批量删除</button>';
        $('#global_button').append(global_button);

        /*
         参数id
         */
        function getParamId() {
            return {
                applicationName: '#search_application',
                applicationEnName:'#search_en_application',
                applicationCode:'#search_application_code'
            };
        }

        /*
         初始化参数
         */
        function initParam() {
            return {
                applicationName: $(getParamId().applicationName).val(),
                applicationEnName:$(getParamId().applicationEnName).val(),
                applicationCode:$(getParamId().applicationCode).val()
            };
        }

        /*
         清空参数
         */
        function cleanParam() {
            $(getParamId().applicationName).val('');
            $(getParamId().applicationEnName).val('');
            $(getParamId().applicationCode).val('');
        }

        $(getParamId().applicationName).keyup(function (event) {
            if (event.keyCode == 13) {
                myTable.ajax.reload();
            }
        });

        $(getParamId().applicationEnName).keyup(function (event) {
            if (event.keyCode == 13) {
                myTable.ajax.reload();
            }
        });

        $(getParamId().applicationCode).keyup(function (event) {
            if (event.keyCode == 13) {
                myTable.ajax.reload();
            }
        });

        $('#search').click(function () {
            myTable.ajax.reload();
        });

        $('#reset_search').click(function () {
            cleanParam();
            myTable.ajax.reload();
        });

        /*
         添加页面
         */
        $('#application_add').click(function () {
            window.location.href = web_path + getAjaxUrl().add;
        });

        /*
         批量注销
         */
        $('#application_dels').click(function () {
            var applicationIds = [];
            var ids = $('input[name="check"]:checked');
            for (var i = 0; i < ids.length; i++) {
                applicationIds.push($(ids[i]).val());
            }

            if (applicationIds.length > 0) {
                var msg;
                msg = Messenger().post({
                    message: "确定删除选中的应用吗?",
                    actions: {
                        retry: {
                            label: '确定',
                            phrase: 'Retrying TIME',
                            action: function () {
                                msg.cancel();
                                dels(applicationIds);
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
            } else {
                Messenger().post("未发现有选中的应用!");
            }
        });

        /*
         编辑页面
         */
        function edit(applicationId) {
            window.location.href = web_path + getAjaxUrl().edit + '?id=' + applicationId;
        }

        /*
         删除
         */
        function application_del(applicationId, applicationName) {
            var msg;
            msg = Messenger().post({
                message: "确定删除应用 '" + applicationName + "' 吗?",
                actions: {
                    retry: {
                        label: '确定',
                        phrase: 'Retrying TIME',
                        action: function () {
                            msg.cancel();
                            del(applicationId);
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

        function del(applicationId) {
            sendUpdateDelAjax(applicationId, '删除', 1);
        }

        function dels(applicationIds) {
            sendUpdateDelAjax(applicationIds.join(","), '批量删除', 1);
        }

        /**
         * 注销或恢复ajax
         * @param applicationId
         * @param message
         */
        function sendUpdateDelAjax(applicationId, message) {
            Messenger().run({
                successMessage: message + '应用成功',
                errorMessage: message + '应用失败',
                progressMessage: '正在' + message + '应用....'
            }, {
                url: web_path + getAjaxUrl().updateDel,
                type: 'post',
                data: {applicationIds: applicationId},
                success: function (data) {
                    if (data.state) {
                        myTable.ajax.reload();
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

    });
});