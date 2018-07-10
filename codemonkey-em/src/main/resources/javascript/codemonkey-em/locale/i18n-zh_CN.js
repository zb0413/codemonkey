Ext.define('AM.locale.i18n', {
	code : '编码',
	name1 : '名称',
	creationDate : '创建日期',
	createdBy : '创建人',
	modificationDate : '修改日期',
	modifiedBy : '修改人',
	description : '描述',
	id : '自动编号',
	save : '保存',
	backToList : '返回列表',
	search : '查询',
	reset : '重置',
	destroy : '删除',
	actions : '操作',
	add : '添加',
	edit : '修改',
	show : '查看',
	cancel : '关闭',
	close : '关闭',
	originVersion : '版本',
	creationInfo : '创建信息',
	ok : '确认',
	failed : '失败',
	donGriffin:'菜单',
	settings:'设置',
	logout:'退出',
	tips:'您确定要退出吗？',
	start:'开始',
	select : '请选择',
    theme : '风格',
    themeClassic : '经典',
    themeGray : '银灰'
});

Ext.define('OverrideText' , {
	override: 'Ext.form.field.Text',
	
	maxLengthText : '超过长度限制'
});

Ext.define('OverridePanel' , {
	override: 'Ext.panel.Panel',
	
	collapseToolText: '收起',
    expandToolText: '展开',
    closeToolText: '关闭'
});

Ext.define('OverrideNumberField' , {
	override: 'Ext.form.field.Number',
	
	minText : '不能低于最小值 {0}',
    maxText : '不能超出最大值{0}',
    nanText : '{0} 不是数字',
    negativeText : '不能为负数'
});

Ext.define('OverrideLabelStyle' , {
	override: 'Ext.form.field.Text',
	
	blankText : '必填项'
});

//grid header 提示
Ext.define('OverrideGridHeaderContainer', {
	override:  'Ext.grid.header.Container' ,
    sortAscText: '升序',
    sortDescText: '降序',
    sortClearText: '清除排序',
    columnsText: '可选列'
});

Ext.define('OverrideGridFeatureGrouping', {
	override:  'Ext.grid.feature.Grouping' ,
    groupByText : '以本列分组', 
    showGroupsText : '在分组中展示', 
    expandTip : '点击展开 。按ctrl键收缩所有项 ',
    collapseTip: '点击收缩 。按ctrl键收缩所有项'
});

//分页条按钮提示
Ext.define('OverridePaging', {
    override: 'Ext.toolbar.Paging',
	
    firstText : '第一页',
    prevText : '上一页',
    nextText : '下一页',
    lastText : '最后一页',
    refreshText : '刷新'

});

//tab关闭提示
Ext.define('OverrideTab', {
    override: 'Ext.tab.Tab',
    closeText : '关闭'
});

//datapicker提示信息
Ext.define('OverrideDataPicker', {
    override: 'Ext.picker.Date',
    todayText : '今天',
    ariaTitle: '选择日期: {0}',
    minText : '早于最早日期',
    maxText : '晚于最晚日期',
    disabledDaysText : 'Disabled',
    disabledDatesText : 'Disabled',
    nextText : '下一月(Control+Right)',
    prevText : '上一月 (Control+Left)',
    monthYearText : '选择月份',
    dayNames : ['日','一','二','三','四','五','六']
});

Ext.define('OverrideGridRowEditor', {
    override: 'Ext.grid.RowEditor',
    saveBtnText  : '更新',
    cancelBtnText: '取消',
    errorsText: '错误',
    dirtyText: '您需要提交或者取消您的选择！'
});

Ext.define('OverrideMessageBox', {
	override : 'Ext.window.MessageBox',
	buttonText: {
        ok: '确认',
        yes: '是',
        no: '否',
        cancel: '取消'
    }
});
