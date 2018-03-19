<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/tag.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<%@ include file="../common/head.jsp"%>
<link rel="stylesheet" href="${path }/static/plugin/cropper/cropper.min.css">
<style type="text/css">
img {
    max-width: 100%;
}
.preview{
	overflow: hidden;
}
</style>
</head>
<body>
	<%@ include file="../common/top.jsp"%>

	<div class="container normal-container">
		<div class="col-sm-12">
			<div class="panel panel-default">
				<div class="panel-heading">
					<h3 class="panel-title">
						修改头像
					</h3>
				</div>
				<div class="panel-body aboutme-panel-body">
					<div class="visible-xs">对不起，该分辨率下暂不支持头像修改...</div>
					<div class="ma-panel-top">
						<form id="avatarForm" enctype="multipart/form-data" method="post">
							<div class="col-sm-8 col-md-6 hidden-xs"><span class="btn btn-default avatar-select-span">选择图片</span><input type="file" name="avatar" id="avatarSelect"></div>
						</form>
						<div class="col-sm-4 col-md-6 hidden-xs ma-preview-div">预览</div>
					</div>
					<div class="col-sm-8 col-md-6 hidden-xs">
						<div id="imageDiv" class="ma-image-div">
	        				<img id="image" class="full-width-height">
	      				</div>
					</div>
					<div class="col-sm-4 col-md-6 hidden-xs">
						<div class="width-200">
							<div class="preview ma-b-preview"></div>
					    </div>
					    <div class="width-100">
					        <div class="preview ma-m-preview"></div>
					    </div>
					    <div class="width-50">
					        <div class="preview ma-s-preview"></div>
					    </div>
					    <div class="margin-top-20">
					    	<button class="btn btn-primary" type="button"  id="saveAvatar">保存</button>
							<button type="button" class="btn btn-default margin-left-15" id="modifyAvatarCancel">取消</button>
					    </div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<%@ include file="../common/footer.jsp"%>
	<script src="${path }/static/plugin/cropper/cropper.min.js"></script>
	<script src="${path}/static/js/jquery.form.js"></script>
	<script src="${path }/static/js/aboutme/modifyAvatar.js"></script>
	
	<script type="text/javascript">
		var _path = '${path}';
		var _userId = '${wholeId}';
		$(function(){
			$('.nav-about').addClass('active');
			modifyAvatar.init();
		});
		
	</script>
</body>
</html>