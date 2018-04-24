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
	<div class="container article-top-container">
		<div class="full-width overflow-block article-top-div">
			<img src="/static/upload/img/l/${all.user.avatar }" class="top-info-img">
			<div class="top-info-div">
				<h2>${all.user.nickname }</h2>
				<p>${all.user.remark }</p>
			</div>
		</div>
	</div>

	<div class="container index-container">
		<div class="col-sm-8 col-xs-12" id="articleWindow">
			<div class="full-width overflow-block article-detail">
				<div class="article-detail-top">
					<i class="fa fa-window-maximize article-max" aria-hidden="true" id="articleMax"></i>
					<i class="fa fa-window-minimize article-min" aria-hidden="true" id="articleMin"></i>
					&nbsp;&nbsp;<span class="article-type">${all.article.articleType }</span>
					<a class="article-return" href="${path }/article/${all.userId}?t=${all.diaryType }&i=${all.isType}"><i class="fa fa-reply" aria-hidden="true"></i>&nbsp;返回日志列表</a>
				</div>
				<h3 class="article-title">${all.article.title }</h3>
				<div class="last-edit-time">
					编辑于&nbsp;&nbsp;<span>
						<c:choose>
							<c:when test="${all.article.updateTime == null || all.article.updateTime == '' }">
								<fmt:formatDate value="${all.article.createTime }" pattern="yyyy-MM-dd HH:mm:ss" />
							</c:when>
							<c:otherwise>
								<fmt:formatDate value="${all.article.updateTime }" pattern="yyyy-MM-dd HH:mm:ss" />
							</c:otherwise>
						</c:choose>
					</span>
				</div>
				<div class="article-detail-content">
					<c:out value="${all.article.content }" escapeXml="false"></c:out>
				</div>
				<div class="article-opercate">
					<c:choose>
						<c:when test="${self }">
							<span class="ireport hover-pointer" onclick="articleDetail.detail.delBlog()">删除</span>&nbsp;&nbsp; 
							<span class="ireport hover-pointer" onclick="window.location.href='${path}/articleDo/mark?articleId=${all.article.id }'">编辑</span>&nbsp;&nbsp;
						</c:when>
						<c:otherwise>
							<span class="ireport hover-pointer" title="暂时无法使用">举报</span>&nbsp;&nbsp; 
						</c:otherwise>
					</c:choose>
					
					<span class="hover-pointer" onclick="articleDetail.detail.reply()">评论</span>&nbsp;&nbsp; 
					<c:choose>
						<c:when test="${all.article.favoriteFlag == 'N' }">
							<span class="hover-pointer" onclick="articleDetail.detail.favorite(1)">
								<i class="fa fa-heart-o" aria-hidden="true" title="收藏"></i>(${all.article.favoriteCount })
							</span>&nbsp;&nbsp;
						</c:when>
						<c:otherwise>
							<span class="hover-pointer" onclick="articleDetail.detail.favorite(0)">
								<i class="fa fa-heart-o red" aria-hidden="true" title="取消收藏"></i>(${all.article.favoriteCount })
							</span>&nbsp;&nbsp;
						</c:otherwise>
					</c:choose>
					<c:choose>
						<c:when test="${all.article.praiseFlag == 'N' }">
							<span class="hover-pointer" onclick="articleDetail.detail.praise(1)">
								<i class="fa fa-thumbs-o-up" aria-hidden="true" title="点赞"></i>(${all.article.praiseCount })
							</span>&nbsp;&nbsp; 
						</c:when>
						<c:otherwise>
							<span class="hover-pointer" onclick="articleDetail.detail.praise(0)">
								<i class="fa fa-thumbs-up" aria-hidden="true" title="取消点赞"></i>(${all.article.praiseCount })
							</span>&nbsp;&nbsp; 
						</c:otherwise>
					</c:choose>
					<span>浏览(${all.article.readCount })</span>
				</div>
				<div class="full-width overflow-block">
					<div class="col-sm-6 col-xs-12">
						<c:choose>
							<c:when test="${all.prev == null }">
								上一篇：无
							</c:when>
							<c:otherwise>
								上一篇：<a href="${path }/article/detail/${all.prev.id}?i=${all.isType}">${all.prev.title }</a>
							</c:otherwise>
						</c:choose>
					</div>
					<div class="col-sm-6 col-xs-12 next-title">
						<c:choose>
							<c:when test="${all.next == null }">
								下一篇：无
							</c:when>
							<c:otherwise>
								下一篇：<a href="${path }/article/detail/${all.next.id}?i=${all.isType}">${all.next.title }</a>
							</c:otherwise>
						</c:choose>
					</div>
				</div>
				<hr>
				<div class="article-comment">
					<h5>评论(<span id="commentSize"></span>)</h5>
					<ul class="blog-comment-ul"></ul>
					<div class="text-center" id="commentPagination"></div>
					<div style="margin-bottom:15px;">
						<div id="replyTo">
						<!-- 
							<div class="alert alert-default" role="alert">
	    						<button class="close"  data-dismiss="alert" type="button" >&times;</button>
							    <p>回复 隔壁老王  #1 ：</p>
							</div>
							 -->
						</div>
						<textarea rows="5" style="width:100%;resize: none;" id="reply"></textarea>
						<button type="button" class="btn btn-primary btn-sm" onclick="articleDetail.detail.saveComment()">&nbsp;确&nbsp;&nbsp;定&nbsp;</button>
					</div>
				</div>
			</div>
		</div>

		<div class="col-sm-4 col-xs-12" id="otherWindow">
			<div class="index-right-block margin-top-0">
				<span>日志分类</span>
				<ul class="last-blog">
					<c:forEach var="type" items="${all.types }">
						<li><a href="${path }/article/${all.userId}?i=${type.id}">${type.typeName }</a></li>
					</c:forEach>
				</ul>
			</div>
			<div class="index-right-block margin-top-35">
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
			<div class="index-right-block margin-top-35">
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

	<%@ include file="../common/footer.jsp"%>
	<script src="${path }/static/js/article/articleDetail.js"></script>
	<script type="text/javascript">
		var _path = '${path}';
		var _userId = '${SPRING_SECURITY_CONTEXT.authentication.principal.id }';
		var _blogId = '${all.article.id}';
		$(function(){
			$('.nav-blog').addClass('active');
            articleDetail.init();
		});
	</script>
</body>
</html>