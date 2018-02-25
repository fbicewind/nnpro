function _logout() {
    $.ajax({
        type : 'POST',
        url : _path + '/logout',
        dataType : 'json',
        success : function(result) {
            if (result.code == '1') {
                window.location.reload();
            } else {
                _alert('错误', '退出失败!')
            }
        }
    });
}

