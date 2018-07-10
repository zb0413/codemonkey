/**
 * @class AM.portal.Portlet
 * @extends Ext.panel.Panel
 * A {@link Ext.panel.Panel Panel} class that is managed by {@link Ext.app.PortalPanel}.
 */
Ext.define('AM.portal.Portlet', {
    extend: 'Ext.panel.Panel',
    alias: 'widget.portlet',
    layout: 'fit',
    anchor: '100%',
    frame: true,
    closable: true,
    resizable : true,
    resizeHandles : 's',
    collapsible: true,
    animCollapse: true,
    stateful : true,
    draggable: {
        moveOnDrag: false    
    },
    cls: 'x-portlet',

    // Override Panel's default doClose to provide a custom fade out effect
    // when a portlet is removed from the portal
    doClose: function() {
        if (!this.closing) {
            this.closing = true;
            this.el.animate({
                opacity: 0,
                callback: function(){
                	this.fireEvent('close', this);
                	var portalApp = this.up('portalapp');
                	if(portalApp){
                		portalApp.removePortlet(this);
                	}
                	this[this.closeAction]();
                },
                scope: this
            });
        }
    }
});