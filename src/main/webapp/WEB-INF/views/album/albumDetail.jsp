<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
			<div class="album-button-div">
				<button class="btn btn-default" onclick="history.go(-1)">
					<i class="fa fa-reply" aria-hidden="true"></i>&nbsp;&nbsp;返回
				</button>
				<c:if test="${self}">
					<button class="btn btn-default" data-toggle="modal" data-target="#uploadPhotoModal" onclick="albumDetail.detail.cannelEvent()">
						<i class="fa fa-picture-o" aria-hidden="true"></i>&nbsp;&nbsp;上传
					</button>
					<button class="btn btn-default" onclick="albumDetail.detail.deleteEvent()">
						<i class="fa fa-trash" aria-hidden="true"></i>&nbsp;&nbsp;删除
					</button>
					<button class="btn btn-default" onclick="albumDetail.detail.coverEvent()">
						<i class="fa fa-tag" aria-hidden="true"></i>&nbsp;&nbsp;封面
					</button>
					<div class="album-sure-group" id="hideButtonGroup">
						<button class="btn btn-default" onclick="albumDetail.detail.sureEvent()">确定</button>
						<button class="btn btn-default" onclick="albumDetail.detail.cannelEvent()">取消</button>
					</div>
				</c:if>
			</div>
			<c:forEach var="blogPhoto" items="${photoList }" begin="0" end="${photoList.size() }" varStatus="i" step="1">
				<div class="col-md-2 col-sm-3 col-xs-6 album-big-div">
					<div class="album-img-div">
						<div class="photo-check-cover"></div>
						<input type="checkbox" class="photo-check" name="photocheck" value="${blogPhoto.id}">
						<input type="checkbox" class="photo-radio" name="photoradio" value="${blogPhoto.id}">
						<a class="album-img-a" onclick="albumDetail.detail.showFullImage(${i.index })">
							<img class="album-photo photo-${i.index }" data-src="${blogPhoto.photo }" src="/static/upload/album/s/${blogPhoto.photo }">
						</a>
					</div>
				</div>
			</c:forEach>
		</div>
	</div>
	
	<div class="album-cover"></div>
	<div class="album-photo-div noselect">
		<i class="fa fa-times fa-2x photo-close" aria-hidden="true" onclick="albumDetail.detail.hideFullImage()"></i>
		<i class="fa fa-chevron-left fa-4x photo-guide" aria-hidden="true" style="left: 20px;" onclick="albumDetail.detail.showLeftImage()"></i>
		<i class="fa fa-chevron-right fa-4x photo-guide" aria-hidden="true" style="right:20px;" onclick="albumDetail.detail.showRightImage()"></i>
		<img id="fullImage" alt="" src="">
		<div class="photo-zoom-div">
			<i class="fa fa-search-plus fa-2x" aria-hidden="true" onclick="albumDetail.detail.zoom(1)"></i>
			<i class="fa fa-search-minus fa-2x" aria-hidden="true" onclick="albumDetail.detail.zoom(0)"></i>
			<i class="fa fa-expand fa-2x" aria-hidden="true" onclick="albumDetail.detail.showOriginPic()"></i>
			<i class="fa fa-compress fa-2x" aria-hidden="true" onclick="albumDetail.detail.showFullImage(showNow);"></i>
		</div>
	</div>

    <!-- 模态框（Modal） -->
    <div class="modal fade" id="uploadPhotoModal" tabindex="-1" role="dialog"
         aria-labelledby="uploadTitle" aria-hidden="true" data-backdrop="static" data-keyboard="false">
        <div class="modal-dialog" id="uploadPhotoDialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true" onclick="album.detail.removeHistory()">×</button>
                    <h4 class="modal-title" id="uploadTitle">
                        <span>上传照片</span>
                    </h4>
                </div>
                <div class="modal-body">
                    <form role="form" class="form-horizontal margin-top-0" id="uploadPhotoForm">
                        <div class="photo-parent" id="addTarget">
                            <img src="${path }/static/image/add.png" class="photo-cover">
                            <input id="imgFiles" class="photo-file" type="file" name="img" multiple="multiple" accept="image/gif,image/jpg,image/jpeg,image/png,image/bmp"/>
                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-primary" onclick="albumDetail.detail.uploadPhoto(0)">开始上传</button>
                    <button type="button" class="btn btn-default" data-dismiss="modal" onclick="albumDetail.detail.removeHistory()">取消</button>
                </div>
            </div>
        </div>
    </div>

	<%@ include file="../common/footer.jsp"%>
	<script src="${path }/static/js/album/notDragOut.js"></script>
	<script src="${path }/static/js/album/albumDetail.js"></script>
	<script type="text/javascript">
		var _path = '${path}';
		var showNow = '';
		var endSize = parseInt('${photoList.size()}') - 1;
		var _albumId = '${albumId}';
        var fileArr = [];
        var fileFmtArr = ['image/gif','image/jpg','image/jpeg','image/png','image/bmp'];
        var delOrCover = '0';
		$(function(){
			$('.nav-album').addClass('active');
			albumDetail.init();
		});
		albumDetail.changeImgSize();
	</script>
</body>
</html>