var _experience;

function initShowOrHide() {
    $('#moreSkill').on('click', '.showMore', function () {
        $(this).html('隐藏<i class="fa fa-angle-double-up" aria-hidden="true"></i>');
        $(this).removeClass('showMore').addClass('hideMore');
        $('.resume-hidden-skill').toggle();
    });
    $('#moreSkill').on('click', '.hideMore', function () {
        $(this).html('显示更多<i class="fa fa-angle-double-down" aria-hidden="true"></i>');
        $(this).removeClass('hideMore').addClass('showMore');
        $('.resume-hidden-skill').toggle();
    });
    $('.resume-big-div').on('click', '.resume-div-top1', function () {
        $(this).next().toggle(300);
        $(this).find('.show-skill').removeClass('fa-chevron-down').addClass('fa-chevron-up');
        $(this).removeClass('resume-div-top1').addClass('resume-div-top2');
    });
    $('.resume-big-div').on('click', '.resume-div-top2', function () {
        $(this).next().toggle(300);
        $(this).find('.show-skill').removeClass('fa-chevron-up').addClass('fa-chevron-down');
        $(this).removeClass('resume-div-top2').addClass('resume-div-top1');
    });
}

function initExperience() {
    $.ajax({
        url: _path + '/aboutMe/listExperience',
        dataType: 'json',
        success: function (result) {
            _experience = result;
            var str = '';
            for (var i = 0; i < result.length; i++) {
                var left = 0;
                var year = result[i].startDate.substring(0, 4);
                var month = result[i].startDate.substring(5, 7);
                if (year < 2016) {
                    left = parseInt(month) / 12 * 45 + 5;
                } else {
                    left = parseInt(month) / 12 * 45 + 50;
                }
                str += '<div class="modal-class radius-circle" style="left:' + left + '%;" onclick="showModal(' + i + ')"></div>';
            }
            $('#yearSpan').after(str);
        }
    });
}

function showModal(n) {
    $('#startDate').text(_experience[n].startDate);
    $('#endDate').text(_experience[n].endDate);
    $('#modalCompany').text(_experience[n].company);
    $('#modalProject').text(_experience[n].project);
    var content = '';
    $(_experience[n].workContent.split('|||')).each(function (index, item) {
        content += '<li>' + item + '</li>';
    });
    $('#resumeModalContent').html(content);
    $('#resumeModalTech').html(_experience[n].workTechnology);
    $('#resumeModalComment').html(_experience[n].comment);
    $('#myModal').modal('show');
}