var articleDetail = {
    init: function () {
        articleDetail.detail.initComments();
        $('#articleMax').click(function () {
            $('#articleWindow').removeClass("col-sm-8");
            $('#otherWindow, #articleMax').hide();
            $('#articleMin').show();
        });
        $('#articleMin').click(function () {
            $('#articleWindow').addClass("col-sm-8");
            $('#articleMin').hide();
            $('#otherWindow, #articleMax').show();
        });
    },
    detail: {
        saveComment: function () {
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
                    articleId: _blogId,
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
                    type: 'POST',
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
                    type: 'POST',
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
                type: 'POST',
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
        },
        initComments: function () {
            $.ajax({
                type: 'POST',
                url: _path + '/article/getComments',
                data: {
                    articleId: _blogId,
                    pageNo: articleDetail.param.commentNo
                },
                dataType: 'json',
                success: function (result) {
                    $('#commentSize').text(result.totalSize);
                    articleDetail.param.totalNo = result.totalNo;
                    var str = '';
                    if (result.totalSize > 0) {
                        $.each(result.datalist, function (index, item) {
                            var num = index + (result.pageNo - 1) * result.pageSize + 1;
                            str += '<li><div class="media"><a class="pull-left text-center" href="#">'
                                + '<img class="media-object" src="/static/upload/img/m/' + item.avatar + '">'
                                + '<span>' + item.nickname + '</span></a><div class="media-body">'
                                + '<div class="article-comment-span"><span class="margin-right-30">' + num + '楼</span>'
                                + '<span class="hidden-xs">' + item.createTime + '</span>'
                                + '<a class="right-tag" href="javascript:void(0);" onclick="articleDetail.detail.reply(\'' + num + '\',\'' + item.nickname + '\',\'' + item.commentId + '\')">回复</a>'
                                + '</div>' + item.comment + '</div></div></li>';
                        });
                    } else {
                        str = '<li class="text-center">暂无评论...</li>';
                    }
                    $('.blog-comment-ul').html(str);
                    articleDetail.detail.drawPagination();
                }
            });
        },
        drawPagination: function () {
            if (articleDetail.param.totalNo < 2)
                return;
            var str = '<ul class="pagination pagination-sm text-center">';
            if (articleDetail.param.totalNo == 2) {
                if (articleDetail.param.commentNo == 1) {
                    str += '<li class="disabled"><a href="javascript:void(0);">&lt;</a></li>'
                        + '<li class="active disabled"><a href="javascript:void(0);">1</a></li>'
                        + '<li><a href="javascript:void(0);" onclick="articleDetail.detail.pointPage(2)">2</a></li>'
                        + '<li><a href="javascript:void(0);" onclick="articleDetail.detail.nextPage()">&gt;</a></li>';
                } else {
                    str += '<li><a href="javascript:void(0);" onclick="articleDetail.detail.prevPage()">&lt;</a></li>'
                        + '<li><a href="javascript:void(0);" onclick="articleDetail.detail.pointPage(1)">1</a></li>'
                        + '<li class="active disabled"><a href="javascript:void(0);">2</a></li>'
                        + '<li class="disabled"><a href="javascript:void(0);">&gt;</a></li>';
                }
            } else if (articleDetail.param.totalNo == 3) {
                if (articleDetail.param.commentNo == 1) {
                    str += '<li class="disabled"><a href="javascript:void(0);">&lt;</a></li>'
                        + '<li class="active disabled"><a href="javascript:void(0);">1</a></li>'
                        + '<li><a href="javascript:void(0);" onclick="articleDetail.detail.pointPage(2)">2</a></li>'
                        + '<li><a href="javascript:void(0);" onclick="articleDetail.detail.pointPage(3)">3</a></li>'
                        + '<li><a href="javascript:void(0);" onclick="articleDetail.detail.nextPage()">&gt;</a></li>';
                } else if (articleDetail.param.commentNo == 3) {
                    str += '<li><a href="javascript:void(0);" onclick="articleDetail.detail.prevPage()">&lt;</a></li>'
                        + '<li><a href="javascript:void(0);" onclick="articleDetail.detail.pointPage(1)">1</a></li>'
                        + '<li><a href="javascript:void(0);" onclick="articleDetail.detail.pointPage(2)">2</a></li>'
                        + '<li class="active disabled"><a href="javascript:void(0);">3</a></li>'
                        + '<li class="disabled"><a href="javascript:void(0);">&gt;</a></li>';
                } else {
                    str += '<li><a href="javascript:void(0);" onclick="articleDetail.detail.prevPage()">&lt;</a></li>'
                        + '<li><a href="javascript:void(0);" onclick="articleDetail.detail.pointPage(1)">1</a></li>'
                        + '<li class="active disabled"><a href="javascript:void(0);">2</a></li>'
                        + '<li><a href="javascript:void(0);" onclick="articleDetail.detail.pointPage(3)">3</a></li>'
                        + '<li><a href="javascript:void(0);" onclick="articleDetail.detail.nextPage()">&gt;</a></li>';
                }
            } else {
                if (articleDetail.param.commentNo == 1) {
                    str += '<li class="disabled"><a href="javascript:void(0);">&lt;</a></li>'
                        + '<li class="active disabled"><a href="javascript:void(0);">1</a></li>'
                        + '<li><a href="javascript:void(0);" onclick="articleDetail.detail.pointPage(2)">2</a></li>'
                        + '<li class="disabled"><a href="javascript:void(0);">...</a></li>'
                        + '<li><a href="javascript:void(0);" onclick="articleDetail.detail.pointPage(' + articleDetail.param.totalNo + ')">' + articleDetail.param.totalNo + '</a></li>'
                        + '<li><a href="javascript:void(0);" onclick="articleDetail.detail.nextPage()">&gt;</a></li>';
                } else if (articleDetail.param.commentNo == articleDetail.param.totalNo) {
                    str += '<li><a href="javascript:void(0);" onclick="articleDetail.detail.prevPage()">&lt;</a></li>'
                        + '<li><a href="javascript:void(0);" onclick="articleDetail.detail.pointPage(1)">1</a></li>'
                        + '<li class="disabled"><a href="javascript:void(0);">...</a></li>'
                        + '<li><a href="javascript:void(0);" onclick="articleDetail.detail.prevPage()">' + (articleDetail.param.totalNo - 1) + '</a></li>'
                        + '<li class="active disabled"><a href="javascript:void(0);">' + articleDetail.param.totalNo + '</a></li>'
                        + '<li class="disabled"><a href="javascript:void(0);">&gt;</a></li>';
                } else {
                    str += '<li><a href="javascript:void(0);" onclick="articleDetail.detail.prevPage()">&lt;</a></li>'
                        + '<li><a href="javascript:void(0);" onclick="articleDetail.detail.pointPage(1)">1</a></li>';
                    if (articleDetail.param.commentNo != 2) {
                        var prev = articleDetail.param.commentNo - 1;
                        if (articleDetail.param.commentNo != 3) {
                            str += '<li class="disabled"><a href="javascript:void(0);">...</a></li>';
                        }
                        str += '<li><a href="javascript:void(0);" onclick="articleDetail.detail.prevPage()">' + prev + '</a></li>';
                    }
                    str += '<li class="active disabled"><a href="javascript:void(0);">' + articleDetail.param.commentNo + '</a></li>';
                    if (articleDetail.param.commentNo != articleDetail.param.totalNo - 1) {
                        var next = articleDetail.param.commentNo + 1;
                        str += '<li><a href="javascript:void(0);" onclick="articleDetail.detail.nextPage()">' + next + '</a></li>';
                        if (articleDetail.param.commentNo != articleDetail.param.totalNo - 2) {
                            str += '<li class="disabled"><a href="javascript:void(0);">...</a></li>';
                        }
                    }
                    str += '<li><a href="javascript:void(0);" onclick="articleDetail.detail.pointPage(' + articleDetail.param.totalNo + ')">' + articleDetail.param.totalNo + '</a></li>'
                        + '<li><a href="javascript:void(0);" onclick="articleDetail.detail.nextPage()">&gt;</a></li>';
                }
            }
            str += '</ul>';
            $('#commentPagination').html(str);
        },
        pointPage: function (num) {
            if (articleDetail.param.commentNo == num)
                return;
            articleDetail.param.commentNo = num;
            articleDetail.detail.initComments();
        },
        prevPage: function () {
            if (articleDetail.param.commentNo == 1)
                return;
            articleDetail.param.commentNo--;
            articleDetail.detail.initComments();
        },
        nextPage: function () {
            if (articleDetail.param.commentNo == articleDetail.param.totalNo)
                return;
            articleDetail.param.commentNo++;
            articleDetail.detail.initComments();
        }
    },
    param: {
        replyId: '',
        commentNo: 1,
        totalNo: 0
    }
}