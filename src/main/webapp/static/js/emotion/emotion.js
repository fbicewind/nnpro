// 定义最后光标对象
var lastEditRange;
var emotion = {
    init: function () {
        emotion.detail.initEmoji();
        emotion.detail.clickEdit();
        emotion.detail.keyUpEdit();
        emotion.detail.chooseEmoji();
        emotion.detail.toggleEmoji();
        emotion.detail.initEmotionContent();
        emotion.detail.initGoalOperation();
        emotion.detail.showMoreEmotion();
        emotion.detail.goalBindOperation();
    },
    detail: {
        initEmoji: function () {
            $.ajax({
                url: '/static/upload/emoji.json',
                headers: {
                    contentType: "application/x-www-form-urlencoded"
                },
                success: function (data) {
                    var defaultStr = '', moodStr = '', weatherStr = '', foxStr = '';
                    $.each(data, function (index, item) {
                        if (item.category == null || item.category == '') {
                            defaultStr += '<img class="emotion-icon" src="' + item.url + '" title=' + item.phrase + '>';
                        } else if (item.category == '心情') {
                            moodStr += '<img class="emotion-icon" src="' + item.url + '" title=' + item.phrase + '>';
                        } else if (item.category == '天气') {
                            weatherStr += '<img class="emotion-icon" src="' + item.url + '" title=' + item.phrase + '>';
                        } else if (item.category == '阿狸') {
                            foxStr += '<img class="emotion-icon" src="' + item.url + '" title=' + item.phrase + '>';
                        }
                    });
                    $('#defaultTab').html(defaultStr);
                    $('#moodTab').html(moodStr);
                    $('#weatherTab').html(weatherStr);
                    $('#foxTab').html(foxStr);
                }
            });
        },
        clickEdit: function () {	// 编辑框点击事件
            $('#emotionContent').click(function () {
                // 获取选定对象
                var selection = getSelection();
                // 设置最后光标对象
                lastEditRange = selection.getRangeAt(0);
            });
        },
        keyUpEdit: function () {
            $('#emotionContent').keyup(function () {
                // 获取选定对象
                var selection = getSelection();
                // 设置最后光标对象
                lastEditRange = selection.getRangeAt(0);
            });
        },
        chooseEmoji: function () {
            $(document).on('click', '.emotion-icon', function () {
                // 获取编辑框对象
                var edit = document.getElementById('emotionContent');
                // 编辑框设置焦点
                edit.focus();
                // 获取选定对象
                var selection = getSelection();
                // 判断是否有最后光标对象存在
                if (lastEditRange) {
                    // 存在最后光标对象，选定对象清除所有光标并添加最后光标还原之前的状态
                    selection.removeAllRanges();
                    selection.addRange(lastEditRange);
                }
                // 创建img元素
                var img = document.createElement('img');
                img.src = $(this).attr('src');
                // 判断选定对象范围是编辑框还是文本节点
                if (selection.anchorNode.nodeName != '#text') {
                    // 如果是编辑框范围。则创建表情文本节点进行插入
                    if (edit.childNodes.length > 0) {
                        // 是否是最后一个元素
                        var isLast = true;
                        // 如果文本框的子元素大于0，则表示有其他元素，则按照位置插入表情节点
                        for (var i = 0; i < edit.childNodes.length; i++) {
                            if (i == selection.anchorOffset) {
                                edit.insertBefore(img, edit.childNodes[i]);
                                isLast = false;
                                break;
                            }
                        }
                        // 如果是最后一个元素，则直接在结尾插入
                        if (isLast) {
                            edit.appendChild(img);
                        }
                    } else {
                        // 否则直接插入一个表情元素
                        edit.appendChild(img);
                    }
                    // 创建新的光标对象
                    var range = document.createRange();
                    // 光标对象的范围界定为新建的表情节点
                    range.selectNodeContents(img);
                    range.setStartAfter(img);
                    // 使光标开始和光标结束重叠
                    range.collapse(true);
                    // 清除选定对象的所有光标对象
                    selection.removeAllRanges();
                    // 插入新的光标对象
                    selection.addRange(range)
                } else {
                    // 如果是文本节点则先获取光标对象
                    var range = selection.getRangeAt(0);
                    // 插入表情
                    range.insertNode(img);
                    // 光标位置后移
                    range.setStartAfter(img);
                    // 光标开始和光标结束重叠
                    range.collapse(true);
                    // 清除选定对象的所有光标对象
                    selection.removeAllRanges();
                    // 插入新的光标对象
                    selection.addRange(range);
                }
                // 无论如何都要记录最后光标对象
                lastEditRange = selection.getRangeAt(0);
                $('#emojiDiv').hide();
            });
        },
        toggleEmoji: function () {
            $('#emotionIcon').click(function (event) {
                $('#emojiDiv').toggle();
                event.stopPropagation();
            });
            $(document).click(function (event) {
                var target = $(event.target);
                if (target.closest("#emojiDiv").length != 0) return;
                $('#emojiDiv').hide();
            });
        },
        saveEmotion: function (type) {
            var text = $('#emotionContent').html();
            if (text == null || $.trim(text.replace(new RegExp('&nbsp;', 'gm'), '')) == '') {
                alert('发送内容不能为空！');
                return;
            }
            $.ajax({
                url: _path + '/emotionDo/saveEmotion',
                type: 'POST',
                dataType: 'json',
                data: {
                    text: text,
                    type: type
                },
                success: function (result) {
                    if (result.code != '1') {
                        _alert('错误', '保存失败');
                        return;
                    }
                    window.location.reload();
                }
            });
        },
        initEmotionContent: function () {
            $.ajax({
                url: _path + '/emotion/getEmotions',
                type: 'GET',
                dataType: 'json',
                data: {
                    userId: _userId,
                    pageNo: emotion.param.pageNo
                },
                success: function (data) {
                    var str = '';
                    $.each(data.datalist, function (index, item) {
                        str += '<div class="emotion-content-div"><p>'
                            + item.emotion + '</p><div><span class="goal-start">'
                            + item.createTime + '</span></div></div>';
                    });
                    if (data.pageNo < data.totalNo) {
                        str += '<div class="show-more-emotion"><span id="showMoreEmotion"><i class="fa' +
                            ' fa-angle-double-down"' +
                            ' aria-hidden="true"></i>' +
                            ' more</span></div>';
                        emotion.param.isLast = false;
                    }
                    if (data.totalSize == 0) {
                        if (_isSelf) {
                            str = '<div class="emotion-content-div"><p>什么都没有，要不要发表一个小心情呢~</p></div>';
                        } else {
                            str = '<div class="emotion-content-div"><p>主人还没有发表小心情呢~</p></div>';
                        }
                    }
                    $('#emotions').html(str);
                }
            });
            $.ajax({
                url: _path + '/emotion/getGoals',
                type: 'GET',
                dataType: 'json',
                data: {
                    userId: _userId
                },
                success: function (data) {
                    var inprogressStr = '';
                    var completedStr = '';
                    var failuredStr = '';
                    var inprogressCount = 0;
                    var completedCount = 0;
                    var failuredCount = 0;
                    $.each(data, function (index, item) {
                        if (item.status == '0') {
                            inprogressCount++;
                            if (inprogressStr == '') {
                                inprogressStr = '<div class="goal-detail-block"' +
                                    ' id="inProgressGoal"><h5>进行中<span id="progressCount"></span></h5><ul>';
                            }
                            if (_isSelf) {
                                inprogressStr += '<li><p>' + item.goal + '</p><span class="goal-start">' + item.startTime
                                    + '开始</span><div class="goal-operation" data-id="' + item.id + '">'
                                    + '<span class="text-success goal-to-success">成功</span>'
                                    + '<span class="text-warning goal-to-failure">失败</span></div></li>';
                            } else {
                                inprogressStr += '<li><p>' + item.goal + '</p><span class="goal-start">' + item.startTime
                                    + '开始</span></li>';
                            }
                        } else if (item.status == '1') {
                            completedCount++;
                            if (completedStr == '') {
                                completedStr = '<div class="goal-detail-block"><h5>完成<span id="completeCount"></span></h5><ul>';
                            }
                            completedStr += '<li><p>' + item.goal + '</p><span class="goal-start">'
                                + item.startTime + '开始</span><span class="goal-end">'
                                + item.endTime + '成功</span></li>';
                        } else {
                            failuredCount++;
                            if (failuredStr == '') {
                                failuredStr = '<div class="goal-detail-block"><h5>失败<span id="failureCount"></span></h5><ul>';
                            }
                            failuredStr += '<li><p>' + item.goal + '</p><span class="goal-start">'
                                + item.startTime + '开始</span><span class="goal-end">'
                                + item.endTime + '结束</span></li>';
                        }
                    });
                    if (data.length > 0) {
                        emotion.detail.setState(data.length, inprogressCount, completedCount, failuredCount);
                    }
                    var detailStr = '';
                    if (inprogressStr != '') {
                        inprogressStr += '</ul></div>';
                        detailStr += inprogressStr;
                    }
                    if (completedStr != '') {
                        completedStr += '</ul></div>'
                        detailStr += completedStr;
                    }
                    if (failuredStr != '') {
                        failuredStr += '</ul></div>'
                        detailStr += failuredStr;
                    }
                    if (detailStr == '') {
                        if (_isSelf) {
                            detailStr = '<div class="goal-detail-block">No Goal! 设置一个吧~</div>';
                        } else {
                            detailStr = '<div class="goal-detail-block">主人还没有设置Goal呢~</div>';
                        }
                    }
                    $('#goalDetials').html(detailStr);
                    if (inprogressCount != 0) {
                        $('#progressCount').text('(' + inprogressCount + ')');
                    }
                    if (completedCount != 0) {
                        $('#completeCount').text('(' + completedCount + ')');
                    }
                    if (failuredCount != 0) {
                        $('#failureCount').text('(' + failuredCount + ')');
                    }
                }
            });
        },
        initGoalOperation: function () {
            $(document).on('mouseover', '#inProgressGoal li', function () {
                $(this).find('.goal-operation').show();
            }).on('mouseout', '#inProgressGoal li', function () {
                $(this).find('.goal-operation').hide();
            });
        },
        showMoreEmotion: function () {
            $(document).on('click', '#showMoreEmotion', function () {
                emotion.param.pageNo++;
                $.ajax({
                    url: _path + '/emotion/getEmotions',
                    type: 'GET',
                    dataType: 'json',
                    data: {
                        userId: _userId,
                        pageNo: emotion.param.pageNo
                    },
                    success: function (data) {
                        var str = '';
                        $.each(data.datalist, function (index, item) {
                            str += '<div class="emotion-content-div"><p>'
                                + item.emotion + '</p><div><span class="goal-start">'
                                + item.createTime + '</span></div></div>';
                        });
                        $('.show-more-emotion').before(str);
                        if (data.pageNo >= data.totalNo) {
                            emotion.param.isLast = true;
                            $('.show-more-emotion').hide();
                        }
                    }
                });
            });
        },
        goalBindOperation: function () {
            $(document).on('click', '.goal-to-success', function () {
                var id = $(this).parent().attr('data-id');
                $.ajax({
                    url: _path + '/emotionDo/updateGoal',
                    type: 'POST',
                    dataType: 'json',
                    data: {
                        id: id,
                        type: '1'
                    },
                    success: function (data) {
                        if (data.code = '1') {
                            _alert('提示', '操作成功');
                            window.location.reload();
                        } else {
                            _alert('错误', '操作失败');
                        }
                    }
                });
            });
            $(document).on('click', '.goal-to-failure', function () {
                var id = $(this).parent().attr('data-id');
                $.ajax({
                    url: _path + '/emotionDo/updateGoal',
                    type: 'POST',
                    dataType: 'json',
                    data: {
                        id: id,
                        type: '2'
                    },
                    success: function (data) {
                        if (data.code = '1') {
                            _alert('提示', '操作成功');
                            window.location.reload();
                        } else {
                            _alert('错误', '操作失败');
                        }
                    }
                });
            });
        },
        setState: function (a, p, c, f) {
            if (a < 5) {
                $('#goalState').text('初定目标');
                return;
            }
            if (f / a >= 0.5) {
                $('#goalState').text('LOSER？？？');
                return;
            }
            if (c / a >= 0.5) {
                $('#goalState').text('终结者');
                return;
            }
            $('#goalState').text('任务满满');
        }
    },
    param: {
        pageNo: 1,
        isLast: true
    }
}