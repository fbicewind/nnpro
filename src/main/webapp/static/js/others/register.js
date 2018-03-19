var chars = ['1', '2', '3', '4', '5', '6', '7', '8', '9', '0', 'Q', 'W', 'E',
    'R', 'T', 'Y', 'U', 'I', 'O', 'P', 'A', 'S', 'D', 'F', 'G', 'H', 'J',
    'K', 'L', 'Z', 'X', 'C', 'V', 'B', 'N', 'M', 'q', 'w', 'e', 'r', 't',
    'y', 'u', 'i', 'o', 'p', 'a', 's', 'd', 'f', 'g', 'h', 'j', 'k', 'l',
    'z', 'x', 'c', 'v', 'b', 'n', 'm', '2', '3', '4', '5', '6', '7', '8',
    '9'];
var register = {
    param: {
        code: '',
        username: '',
        count: 0
    },
    init: function () {
        $('.form_date').datetimepicker({
            language: 'zh-CN',
            weekStart: 0,
            todayBtn: 0,
            autoclose: 1,
            todayHighlight: 1,
            startView: 2,
            minView: 2,
            forceParse: 0
        });
        var bWidth = $(window).width();
        if (bWidth < 768) {
            var width = $('#validateCodeDiv').width() - 190;
            if (width > 140) {
                width = 140;
            }
            $('.validate-code').width(width);
        }
        register.detail.createCode();
        $('.validate-code').click(function () {
            register.detail.createCode();
        });
        register.validate.register();
        $('#registerButton').click(function () {
            $('#registerForm').submit();
        });
    },
    detail: {
        createCode: function () {
            var code = '';
            for (var i = 0; i < 4; i++) {
                var num = Math.ceil(Math.random() * 60);
                code += chars[num];
            }
            $('.validate-code').text(code);
            register.param.code = code;
        },
        toLogin: function () {
            $('#_confirmModal').modal('hide');
            $('#_loginModal').modal('show');
            $('#_username').val(register.param.username);
        }
    },
    validate: {
        register: function () {
            $('#registerForm').validate({
                rules: {
                    username: {
                        required: true,
                        minlength: 4,
                        maxlength: 12,
                        remote: {
                            url: _path + "/validate/username",
                            type: "get",
                            data: {
                                username: function () {
                                    return $("#username").val();
                                }
                            },
                            dataType: 'json',
                            dataFilter: function (data) {
                                data = eval('(' + data + ')');
                                return data.code == '1';
                            }
                        }
                    },
                    nickname: {
                        required: true,
                        minlength: 2,
                        maxlength: 8,
                        remote: {
                            url: _path + "/validate/nickname",
                            type: "get",
                            data: {
                                nickname: function () {
                                    return $("#nickname").val();
                                }
                            },
                            dataType: 'json',
                            dataFilter: function (data) {
                                data = eval('(' + data + ')');
                                return data.code == '1';
                            }
                        }
                    },
                    password: {
                        required: true,
                        minlength: 6,
                        maxlength: 16
                    },
                    passwordAgain: {
                        required: true,
                        minlength: 6,
                        maxlength: 16,
                        equalTo: '#password'
                    },
                    email: {
                        email: true
                    },
                    mobile: {
                        rangelength: [11, 11]
                    },
                    profession: {
                        maxlength: 20
                    },
                    remark: {
                        maxlength: 100
                    },
                    code: {
                        validateCode: register.param.code
                    }
                },
                messages: {
                    username: {
                        required: '请填写用户名',
                        minlength: '用户名不得少于4个字符',
                        maxlength: '用户名不得超过12个字符',
                        remote: '用户名已存在'
                    },
                    nickname: {
                        required: '请填写昵称',
                        minlength: '昵称不得少于2个字符',
                        maxlength: '昵称不得超过8个字符',
                        remote: '昵称已存在'
                    },
                    password: {
                        required: '请填写密码',
                        minlength: '密码不得少于6个字符',
                        maxlength: '密码不得超过16个字符'
                    },
                    passwordAgain: {
                        required: '请填写密码',
                        minlength: '密码不得少于6个字符',
                        maxlength: '密码不得超过16个字符',
                        equalTo: '两次输入密码不同'
                    },
                    email: {
                        email: '请输入正确的邮箱'
                    },
                    mobile: {
                        rangelength: '请输入正确的手机号'
                    },
                    profession: {
                        maxlength: '职业不得超过20个字符'
                    },
                    remark: {
                        maxlength: '个性签名不得超过100个字符'
                    },
                    code: {
                        validateCode: '请输入正确的验证码'
                    }
                },
                submitHandler: function () {
                    register.param.count++;
                    if (register.param.count != 1) {
                        return;
                    }
                    $.ajax({
                        type: 'POST',
                        url: _path + '/doRegister',
                        data: $('#registerForm').serialize(),
                        dataType: 'json',
                        success: function (result) {
                            register.param.count--;
                            if (result.code == '1') {
                                register.param.username = result.data;
                                _confirm('登录', '注册成功，是否前往登录？', register.detail.toLogin);
                            } else {
                                _alert('错误', '注册发生异常！');
                            }
                        }
                    });
                }
            });
        }
    }
}

$.validator.addMethod("validateCode", function (value, element, params) {
    return value.toUpperCase() == register.param.code.toUpperCase();
}, "请填写正确的验证码");