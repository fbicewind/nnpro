var albumDetail = {
    param: {
        percent: 0,
        width: 0,
        height: 0
    },
    init: function () {
        albumDetail.detail.initSize();
        albumDetail.detail.windowResize();
        albumDetail.detail.bindEvent();
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
    },
    detail: {
        initSize: function () {
            var width = $('.album-big-div').width();
            var imgWidth = $('.album-img-div').width() + 2 + 'px';
            var height = width / 5 * 4 + 'px';
            $('.album-big-div').height(height);
        },
        windowResize: function () {
            $(window).resize(function () {
                albumDetail.detail.initSize();
                albumDetail.changeImgSize();
                if (showNow != '' && $('.album-photo-div').is(':visible')) {
                    albumDetail.detail.showFullImage(showNow);
                }
            });
        },
        showFullImage: function (i) {
            albumDetail.param.percent = 0;
            showNow = i;
            var src = $('.photo-' + i).attr('data-src');
            src = '/static/upload/album/l/' + src;
            $('#fullImage').attr('src', src)
            $('.album-photo-div').show();
            $('.album-cover').show();
            var windowWidth = $(window).width();
            var windowHeight = $(window).height();
            var theImage = new Image();
            theImage.src = src;
            albumDetail.imgLoad(theImage, this, function (theImage, img) {
                var imgWidth = theImage.width;
                var imgHeight = theImage.height;
                //$('#fullImage').css({'left':'50%','margin-left': ('-' + imgWidth/2 + 'px'),'top':'50%','margin-top':('-' + (imgHeight/2+10) + 'px')});
                if (imgWidth <= windowWidth && imgHeight <= windowHeight) {
                    albumDetail.param.width = imgWidth;
                    albumDetail.param.height = imgHeight;
                    $('#fullImage').css({
                        'width': (imgWidth + 'px'),
                        'height': (imgHeight + 'px'),
                        'left': '50%',
                        'margin-left': ('-' + imgWidth / 2 + 'px'),
                        'top': '50%',
                        'margin-top': ('-' + (imgHeight / 2) + 'px')
                    });
                } else if (imgWidth <= windowWidth && imgHeight > windowHeight) {
                    albumDetail.param.width = imgWidth * windowHeight / imgHeight;
                    albumDetail.param.height = windowHeight;
                    $('#fullImage').css({
                        'width': (imgWidth * windowHeight / imgHeight + 'px'),
                        'top': '0',
                        'margin-top': '0',
                        'height': (windowHeight + 'px'),
                        'left': '50%',
                        'margin-left': ('-' + (imgWidth * windowHeight / (imgHeight * 2)) + 'px')
                    });
                } else if (imgWidth > windowWidth && imgHeight <= windowHeight) {
                    albumDetail.param.width = windowWidth;
                    albumDetail.param.height = imgHeight * windowHeight / imgWidth;
                    $('#fullImage').css({
                        'height': (imgHeight * windowHeight / imgWidth + 'px'),
                        'left': '0',
                        'margin-left': '0',
                        'width': (windowWidth + 'px'),
                        'top': '50%',
                        'margin-top': ('-' + (windowWidth * imgHeight / (imgWidth * 2)) + 'px')
                    });
                } else if (imgWidth > windowWidth && imgHeight > windowHeight) {
                    if (imgWidth / imgHeight >= windowWidth / windowHeight) {
                        albumDetail.param.width = windowWidth;
                        albumDetail.param.height = imgHeight * windowHeight / imgWidth;
                        $('#fullImage').css({
                            'height': (imgHeight * windowHeight / imgWidth + 'px'),
                            'left': '0',
                            'margin-left': '0',
                            'width': (windowWidth + 'px'),
                            'top': '50%',
                            'margin-top': ('-' + (windowWidth * imgHeight / (imgWidth * 2)) + 'px')
                        });
                    } else {
                        albumDetail.param.width = imgWidth * windowHeight / imgHeight;
                        albumDetail.param.height = windowHeight;
                        $('#fullImage').css({
                            'width': (imgWidth * windowHeight / imgHeight + 'px'),
                            'top': '0',
                            'margin-top': '0',
                            'height': (windowHeight + 'px'),
                            'left': '50%',
                            'margin-left': ('-' + (imgWidth * windowHeight / (imgHeight * 2)) + 'px')
                        });
                    }
                }
                new Drag("fullImage");
            });
        },
        hideFullImage: function () {
            $('.album-photo-div,.album-cover').hide();
        },
        showLeftImage: function () {
            if (showNow == 0) {
                return;
            }
            albumDetail.detail.showFullImage(parseInt(showNow) - 1);
        },
        showRightImage: function () {
            if (showNow == endSize) {
                return;
            }
            albumDetail.detail.showFullImage(parseInt(showNow) + 1);
        },
        showOriginPic: function () {
            albumDetail.param.percent = 0;
            var theImage = new Image();
            theImage.src = $('#fullImage').attr('src');
            albumDetail.imgLoad(theImage, this, function (theImage, img) {
                var imgWidth = theImage.width;
                var imgHeight = theImage.height;
                albumDetail.param.width = imgWidth;
                albumDetail.param.height = imgHeight;
                $('#fullImage').css({
                    'width': (imgWidth + 'px'),
                    'height': (imgHeight + 'px'),
                    'left': '50%',
                    'margin-left': ('-' + imgWidth / 2 + 'px'),
                    'top': '50%',
                    'margin-top': ('-' + imgHeight / 2 + 'px')
                });
                new Drag("fullImage");
            });
        },
        zoom: function (type) {
            var windowWidth = $(window).width();
            var windowHeight = $(window).height();
            var percent = 1;
            if (type == 1) {
                percent = parseInt(percent) + (parseInt(albumDetail.param.percent) + 1) / 10;
                albumDetail.param.percent = parseInt(albumDetail.param.percent) + 1;
            } else if (albumDetail.param.percent > -7) {
                percent = parseInt(percent) + (parseInt(albumDetail.param.percent) - 1) / 10;
                albumDetail.param.percent = parseInt(albumDetail.param.percent) - 1;
            } else {
                return;
            }
            var width = albumDetail.param.width * percent;
            var height = albumDetail.param.height * percent;
            $('#fullImage').css({
                'width': (width + 'px'),
                'height': (height + 'px'),
                'left': '50%',
                'margin-left': ('-' + width / 2 + 'px'),
                'top': '50%',
                'margin-top': ('-' + height / 2 + 'px')
            });
            new Drag("fullImage");
        },
        uploadPhoto: function (i) {
            if (fileArr.length == 0) {
                _alert('确认', '请先选择照片');
                return;
            }
            if (i < fileArr.length) {
                if (typeof(fileArr[i].tag) != 'undefined' && fileArr[i].tag == 1) {
                    i++;
                    albumDetail.detail.uploadPhoto(i);
                } else {
                    var albumData = new FormData();
                    albumData.append("albumId", _albumId);
                    albumData.append("photo", fileArr[i]);
                    $('.photo-to-upload').eq(i).append('<span class="this-uploading">上传中<i class="fa fa-spinner fa-pulse fa-fw margin-bottom"></i></span>');
                    $.when(albumDetail.detail.uploadDetail(albumData)).done(function (data, status) {
                        i++;
                        if (status == 1) {
                            albumData.get('photo').tag = 1;
                            $('.this-uploading').text('已上传').addClass('this-uploaded').removeClass('this-uploading');
                            var photoStr = '<div class="col-md-2 col-sm-3 col-xs-6 album-big-div">'
                                + '<div class="album-img-div">'
                                + '<a class="album-img-a" onclick="albumDetail.detail.showFullImage(' + data.data.index + ')">'
                                + '<img class="album-photo photo-' + data.data.index + '" data-src="' + data.data.photo
                                + '" src="/static/upload/album/s/' + data.data.photo + '">'
                                + '</a></div></div>';
                            $('.album-big-div:last-child').after(photoStr);
                        } else {
                            $('.this-uploading').text('上传失败').addClass('this-uploaded').removeClass('this-uploading');
                        }
                        albumDetail.detail.uploadPhoto(i);
                    });
                }
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
        },
        deleteEvent: function () {
            $('.photo-radio').hide();
            $('.photo-radio,.photo-check').attr('checked', false);
            $('#hideButtonGroup, .photo-check, .photo-check-cover').show();
            delOrCover = '1';
        },
        coverEvent: function () {
            $('.photo-check').hide();
            $('.photo-radio, .photo-check').attr('checked', false);
            $('#hideButtonGroup, .photo-radio, .photo-check-cover').show();
            delOrCover = '2';
        },
        cannelEvent: function () {
            $('#hideButtonGroup, .photo-check, .photo-radio, .photo-check-cover').hide();
            $('.photo-radio, .photo-check').attr('checked', false);
        },
        sureEvent: function () {
            var ids = [];
            if (delOrCover == '1') {
                $('.photo-check[name="photocheck"]:checked').each(function () {
                    ids.push($(this).val());
                });
            } else if (delOrCover == '2') {
                $('input[name="photoradio"]:checked').each(function () {
                    ids.push($(this).val());
                });
            }
            $.ajax({
                url: _path + '/albumDo/operatePhoto',
                type: 'POST',
                data: JSON.stringify({
                    ids: ids,
                    operate: delOrCover
                }),
                headers: {
                    'Content-Type': 'application/json;charset=utf-8'
                },
                dataType: 'json',
                success: function (result) {
                    console.log(result);
                    if (delOrCover == '1' && result.code == '1') {
                        window.location.reload();
                    } else if (delOrCover == '2' && result.code == '1') {
                        _alert('提示', '封面设置成功！');
                        albumDetail.detail.cannelEvent();
                    } else {
                        _alert('错误', '操作发生错误！');
                    }
                }
            });
        },
        bindEvent: function () {
            $(document).on('click', '.photo-radio', function () {
                $('.photo-radio').not(this).attr('checked', false);
            });
        }
    },
    imgLoad: function (theImage, img, callback) {
        var timer = setInterval(function () {
            if (theImage.complete) {
                callback(theImage, img);
                clearInterval(timer);
            }
        }, 20);
    },
    changeImgSize: function () {
        $('.album-big-div img').each(function () {
            var theImage = new Image();
            theImage.src = $(this).attr("src");
            albumDetail.imgLoad(theImage, this, function (theImage, img) {
                var width = theImage.width;
                var height = theImage.height;
                if (height / width < 0.8) {
                    $(img).css('height', '100%');
                } else {
                    $(img).css('width', '100%');
                }
            });
        });
    }
}