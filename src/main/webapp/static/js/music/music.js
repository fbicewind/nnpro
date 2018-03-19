var music = {
    init: function () {
        music.detail.getList();
        music.validate.musicForm();
        music.detail.changeFile();
    },
    detail: {
        getList: function () {
            $.ajax({
                url: _path + '/music/getList',
                type: "GET",
                data: {
                    userId: _userId
                },
                success: function (result) {
                    if (result.length == 0) {
                        $('#musicLrc').html('<h4>当前未添加音乐</h4><a href="#" data-toggle="modal" data-target="#musicModal">点击此处添加音乐</a> ')
                    }
                    for (var i in result) {
                        nightPlayer.sysParam.list.push(result[i]);
                    }
                    nightPlayer.system.initMusicList();
                    nightPlayer.init();
                }
            });
        },
        changeType: function (i) {
            if (i == 0 && $('#musicFile #hiddenUrl').length < 1) {
                var str = '<div class="input-group col-sm-7" style="padding: 0 15px;">'
                    + '<input type="text" class="form-control" name="url" id="url" readonly>'
                    + '<span class="input-group-btn">'
                    + '<button type="button" class="btn btn-default music-choose-buttom" onclick="$(\'#hiddenUrl\').click()">选择文件</button>'
                    + '</span></div><input type="file" id="hiddenUrl" class="hidden" name="urlfile">';
                $('#musicFile').html(str);
            } else if (i == 1 && $('#musicFile #hiddenUrl').length > 0) {
                var str = '<div class="col-sm-7"><input type="text" class="form-control" id="url" name="url"></div>';
                $('#musicFile').html(str);
            }
        },
        changeFile: function () {
            $('#hiddenUrl').change(function () {
                var name = $(this).get(0).files[0].name;
                var typeArr = name.split('.');
                if (typeArr.length < 2 || (typeArr[1] != 'mp3' && typeArr[1] != 'wav')) {
                    $(this).val('');
                    return;
                }
                $('#url').val(name)
            });
            $('#hiddenLrc').change(function () {
                var name = $(this).get(0).files[0].name;
                var typeArr = name.split('.');
                if (typeArr.length < 2 || typeArr[1] != 'lrc') {
                    $(this).val('');
                    return;
                }
                $('#lrcurl').val(name)
            });
            $('#hiddenCover').change(function () {
                var imageArr = ['image/gif', 'image/jpg', 'image/jpeg', 'image/png', 'image/bmp'];
                var file = $(this).get(0).files[0];
                if (imageArr.toString().indexOf(file.type) < 0) {
                    $(this).val('');
                    return;
                }
                $('#cover').val(file.name)
            });
        },
        editMusic: function (index) {
            $('#musicId').val(nightPlayer.sysParam.list[index].id);
            if (nightPlayer.sysParam.list[index].type == '1') {
                $('input[name="type"][value="1"]').click();
                $('#url').val(nightPlayer.sysParam.list[index].url);
            } else {
                $('input[name="type"][value="0"]').click();
                if (nightPlayer.sysParam.list[index].url != null && nightPlayer.sysParam.list[index].url != '') {
                    var urlArr = nightPlayer.sysParam.list[index].url.split('/');
                    var url = urlArr[urlArr.length - 1];
                    $('#url').val(url.substring(url.indexOf('_') + 1));
                }
            }
            $('#title').val(nightPlayer.sysParam.list[index].title);
            $('#singer').val(nightPlayer.sysParam.list[index].singer);
            if (nightPlayer.sysParam.list[index].lrcurl != null && nightPlayer.sysParam.list[index].lrcurl != '') {
                var lrcUrlArr = nightPlayer.sysParam.list[index].lrcurl.split('/');
                var lrcurl = lrcUrlArr[lrcUrlArr.length - 1];
                $('#lrcurl').val(lrcurl.substring(lrcurl.indexOf('_') + 1));
            }
            if (nightPlayer.sysParam.list[index].cover != null && nightPlayer.sysParam.list[index].cover != '') {
                var coverUrlArr = nightPlayer.sysParam.list[index].cover.split('/');
                var coverUrl = coverUrlArr[coverUrlArr.length - 1];
                var cover = coverUrl.substring(coverUrl.indexOf('_') + 1);
                $('#cover').val(cover);
            }
            $('#musicModal').modal('show');
        },
        deleteMusic: function (musicId) {
            $('#_confirmTitle').text('提示');
            $('#_confirmBody').text('确认删除此歌曲？');
            $('#_confirmSure').on('click', function () {
                $.ajax({
                    url: _path + '/musicDo/deleteMusic',
                    type: "POST",
                    data: {
                        musicId: musicId
                    },
                    success: function (result) {
                        if (result.code != '1') {
                            _alert('错误', '发生错误，音乐删除失败!');
                            return;
                        }
                        window.location.reload();
                    }
                });
            });
            $('#_confirmModal').modal('show');
        }
    },
    validate: {
        musicForm: function () {
            $('#musicForm').validate({
                rules: {
                    title: {
                        required: true,
                        maxlength: 40
                    },
                    singer: {
                        required: true,
                        maxlength: 20
                    }
                },
                messages: {
                    title: {
                        required: '请填写歌曲名',
                        maxlength: '曲名不得超过40个字符'
                    },
                    singer: {
                        required: '请填写歌手名',
                        maxlength: '歌手名不得超过20个字符'
                    }
                },
                submitHandler: function () {
                    if ($('#url').val() == '') {
                        _alert('警告', '请上传音乐或填写网络音乐地址!');
                        return;
                    }
                    var musicData = new FormData(document.getElementById("musicForm"));
                    $.ajax({
                        url: _path + '/musicDo/saveMusic',
                        type: "POST",
                        data: musicData,
                        processData: false,
                        contentType: false,
                        success: function (result) {
                            if (result.code != '1') {
                                _alert('错误', '发生错误，保存失败!');
                                return;
                            }
                            window.location.reload();
                        }
                    });
                }
            });
        }
    }
}