var index = {
    init: function () {
        index.detail.dot();
        index.detail.initSize();
        index.detail.windowResize();
        index.detail.initMusic();
        index.detail.playMusic();
        index.detail.pauseMusic();
        index.detail.nextMusic();
        index.detail.muteMusic();

        ntAudio.addEventListener('ended', function () {
            $('#ntMusicPlay,#ntMusicPause').hide();
            $('#ntMusicNext').click();
        });
        ntAudio.addEventListener('error', function () {
            $('#ntMusicTitle>h5').text('歌曲资源加载失败！');
        });
    },
    detail: {
        initSize: function () {
            var width = $('.index-image-outer').width();
            var height = width / 5 * 3 + 'px';
            $('.index-image-outer').height(height);
        },
        windowResize: function () {
            $(window).resize(function () {
                index.detail.initSize();
                index.changeImgSize();
            });
        },
        dot: function () {
            $('.article-li p').dotdotdot();
        },
        initMusic: function () {
            $.ajax({
                url: _path + '/music/getList',
                type: "GET",
                data: {
                    userId: _userId
                },
                success: function (result) {
                    if (result.length > 0) {
                        for (var i in result) {
                            index.musicParam.list.push(result[i]);
                        }
                        index.detail.loadMusic(0);
                    } else {
                        $('#ntMusicTitle>h5').text('无可播放音乐！');
                    }
                }
            });
            $('#ntMusicRight').hover(function () {
                $('#ntMusicOper').show().css({'display': '-webkit-flex'});
            }, function () {
                $('#ntMusicOper').hide();
            });
        },
        playMusic: function () {
            $('#ntMusicPlay').click(function () {
                $(this).hide();
                $('#ntMusicPause').show();
                ntAudio.play();
            });
        },
        pauseMusic: function () {
            $('#ntMusicPause').click(function () {
                $(this).hide();
                $('#ntMusicPlay').show();
                ntAudio.pause();
            });
        },
        nextMusic: function () {
            $('#ntMusicNext').click(function () {
                index.detail.loadMusic(index.musicParam.next);
                $('#ntMusicPlay').click();
            });
        },
        muteMusic: function () {
            $('#ntMusicMute').click(function () {
                if ($(this).hasClass('fa-volume-off')) {
                    $(this).removeClass('fa-volume-off').addClass('fa-volume-up');
                    ntAudio.muted = false;
                } else {
                    $(this).removeClass('fa-volume-up').addClass('fa-volume-off');
                    ntAudio.muted = true;
                }
            });
        },
        loadMusic: function (i) {
            index.musicParam.playIndex = i;
            index.musicParam.next = index.detail.getNextRandom();
            $('#ntMusicTitle>h5').text(index.musicParam.list[i].title + ' - ' + index.musicParam.list[i].singer);
            var bgImage = 'url(' + index.musicParam.list[i].cover + ')';
            $('#ntMusicCover').css('background-image', bgImage);
            ntAudio.src = index.musicParam.list[i].url;
            ntAudio.load();
        },
        getNextRandom: function () {
            var next = Math.floor(Math.random() * index.musicParam.list.length);
            while (next == index.musicParam.playIndex && index.musicParam.list.length > 1) {
                next = Math.floor(Math.random() * index.musicParam.list.length);
            }
            return next;
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
        $('.index-image-outer>img').each(function () {
            var theImage = new Image();
            theImage.src = $(this).attr("src");
            index.imgLoad(theImage, this, function (theImage, img) {
                var width = theImage.width;
                var height = theImage.height;
                if (height / width > 0.6) {
                    $(img).css('height', '100%');
                } else {
                    $(img).css('width', '100%');
                    var h = $(img).parent().height();
                    var th = $(img).height();
                    var top = (h - th) / 2 + 'px';
                    $(img).css('margin-top', top);
                }
            });
        });
    },
    musicParam: {
        list: [],
        playIndex: 0,
        next: 0
    }
}