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

	<div class="container normal-container">
		<div class="col-sm-12">
			<div class="panel panel-default">
				<div class="panel-heading">
					<h3 class="panel-title">
						${user.nickname }的个人信息
					</h3>
				</div>
				<div class="panel-body aboutme-panel-body">
					<div class="col-xs-12 col-sm-4 col-md-2">
						<div class="text-center">
							<img src="/static/upload/img/l/${user.avatar }" class="aboutme-avatar">
						</div>
						<c:if test="${self }">
							<div class="text-center modify-avatar">
								<a href="${path }/aboutMeDo/modifyAvatar">修改头像</a>
							</div>
						</c:if>
						<div class="three-count">
							<div class="col-xs-4 text-center padding-0">
								<a href="#">
									<span>文章<br>${user.articleCount }</span>
								</a>
							</div>
							<div class="col-xs-4 text-center padding-0">
								<a href="#">
									<span>照片<br>${user.photoCount }</span>
								</a>
							</div>
							<div class="col-xs-4 text-center padding-0">
								<a href="#">
									<span>音乐<br>${user.musicCount }</span>
								</a>
							</div>
						</div>
					</div>
					<div class="col-xs-12 col-sm-8 col-md-10">
						<h4>${user.nickname }</h4>
						<div class="text-right modify-info">
							<c:if test="${user.id == 1 }">
								<a href="${path }/aboutMe/resume">查看简历</a>
							</c:if>
							<c:if test="${self }">
								<a href="${path }/aboutMeDo/modifyInfo" class="aboutme-modify-info">修改</a>
							</c:if>
						</div>
						<div>
							<div class="about-info-div">
								<div class="col-sm-3 col-md-2">邮箱：</div>
								<div class="col-sm-9 col-md-10">
									${user.email }
									<c:if test="${user.email == null || user.email == '' }">无</c:if>
								</div>
							</div>
							<div class="about-info-div">
								<div class="col-sm-3 col-md-2">手机：</div>
								<div class="col-sm-9 col-md-10">
									${user.mobile }
									<c:if test="${user.mobile == null || user.mobile == '' }">无</c:if>
								</div>
							</div>
							<div class="about-info-div">
								<div class="col-sm-3 col-md-2">职业：</div>
								<div class="col-sm-9 col-md-10">
									${user.profession }
									<c:if test="${user.profession == null || user.profession == '' }">无</c:if>
								</div>
							</div>
							<div class="about-info-div">
								<div class="col-sm-3 col-md-2">性别：</div>
								<div class="col-sm-9 col-md-10">
									<c:choose>
										<c:when test="${user.gender == 0 }">女</c:when>
										<c:when test="${user.gender == 1 }">男</c:when>
										<c:otherwise>保密</c:otherwise>
									</c:choose>
								</div>
							</div>
							<div class="about-info-div">
								<div class="col-sm-3 col-md-2">生日：</div>
								<div class="col-sm-9 col-md-10">
									<fmt:formatDate value="${user.birthday }" pattern="yyyy-MM-dd" />
									<c:if test="${user.birthday == null || user.birthday == '' }">无</c:if>
								</div>
							</div>
							<div class="about-info-div">
								<div class="col-sm-3 col-md-2">个性签名：</div>
								<div class="col-sm-9 col-md-10">
									${user.remark }
									<c:if test="${user.remark == null || user.remark == '' }">无</c:if>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<%@ include file="../common/footer.jsp"%>
	<script src="${path }/static/js/aboutme/aboutMe.js"></script>
	<script type="text/javascript">
		var _path = '${path}';
		$(function(){
			$('.nav-about').addClass('active');
		});
		
	</script>
</body>
</html>