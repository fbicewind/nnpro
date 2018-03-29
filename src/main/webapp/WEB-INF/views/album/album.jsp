<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../common/tag.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<%@ include file="../common/head.jsp"%>
</head>
<body>
	<%@ include file="../common/top.jsp"%>

	<div class="container">
		<div class="album-container">
			<c:if test="${self }">
				<div class="album-button-div">
					<button class="btn btn-default" style="margin-right: 20px;" data-toggle="modal" onclick="album.detail.toUploadPhotos()">上传照片</button>
					<button class="btn btn-default" data-toggle="modal" data-target="#createAlbumModal">创建相册</button>
				</div>
			</c:if>
			<div style="overflow: hidden;">
				<c:forEach var="album" items="${albumList }">
					<div class="col-xs-12 col-sm-4 col-md-3 album-box">
						<div class="album-s-box">
							<div class="album-image-box">
								<c:if test="${self }">
									<span class="image-operation-span">
										<i class="fa fa-chevron-down" aria-hidden="true"></i>
									</span>
									<ul class="image-operation-ul">
										<li onclick="album.detail.editAlbum(${album.id})"><i class="fa fa-pencil-square-o" aria-hidden="true"></i>&nbsp;&nbsp;编辑</li>
										<li onclick="album.detail.delAlbum(${album.id})" <i class="fa fa-trash-o" aria-hidden="true"></i>&nbsp;&nbsp;&nbsp;删除</li>
									</ul>
								</c:if>
								<a href="${path }/album/detail/${album.id}"><img src="/static/upload/album/s/${album.coverImg }" class="full-width"></a>
								<h3 class="album-photo-count photo-count-${album.id }">${album.photoCount }</h3>
							</div>
							<a href="#">${album.albumName }</a>
						</div>
					</div>
				</c:forEach>
			</div>
		</div>
	</div>

	<!-- 模态框（Modal） -->
	<div class="modal fade" id="uploadPhotoModal" tabindex="-1" role="dialog"
		aria-labelledby="uploadTitle" aria-hidden="true" data-backdrop="static" data-keyboard="false">
		<div class="modal-dialog" id="uploadPhotoDialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true" onclick="album.detail.removeHistory()">×</button>
					<h4 class="modal-title" id="uploadTitle">
						<span>上传照片</span>
					</h4>
				</div>
				<div class="modal-body">
					<div class="to-big-frame">
						<span class="to-show-description">上传到</span>
						<div class="to-show-exist">
							<img src="/static/upload/img/s/${albumList[0].coverImg }" class="ablum-thumb-cover">
							<span class="ablum-thumb-title">${albumList[0].albumName }</span>
							<span class="glyphicon glyphicon-chevron-down"></span>
						</div>
						<div class="to-show-select">
							<div class="to-select-top">
								<img src="/static/upload/img/s/${albumList[0].coverImg }" class="ablum-thumb-cover">
								<span class="ablum-thumb-title">${albumList[0].albumName }</span>
								<span class="glyphicon glyphicon-chevron-down"></span>
							</div>
							<ul class="to-select-ul">
								<c:forEach var="album" items="${albumList }">
									<li data-id="${album.id }">
										<img src="/static/upload/img/s/${album.coverImg }" class="ablum-thumb-cover">
										<span class="ablum-thumb-title">${album.albumName }</span>
										<c:if test="${album.publicFlag == 'N' }">
											<span class="glyphicon glyphicon-lock" title="仅自己可见"></span>
										</c:if>
									</li>
								</c:forEach>
							</ul>
							<div class="to-select-bottom" data-toggle="modal" data-target="#createAlbumModal">
								<span class="glyphicon glyphicon-camera"></span>
								<span>创建相册</span>
							</div>
						</div>
					</div>
					<form role="form" class="form-horizontal" id="uploadPhotoForm">
						<div class="photo-parent" id="addTarget">
							<img src="${path }/static/image/add.png" class="photo-cover">
							<input id="imgFiles" class="photo-file" type="file" name="img" multiple="multiple" accept="image/gif,image/jpg,image/jpeg,image/png,image/bmp"/>
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-primary" onclick="album.detail.uploadPhoto(0)">开始上传</button>
					<button type="button" class="btn btn-default" data-dismiss="modal" onclick="album.detail.removeHistory()">取消</button>
				</div>
			</div>
		</div>
	</div>
	
	<div class="modal fade" id="createAlbumModal" tabindex="-1" role="dialog"
		aria-labelledby="createAlbumTitle" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">×</button>
					<h4 class="modal-title" id="createAlbumTitle">
						<span>创建相册</span>
					</h4>
				</div>
				<div class="modal-body">
					<form role="form" class="form-horizontal" id="albumForm">
						<input type="hidden" name="id" id="albumId">
	            		<div class="form-group">
							<label for="typeName" class="col-sm-3 control-label">相册名称：</label>
							<div class="col-sm-7">
								<input type="text" class="form-control" name="albumName" id="albumName">
							</div>
						</div>
	            		<div class="form-group">
							<label for="typeName" class="col-sm-3 control-label">相册描述：</label>
							<div class="col-sm-7">
								<textarea class="form-control" rows="3" name="albumDesc" id="albumDesc"></textarea>
							</div>
						</div>
						<div class="form-group">
						<label for="publicFlag" class="col-sm-3 control-label">是否公开：</label>
							<div class="col-sm-7">
								<label class="radio-inline">
								    <input type="radio" value="Y" name="publicFlag" checked="checked" id="publicYes">是
								</label>
								<label class="radio-inline">
								    <input type="radio" value="N" name="publicFlag" id="publicNo">否
								</label>
							</div>
						</div>
	            	</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-primary" onclick="album.detail.addAlbum()">确定</button>
					<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
				</div>
			</div>
		</div>
	</div>

	<%@ include file="../common/footer.jsp"%>
	<script src="${path }/static/js/album/album.js"></script>
	<script type="text/javascript">
		var _path = '${path}';
		var fileArr = [];
		var toAlbumId = '${albumList[0].id }';
		var fileFmtArr = ['image/gif','image/jpg','image/jpeg','image/png','image/bmp'];
        var _isSelf = '${self}' == 'true';
        var delId = '';
		$(function(){
			$('.nav-album').addClass('active');
			album.init();
		});
	</script>
</body>
</html>