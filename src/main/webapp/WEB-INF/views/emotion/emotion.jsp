<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../common/tag.jsp" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <%@ include file="../common/head.jsp" %>
</head>
<body>
<%@ include file="../common/top.jsp" %>
<div class="container emotion-container">
    <div class="col-sm-8 col-xs-12">
        <c:if test="${self}">
            <div class="emotion-textarea-div">
                <div id="emotionContent" contenteditable="true" style="height: 140px;padding: 10px;"></div>
                <div>
                    <i class="fa fa-smile-o fa-lg" aria-hidden="true" id="emotionIcon"></i>
                    <div id="emojiDiv" style="position: absolute;border: 1px solid #ddd;background: #fff;display: none;width: 100%;">
                        <div id="myTabContent" class="tab-content" style="height: 160px;width: 100%;overflow-y: scroll;">
                            <div class="tab-pane fade in active" id="defaultTab"></div>
                            <div class="tab-pane fade" id="moodTab"></div>
                            <div class="tab-pane fade" id="weatherTab"></div>
                            <div class="tab-pane fade" id="foxTab"></div>
                        </div>
                        <ul id="myTab" class="nav nav-tabs" style="margin-bottom: 0;">
                            <li class="active"><a href="#defaultTab" data-toggle="tab">默认</a></li>
                            <li><a href="#moodTab" data-toggle="tab">心情</a></li>
                            <li><a href="#weatherTab" data-toggle="tab">天气</a></li>
                            <li><a href="#foxTab" data-toggle="tab">阿狸</a></li>
                        </ul>
                    </div>
                    <button type="button" id="emotionButton" class="btn btn-default pull-right"
                            onclick="emotion.detail.saveEmotion(1)">发&nbsp;&nbsp;送
                    </button>
                    <button type="button" id="goalButton" class="btn btn-default pull-right"
                            onclick="emotion.detail.saveEmotion(2)">GOAL
                    </button>
                </div>
            </div>
        </c:if>
        <div id="emotions"></div>
    </div>
    <div class="col-sm-4 col-xs-12 emotion-right-panel">
        <div style="background: #fff;overflow: hidden;padding:15px 0;">
            <div class="emotion-personal-info">
                <div class="col-md-4 col-sm-12">
                    <img src="/static/upload/img/m/${user.avatar }" class="full-width">
                </div>
                <div class="col-md-8 col-sm-12">
                    <ul>
                        <li><span style="display: inline-block;width: 42px;">昵&nbsp;&nbsp;称</span>：${user.nickname }</li>
                        <li><span style="display: inline-block;width: 42px;">GOAL</span>：<span id="goalState">未设置</span></li>
                    </ul>
                </div>
            </div>
        </div>
        <div id="goalDetials">
        </div>
    </div>
</div>

<%@ include file="../common/footer.jsp" %>
<script src="${path }/static/js/emotion/emotion.js"></script>
<script type="text/javascript">
    var _path = '${path}';
    var _userId = '${wholeId}';
    var _isSelf = '${self}' == 'true';
    $(function () {
        $('.nav-mood').addClass('active');
        emotion.init();
    });
</script>
</body>
</html>