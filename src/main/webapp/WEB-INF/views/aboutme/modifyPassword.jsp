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
						修改密码
					</h3>
				</div>
				<div class="panel-body aboutme-panel-body">
					<form class="form-horizontal" role="form" id="modifyPwdForm">
						<div class="form-group">
							<label for="oldPassword" class="col-sm-3 control-label">原密码<span class="red">&nbsp;*</span></label>
							<div class="col-sm-8">
								<input type="password" class="form-control" id="oldPassword" name="oldPassword" placeholder="请输入原密码...">
							</div>
						</div>
						<div class="form-group">
							<label for="password" class="col-sm-3 control-label">新密码<span class="red">&nbsp;*</span></label>
							<div class="col-sm-8">
								<input type="password" class="form-control" id="password" name="password" placeholder="请输入新密码...">
							</div>
						</div>
						<div class="form-group">
							<label for="passwordAgain" class="col-sm-3 control-label">确认密码<span class="red">&nbsp;*</span></label>
							<div class="col-sm-8">
								<input type="password" class="form-control" id="passwordAgain" name="passwordAgain" placeholder="请再次输入新密码...">
							</div>
						</div>
						<div class="form-group">
					    	<div class="col-sm-offset-3 col-sm-8">
					    		<button type="submit" class="btn btn-primary">保存</button>
					    		<button type="button" class="btn btn-default margin-left-15" id="modifyPswCancel">取消</button>
					    	</div>
					  	</div>
					</form>
				</div>
			</div>
		</div>
	</div>

	<%@ include file="../common/footer.jsp"%>
	<%@ include file="../common/alert.jsp"%>
	<script src="${path }/static/js/aboutme/modifyPassword.js"></script>
	<script type="text/javascript">
		var _path = '${path}';
		var _userId = '${wholeId}';
		$(function(){
			$('.nav-about').addClass('active');
			modifyPassword.init();
		});
		
	</script>
</body>
</html>