var album = {
    init: function () {
        album.validate.albumValidate();
        album.detail.bindClickEvent();
        imgFiles.onchange = function () {
            var file = this.files;
            var str = '';
            var illegalFlag = false;
            for (var i = 0; i < file.length; i++) {
                if (fileFmtArr.toString().indexOf(file[i].type) > -1) {
                    str += '<div class="photo-parent photo-to-upload"><img class="photo-cover" src="'
                        + window.URL.createObjectURL(file[i])
                        + '"></div>';
                    fileArr.push(file[i]);
                } else {
                    illegalFlag = true;
                }
            }
            $('#addTarget').before(str);
            if (illegalFlag) {
                _alert('提示', '目前仅支持上传gif、jpg、png、bmp格式的图片');
            }
        }
        $('#createAlbumModal').on('hidden.bs.modal', function () {
            $('#albumId').val('');
            $('#albumName').val('');
            $('#albumDesc').val('');
            $('#publicYes').click();
        });
    },
    detail: {
        addAlbum: function () {
            $('#albumForm').submit();
        },
        toUploadPhotos: function () {
            if (toAlbumId == null || toAlbumId == '') {
                _confirm('提示', '尚未创建相册，是否新建相册？', album.detail.createNewAlbum);
                return;
            }
            album.detail.removeHistory();
            $('#uploadPhotoModal').modal('show');
        },
        uploadPhoto: function (i) {
            if (fileArr.length == 0) {
                _alert('确认', '请先选择照片');
                return;
            }
            if (i < fileArr.length) {
                if(typeof(fileArr[i].tag) != 'undefined' && fileArr[i].tag == 1) {
                    i++;
                    album.detail.uploadPhoto(i);
                }else {
                    var albumData = new FormData();
                    albumData.append("albumId", toAlbumId);
                    albumData.append("photo", fileArr[i]);
                    $('.photo-to-upload').eq(i).append('<span class="this-uploading">上传中<i class="fa fa-spinner fa-pulse fa-fw margin-bottom"></i></span>');
                    $.when(album.detail.uploadDetail(albumData)).done(function (data, status) {
                        i++;
                        if (status == 1) {
                            albumData.get('photo').tag = 1;
                            $('.this-uploading').text('已上传').addClass('this-uploaded').removeClass('this-uploading');
                        } else {
                            $('.this-uploading').text('上传失败').addClass('this-uploaded').removeClass('this-uploading');
                        }
                        album.detail.uploadPhoto(i);
                    });
                }
            } else {//全部上传完成，重新计算数量
                $.ajax({
                    url: _path + '/album/getAlbum',
                    type: 'POST',
                    data: {
                        albumId: toAlbumId
                    },
                    dataType: 'json',
                    success: function (result) {
                        $('.photo-count-' + toAlbumId).text(result.photoCount);
                    }
                });
            }

        },
        uploadDetail: function (albumData) {
            var defer = $.Deferred();
            $.ajax({
                url: _path + '/albumDo/uploadPhoto',
                type: 'POST',
                data: albumData,
                processData: false,
                contentType: false,
                dataType: 'json',
                success: function (result) {
                    defer.resolve(result, 1);
                },
                error: function () {
                    defer.resolve(result, 0);
                }
            });
            return defer.promise();
        },
        removeHistory: function () {
            fileArr = [];
            $('#addTarget').prevAll().remove();
            $('.to-show-select').hide();
        },
        createNewAlbum: function () {
            $('#_confirmModal').modal('hide');
            $('#createAlbumModal').modal('show');
        },
        bindClickEvent: function () {
            $('.to-show-exist').click(function () {
                $('.to-show-select').show();
            });
            $('.to-select-top').click(function () {
                $('.to-show-select').hide();
            });
            $('.to-select-ul>li').click(function () {
                var newSrc = $(this).find('img').attr('src');
                var newTitle = $(this).find('.ablum-thumb-title').text();
                toAlbumId = $(this).attr('data-id');
                $('.to-show-exist>img,.to-select-top>img').attr('src', newSrc);
                $('.to-show-exist>.ablum-thumb-title,.to-select-top>.ablum-thumb-title').text(newTitle);
                $('.to-show-select').hide();
            });
            $('#uploadPhotoForm').on('click', '.photo-to-upload', function () {
                var i = $('.photo-to-upload').index(this);
                fileArr.splice(i, 1);
                $(this).remove();
            });
            if (_isSelf) {
                $('.album-image-box').hover(function () {
                    $(this).find('.image-operation-span').show();
                }, function () {
                    $(this).find('.image-operation-span').hide();
                });
                $('.image-operation-span').hover(function () {
                    $(this).next().show();
                }, function () {
                    $(this).next().hide();
                });
                $('.image-operation-ul').hover(function () {
                    $(this).show();
                }, function () {
                    $(this).hide();
                });
            }
        },
        editAlbum: function (id) {
            $.ajax({
                url: _path + '/album/getAlbum',
                type: 'POST',
                data: {
                    albumId: id
                },
                dataType: 'json',
                success: function (result) {
                    $('#albumName').val(result.albumName);
                    $('#albumDesc').val(result.albumDesc);
                    $('#albumId').val(result.id);
                    if (result.publicFlag == 'Y') {
                        $('#publicYes').click();
                    } else {
                        $('#publicNo').click();
                    }
                    $('#createAlbumModal').modal('show');
                }
            });
        },
        delAlbum: function (id) {
            delId = id;
            _confirm('提示', '删除相册后所有照片将不可恢复，是否确认删除？', album.detail.deleteSure);
        },
        deleteSure: function () {
            $.ajax({
                url: _path + '/albumDo/delAlbum',
                type: 'POST',
                data: {
                    albumId: delId
                },
                dataType: 'json',
                success: function (result) {
                    if (result.code == '1') {
                        window.location.reload();
                    } else {
                        $('#_confirmModal').modal('hide');
                        _alert('错误', '删除失败！');
                    }
                }
            });
        }
    },
    validate: {
        albumValidate: function () {
            $('#albumForm').validate({
                rules: {
                    albumName: {
                        required: true,
                        minlength: 1,
                        maxlength: 10,
                        remote: {
                            url: _path + "/validate/albumName",
                            type: "get",
                            data: {
                                albumName: function () {
                                    return $('#albumName').val();
                                },
                                albumId: function () {
                                    return $('#albumId').val();
                                }
                            },
                            dataType: 'json',
                            dataFilter: function (data) {
                                data = eval('(' + data + ')');
                                return data.code == '1';
                            }
                        }
                    },
                    albumDesc: {
                        required: true,
                        minlength: 1,
                        maxlength: 100
                    }
                },
                messages: {
                    albumName: {
                        required: '请填写相册名称',
                        minlength: '相册名称不能少于一个字符',
                        maxlength: '相册名称不能多余十个字符',
                        remote: '相册名称已存在'
                    },
                    albumDesc: {
                        required: '请填写相册描述',
                        minlength: '相册描述不能少于一个字符',
                        maxlength: '相册描述不能多于一百个字符'
                    }
                },
                submitHandler: function () {
                    $.ajax({
                        type: 'POST',
                        url: _path + '/albumDo/saveAlbum',
                        data: $('#albumForm').serialize(),
                        dataType: 'json',
                        success: function (result) {
                            if (result.code == '1') {
                                window.location.reload();
                            } else {
                                _alert('确认', '创建失败！');
                            }
                        }
                    });
                }
            });
        }
    }
}
