Ext.define('AM.field.TreeComboBox', {
    extend: 'Ext.form.field.ComboBox',

    url: '',
    tree: {},
    textProperty: 'text',
    valueProperty: 'id',
    
    alias: 'widget.treecombo',

    initComponent: function () {
        Ext.apply(this, {
            editable: false,
            queryMode: 'local',
            select: Ext.emptyFn
        });

        this.displayField = this.displayField || 'text',
        this.treeid = Ext.String.format('tree-combobox-{0}', Ext.id());
        this.tpl = Ext.String.format('<div id="{0}"></div>', this.treeid);

        if (this.modelName) {
            var me = this;
            var store = ExtUtils.treeStore({modelName : me.modelName});
            
            this.tree = Ext.create('Ext.tree.Panel', {
                rootVisible: false,
                autoScroll: true,
                height: 200,
                store: store
            });
            this.tree.on('itemclick', function (view, record) {
                me.setValue(record);
                me.collapse();
            });
            me.on('expand', function () {
                if (!this.tree.rendered) {
                    this.tree.render(this.treeid);
                }
            });
        }
        this.callParent(arguments);
    }
});
 

//前台代码
//
//Ext.create('TreeComboBox', {
//    renderTo: Ext.getBody(),
//    width: 400,
//    url: '/models/tree-data.json'
//});