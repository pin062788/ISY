/**
 * Created by lenovo on 2016-10-19.
 */
requirejs.config({
    // pathsオプションの設定。"module/name": "path"を指定します。拡張子（.js）は指定しません。
    paths: {
        "csrf": web_path + "/js/util/csrf",
        "nav": web_path + "/js/util/nav",
        "constants":web_path + "/js/util/constants",
        "isy-bootstrap-treeview":web_path + "/plugin/bootstrap-treeview/bootstrap-treeview"
    },
    // shimオプションの設定。モジュール間の依存関係を定義します。
    shim: {
        "messenger": {
            deps: ["jquery"]
        },
        "bootstrap-treeview":{
            deps: ["jquery"]
        }
    }
});

// require(["module/name", ...], function(params){ ... });
require(["jquery", "handlebars", "csrf", "messenger", "bootstrap-treeview","nav","constants"], function ($, Handlebars, csrf, messenger,treeview, nav, constants) {

    /*
     初始化消息机制
     */
    Messenger.options = {
        extraClasses: 'messenger-fixed messenger-on-bottom messenger-on-right',
        theme: 'flat'
    };

    /*
     ajax url.
     */
    var ajax_url = {
        school_data_url: '/user/schools',
        college_data_url: '/user/colleges',
        application_json_data:'/special/channel/system/application/json',
        role_application_data:'/web/platform/role/application/data',
        update: '/web/platform/role/update',
        valid: '/web/platform/role/update/valid',
        back:'/web/menu/platform/role'
    };

    /*
     参数id
     */
    var paramId = {
        schoolId: '#select_school',
        collegeId: '#select_college',
        roleName: '#roleName',
        roleId:'#roleId'
    };

    /*
     参数
     */
    var param = {
        schoolId: $(paramId.schoolId).val(),
        collegeId: $(paramId.collegeId).val(),
        roleName: $(paramId.roleName).val(),
        applicationIds:'',
        roleId:$(paramId.roleId).val()
    };

    /*
     检验id
     */
    var validId = {
        schoolId: '#valid_school',
        collegeId: '#valid_college',
        roleName: '#valid_role_name'
    };

    /*
     错误消息id
     */
    var errorMsgId = {
        schoolId: '#school_error_msg',
        collegeId: '#college_error_msg',
        roleName: '#role_name_error_msg'
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

    /**
     * 初始化参数
     */
    function initParam() {
        param.schoolId = $(paramId.schoolId).val();
        param.collegeId = $(paramId.collegeId).val();
        param.roleName = $(paramId.roleName).val();
        param.roleId = $(paramId.roleId).val();
        param.applicationIds = getAllCheckedData();
    }

    /*
     页面加载时初始化选中
     */
    var selectedSchoolCount = true;
    var selectedCollegeCount = true;

    /*
     初始化数据
     */
    init();

    /**
     * 学校数据展现
     * @param data json数据
     */
    function schoolData(data) {
        var source = $("#school-template").html();
        var template = Handlebars.compile(source);

        Handlebars.registerHelper('school_value', function () {
            var value = Handlebars.escapeExpression(this.schoolId);
            return new Handlebars.SafeString(value);
        });

        Handlebars.registerHelper('school_name', function () {
            var name = Handlebars.escapeExpression(this.schoolName);
            return new Handlebars.SafeString(name);
        });

        var html = template(data);
        $(paramId.schoolId).html(html);

        if (selectedSchoolCount) {
            selectedSchool();
            selectedSchoolCount = false;
        }
    }

    /**
     * 选中学校
     */
    function selectedSchool() {
        var realSchoolId = $('#schoolId').val();
        var schoolChildrens = $('#select_school').children();
        for (var i = 0; i < schoolChildrens.length; i++) {
            if ($(schoolChildrens[i]).val() === realSchoolId) {
                $(schoolChildrens[i]).prop('selected', true);
                changeCollege($(schoolChildrens[i]).val());
                break;
            }
        }
    }

    /**
     * 选中院
     */
    function selectedCollege() {
        var realCollegeId = $('#collegeId').val();
        var collegeChildrens = $('#select_college').children();
        for (var i = 0; i < collegeChildrens.length; i++) {
            if ($(collegeChildrens[i]).val() === realCollegeId) {
                $(collegeChildrens[i]).prop('selected', true);
                break;
            }
        }
    }

    /**
     * 初始化界面
     */
    function init(){
        initSchoolData();
        initTreeView();
    }

    function initSchoolData(){
        $.get(web_path + ajax_url.school_data_url, function (data) {
            schoolData(data);
        });
    }

    // 当改变学校时，变换学院数据.
    $(paramId.schoolId).change(function () {
        initParam();
        var school = param.schoolId;
        changeCollege(school);// 根据学校重新加载院数据

        // 改变选项时，检验
        if (Number(school) > 0) {
            validSuccessDom(validId.schoolId, errorMsgId.schoolId);
        } else {
            validErrorDom(validId.schoolId, errorMsgId.schoolId, '请选择学校');
        }

        validCleanDom(validId.collegeId, errorMsgId.collegeId);
    });

    // 当改变学院时，变换系数据.
    $(paramId.collegeId).change(function () {
        initParam();
        var college = param.collegeId;

        if (Number(college) > 0) {
            validSuccessDom(validId.collegeId, errorMsgId.collegeId);
        } else {
            validErrorDom(validId.collegeId, errorMsgId.collegeId, '请选择院');
        }
    });

    /**
     * 改变学院选项
     * @param school_id 学校id
     */
    function changeCollege(school_id) {
        if (Number(school_id) == 0) {
            var source = $("#college-template").html();
            var template = Handlebars.compile(source);

            var context = {
                listResult: [
                    {name: "请选择院", value: ""}
                ]
            };

            Handlebars.registerHelper('college_value', function () {
                var value = Handlebars.escapeExpression(this.value);
                return new Handlebars.SafeString(value);
            });

            Handlebars.registerHelper('college_name', function () {
                var name = Handlebars.escapeExpression(this.name);
                return new Handlebars.SafeString(name);
            });

            var html = template(context);
            $(paramId.collegeId).html(html);
        } else {
            // 根据学校id查询院数据
            $.post(web_path + ajax_url.college_data_url, {schoolId: school_id}, function (data) {
                var source = $("#college-template").html();
                var template = Handlebars.compile(source);

                Handlebars.registerHelper('college_value', function () {
                    var value = Handlebars.escapeExpression(this.collegeId);
                    return new Handlebars.SafeString(value);
                });

                Handlebars.registerHelper('college_name', function () {
                    var name = Handlebars.escapeExpression(this.collegeName);
                    return new Handlebars.SafeString(name);
                });

                var html = template(data);
                $(paramId.collegeId).html(html);

                if (selectedCollegeCount) {
                    selectedCollege();
                    selectedCollegeCount = false;
                }
            });
        }
    }

    /*
     即时检验系名
     */
    $(paramId.roleName).blur(function () {
        initParam();
        var roleName = param.roleName;
        if (roleName.length <= 0 || roleName.length > 50) {
            validErrorDom(validId.roleName, errorMsgId.roleName, '角色名50个字符以内');
        } else {
            // 院名是否重复
            Messenger().run({
                errorMessage: '请求失败'
            }, {
                url: web_path + ajax_url.valid,
                type: 'post',
                data: param,
                success: function (data) {
                    if (data.state) {
                        validSuccessDom(validId.roleName, errorMsgId.roleName);
                    } else {
                        validErrorDom(validId.roleName, errorMsgId.roleName, data.msg);
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

    /*
     返回
     */
    $('#page_back').click(function () {
        window.location.href = web_path + ajax_url.back;
    });

    /*
     保存数据
     */
    $('#save').click(function () {
        add();
    });

    /*
     添加询问
     */
    function add() {
        initParam();
        var roleName = param.roleName;
        var msg;
        msg = Messenger().post({
            message: "确定添加角色 '" + roleName + "'  吗?",
            actions: {
                retry: {
                    label: '确定',
                    phrase: 'Retrying TIME',
                    action: function () {
                        msg.cancel();
                        validRoleName();
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
     * 添加时检验并提交数据
     */
    function validRoleName() {
        initParam();
        var roleName = param.roleName;
        if (roleName.length <= 0 || roleName.length > 50) {
            Messenger().post({
                message: '角色名为1~50个字符',
                type: 'error',
                showCloseButton: true
            });
        } else {
            Messenger().run({
                errorMessage: '请求失败'
            }, {
                url: web_path + ajax_url.valid,
                type: 'post',
                data: param,
                success: function (data) {
                    if (data.state) {
                        sendAjax();
                    } else {
                        Messenger().post({
                            message: data.msg,
                            type: 'error',
                            showCloseButton: true
                        });
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
     * 发送数据到后台
     */
    function sendAjax() {
        initParam();
        Messenger().run({
            successMessage: '保存数据成功',
            errorMessage: '保存数据失败',
            progressMessage: '正在保存数据....'
        }, {
            url: web_path + ajax_url.update,
            type: 'post',
            data: param,
            success: function (data) {
                if (data.state) {
                    window.location.href = web_path + ajax_url.back;
                } else {
                    Messenger().post({
                        message: data.msg,
                        type: 'error',
                        showCloseButton: true
                    });
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

    var treeviewId = $('#treeview-checkable');

    /**
     * 初始化tree view
     */
    function initTreeView(){
        $.get(web_path + ajax_url.application_json_data, function (data) {
            if(data.listResult.length>0){
                treeViewData(data.listResult);
            }
        });
    }

    function treeViewData(data){
        var $checkableTree = treeviewId.treeview({
            data: data,
            showIcon: false,
            showCheckbox: true,
            onNodeChecked: function(event, node) {
                checkAllParentNode(node);
                checkAllChildrenNode(node);
            },
            onNodeUnchecked: function (event, node) {
                uncheckAllChildrenNode(node);
                getAllParent(node);// 若任何子节点选中则取消选中该父节点
            }
        });

        selectedApplication();
    }

    /**
     * 选中应用
     */
    function selectedApplication(){
        initParam();
        var roleId = param.roleId;
        $.post(web_path + ajax_url.role_application_data,{roleId:roleId}, function (data) {
            var list = data.listResult;
            if(list.length>0){
                var unCheckeds =  treeviewId.treeview('getUnchecked');
                for(var i = 0;i<list.length;i++){
                    for(var j = 0;j<unCheckeds.length;j++){
                        if(list[i].applicationId == unCheckeds[j].dataId){
                            treeviewId.treeview('checkNode', [ unCheckeds[j], { silent: true } ]);
                            break;
                        }
                    }
                }
            }
        });
    }

    /**
     * 选中所有父节点
     * @param node
     */
    function checkAllParentNode(node){
        if(node.hasOwnProperty('parentId') && node.parentId != undefined){
            var parentNode = treeviewId.treeview('getParent', node);
            checkAllParentNode(parentNode);
        }
        treeviewId.treeview('checkNode', [ node.nodeId, { silent: true } ]);
    }

    /**
     * 取消所有子节点的选中
     * @param node
     */
    function uncheckAllChildrenNode(node){
        if(node.hasOwnProperty('nodes')&&node.nodes!=null){
            var n = node.nodes;
            for(var i = 0;i<n.length;i++){
                uncheckAllChildrenNode(n[i]);
            }
        }
        treeviewId.treeview('uncheckNode', [ node.nodeId, { silent: true } ]);
    }

    /**
     * 选中所有子节点
     * @param node
     */
    function checkAllChildrenNode(node){
        if(node.hasOwnProperty('nodes')&&node.nodes!=null){
            var n = node.nodes;
            for(var i = 0;i<n.length;i++){
                checkAllChildrenNode(n[i]);
            }
        }
        treeviewId.treeview('checkNode', [ node.nodeId, { silent: true } ]);
    }

    var childrenArr = [];
    function getAllChildren(node){
        if(node.hasOwnProperty('nodes')&&node.nodes!=null){
            var n = node.nodes;
            for(var i = 0;i<n.length;i++){
                getAllChildren(n[i]);
            }
        }
        childrenArr.push(node);
    }

    function getAllParent(node){
        if(node.hasOwnProperty('parentId') && node.parentId != undefined){
            var parentNode = treeviewId.treeview('getParent', node);
            childrenArr = [];
            getAllChildren(parentNode);
            var parentNodeIsChecked = false;
            for(var i = 0;i<childrenArr.length;i++){
                if(childrenArr[i].nodeId != parentNode.nodeId && childrenArr[i].state.checked){
                    parentNodeIsChecked = true;
                }
            }
            if(!parentNodeIsChecked){
                treeviewId.treeview('uncheckNode', [ parentNode.nodeId, { silent: true } ]);
                getAllParent(parentNode);
            }

        }
    }

    /**
     * 获取所有选中节点的dataId
     * @returns {string}
     */
    function getAllCheckedData(){
        var applicationIds = '';
        var checkeds =  treeviewId.treeview('getChecked');
        var temp = [];
        for(var i = 0;i<checkeds.length;i++){
            temp.push(checkeds[i].dataId);
        }
        if(temp.length>0){
            applicationIds = temp.join(",");
        }
        return applicationIds;
    }

});