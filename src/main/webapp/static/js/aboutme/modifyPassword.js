var modifyPassword = {
    init: function () {
        modifyPassword.validate.modifyPwd();
        $('#modifyPswCancel').click(function () {
            window.history.go(-1);
        });
    },
    validate: {
        modifyPwd: function () {
            $('#modifyPwdForm').validate({
                rules: {
                    oldPassword: {
                        required: true,
                        minlength: 6,
                        maxlength: 16
                    },
                    password: {
                        required: true,
                        minlength: 6,
                        maxlength: 16,
                        notEqualTo: '#oldPassword'
                    },
                    passwordAgain: {
                        required: true,
                        minlength: 6,
                        maxlength: 16,
                        equalTo: '#password'
                    }
                },
                messages: {
                    oldPassword: {
                        required: '请填写原密码',
                        minlength: '密码不得少于6个字符',
                        maxlength: '密码不得超过16个字符'
                    },
                    password: {
                        required: '请填写新密码',
                        minlength: '密码不得少于6个字符',
                        maxlength: '密码不得超过16个字符',
                        notEqualTo: '新旧密码不能相同'
                    },
                    passwordAgain: {
                        required: '请填写新密码',
                        minlength: '密码不得少于6个字符',
                        maxlength: '密码不得超过16个字符',
                        equalTo: '两次输入密码不同'
                    }
                },
                submitHandler: function () {
                    $.ajax({
                        type: 'POST',
                        url: _path + '/aboutMeDo/modifyPwd',
                        data: $('#modifyPwdForm').serialize(),
                        dataType: 'json',
                        success: function (result) {
                            if (result.code == 2) {//原密码错误，后续增加锁定功能
                                _alert('确认', '原密码错误！');
                            } else if (result.code == 1) {//修改成功
                                window.location.href = _path + '/aboutMe/' + _userId;
                            } else {
                                _alert('确认', '密码修改失败！');
                            }
                        }
                    });
                }
            });
        }
    }
}

$.validator.addMethod("notEqualTo", function (value, element, param) {
    return value != $(param).val();
}, "两次内容不能相同");