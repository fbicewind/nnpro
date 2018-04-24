<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="common/tag.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<%@ include file="common/head.jsp"%>
<link rel="stylesheet" type="text/css" href="${path }/static/plugin/datetimepicker/bootstrap-datetimepicker.min.css">
</head>
<body>
	<%@ include file="common/top.jsp"%>

	<div class="container register-container">
		<div class="register-box">
			<div class="col-sm-7">
				<form class="form-horizontal" role="form" id="registerForm">
					<div class="form-group">
						<label for="username" class="col-sm-3 control-label">用户名<span class="red">&nbsp;*</span></label>
						<div class="col-sm-8">
							<input type="text" class="form-control" id="username" name="username" placeholder="请输入用户名...">
						</div>
					</div>
					<div class="form-group">
						<label for="nickname" class="col-sm-3 control-label">昵称<span class="red">&nbsp;*</span></label>
						<div class="col-sm-8">
							<input type="text" class="form-control" id="nickname" name="nickname" placeholder="请输入昵称...">
						</div>
					</div>
					<div class="form-group">
						<label for="password" class="col-sm-3 control-label">密码<span class="red">&nbsp;*</span></label>
						<div class="col-sm-8">
							<input type="password" class="form-control" id="password" name="password" placeholder="请输入密码...">
						</div>
					</div>
					<div class="form-group">
						<label for="passwordAgain" class="col-sm-3 control-label">确认密码<span class="red">&nbsp;*</span></label>
						<div class="col-sm-8">
							<input type="password" class="form-control" id="passwordAgain" name="passwordAgain" placeholder="请再次输入密码...">
						</div>
					</div>
					<div class="form-group">
						<label for="email" class="col-sm-3 control-label">邮箱&nbsp;&nbsp;&nbsp;</label>
						<div class="col-sm-8">
							<input type="text" class="form-control" id="email" name="email" placeholder="请输入邮箱...">
						</div>
					</div>
					<div class="form-group">
						<label for="mobile" class="col-sm-3 control-label">手机&nbsp;&nbsp;&nbsp;</label>
						<div class="col-sm-8">
							<input type="text" class="form-control" id="mobile" name="mobile" placeholder="请输入手机...">
						</div>
					</div>
					<div class="form-group">
						<label for="profession" class="col-sm-3 control-label">职业&nbsp;&nbsp;&nbsp;</label>
						<div class="col-sm-8">
							<input type="text" class="form-control" id="profession" name="profession" placeholder="请输入职业...">
						</div>
					</div>
					<div class="form-group">
						<label for="birthday" class="col-sm-3 control-label">生日&nbsp;&nbsp;&nbsp;</label>
						<div class="input-group date form_date col-sm-8 register-birthday-div" data-date-format="yyyy-mm-dd">
		                    <input class="form-control background-white" size="16" type="text" id="birthday" name="birthday" readonly="readonly">
							<span class="input-group-addon background-white"><span class="fa fa-calendar"></span></span>
		                </div>
					</div>
					<div class="form-group">
						<label for="gender" class="col-sm-3 control-label">性别&nbsp;&nbsp;&nbsp;</label>
						<div class="col-sm-8">
							<label class="radio-inline">
							    <input type="radio" value="1" name="gender">男
							</label>
							<label class="radio-inline">
							    <input type="radio" value="0" name="gender">女
							</label>
							<label class="radio-inline">
							    <input type="radio" value="2" name="gender" checked="checked">保密
							</label>
						</div>
					</div>
					<div class="form-group">
						<label for="remark" class="col-sm-3 control-label">个性签名&nbsp;&nbsp;&nbsp;</label>
						<div class="col-sm-8">
							<textarea class="form-control" rows="3" name="remark" id="remark"></textarea>
						</div>
					</div>
					<div class="form-group" id="validateCodeDiv">
						<label for="code" class="col-sm-3 control-label">验证码<span class="red">&nbsp;*</span></label>
						<div class="col-sm-4">
							<input type="text" class="form-control validate-code-input" id="code" name="code" placeholder="请输入验证码...">
							<span class="validate-code" title="点击刷新"></span>
						</div>
					</div>
					<div class="form-group">
				    	<div class="col-sm-offset-3 col-sm-8">
				    		<button type="button" class="btn btn-default" id="registerButton">注册</button>
				    	</div>
				  	</div>
				</form>
			</div>
			<div class="col-sm-5">
				<div class="register-image hidden-xs"></div>
			</div>
		</div>
	</div>

	<%@ include file="common/footer.jsp"%>
	<script type="text/javascript" src="${path }/static/plugin/datetimepicker/bootstrap-datetimepicker.min.js"></script>
	<script type="text/javascript" src="${path }/static/plugin/datetimepicker/bootstrap-datetimepicker.zh-CN.js"></script>
	<script src="${path }/static/js/others/register.js"></script>
	<script type="text/javascript">
		var _path = '${path}';
		$(function(){
			register.init();
		});
	</script>
</body>
</html>