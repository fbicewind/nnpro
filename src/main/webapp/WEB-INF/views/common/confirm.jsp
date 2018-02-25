<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!-- 模态框（Modal） -->
<div class="modal fade" id="_confirmModal" tabindex="-1" role="dialog"
	aria-labelledby="confirmModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true">×</button>
				<h4 class="modal-title" id="confirmModalLabel">
					<span id="_confirmTitle"></span>
				</h4>
			</div>
			<div class="modal-body">
				<span id="_confirmBody"></span>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-primary" id="_confirmSure">确认</button>
				<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
			</div>
		</div>
		<!-- /.modal-content -->
	</div>
	<!-- /.modal-dialog -->
</div>
<!-- /.modal -->

<script>
	function _confirm(title, body, callback){
		$('#_confirmTitle').text(title);
		$('#_confirmBody').text(body);
		$('#_confirmSure').on('click', callback);
		$('#_confirmModal').modal('show');
	}
</script>