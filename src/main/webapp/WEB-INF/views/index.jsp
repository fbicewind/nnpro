<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="common/tag.jsp" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <%@ include file="common/head.jsp" %>
</head>
<body>
<%@ include file="common/top.jsp" %>
<div class="banner" style="margin-bottom: 40px;"></div>
<div class="container" style="min-height: 400px; margin-bottom: 40px;">

</div>

<%@ include file="common/footer.jsp" %>

<script src="${path }/static/js/others/index.js"></script>
<script type="text/javascript">
    var _path = '${path}';
    $(function () {
        $('.nav-index').addClass('active');
    });
</script>
</body>
</html>