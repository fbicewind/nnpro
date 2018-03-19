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

	<div class="container" style="min-height: 400px; margin-bottom: 40px;margin-top:40px;">
		<c:forEach var="blogPhoto" items="${photoList }" begin="0" end="${photoList.size() }" varStatus="i" step="1">
			<div class="col-md-2 col-sm-3 col-xs-6 album-big-div">
				<div class="album-img-div">
					<a class="album-img-a" onclick="albumDetail.detail.showFullImage(${i.index })">
						<img class="album-photo photo-${i.index }" data-src="${blogPhoto.photo }" src="/static/upload/album/s/${blogPhoto.photo }">
					</a>
				</div>
			</div>
		</c:forEach>
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

	<%@ include file="../common/footer.jsp"%>
	<script src="${path }/static/js/album/notDragOut.js"></script>
	<script src="${path }/static/js/album/albumDetail.js"></script>
	<script type="text/javascript">
		var _path = '${path}';
		var showNow = '';
		var endSize = parseInt('${photoList.size()}') - 1;
		$(function(){
			$('.nav-album').addClass('active');
			albumDetail.init();
		});
		albumDetail.changeImgSize();
	</script>
</body>
</html>