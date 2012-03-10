
Ext.onReady(function(){
	var app = new Ext.Viewport({
	    autoScroll: true,
	    items: [{
	    	xtype: 'tabpanel',
	    	activeItem: 0,
	    	items:[new BTI.dictionary.Grid(), new BTI.util.restTest()]
	    }]
	});
});