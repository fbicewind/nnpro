<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!-- 模态框（Modal） -->
<div class="modal fade" id="_loginModal" tabindex="-1" role="dialog"
	aria-labelledby="loginModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true">×</button>
				<h4 class="modal-title" id="myModalLabel">
					<span id="modalCompany">登录</span>
				</h4>
			</div>
			<div class="modal-body">
				<form role="form" class="form-horizontal" id="_loginForm">
					<div class="form-group">
						<label for="_username" class="col-sm-3 control-label">用户名：</label>
						<div class="col-sm-7">
							<input type="text" class="form-control" id="_username" name="username">
						</div>
					</div>
					<div class="form-group">
						<label for="_password" class="col-sm-3 control-label">密码：</label>
						<div class="col-sm-7">
							<input type="password" class="form-control" id="_password" name="password">
						</div>
					</div>
					<div class="form-group">
						<div class="col-sm-7 col-sm-offset-3">
							<div class="checkbox">
                                <label><input type="checkbox" id="rememberme" name="remember-me">两周内免登陆</label>  
                              </div>
						</div>
					</div>
				</form>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-primary" onclick="_login()">登录</button>
				<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
			</div>
		</div>
		<!-- /.modal-content -->
	</div>
	<!-- /.modal-dialog -->
</div>
<!-- /.modal -->

<script>
	function _login() {
		$('#_loginForm').submit();
	}

	$(function() {
		$('#_loginForm').validate({
			rules : {
				username : {
					required : true,
					minlength : 4,
					maxlength : 12
				},
				password : {
					required : true,
					minlength : 4,
					maxlength : 16
				}
			},
			messages : {
				username : {
					required : '用户名不能为空',
					minlength : '用户名不得少于4个字符',
					maxlength : '用户名不得多于12个字符'
				},
				password : {
					required : '密码不能为空',
					minlength : '密码不得少于4个字符',
					maxlength : '密码不得多于16个字符'
				}
			},
			submitHandler : function() {
				$.ajax({
					type : 'POST',
					url : '${path}/login',
					data : $('#_loginForm').serialize(),
					dataType : 'json',
					success : function(result) {
						if (result.code == '1') {
							window.location.reload();
						} else {
							_alert('错误', '登录失败!');
						}
					}
				});
			}
		});
	});
</script>