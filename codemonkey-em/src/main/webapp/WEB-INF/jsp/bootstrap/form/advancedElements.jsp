<%@ page contentType="text/html;charset=UTF-8"%>

<link rel="stylesheet" href="${ctx}/webjars/select2/4.0.0/dist/css/select2.min.css"/>
<script type="text/javascript" src="${ctx}/webjars/select2/4.0.0/dist/js/select2.full.min.js"></script>

<script type="text/javascript">
	$(document).ready(function(){
		 $(".select2").select2();
	});
</script>
		
<div class="col-md-6 col-xs-12">
	<!-- input states -->
	<div class="form-group has-success">
		<label class="control-label" for="inputSuccess"> <i class="fa fa-check"></i> Input with success</label> 
		<input type="text" class="form-control" id="inputSuccess" placeholder="Enter ...">
	</div>
	<div class="form-group has-warning">
		<label class="control-label" for="inputWarning"><i class="fa fa-bell-o"></i> Input with warning</label> 
		<input type="text" class="form-control" id="inputWarning" placeholder="Enter ...">
	</div>
	<div class="form-group has-error">
		<label class="control-label" for="inputError"><iclass="fa fa-times-circle-o"></i> Input with error</label>
		<input type="text" class="form-control" id="inputError" placeholder="Enter ...">
	</div>
	
	<div class="form-group">
		<div class="input-group">
			<span class="input-group-addon">@</span> 
			<input type="text" class="form-control" placeholder="Username">
		</div>
	</div>
	
	<div class="form-group">
		<div class="input-group">
			<input type="text" class="form-control"> 
			<span class="input-group-addon">.00</span>
		</div>
	</div>
	
	<div class="form-group">
		<div class="input-group">
			<span class="input-group-addon">$</span> 
			<input type="text" class="form-control"> 
			<span class="input-group-addon">.00</span>
		</div>
	</div>
	
	<h4>With icons</h4>
	<div class="form-group">
		<div class="input-group">
			<span class="input-group-addon"><i class="fa fa-envelope"></i></span> 
			<input type="email" class="form-control" placeholder="Email">
		</div>
	</div>
	<div class="form-group">
		<div class="input-group">
			<input type="text" class="form-control"> 
			<span class="input-group-addon"><i class="fa fa-check"></i></span>
		</div>
	</div>
	<div class="form-group">
		<div class="input-group">
			<span class="input-group-addon"><i class="fa fa-dollar"></i></span> 
			<input type="text" class="form-control"> 
			<span class="input-group-addon"><i class="fa fa-ambulance"></i></span>
		</div>
	</div>
	
	<h4>With checkbox and radio inputs</h4>
	<div class="form-group">
		<div class="input-group">
			<span class="input-group-addon"> 
				<input type="checkbox">
			</span> 
			<input type="text" class="form-control">
		</div>
	</div>
	<div class="form-group">
		<div class="input-group">
			<span class="input-group-addon"> 
				<input type="radio">
			</span> 
			<input type="text" class="form-control">
		</div>
	</div>
	
</div>
<div class="col-md-6 col-xs-12">
	<h4>With buttons</h4>
	<div class="form-group">
		<div class="input-group">
			<div class="input-group-btn">
				<button type="button" class="btn btn-warning dropdown-toggle"
					data-toggle="dropdown" aria-expanded="false">
					Action <span class="fa fa-caret-down"></span>
				</button>
				<ul class="dropdown-menu">
					<li><a href="#">Action</a></li>
					<li><a href="#">Another action</a></li>
					<li><a href="#">Something else here</a></li>
					<li class="divider"></li>
					<li><a href="#">Separated link</a></li>
				</ul>
			</div>
			<!-- /btn-group -->
			<input type="text" class="form-control">
		</div>
	</div>
	<div class="form-group">
		<div class="input-group">
			<div class="input-group-btn">
				<button type="button" class="btn btn-danger">Action</button>
			</div>
			<input type="text" class="form-control">
		</div>
	</div>
	
	<div class="form-group">
		<div class="input-group">
			<input type="text" class="form-control"> 
			<span class="input-group-btn">
				<button class="btn btn-info btn-flat" type="button">Go!</button>
			</span>
		</div>
	</div>
	
	<div class="form-group">
		<label class="control-label" for="inputSuccess">datepicker</label> 
		<div class="input-group">
			<input name="signDate" inputType="datepicker" class="form-control" placeholder="签订日期">
		</div>
    </div>
</div>
<div class="col-md-12 col-xs-12">
	<!-- SELECT2 EXAMPLE -->
	<div class="box box-default">
	  <div class="box-header with-border">
	    <h3 class="box-title">Select2</h3>
	    <div class="box-tools pull-right">
	      <button class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i></button>
	      <button class="btn btn-box-tool" data-widget="remove"><i class="fa fa-remove"></i></button>
	    </div>
	  </div><!-- /.box-header -->
	  <div class="box-body">
	    <div class="row">
	      <div class="col-md-6">
	        <div class="form-group">
	          <label>Minimal</label>
	          <select class="form-control select2">
	            <option selected="selected">Alabama</option>
	            <option>Alaska</option>
	            <option>California</option>
	            <option>Delaware</option>
	            <option>Tennessee</option>
	            <option>Texas</option>
	            <option>Washington</option>
	          </select>
	        </div><!-- /.form-group -->
	        <div class="form-group">
	          <label>Disabled</label>
	          <select class="form-control select2" disabled="disabled">
	            <option selected="selected">Alabama</option>
	            <option>Alaska</option>
	            <option>California</option>
	            <option>Delaware</option>
	            <option>Tennessee</option>
	            <option>Texas</option>
	            <option>Washington</option>
	          </select>
	        </div><!-- /.form-group -->
	      </div><!-- /.col -->
	      <div class="col-md-6">
	        <div class="form-group">
	          <label>Multiple</label>
	          <select class="form-control select2" multiple="multiple" data-placeholder="Select a State">
	            <option>Alabama</option>
	            <option>Alaska</option>
	            <option>California</option>
	            <option>Delaware</option>
	            <option>Tennessee</option>
	            <option>Texas</option>
	            <option>Washington</option>
	          </select>
	        </div><!-- /.form-group -->
	        <div class="form-group">
	          <label>Disabled Result</label>
	          <select class="form-control select2">
	            <option selected="selected">Alabama</option>
	            <option>Alaska</option>
	            <option disabled="disabled">California (disabled)</option>
	            <option>Delaware</option>
	            <option>Tennessee</option>
	            <option>Texas</option>
	            <option>Washington</option>
	          </select>
	        </div><!-- /.form-group -->
	      </div><!-- /.col -->
	    </div><!-- /.row -->
	  </div><!-- /.box-body -->
	  <div class="box-footer">
	    Visit <a href="https://select2.github.io/">Select2 documentation</a> for more examples and information about the plugin.
	  </div>
	</div><!-- /.box --> 
</div>