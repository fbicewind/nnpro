<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<div class="background-cover-div" id="_noBackgroundCover"></div>
<div class="background-cover-div dark-background" id="_darkBackgroundCover"></div>
<div class="background-progress-div" id="_backTextDiv">
    <span id="_progressText">正在处理中</span>
    <span class="load-dot-1">.</span>
    <span class="load-dot-2">.</span>
    <span class="load-dot-3">.</span>
</div>

<script>
    function _openProgress() {
        $('#_noBackgroundCover').show();
    }

    function _closeProgress() {
        $('#_noBackgroundCover').hide();
    }

    function _openProgressWithText(text) {
        if (text != null && text != ''){
            $('#_progressText').text(text);
        }
        $('#_darkBackgroundCover, #_backTextDiv').show();
    }

    function _closeProgressWithText() {
        $('#_darkBackgroundCover, #_backTextDiv').hide();
    }
</script>