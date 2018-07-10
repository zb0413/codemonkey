<%@ page contentType="text/html;charset=UTF-8"%>
<div class="col-md-6 col-xs-12">
	<div class="box box-default">
		<div class="box-header with-border">
			<h3 class="box-title">垂直布局</h3>
			<div class="box-tools pull-right">
				<button class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i></button>
				<button class="btn btn-box-tool" data-widget="remove"><i class="fa fa-remove"></i></button>
			</div>
		</div><!-- /.box-header -->
		<div class="box-body">
			<form role="form">
		    	<!-- text input -->
		    	<div class="form-group">
		        	<label>Text</label>
		        	<input type="text" class="form-control" placeholder="Enter ...">
		    	</div>
		    	<div class="form-group">
		    		<label>email</label>
					<div class="input-group">
						<span class="input-group-addon"><i class="fa fa-envelope"></i></span> 
						<input type="email" class="form-control" placeholder="Email">
					</div>
				</div>
				<div class="form-group">
					<div class="checkbox">
						<label>
							<input type="checkbox">
							Checkbox 1
						</label>
					</div>
					<div class="checkbox">
						<label>
							<input type="checkbox">
							Checkbox 2
						</label>
					</div>	
				</div>
				<!-- radio -->
				<div class="form-group">
					<div class="radio">
						<label>
							<input type="radio" name="optionsRadios" id="optionsRadios1" value="option1" checked="">&gt;
							Option one is this and that—be sure to include why it's great
						</label>
					</div>
					<div class="radio">
						<label>
							<input type="radio" name="optionsRadios" id="optionsRadios2" value="option2">&gt;
							Option two can be something else and selecting it will deselect option one
						</label>
					</div>
				</div>
				<!-- select -->
				<div class="form-group">
					<label>Select</label>
					<select class="form-control">
						<option>option 1</option>
						<option>option 2</option>
					</select>
				</div>
			</form>
		</div><!-- /.box-body -->
		<div class="box-footer">
			<button type="submit" class="btn btn-primary">Submit</button>
		</div>
	</div>
</div>

<div class="col-md-6 col-xs-12">
	<div class="box box-default">
		<div class="box-header with-border">
			<h3 class="box-title">水平布局</h3>
			<div class="box-tools pull-right">
				<button class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i></button>
				<button class="btn btn-box-tool" data-widget="remove"><i class="fa fa-remove"></i></button>
			</div>
		</div><!-- /.box-header -->
		<div class="box-body">
			<form role="form" class="form-horizontal">
		    	<!-- text input -->
		    	<div class="form-group">
		        	<label class="col-sm-2 control-label">Text</label>
		        	<div class="col-sm-10">
		        		<input type="text" class="form-control" placeholder="Enter ...">
		        	</div>
		    	</div>
		    	<div class="form-group">
		    		<label class="col-sm-2 control-label">email</label>
		    		<div class="col-sm-10">
						<div class="input-group">
							<span class="input-group-addon"><i class="fa fa-envelope"></i></span> 
							<input type="email" class="form-control" placeholder="Email">
						</div>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">email</label>
					<div class="col-sm-10">
						<div class="input-group">
							<input type="text" class="form-control"> 
							<span class="input-group-btn">
								<button class="btn btn-info btn-flat" type="button">Go!</button>
							</span>
						</div>
					</div>	
				</div>
				<!-- select -->
				<div class="form-group">
					<label class="col-sm-2 control-label">Select</label>
					<div class="col-sm-10">
						<select class="form-control">
							<option>option 1</option>
							<option>option 2</option>
						</select>
					</div>
				</div>
				
				<div class="form-group">
					<div class="col-sm-offset-2 col-sm-10">
						<div class="checkbox">
							<label>
								<input type="checkbox">
								Checkbox 1
							</label>
						</div>
						<div class="checkbox">
							<label>
								<input type="checkbox">
								Checkbox 2
							</label>
						</div>
					</div>
				</div>
				<!-- radio -->
				<div class="form-group">
					<div class="col-sm-offset-2 col-sm-10">
						<div class="radio">
							<label>
								<input type="radio" name="optionsRadios" id="optionsRadios1" value="option1" checked="">&gt;
								Option one is this and that—be sure to include why it's great
							</label>
						</div>
						<div class="radio">
							<label>
								<input type="radio" name="optionsRadios" id="optionsRadios2" value="option2">&gt;
								Option two can be something else and selecting it will deselect option one
							</label>
						</div>
					</div>	
				</div>
			</form>
		</div><!-- /.box-body -->
		<div class="box-footer">
			<button type="submit" class="btn btn-primary">Submit</button>
		</div>
	</div>
</div>