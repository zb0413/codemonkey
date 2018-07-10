<script type="text/javascript">
	function openDialog(){
		$('#myModal').modal('toggle');
	}
	
	function openDialog2(){
		$('#myModal').modal('show');
	}
	
	function hide(){
		$('#myModal').modal('hide');
	}

</script>
<button type="button" class="btn btn-primary btn-lg" onclick="openDialog()">
  toggle
</button>

<button type="button" class="btn btn-primary btn-lg" onclick="openDialog2()">
  show
</button>



<!-- Modal -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="myModalLabel">Modal title</h4>
      </div>
      <div class="modal-body">
        ...
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
        <button type="button" class="btn btn-primary" onclick="hide()">Hide</button>
      </div>
    </div>
  </div>
</div>

<script type="text/javascript">
	$('#myModal').on('show.bs.modal', function (e) {
	 	alert("show.bs.modal");
	});
	
	$('#myModal').on('shown.bs.modal', function (e) {
	 	alert("shown.bs.modal");
	});
	
	$('#myModal').on('hide.bs.modal', function (e) {
	 	alert("hide.bs.modal");
	});
	
	$('#myModal').on('hidden.bs.modal', function (e) {
	 	alert("hidden.bs.modal");
	});
</script>
