avalon.component('ms-navi-tree', {
    template: AvalonUtils.heredoc(function (){
        /*
         <li class="treeview">
	         <li class="active" ms-for="(index, el) in tree | get(0)">
	         	<a href="#">
					<i class="fa fa-dashboard"></i>
					<span> {{ el.text }} </span>
					<span class="pull-right-container">
						<i class="fa fa-angle-left pull-right"></i>
					</span>
				</a>
				<ul ms-if="el.subtree.length > 0" class="treeview-menu">
					<wbr is="ms-navi-tree" ms-widget="{tree : el.subtree}"/>
				</ul>
	         </li>
         </li>
		*/
    }),
	defaults : {
		tree: [
            {text: "aaa", open: 1, subtree: [
                {text: 1111, open: 1, subtree: []},
                {text: 2222, open: 1, subtree: [
                    {text: 777, open: 1, subtree: []}
                ]},
                {text: 3333, open: 1, subtree: [
                    {text: 8888, open: 1, subtree: []},
                    {text: 9999, open: 1, subtree: [
                        {text: '司徒正美', open: 1, subtree: []}
                    ]}
                ]}
            ]},
            {text: "bbb", open: 1, subtree: [
                {text: 4444, open: 1, subtree: []},
                {text: 5555, open: 1, subtree: []},
                {text: 6666, open: 1, subtree: []}
            ]},
            {text: "ccc", open: 1, subtree: []},
            {text: "ddd", open: 1, subtree: []},
            {text: "eee", open: 1, subtree: [
                {text: 1234, open: 1, subtree: []}
            ]},
            {text: "fff", open: 1, subtree: []}
        ]
	}
});
