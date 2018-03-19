<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../common/tag.jsp" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <%@ include file="../common/head.jsp" %>
    <link rel="stylesheet" type="text/css" href="${path }/static/css/nightPlayer.css">
    <link rel="stylesheet" type="text/css" href="${path }/static/css/nightPlayerFit.css">
</head>
<body>
<%@ include file="../common/top.jsp" %>

<div class="container music-container">
    <div class="col-sm-12">
        <div class="panel panel-default">
            <div class="panel-heading">
                <h3 class="panel-title nt-panel-title">NIGHT MUSIC</h3>
            </div>
            <div class="panel-body nt-music-panel">
                <div>
                    <div class="music-left-panel">
                        <div class="nt-music-cover">
                            <div class="music-center-cover" id="ntCover"></div>
                        </div>
                        <div class="nt-music-favorable">
                            <span class="music-love-button"><i class="fa fa-heart-o" aria-hidden="true"></i>&nbsp;喜欢</span>
                            <span class="music-love-button"><i class="fa fa-star-o" aria-hidden="true"></i>&nbsp;收藏</span>
                        </div>
                    </div>
                    <div class="music-right-panel">
                        <h4 id="musicTitle"></h4>
                        <div class="music-lrc-panel hidden-xs" id="musicLrc"></div>
                    </div>
                </div>
                <div class="nt-music-operation">
                    <div class="col-sm-3 music-operation-left">
                        <i class="fa fa-step-backward fa-2x" aria-hidden="true" id="ntPrev"></i>
                        <i class="fa fa-spinner fa-2x nt-loading-icon fast-spin" aria-hidden="true" id="loading"></i>
                        <i class="fa fa-play-circle fa-2x nt-operation-icon" aria-hidden="true" id="play"></i>
                        <i class="fa fa-pause-circle fa-2x nt-operation-icon hide-tag" aria-hidden="true" id="pause"></i>
                        <i class="fa fa-step-forward fa-2x" aria-hidden="true" id="ntNext"></i>
                    </div>
                    <div class="col-sm-6 nt-music-center">
                        <span class="music-progress-time" id="startTime">00:00</span>
                        <div class="progress music-progress" id="musicProgress">
                            <div class="progress-bar progress-bar-danger" role="progressbar" id="musicNowProgress" style="width: 0%;">
                                <span class="sr-only"></span>
                            </div>
                        </div>
                        <span class="music-all-time" id="endTime">00:00</span>
                    </div>
                    <div class="col-sm-3 nt-music-center">
                        <div class="music-operation-smodule">
                            <i class="fa fa-volume-up fa-lg" aria-hidden="true" id="volume"></i>
                        </div>
                        <div class="progress volume-progress" id="volumeProgress">
                            <div class="progress-bar progress-bar-danger" role="progressbar" id="volumeNowProgress" style="width: 100%;">
                                <span class="sr-only"></span>
                            </div>
                        </div>
                        <div class="music-operation-module">
                            <i class="fa fa-random fa-lg" aria-hidden="true" title="当前列表循环" id="randomMode"></i>
                        </div>
                        <div class="music-operation-module">
                            <i class="fa fa-list fa-lg" aria-hidden="true" id="toggleList"></i>
                        </div>
                        <div id="ntMusicList" class="nt-music-list">
                            <h5 class="music-list-title">
                                播放列表
                                <span class="right-tag">
                                    <c:if test="${self}">
                                        <i id="addMusic" title="添加音乐" class="fa fa-plus-square-o fa-lg"
                                           data-toggle="modal" data-target="#musicModal" style="margin-right: 20px;"
                                           aria-hidden="true"></i>
                                    </c:if>
                                    <i id="closeList" title="隐藏列表" class="fa fa-times" aria-hidden="true"></i>
                                </span>
                            </h5>
                            <ul id="musicListUl">
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <audio id="ntAudio"></audio>
</div>

<div class="modal fade" id="musicModal" tabindex="-1" role="dialog" aria-labelledby="loginModalLabel"
     aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                <h4 class="modal-title" id="myModalLabel">
                    <span id="modalCompany">上传音乐</span>
                </h4>
            </div>
            <div class="modal-body">
                <form role="form" class="form-horizontal" id="musicForm" enctype="multipart/form-data" method="post">
                    <input type="hidden" id="musicId" name="id">
                    <div class="form-group">
                        <label for="type" class="col-sm-3 control-label">类型<span class="red">&nbsp;*</span></label>
                        <div class="col-sm-7">
                            <label class="radio-inline">
                                <input type="radio" value="0" name="type" checked="checked" onclick="music.detail.changeType(0)">本地上传
                            </label>
                            <label class="radio-inline">
                                <input type="radio" value="1" name="type" onclick="music.detail.changeType(1)">网络音乐
                            </label>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="url" class="col-sm-3 control-label">音乐文件<span class="red">&nbsp;*</span></label>
                        <div id="musicFile">
                            <div class="input-group col-sm-7 music-custom-padding">
                                <input type="text" class="form-control" name="url" id="url" readonly>
                                <span class="input-group-btn">
                                    <button type="button" class="btn btn-default music-choose-buttom" onclick="$('#hiddenUrl').click()">选择文件</button>
                                </span>
                            </div>
                            <input type="file" id="hiddenUrl" class="hidden" name="urlfile">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="title" class="col-sm-3 control-label">歌曲名<span class="red">&nbsp;*</span></label>
                        <div class="col-sm-7">
                            <input type="text" class="form-control" id="title" name="title">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="singer" class="col-sm-3 control-label">歌手<span class="red">&nbsp;*</span></label>
                        <div class="col-sm-7">
                            <input type="text" class="form-control" id="singer" name="singer">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="lrcurl" class="col-sm-3 control-label">歌词lrc文件&nbsp;&nbsp;</label>
                        <div class="input-group col-sm-7 music-custom-padding">
                            <input type="text" class="form-control" name="lrcurl" id="lrcurl" readonly>
                            <span class="input-group-btn">
                                <button type="button" class="btn btn-default music-choose-buttom" onclick="$('#hiddenLrc').click()">选择文件</button>
                            </span>
                        </div>
                        <input type="file" id="hiddenLrc" class="hidden" name="lrcfile">
                    </div>
                    <div class="form-group">
                        <label for="cover" class="col-sm-3 control-label">专辑封面&nbsp;&nbsp;</label>
                        <div class="input-group col-sm-7 music-custom-padding">
                            <input type="text" class="form-control" name="cover" id="cover" readonly>
                            <span class="input-group-btn">
                                <button type="button" class="btn btn-default music-choose-buttom" onclick="$('#hiddenCover').click()">选择文件</button>
                            </span>
                        </div>
                        <input type="file" id="hiddenCover" class="hidden" name="coverfile" accept="image/gif,image/jpg,image/jpeg,image/png,image/bmp">
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" onclick="$('#musicForm').submit();">保存</button>
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
            </div>
        </div>
    </div>
</div>

<%@ include file="../common/footer.jsp" %>
<script src="${path }/static/js/music/nightPlayer.js"></script>
<script src="${path }/static/js/music/music.js"></script>
<script type="text/javascript">
    var _path = '${path}';
    var _userId = '${wholeId}';
    var _isSelf = '${self}' == 'true';
    $(function () {
        $('.nav-music').addClass('active');
        var ntAudio = $('#ntAudio')[0];
        nightPlayer.option = {
            volume: 1,
            autoplay: false,
            random: false
        };
        music.init();
    });
</script>
</body>
</html>