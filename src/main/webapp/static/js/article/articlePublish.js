var editor = new wangEditor('wEditor');
var articlePublish = {
    init: function () {
        articlePublish.detail.initEditor();
        articlePublish.validate.type();
        $('#myModal').on('hide.bs.modal', function () {
            $('#typeName').val('');
        });
    },
    detail: {
        initEditor: function () {
            wangEditor.config.printLog = false;

            if (articlePublish.detail.isPC()) {
                editor.config.menus = ['bold', 'underline', 'italic',
                    'strikethrough', 'forecolor', 'bgcolor', 'fontfamily',
                    'fontsize', 'head', '|', 'unorderlist', 'orderlist',
                    'alignleft', 'aligncenter', 'alignright', '|', 'link',
                    'table', 'emotion', 'img', 'fullscreen'];
            } else {
                editor.config.menus = ['head', 'alignleft',
                    'aligncenter', 'alignright', 'link', 'emotion', 'img',
                    'fullscreen'];
            }
            editor.config.menuFixed = false;
            editor.config.uploadImgUrl = _path + '/blogDo/upload';
            editor.config.uploadImgFileName = 'image';
            editor.config.hideLinkImg = true;
            editor.create();
            editor.$txt.html($('#_blogDetail').val());
        },
        isPC: function () {
            var userAgentInfo = navigator.userAgent;
            var Agents = ["Android", "iPhone", "SymbianOS", "Windows Phone",
                "iPad", "iPod"];
            var flag = true;
            for (var v = 0; v < Agents.length; v++) {
                if (userAgentInfo.indexOf(Agents[v]) > 0) {
                    flag = false;
                    break;
                }
            }
            return flag;
        },
        submitBlog: function () {
            if ($('#title').val() == '') {
                alert('请输入标题');
                return;
            }
            var content = editor.$txt.html();
            var thumb = '';
            //匹配图片（g表示匹配所有结果i表示区分大小写）
            var imgReg = /<img.*?(?:>|\/>)/gi;
            //匹配src属性
            var srcReg = /src=[\'\"]?([^\'\"]*)[\'\"]?/i;
            var arr = content.match(imgReg);
            if (arr != null) {
                var n = 0;
                for (var i = 0; i < arr.length; i++) {
                    if (arr[i].indexOf('/static/upload/img/blog') > -1 && n < 3) {
                        var src = arr[i].match(srcReg);
                        if (thumb == '') {
                            thumb = src[1].replace('/l/', '/s/');
                        } else {
                            thumb += '|||' + src[1].replace('/l/', '/s/');
                        }
                        n++;
                    }
                }
            }
            $.ajax({
                type: 'POST',
                url: _path + '/articleDo/saveArticle',
                data: {
                    title: $('#title').val(),
                    thumbContent: editor.$txt.formatText(),
                    thumb: thumb,
                    content: content,
                    typeId: $('#type').val(),
                    publicFlag: $('#isPublic').is(':checked') ? 'N' : 'Y',
                    topFlag: $('#isTop').is(':checked') ? 'Y' : 'N',
                    recommendFlag: $('#isRecommend').is(':checked') ? 'Y' : 'N',
                    id: $('#blogId').val() == '' ? 0 : $('#blogId').val(),
                    draftFlag: 'N'
                },
                dataType: 'json',
                success: function (result) {
                    window.location.href = _path + '/article/detail/' + result.data.id;
                }
            });
        },
        addType: function () {
            $('#newType').submit();
        },
        cancelBlog: function () {
            _confirm('确认', '是否放弃当前编辑？', articlePublish.detail.returnDetail);
        },
        returnDetail: function () {
            history.go(-1);
        },
        saveDraft: function () {
            var title = $('#title').val();
            var content = editor.$txt.html();
            var text = editor.$txt.formatText();
            if (title == '') {
                title = articlePublish.util.yyyyMMddHHmmss();
                if (text.replace(/(^\s+)|(\s+$)/g, "") == '' && !content.indexOf('src=') > -1) {
                    _alert('确认', '当前未输入内容');
                    return;
                }
            }
            $.ajax({
                type: 'POST',
                url: _path + '/articleDo/saveArticle',
                data: {
                    title: title,
                    content: content,
                    typeId: $('#type').val(),
                    publicFlag: $('#isPublic').is(':checked') ? 'N' : 'Y',
                    topFlag: $('#isTop').is(':checked') ? 'Y' : 'N',
                    recommendFlag: $('#isRecommend').is(':checked') ? 'Y' : 'N',
                    id: $('#blogId').val() == '' ? 0 : $('#blogId').val(),
                    draftFlag: 'Y'
                },
                dataType: 'json',
                success: function (result) {
                    _alert('确认', '草稿保存成功！');
                    $('#blogId').val(result.data.id);
                }
            });
        }
    },
    validate: {
        type: function () {
            $('#newType').validate({
                rules: {
                    typeName: {
                        required: true,
                        minlength: 2,
                        maxlength: 10,
                        remote: {
                            url: _path + "/validate/newType",
                            type: "get",
                            data: {
                                typeName: function () {
                                    return $("#typeName").val();
                                }
                            },
                            dataType: 'json',
                            dataFilter: function (data) {
                                data = eval('(' + data + ')');
                                return data.code == '0';
                            }
                        }
                    },
                },
                messages: {
                    typeName: {
                        required: '请填写分类名称',
                        minlength: '分类名称不能少于两个字符',
                        maxlength: '分类名称不能多余十个字符',
                        remote: '分类名称已存在'
                    }
                },
                submitHandler: function () {
                    $.ajax({
                        type: 'POST',
                        url: _path + '/articleDo/saveType',
                        data: {
                            typeName: $('#typeName').val()
                        },
                        dataType: 'json',
                        success: function (result) {
                            $('#type').append('<option value="' + result.typeId + '" selected="selected">' + result.typeName + '</option>');
                            $('#myModal').modal('hide');
                        }
                    });
                }
            });
        }
    },
    util: {
        yyyyMMddHHmmss: function () {
            var today = new Date()
            var y = today.getFullYear()
            var mo = today.getMonth() + 1
            var d = today.getDate()
            var h = today.getHours()
            var m = today.getMinutes()
            var s = today.getSeconds()
            return y + '' + mo + '' + d + '' + h + '' + m + '' + s;
        }
    }
}

