var modifyInfo = {
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
        modifyInfo.validate.info();
        $('#modifyInfoCancel').click(function () {
            window.history.go(-1);
        });
    },
    validate: {
        info: function () {
            $('#modifyInfoForm').validate({
                rules: {
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
                    }
                },
                messages: {
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
                    }
                },
                submitHandler: function () {
                    $.ajax({
                        type: 'POST',
                        url: _path + '/aboutMeDo/modifyInfo',
                        data: $('#modifyInfoForm').serialize(),
                        dataType: 'json',
                        success: function (result) {
                            if (result.code == '1') {
                                window.location.href = _path + '/aboutMe/' + _userId;
                            } else {
                                _alert('确认', '修改失败！');
                            }
                        }
                    });
                }
            });
        }
    }
}