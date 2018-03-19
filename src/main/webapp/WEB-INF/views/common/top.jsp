<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!-- 需要固定加上navbar-fixed-top -->
<div class="navbar navbar-default margin-bottom-0" role="navigation" style="border:none;">
    <div class="container">
        <div class="full-width">
            <div class="logo-div">
                <c:choose>
                    <c:when test="${SPRING_SECURITY_CONTEXT.authentication.principal.nickname == null }">
                        <a href="${path }/u/1">
                            <img src="${path }/static/image/logo.png" style="height: 50px;">
                        </a>
                    </c:when>
                    <c:otherwise>
                        <a href="${path }/u/${SPRING_SECURITY_CONTEXT.authentication.principal.id }">
                            <img src="${path }/static/image/logo.png" style="height: 50px;">
                        </a>
                    </c:otherwise>
                </c:choose>
            </div>
            <c:choose>
                <c:when test="${SPRING_SECURITY_CONTEXT.authentication.principal.nickname == null && register != 1 }">
                    <div style="float: right;margin-top: 15px;">
                        <a style="cursor: pointer;margin-right:10px;" data-toggle="modal"
                           data-target="#_loginModal">登陆</a>
                        <a href="${path }/register" style="margin-right:15px;">注册</a>
                    </div>
                </c:when>
                <c:when test="${SPRING_SECURITY_CONTEXT.authentication.principal.nickname == null && register == 1}">
                    <div style="float: right;margin-top: 15px;">
                        <a style="cursor: pointer;margin-right:15px;" data-toggle="modal" data-target="#_loginModal">立即登陆</a>
                    </div>
                </c:when>
                <c:otherwise>
                    <div style="float: right; margin-top: 5px">
                        <a href="${path }/u/${SPRING_SECURITY_CONTEXT.authentication.principal.id }">
                            <img class="user-avatar"
                                 src="/static/upload/img/s/${SPRING_SECURITY_CONTEXT.authentication.principal.avatar }">
                        </a>
                        <span class="setting-span dropdown">
							<a href="#" class="dropdown-toggle" data-toggle="dropdown">
			                   <i class="fa fa-cog fa-lg" aria-hidden="true"></i>
			                </a>
			                <ul class="dropdown-menu mymenu">
								<%--<li><a href="#">博客设置</a></li>--%>
								<%--<li><a href="#">消息记录</a></li>--%>
								<li><a href="${path }/aboutMeDo/modifyPwd">修改密码</a></li>
								<li><a style="cursor: pointer;" onclick="_logout();">退&nbsp;&nbsp;出</a></li>
							</ul>
						</span>
                    </div>
                </c:otherwise>
            </c:choose>
            <div class="navbar-header">
                <!-- .navbar-toggle样式用于toggle收缩的内容，即nav-collapse collapse样式所在元素 -->
                <button class="navbar-toggle" type="button" data-toggle="collapse"
                        data-target=".navbar-responsive-collapse" style="float: right;">
                    <span class="sr-only">Toggle Navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
            </div>
            <c:if test="${wholeId == null || wholeId == '' }">
                <c:set var="wholeId" value="1"></c:set>
            </c:if>
            <!-- 屏幕宽度小于768px时，div.navbar-responsive-collapse容器里的内容都会隐藏，显示icon-bar图标，当点击icon-bar图标时，再展开。屏幕大于768px时，默认显示。 -->
            <div class="collapse navbar-collapse navbar-responsive-collapse">
                <ul class="nav navbar-nav nav-ul">
                    <li class="nav-index"><a href="${path }/u/${wholeId }" class="navbar-li-a">主页</a></li>
                    <li class="nav-blog"><a href="${path }/blog/${wholeId }" class="navbar-li-a">随笔</a></li>
                    <li class="nav-mood"><a href="${path }/emotion/${wholeId}" class="navbar-li-a">碎语</a></li>
                    <li class="nav-album"><a href="${path }/album/${wholeId}" class="navbar-li-a">相册</a></li>
                    <!--
                    <li class="nav-word"><a href="#" class="navbar-li-a">留言</a></li> -->
                    <li class="nav-music"><a href="${path }/music/${wholeId}" class="navbar-li-a">音乐</a></li>
                    <li class="nav-about"><a href="${path }/aboutMe/${wholeId }" class="navbar-li-a">我</a></li>
                </ul>
            </div>
        </div>
    </div>
</div>