<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!-- 模态框（Modal） -->
<div class="modal fade" id="_alertModal" tabindex="-1" role="dialog"
	aria-labelledby="alertModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true">×</button>
				<h4 class="modal-title" id="alertModalLabel">
					<span id="_alertTitle"></span>
				</h4>
			</div>
			<div class="modal-body">
				<span id="_alertBody"></span>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">确定</button>
			</div>
		</div>
		<!-- /.modal-content -->
	</div>
	<!-- /.modal-dialog -->
</div>
<!-- /.modal -->

<script>
	function _alert(title, body){
		$('#_alertTitle').text(title);
		$('#_alertBody').text(body);
		$('#_alertModal').modal('show');
	}
</script>