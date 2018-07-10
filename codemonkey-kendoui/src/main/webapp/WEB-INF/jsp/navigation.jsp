<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<body> 
<div id="example" ng-app="KendoDemos"> 
   <div class="demo-section k-content" ng-controller="MyCtrl"> 
    <div id="tickets"> 
     <form id="ticketsForm"> 
      <ul id="fieldlist"> 
       <label >基础按钮</label>
        <p>
            <kendo-button class="k-primary" ng-click="onClick1()">
            默认按钮 </kendo-button>
            <kendo-button on-click="onClick2()"> Button </kendo-button>
        </p>
        <h4>Disabled buttons</h4>
        <p>
            <kendo-button enable="false" class="k-primary">
            默认按钮 </kendo-button>
            <kendo-button enable="false"> Disabled Button </kendo-button>
        </p>
        <p>
            <label><input type="checkbox" ng-model="disabled">
                勾选禁用</label>
            <kendo-button ng-disabled="disabled"> 可禁用的按钮 </kendo-button>
        </p>

        <h4>Buttons with icons</h4>
        <p>
            <kendo-button sprite-css-class="'k-icon k-i-funnel'">
            Filter </kendo-button>
            <kendo-button icon="'funnel-clear'"> Clear Filter
            </kendo-button>
            <kendo-button sprite-css-class="'k-icon k-i-refresh'">
            <span class="k-sprite">Refresh</span> </kendo-button>
        </p>
       <label >菜单</label>
        <h4>Preview</h4>
        <ul kendo-menu="" k-orientation="menuOrientation"
            k-rebind="menuOrientation" k-on-select="onSelect(kendoEvent)">
            <li>Men's                <ul>
                    <li>Footwear
                        <ul>
                            <li>Leisure Trainers</li>
                            <li>Running Shoes</li>
                            <li>Outdoor Footwear</li>
                            <li>Sandals/Flip Flops</li>
                            <li>Footwear Accessories</li>
                        </ul>
                    </li>
                    <li>Leisure Clothing
                        <ul>
                            <li>T-Shirts</li>
                            <li>Hoodies & Sweatshirts</li>
                            <li>Jackets</li>
                            <li>Pants</li>
                            <li>Shorts</li>
                        </ul>
                    </li>
                    <li>Sports Clothing
                        <ul>
                            <li>Football</li>
                            <li>Basketball</li>
                            <li>Golf</li>
                            <li>Tennis</li>
                            <li>Swimwear</li>
                        </ul>
                    </li>
                    <li>Accessories</li>
                </ul>
            </li>
            <li>Women's
                <ul>
                    <li>Footwear
                        <ul>
                            <li>Leisure Trainers</li>
                            <li>Running Shoes</li>
                            <li>Outdoor Footwear</li>
                            <li>Sandals/Flip Flops</li>
                            <li>Footwear Accessories</li>
                        </ul>
                    </li>
                    <li>Leisure Clothing
                        <ul>
                            <li>T-Shirts</li>
                            <li>Jackets</li>
                        </ul>
                    </li>
                    <li>Sports Clothing
                        <ul>
                            <li>Basketball</li>
                            <li>Golf</li>
                            <li>Tennis</li>
                            <li>Swimwear</li>
                        </ul>
                    </li>
                    <li>Accessories</li>
                </ul>
            </li>
            <li>Boy's
                <ul>
                    <li>Footwear
                        <ul>
                            <li>Leisure Trainers</li>
                            <li>Running Shoes</li>
                            <li>Outdoor Footwear</li>
                            <li>Sandals/Flip Flops</li>
                            <li>Footwear Accessories</li>
                        </ul>
                    </li>
                    <li>Leisure Clothing
                        <ul>
                            <li>T-Shirts</li>
                            <li>Hoodies & Sweatshirts</li>
                            <li>Jackets</li>
                            <li>Pants</li>
                            <li>Shorts</li>
                        </ul>
                    </li>
                    <li>Sports Clothing
                        <ul>
                            <li>Football</li>
                            <li>Basketball</li>
                            <li>Rugby</li>
                            <li>Tennis</li>
                            <li>Swimwear</li>
                        </ul>
                    </li>
                    <li>Accessories</li>
                </ul>
            </li>
        </ul>
		
       </ul> 
        <label >菜单panelBar</label>
       <ul kendo-panel-bar="" k-options="panelBarOptions">
			<li>Metallica - Master of Puppets 1986
				<ul>
					<li>Battery</li>
					<li>Master of Puppets</li>
					<li>The Thing That Should Not Be</li>
					<li>Welcome Home (Sanitarium)</li>
					<li>Disposable Heroes</li>
					<li>Leper Messiah</li>
					<li>Orion (Instrumental)</li>
					<li>Damage, Inc.</li>
				</ul>
			</li>
			<li>Iron Maiden - Brave New World 2000
				<ul>
					<li>The Wicker Man</li>
					<li>Ghost Of The Navigator</li>
					<li>Brave New World</li>
					<li>Blood Brothers</li>
					<li>The Mercenary</li>
					<li>Dream Of Mirrors</li>
					<li>The Fallen Angel</li>
					<li>The Nomad</li>
					<li>Out Of The Silent Planet</li>
					<li>The Thin Line Between Love And Hate</li>
				</ul>
			</li>
			 
		</ul>
		
		 <label >tab 页</label>
		 
		 <div kendo-tab-strip="" k-content-urls="[ null, null]"> 
	      <!-- tab list --> 
	      <ul> 
	       <li class="k-state-active">First tab</li> 
	       <li>Second tab</li> 
	      </ul> 
	      <div style="padding: 1em">
	        This is the first tab 
	      </div> 
	      <div style="padding: 1em">
	        This is the second tab 
	      </div> 
	     </div> 
	     
	    <label >toolbar </label>
	    
	      <div kendo-toolbar="" k-options="toolbarOptions"></div> 
	      
	   <label >TreeView </label>
	      
	    <div class="box-col"> 
	     <h4>TreeView</h4> 
	     <div kendo-tree-view="tree" k-data-source="treeData" k-on-change="selectedItem = dataItem"> 
	      <span k-template=""> {{dataItem.text}} <button class="k-button" ng-click="click(dataItem)">Select</button> </span> 
	     </div> 
	    </div> 
	    <div class="box-col" ng-show="selectedItem"> 
	     <h4>Selected: {{selectedItem.text}}</h4> 
	     <button class="k-button" ng-click="addAfter(selectedItem)">Add item below</button> 
	     <button class="k-button" ng-click="addBelow(selectedItem)">Add child item</button> 
	     <button class="k-button" ng-click="remove(selectedItem)">Delete</button> 
	    </div> 
	   </div>   
     </form> 
    </div> 
   </div> 
     <style>
        .demo-section p {
            margin: 0 0 30px;
            line-height: 50px;
        }
        
        .k-primary {
            min-width: 150px;
        }    
    </style> 
    <script>
    
    angular.module("KendoDemos", [ "kendo.directives" ])
    .controller("MyCtrl", function($scope){
        $scope.menuOrientation = "horizontal";
        $scope.onSelect = function(ev) {
            alert($(ev.item.firstChild).text());
        };
        
        $scope.panelBarOptions = {
           contentUrls: [ null, null, "../content/web/loremIpsum.html" ]
        };
        
        $scope.toolbarOptions = {
                items: [
                    { type: "button", text: "Button" },
                    { type: "button", text: "Toggle Button", togglable: true } 
                ]
            };
        /*
        $scope.treeData = new kendo.data.HierarchicalDataSource({ data: [
          { text: "Item 1" },
          { text: "Item 2", items: [
            { text: "SubItem 2.1" },
            { text: "SubItem 2.2" }
          ] },
          { text: "Item 3" }
        ]});*/
        
        
        $scope.treeData = KendoUtils.createDataSource({url: "/kendoui/treeData"});
        

        $scope.click = function(dataItem) {
          alert(dataItem.text);
        };

        function makeItem() {
          var txt = kendo.toString(new Date(), "HH:mm:ss");
          return { text: txt };
        };

        $scope.addAfter = function(item) {
          var array = item.parent();
          var index = array.indexOf(item);
          var newItem = makeItem();
          array.splice(index + 1, 0, newItem);
        };

        $scope.addBelow = function() {
          // can't get this to work by just modifying the data source
          // therefore we're using tree.append instead.
          var newItem = makeItem();
          $scope.tree.append(newItem, $scope.tree.select());
        };

        $scope.remove = function(item) {
          var array = item.parent();
          var index = array.indexOf(item);
          array.splice(index, 1);
        };
                                                                       
    });
    

   </script> 
 </body>