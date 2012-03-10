Ext.ns('BTI');

BTI.servletUrl = '/SpringDictionaryService/bti';

// Create HttpProxy instance.  Notice new configuration parameter "api" here instead of load.  However, you can still use
// the "url" paramater -- All CRUD requests will be directed to your single url instead.
var proxy = new Ext.data.HttpProxy({
    api: {
        read : 'app.php/users/view',
        create : 'app.php/users/create',
        update: 'app.php/users/update',
        destroy: 'app.php/users/destroy'
    }
});

Ext.Ajax.defaultHeaders = {
	'Accept': 'application/json'
};

Ext.override(Ext.data.JsonWriter, {
	render : function(params, baseParams, data) {
        if (this.encode === true) {
            
            Ext.apply(params, baseParams);
            params[this.meta.root] = Ext.encode(data);
        } else {
            var jdata = Ext.apply({}, baseParams);
            //----------BEGIN EDIT--------------
            jdata = Ext.apply(jdata, data); //don't send back under the root element, this will allow Spring bean binding
        	//jdata[this.meta.root] = data; ---default
            //----------END EDIT-----------------
            params.jsonData = jdata;
        }
    }
});

// Typical Store collecting the Proxy, Reader and Writer together.
new Ext.data.Store({
	restful: true,
	autoLoad: true,
    id: 'dictionary',
    url: BTI.servletUrl + '/dictionary.json',
    //proxy: proxy,
    reader: new Ext.data.JsonReader({
        totalProperty: 'total',
        successProperty: 'success',
        idProperty: 'id',
        root: 'data',
        messageProperty: 'message'  // <-- New "messageProperty" meta-data
    }, [
        {name: 'id'},
        {name: 'name', allowBlank: false},
        {name: 'definition', allowBlank: false}
    ]),
    writer: new Ext.data.JsonWriter({
        encode: false,
        writeAllFields: true
    }),  // <-- plug a DataWriter into the store just as you would a Reader
    autoSave: true // <-- false would delay executing create, update, destroy requests until specifically told to do so with some [save] buton.
});
