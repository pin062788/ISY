/**
 * Created by lenovo on 2016-08-19.
 */
requirejs.config({
    // pathsオプションの設定。"module/name": "path"を指定します。拡張子（.js）は指定しません。
    paths: {
        "jquery.showLoading": web_path + "/plugin/loading/js/jquery.showLoading.min",
        "csrf":web_path + "/js/util/csrf",
        "com":web_path + "/js/util/com"
    },
    // shimオプションの設定。モジュール間の依存関係を定義します。
    shim: {
        "jquery.showLoading": {
            // jQueryに依存するのでpathsで設定した"module/name"を指定します。
            deps: ["jquery"]
        }
    }
});
// require(["module/name", ...], function(params){ ... });
require(["jquery", "requirejs-domready", "sb-admin", "jquery.showLoading","csrf","com"], function ($, domready, sa, loading,csrf,com) {
    domready(function () {
        //This function is called once the DOM is ready.
        //It will be safe to query the DOM and manipulate
        //DOM nodes in this function.

        /*
        ajax url
         */
        var ajax_url = {
            valid_email:'/user/login/valid/email',
            change_jcaptcha:'/user/login/jcaptcha?d=',
            student_register:'/register?type=student',
            staff_register:'/register?type=staff',
            password_forget:'/user/login/password/forget',
            login:'/login',
            backstage:'/web/menu/backstage'
        };

        /*
        参数id
         */
        var paramId = {
            email:'#email',
            password:'#password',
            captcha:'#j_captcha_response'
        };

        /*
        参数
         */
        var param = {
            email:$(paramId.email).val().trim(),
            password:$(paramId.password).val().trim(),
            captcha:$(paramId.captcha).val().trim()
        };

        /**
         * 初始化参数
         */
        function initParam(){
            param.email = $(paramId.email).val().trim();
            param.password = $(paramId.password).val().trim();
            param.captcha = $(paramId.captcha).val().trim();
        }

        /*
        检验正则
         */
        var valid_regex = {
            email_regex:/^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/,
            password_regex:/^[a-zA-Z0-9]\w{5,17}$/,
            captcha_regex:/^\w+$/
        };

        /*
        检验id
         */
        var validId = {
            email:'#valid_email',
            password:'#valid_password',
            captcha:'#valid_captcha'
        };

        /*
        错误消息id
         */
        var errorMsgId = {
            email:'#email_error_msg',
            password:'#password_error_msg',
            captcha:'#captcha_error_msg'
        };

        /*
        消息
         */
        var msg = {
            email:'邮箱格式不正确',
            password:'密码至少6位任意字母或数字，以及下划线',
            captcha:'验证码不能为空'
        };

        /**
         * 检验成功
         * @param validId
         * @param errorMsgId
         */
        function validSuccessDom(validId,errorMsgId){
            $(validId).removeClass('has-error');
            $(errorMsgId).addClass('hidden').text('');
        }

        /**
         * 检验失败
         * @param validId
         * @param errorMsgId
         * @param msg
         */
        function validErrorDom(validId,errorMsgId,msg){
            $(validId).addClass('has-error');
            $(errorMsgId).removeClass('hidden').text(msg);
        }

        /**
         * 开始加载
         */
        function startLoading(){
            $('#loading_region').showLoading();
        }

        /**
         * 结束加载
         */
        function endLoading(){
            $('#loading_region').hideLoading();
        }

        $('#student_register').click(function () {
            window.location.href = web_path +  ajax_url.student_register;
        });

        $('#staff_register').click(function () {
            window.location.href = web_path + ajax_url.staff_register;
        });

        $('#forget_password').click(function () {
            window.location.href = web_path + ajax_url.password_forget;
        });

        $('#jcaptcha').click(function () {
            changeJcaptcha();
        });

        $(paramId.email).blur(function () {
            initParam();
            var email = param.email;
            if (!valid_regex.email_regex.test(email)) {
                validErrorDom(validId.email,errorMsgId.email,msg.email);
            } else {
                validSuccessDom(validId.email,errorMsgId.email);
            }
        });

        $(paramId.password).blur(function () {
            initParam();
            var password = param.password;
            if (!valid_regex.password_regex.test(password)) {
                validErrorDom(validId.password,errorMsgId.password,msg.password);
            } else {
                validSuccessDom(validId.password,errorMsgId.password);
            }
        });

        $(paramId.captcha).blur(function () {
            initParam();
            var j_captcha_response = param.captcha;
            if (!valid_regex.captcha_regex.test(j_captcha_response)) {
                validErrorDom(validId.captcha,errorMsgId.captcha,msg.captcha);
            } else {
                validSuccessDom(validId.captcha,errorMsgId.captcha);
            }
        });


        function changeJcaptcha() {
            $('#jcaptcha').attr('src', web_path + ajax_url.change_jcaptcha + new Date().getTime());
        }

        $('#login').click(function () {
            validEmail();
        });

        $(paramId.email).keyup(function(event){
            if(event.keyCode ==13){
                validEmail();
            }
        });

        $(paramId.password).keyup(function(event){
            if(event.keyCode ==13){
                validEmail();
            }
        });

        $(paramId.captcha).keyup(function(event){
            if(event.keyCode ==13){
                validEmail();
            }
        });

        function validEmail() {
            initParam();
            var email = param.email;
            if (!valid_regex.email_regex.test(email)) {
                validErrorDom(validId.email,errorMsgId.email,msg.email);
            } else {
                validSuccessDom(validId.email,errorMsgId.email);
                validPassword();
            }
        }

        function validPassword() {
            initParam();
            var password = param.password;
            if (!valid_regex.password_regex.test(password)) {
                validErrorDom(validId.password,errorMsgId.password,msg.password);
            } else {
                validSuccessDom(validId.password,errorMsgId.password);
                validCaptcha();
            }
        }

        /*
        错误码
         */
        var error_code = {
            AU_ERROR_CODE:'1', // 权限异常
            CAPTCHA_ERROR_CODE:'2', // 验证码错误
            OK_CODE:'3', // 全部正确
            SCHOOL_IS_DEL_CODE:'4', //  用户所在院校或班级被注销
            USERNAME_IS_NOT_EXIST_CODE:'5', // 账号不存在
            CAPTCHA_IS_BLANK:'6', // 验证码为空
            PASSWORD_IS_BLANK:'7', // 密码为空
            EMAIL_IS_BLANK:'8', // 邮箱为空
            EMAIL_IS_NOT_VALID:'9', // 邮箱未验证
            USERNAME_IS_ENABLES:'10' //  账号已被注销
        };

        function validCaptcha() {
            initParam();
            var j_captcha_response = param.captcha;
            if (!valid_regex.captcha_regex.test(j_captcha_response)) {
                validErrorDom(validId.captcha,errorMsgId.captcha,msg.captcha);
            } else {
                // 显示遮罩
                startLoading();
                $.post(web_path + ajax_url.login, $('#login_form').serialize(), function (data) {
                    // 去除遮罩
                    endLoading();
                    var captchaInput = $('#j_captcha_response');
                    var p_error_msg = $('#error_msg');
                    switch (data){
                        case error_code.AU_ERROR_CODE:
                            changeJcaptcha();
                            captchaInput.val('');
                            validSuccessDom(validId.captcha,errorMsgId.captcha);
                            p_error_msg.removeClass('hidden').text('权限异常');
                            break;
                        case error_code.CAPTCHA_ERROR_CODE:
                            changeJcaptcha();
                            validErrorDom(validId.captcha,errorMsgId.captcha,'验证码错误');
                            p_error_msg.addClass('hidden');
                            break;
                        case error_code.OK_CODE:
                            window.location.href = web_path + ajax_url.backstage;
                            break;
                        case error_code.SCHOOL_IS_DEL_CODE:
                            changeJcaptcha();
                            captchaInput.val('');
                            validSuccessDom(validId.captcha,errorMsgId.captcha);
                            p_error_msg.removeClass('hidden').text('您所在院校可能已被注销');
                            break;
                        case error_code.USERNAME_IS_NOT_EXIST_CODE:
                            changeJcaptcha();
                            captchaInput.val('');
                            validSuccessDom(validId.captcha,errorMsgId.captcha);
                            p_error_msg.removeClass('hidden').text('账号不存在');
                            break;
                        case error_code.CAPTCHA_IS_BLANK:
                            changeJcaptcha();
                            captchaInput.val('');
                            validErrorDom(validId.captcha,errorMsgId.captcha,'请填写验证码');
                            p_error_msg.addClass('hidden');
                            break;
                        case error_code.PASSWORD_IS_BLANK:
                            changeJcaptcha();
                            captchaInput.val('');
                            validSuccessDom(validId.captcha,errorMsgId.captcha);
                            p_error_msg.removeClass('hidden').text('请填写密码');
                            break;
                        case error_code.EMAIL_IS_BLANK:
                            changeJcaptcha();
                            captchaInput.val('');
                            validSuccessDom(validId.captcha,errorMsgId.captcha);
                            p_error_msg.removeClass('hidden').text('请填写账号');
                            break;
                        case error_code.EMAIL_IS_NOT_VALID:
                            changeJcaptcha();
                            captchaInput.val('');
                            validSuccessDom(validId.captcha,errorMsgId.captcha);
                            p_error_msg.removeClass('hidden').text('您的邮箱未验证无法登录');
                            break;
                        case error_code.USERNAME_IS_ENABLES:
                            changeJcaptcha();
                            captchaInput.val('');
                            validSuccessDom(validId.captcha,errorMsgId.captcha);
                            p_error_msg.removeClass('hidden').text('您的账号已被注销，请联系管理员');
                            break;
                        default:
                            changeJcaptcha();
                            captchaInput.val('');
                            validSuccessDom(validId.captcha,errorMsgId.captcha);
                            p_error_msg.removeClass('hidden').text('验证异常');
                    }
                });
            }
        }

    });
});