avalon.component('ms-breadcrumb', {
    template: AvalonUtils.heredoc(function (){
        /*
			<ol class="breadcrumb" >
				<li ms-for="obj in breadcrumb" ms-class="obj.style">
					<a href="obj.href"><i class="obj.icon"></i>{{obj.text}}</a>
				</li>
			</ol>
		*/
    }),
	defaults:{
		breadcrumb : [
	         {style : 'active' , href:'#' , icon:'fa fa-dashboard' , text:'home'},
	         {style : '' , href:'#' , icon:'' , text:'dashboard'}
		]
	}	
});
