var articleDetail = {
    init: function () {
        $('#articleMax').click(function(){
            $('#articleWindow').removeClass("col-sm-8");
            $('#otherWindow, #articleMax').hide();
            $('#articleMin').show();
        });
        $('#articleMin').click(function(){
            $('#articleWindow').addClass("col-sm-8");
            $('#articleMin').hide();
            $('#otherWindow, #articleMax').show();
        });
    },
    detail: {
        saveComment: function () {
            _alert('提示', '评论功能暂未开启');
            return;
            var comment = $('#reply').val();
            if (comment == null || comment.replace(/(^\s+)|(\s+$)/g, "") == '') {
                _alert('确认', '评论内容不能为空！');
                return;
            }
            if (_userId == '') {
                _confirm('确认', '您当前尚未登录，是否登录？', articleDetail.detail.toLogin);
                return;
            }
            comment = $('#replyTo').html() + comment;
            $.ajax({
                type: 'POST',
                url: _path + '/articleDo/saveComment',
                data: {
                    comment: comment,
                    userId: _userId,
                    blogId: _blogId,
                    parentId: articleDetail.param.replyId
                },
                dataType: 'json',
                success: function (result) {
                    if (result.code != '1') {
                        _alert('错误', '评论失败！');
                        return;
                    }
                    window.location.reload();
                }
            });
        },
        toLogin: function () {
            $('#_confirmModal').modal('hide');
            $('#_loginModal').modal('show');
        },
        reply: function (num, name, id) {
            location.href = '#reply';
            if (typeof(id) == 'undefined') {
                articleDetail.param.replyId = '';
                $('#replyTo').html('');
            } else {
                articleDetail.param.replyId = id;
                var str = '<div class="alert alert-default" role="alert">';
                str += '<p>回复 ' + name + '  #' + num + ' ：</p></div>';
                $('#replyTo').html(str);
            }
        },
        praise: function (state) {
            if (_userId == '') {
                _confirm('确认', '您当前尚未登录，是否登录？', articleDetail.detail.toLogin);
            } else {
                $.ajax({
                    url: _path + '/articleDo/praiseArticle',
                    data: {
                        state: state,
                        articleId: _blogId
                    },
                    dataType: 'json',
                    success: function (result) {
                        if (result.code != '1') {
                            _alert('错误', '操作失败！');
                            return;
                        }
                        window.location.reload();
                    }
                });
            }
        },
        favorite: function (state) {
            if (_userId == '') {
                _confirm('确认', '您当前尚未登录，是否登录？', articleDetail.detail.toLogin);
            } else {
                $.ajax({
                    url: _path + '/articleDo/favoriteArticle',
                    data: {
                        state: state,
                        articleId: _blogId
                    },
                    dataType: 'json',
                    success: function (result) {
                        if (result.code != '1') {
                            _alert('错误', '操作失败！');
                            return;
                        }
                        window.location.reload();
                    }
                });
            }
        },
        delBlog: function () {
            _confirm('确认', '删除后将无法恢复，是否删除？', articleDetail.detail.toDelete);
        },
        toDelete: function () {
            $.ajax({
                url: _path + '/articleDo/delArticle',
                data: {
                    articleId: _blogId
                },
                dataType: 'json',
                success: function (result) {
                    if (result.code != '1') {
                        _alert('错误', '操作失败！');
                        return;
                    }
                    window.location.href = $('.article-return').attr('href');
                }
            });
        }
    },
    param: {
        replyId: ''
    }
}