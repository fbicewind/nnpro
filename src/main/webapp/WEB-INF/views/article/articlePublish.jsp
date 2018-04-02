<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/tag.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<%@ include file="../common/head.jsp"%>
	<link rel="stylesheet" href="${path }/static/plugin/wangEditor/css/wangEditor.min.css">
<style type="text/css">
#wEditor{
	height:600px;
}
</style>
</head>
<body>
	<%@ include file="../common/top.jsp"%>
	<div class="container" style="min-height: 200px;">
		<div class="full-width overflow-block" style="padding: 40px 30px;">
			<img src="/static/upload/img/l/${all.user.avatar }" class="top-info-img">
			<div class="top-info-div">
				<h2>${all.user.nickname }</h2>
				<p>${all.user.remark }</p>
			</div>
		</div>
	</div>

	<div class="container" style="min-height: 400px; margin-bottom: 40px;">
		<input type="hidden" id="_blogDetail" value="${fn:escapeXml(all.article.content)}">
		<div class="col-sm-8 col-xs-12">
			<form role="form" id="newBlog">
				<input type="hidden" name="blogId" id="blogId" value="${all.article.id }">
			 	<div class="form-group">
					<input type="text" name="title" id="title" class="form-control" placeholder="标题..." value="${all.article.title }">
				</div>
				<div id="wEditor">
				</div>
				<div style="margin:15px 0;">
					<div class="form-group">
	    				<label for="type" class="form-label" style="font-weight: 500;">分类：</label>
	    				<select class="form-control" id="type" style="width:150px;display: inline-block;">
							<c:forEach var="type" items="${all.types }">
								<c:choose>
		    						<c:when test="${all.article.typeId == type.id }">
		    							<option value="${type.id }" selected="selected">${type.typeName }</option>
		    						</c:when>
		    						<c:otherwise>
		    							<option value="${type.id }">${type.typeName }</option>
		    						</c:otherwise>
		    					</c:choose>
							</c:forEach>
						</select>
						<a style="cursor: pointer;" data-toggle="modal" data-target="#myModal">添加分类</a>
	  				</div>
	  				<div class="checkbox">
						<label style="padding-left: 0;width: 110px;">
							仅自己可见
							<c:choose>
								<c:when test="${all.article.publicFlag == 'N' }">
									<input id="isPublic" type="checkbox" style="margin-left: 10px;width:15px;height:15px;" checked> 
								</c:when>
								<c:otherwise>
									<input id="isPublic" type="checkbox" style="margin-left: 10px;width:15px;height:15px;"> 
								</c:otherwise>
							</c:choose>
						</label>
						<label style="padding-left: 0;width: 70px;">
							置顶
							<c:choose>
								<c:when test="${all.article.topFlag == 'Y' }">
									<input id="isTop" type="checkbox" style="margin-left: 10px;width:15px;height:15px;" checked>
								</c:when>
								<c:otherwise>
									<input id="isTop" type="checkbox" style="margin-left: 10px;width:15px;height:15px;">
								</c:otherwise>
							</c:choose>
						</label>
						<label style="padding-left: 0;width: 90px;">
							首页推荐
							<c:choose>
								<c:when test="${all.article.recommendFlag == 'Y' }">
									<input id="isRecommend" type="checkbox" style="margin-left: 10px;width:15px;height:15px;" checked>
								</c:when>
								<c:otherwise>
									<input id="isRecommend" type="checkbox" style="margin-left: 10px;width:15px;height:15px;">
								</c:otherwise>
							</c:choose>
						</label>
					</div>
				</div>
				<div class="form-group">
					<button type="button" class="btn btn-primary"  onclick="articlePublish.detail.submitBlog()" style="margin-right:5px;">&nbsp;发&nbsp;&nbsp;&nbsp;&nbsp;表&nbsp;</button>
					<button type="button" class="btn btn-default" onclick="articlePublish.detail.cancelBlog()">&nbsp;取&nbsp;&nbsp;&nbsp;&nbsp;消&nbsp;</button>
					<button style="float: right;" type="button" class="btn btn-default" onclick="articlePublish.detail.saveDraft()">保存草稿</button>
				</div>
			</form>
		</div>
		<div class="col-sm-4 col-xs-12">
			<div class="index-right-block" style="margin-top: 0px;">
				<span>日志分类</span>
				<ul class="last-blog">
					<c:forEach var="type" items="${all.types }">
						<li><a href="${path }/article/${all.userId}?i=${type.id}">${type.typeName }</a></li>
					</c:forEach>
				</ul>
			</div>
			<div class="index-right-block" style="margin-top: 35px;">
				<span>最近日志</span>
				<ul class="last-blog">
					<c:if test="${all.newArticles.datalist == null }">
						<li>暂无日志..</li>
					</c:if>
					<c:forEach var="blog" items="${all.newArticles.datalist }">
						<li><a href="${path }/article/detail/${blog.id }">${blog.title }</a></li>
					</c:forEach>
				</ul>
			</div>
			<div class="index-right-block" style="margin-top: 35px;">
				<span>大家在看</span>
				<ul class="last-blog">
					<c:if test="${all.viewArticles.datalist == null }">
						<li>暂无日志..</li>
					</c:if>
					<c:forEach var="blog" items="${all.viewArticles.datalist }">
						<li><a href="${path }/article/detail/${blog.id }">${blog.title }</a></li>
					</c:forEach>
				</ul>
			</div>
		</div>
	</div>

<!-- 模态框（Modal） -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                <h4 class="modal-title" id="myModalLabel">
                	<span id="modalCompany">添加日志分类</span>
                </h4>
            </div>
            <div class="modal-body">
            	<form role="form" class="form-horizontal" id="newType">
            		<div class="form-group">
						<label for="typeName" class="col-sm-3 control-label">分类名称：</label>
						<div class="col-sm-5">
							<input type="text" class="form-control" id="typeName" name="typeName">
						</div>
					</div>
            	</form>
			</div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" onclick="articlePublish.detail.addType()">确定</button>
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div>
<!-- /.modal -->

	<%@ include file="../common/footer.jsp"%>
	<script src="${path }/static/plugin/wangEditor/js/wangEditor.min.js"></script>
	<script src="${path }/static/js/article/articlePublish.js"></script>
	<script type="text/javascript">
		var _path = '${path}';
		$(function () {
			$('.nav-blog').addClass('active');
            articlePublish.init();
        });
	</script>
</body>
</html>