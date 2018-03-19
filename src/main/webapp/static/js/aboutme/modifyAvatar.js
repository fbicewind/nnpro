var $image = null;
var saveUrl = '';
var modifyAvatar = {
	init : function() {
		modifyAvatar.detail.initCropper();
		modifyAvatar.detail.selectAvatar();
		modifyAvatar.detail.saveAvatar();
        $('#modifyAvatarCancel').click(function () {
            window.history.go(-1);
        });
	},
	detail : {
		selectAvatar : function() {
			$('#avatarSelect').change(function() {
				if ($('#avatarSelect').val() != '') {
					var option = {
						url : _path + '/aboutMeDo/uploadTempAvatar',
				        　　  	type : 'POST',
				         　　	dataType : 'json',
				         　　	headers : {"ClientCallMode" : "ajax"},
				        　　 	success : function(result) {
				        　　 		if(result.code == 1){
				        　　 			$('#image').attr('src', result.data);
				        　　 			modifyAvatar.detail.initCropper();
				        　　 			saveUrl = result.data;
				        　　 		}else{
				        　　 			_alert('错误', '发生错误！');
				        　　 		}
				        }
				     };
					$('#avatarForm').ajaxSubmit(option);
				}
			});
		},
		initCropper : function(){
			if($image != null){
				$image.cropper('destroy');
			}
			$image = $('#image').cropper({
		        aspectRatio: 1,
		        viewMode: 1,
		        preview:'.preview',
		        zoomable: false,
		        ready: function () {
		        	croppable = true;
		        }
		    });
		},
		saveAvatar : function(){
			$('#saveAvatar').click(function(){
				if(saveUrl == ''){
					_alert('提示', '请选择图片');
					return;
				}
				var params = {
					url : saveUrl, 
					bigBox : $('#image').cropper('getCanvasData'), 
					selectedAvatar : $('#image').cropper('getCropBoxData')
				};
				$.ajax({
					type : 'POST',
					url : _path + '/aboutMeDo/saveAvatar',
					data : JSON.stringify(params),
					dataType : 'json',
					headers : {'Content-Type' : 'application/json;charset=utf-8'},
					success : function(result){
						if(result.code == '1'){
							window.location.href = _path + '/aboutMe/' + _userId;
						}else{
							_alert('提示', '头像修改失败！');
						}
					}
				});
			});
		}
	}
}