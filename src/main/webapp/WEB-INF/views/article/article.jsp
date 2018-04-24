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
		<div class="col-sm-8 col-xs-12">
			<div class="full-width overflow-block">
				<div id="myTabContent" class="tab-content">
					<div class="tab-pane fade in active" id="myDiary">
						<ul>
							<c:if test="${all.articles.totalSize == 0 }">
								<li class="article-li">
									暂无日志...
								</li>
							</c:if>
							<c:forEach var="blog" items="${all.articles.datalist }">
								<li class="article-li">
									<div>
										<h4>
											<a href="${path }/article/detail/${blog.id}">${blog.title }</a>
										</h4>
										<c:if test="${blog.thumb != '' && blog.thumb != null }">
											<div class="overflow-block">
												<c:forEach var="image" items="${fn:split(blog.thumb, '|||')}">
													<div class="col-sm-4 hidden-xs padding-left-0">
														<div class="index-image-outer article-image-div">
															<img src="${image }">
														</div>
													</div>
												</c:forEach>
											</div>
										</c:if>
										<a href="${path }/article/detail/${blog.id}">
											<p>${blog.thumbContent }</p>
										</a>
									</div>
									<div>
										<span>
											<i class="fa fa-clock-o" aria-hidden="true"></i>
											<fmt:formatDate value="${blog.createTime }" pattern="yyyy-MM-dd HH:mm:ss" />
										</span>&nbsp;&nbsp;&nbsp;&nbsp; <span>分类：<a href="#">${blog.articleType }</a></span>&nbsp;&nbsp;&nbsp;&nbsp;
										<span>浏览量：${blog.readCount }</span>
									</div>
								</li>
							</c:forEach>
						</ul>
					</div>
				</div>
				<div class="text-center">
					<c:choose>
						<c:when test="${all.articles.totalNo == 1 || all.articles.totalNo == 0}">
							<ul class="pagination text-center">
								<li class="disabled"><a href="#">&lt;</a></li>
								<li class="active disabled"><a href="#">1</a></li>
								<li class="disabled"><a href="#">&gt;</a></li>
							</ul>
						</c:when>
						<c:when test="${all.articles.totalNo == 2 }">
							<ul class="pagination text-center">
								<c:choose>
									<c:when test="${all.articles.pageNo == 1 }">
										<li class="disabled"><a href="#">&lt;</a></li>
										<li class="active disabled"><a href="#">1</a></li>
										<li><a href="${path }/article/${all.userId}?n=2&i=${all.typeId}">2</a></li>
										<li><a href="${path }/article/${all.userId}?n=2&i=${all.typeId}">&gt;</a></li>
									</c:when>
									<c:otherwise>
										<li><a href="${path }/article/${all.userId}?n=1&i=${all.typeId}">&lt;</a></li>
										<li><a href="${path }/article/${all.userId}?n=1&i=${all.typeId}">1</a></li>
										<li class="disabled"><a href="#">2</a></li>
										<li class="disabled"><a href="#">&gt;</a></li>
									</c:otherwise>
								</c:choose>
							</ul>
						</c:when>
						<c:when test="${all.articles.totalNo == 3 }">
							<ul class="pagination text-center">
								<c:choose>
									<c:when test="${all.articles.pageNo == 1}">
										<li class="disabled"><a href="#">&lt;</a></li>
										<li class="active disabled"><a href="#">1</a></li>
										<li><a href="${path }/article/${all.userId}?n=2&i=${all.typeId}">2</a></li>
										<li><a href="${path }/article/${all.userId}?n=3&i=${all.typeId}">3</a></li>
										<li><a href="${path }/article/${all.userId}?n=2&i=${all.typeId}">&gt;</a></li>
									</c:when>
									<c:when test="${all.articles.pageNo == 3 }">
										<li><a href="#">&lt;</a></li>
										<li><a href="${path }/article/${all.userId}?n=1&i=${all.typeId}">1</a></li>
										<li><a href="${path }/article/${all.userId}?n=2&i=${all.typeId}">2</a></li>
										<li class="disabled"><a href="#">3</a></li>
										<li class="disabled"><a href="#">&gt;</a></li>
									</c:when>
									<c:otherwise>
										<li><a href="${path }/article/${all.userId}?n=1&i=${all.typeId}">&lt;</a></li>
										<li><a href="${path }/article/${all.userId}?n=1&i=${all.typeId}">1</a></li>
										<li class="active disabled"><a href="#">2</a></li>
										<li><a href="${path }/article/${all.userId}?n=3&i=${all.typeId}">3</a></li>
										<li><a href="#">&gt;</a></li>
									</c:otherwise>
								</c:choose>
							</ul>
						</c:when>
						<c:otherwise>
							<ul class="pagination text-center">
								<c:choose>
									<c:when test="${all.articles.pageNo == 1 }">
										<li class="disabled"><a href="#">&lt;</a></li>
										<li class="active disabled"><a href="#">1</a></li>
										<li><a href="${path }/article/${all.userId}?n=2&i=${all.typeId}">2</a></li>
										<li class="disabled"><a href="#">...</a></li>
										<li><a href="${path }/article/${all.userId}?n=${all.articles.totalNo}&i=${all.typeId}">${all.articles.totalNo}</a></li>
										<li><a href="${path }/article/${all.userId}?n=2&i=${all.typeId}">&gt;</a></li>
									</c:when>
									<c:when test="${all.articles.pageNo == all.articles.totalNo }">
										<li><a href="${path }/article/${all.userId}?n=${all.articles.pageNo-1}&i=${all.typeId}">&lt;</a></li>
										<li><a href="${path }/article/${all.userId}?n=1&i=${all.typeId}">1</a></li>
										<li class="disabled"><a href="#">...</a></li>
										<li><a href="${path }/article/${all.userId}?n=${all.articles.pageNo-1}&i=${all.typeId}">${all.articles.pageNo-1}</a></li>
										<li class="active disabled"><a href="#">${all.articles.pageNo}</a></li>
										<li class="disabled"><a href="#">&gt;</a></li>
									</c:when>
									<c:otherwise>
										<li><a href="${path }/article/${all.userId}?n=${all.articles.pageNo-1}&i=${all.typeId}">&lt;</a></li>
										<li><a href="${path }/article/${all.userId}?n=1&i=${all.typeId}">1</a></li>
										<c:if test="${all.articles.pageNo != 2 && all.articles.pageNo != 3 }">
											<li class="disabled"><a href="#">...</a></li>
										</c:if>
										<c:if test="${all.articles.pageNo != 2 }">
											<li><a href="${path }/article/${all.userId}?n=${all.articles.pageNo-1}&i=${all.typeId}">${all.articles.pageNo-1}</a></li>
										</c:if>
										<li class="active disabled"><a href="#">${all.articles.pageNo}</a></li>
										<c:if test="${all.articles.pageNo != (all.articles.totalNo - 1) }">
											<li><a href="${path }/article/${all.userId}?n=${all.articles.pageNo+1}&i=${all.typeId}">${all.articles.pageNo+1}</a></li>
										</c:if>
										<c:if test="${all.articles.pageNo != (all.articles.totalNo - 1) && all.articles.pageNo != (all.articles.totalNo - 2) }">
											<li class="disabled"><a href="#">...</a></li>
										</c:if>
										<li><a href="${path }/article/${all.userId}?n=${all.articles.totalNo}&i=${all.typeId}">${all.articles.totalNo}</a></li>
										<li><a href="${all.articles.pageNo+1}">&gt;</a></li>
									</c:otherwise>
								</c:choose>
							</ul>
						</c:otherwise>
					</c:choose>
				</div>
			</div>
		</div>
		<div class="col-sm-4 col-xs-12">
			<div class="index-right-block margin-top-0">
				<span>日志分类</span>
				<ul class="last-blog">
					<c:forEach var="type" items="${all.types }">
						<c:choose>
							<c:when test="${type.id == all.typeId }">
								<li class="active"><a href="${path }/blog/${all.userId}?i=${type.id}">${type.typeName }</a></li>
							</c:when>
							<c:otherwise>
								<li><a href="${path }/article/${all.userId}?i=${type.id}">${type.typeName }</a></li>
							</c:otherwise>
						</c:choose>
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
	<script src="${path }/static/js/article/article.js"></script>
	<script type="text/javascript">
		var _path = '${path}';
		$(function(){
			$('.nav-blog').addClass('active');
            article.init();
		});
        article.changeImgSize();
	</script>
</body>
</html>