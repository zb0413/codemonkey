avalon.component('ms-search-form', {
    template: AvalonUtils.heredoc(function (){
        /*
		<form ms-attr="{action : url}" method="post" class="sidebar-form">
			<div class="input-group">
				<input type="text" name="q" class="form-control" ms-attr="{placeholder : placeholder}" >
				<span class="input-group-btn">
					<button type="submit" name="search" id="search-btn" class="btn btn-flat">
						<i class="fa fa-search"></i>
					</button>
				</span>
			</div>
		</form>
		*/
    }),
	defaults : {
		url : '#',
		placeholder : '搜索...'
	}
});
