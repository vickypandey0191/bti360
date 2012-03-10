/*!
 * Ext JS Library 3.3.0
 * Copyright(c) 2006-2010 Ext JS, Inc.
 * licensing@extjs.com
 * http://www.extjs.com/license
 * 
 * This code is adapted from the Ext JS Ext.data.DataWriter Example
 * http://dev.sencha.com/deploy/dev/examples/writer/writer.html
 */
Ext.ns('BTI', 'BTI.dictionary');
/**
 * BTI.dictionary
 * A typical EditorGridPanel extension.
 */
BTI.dictionary.Grid = Ext.extend(Ext.grid.GridPanel, {
	store: Ext.StoreMgr.get('dictionary'),
    frame: true,
    title: 'Dictionary',
    height: 300,
    width: 500,

    initComponent : function() {

        // typical viewConfig
        this.viewConfig = {
            forceFit: true
        };
        
        // use RowEditor for editing
        this.editor = new Ext.ux.grid.RowEditor({
            saveText: 'Update'
        });

        // relay the Store's CRUD events into this grid so these events can be conveniently listened-to in our application-code.
        this.relayEvents(this.store, ['destroy', 'save', 'update']);

        // build toolbars and buttons.
        this.tbar = this.buildTopToolbar();
        this.bbar = this.buildBottomToolbar();
        this.buttons = this.buildButtons();
        this.columns = this.buildColumns();
        this.plugins = [this.editor];

        // super
        BTI.dictionary.Grid.superclass.initComponent.call(this);
    },
    
    buildColumns : function(){
    	return [{header: "ID", width: 20, sortable: true, dataIndex: 'id'},
	        {header: "Word", width: 35, sortable: true, dataIndex: 'name', editor: new Ext.form.TextField()},
	        {header: "Definition", width: 150, sortable: true, dataIndex: 'definition', editor: new Ext.form.TextField()}];
    },

    /**
     * buildTopToolbar
     */
    buildTopToolbar : function() {
        return [{
            text: 'Add',
            handler: this.onAdd,
            scope: this
        }, '-', {
            text: 'Delete',
            handler: this.onDelete,
            scope: this
        }, '-'];
    },

    /**
     * buildBottomToolbar
     */
    buildBottomToolbar : function() {
        return ['<b>@cfg:</b>', '-', {
            text: 'autoSave',
            enableToggle: true,
            pressed: true,
            tooltip: 'When enabled, Store will execute Ajax requests as soon as a Record becomes dirty.',
            toggleHandler: function(btn, pressed) {
                this.store.autoSave = pressed;
                Ext.each(this.buttons, function(button){
                	button.setDisabled(pressed);
                })
            },
            scope: this
        }, '-', {
            text: 'writeAllFields',
            enableToggle: true,
            pressed: true,
            tooltip: 'When enabled, Writer will write *all* fields to the server -- not just those that changed.',
            toggleHandler: function(btn, pressed) {
                this.store.writer.writeAllFields = pressed;
            },
            scope: this
        }, '-'];
    },

    /**
     * buildButtons
     */
    buildButtons : function() {
        return [{
            text: 'Save',
            disabled: true,
            handler: this.onSave,
            scope: this
        },{
            text: 'Reject',
            disabled: true,
            handler: this.onReject,
            scope: this
        }];
    },

    /**
     * onSave
     */
    onSave : function(btn, ev) {
        this.store.save();
    },
    
    /**
     * onReject
     */
    onReject : function(btn, ev) {
        this.store.rejectChanges();
    },

    /**
     * onAdd
     */
    onAdd : function(btn, ev) {
        var record = new this.store.recordType({
            word : '',
            definition: ''
        });
        this.editor.stopEditing();
        this.store.insert(0, record);
        this.editor.startEditing(0);
    },

    /**
     * onDelete
     */
    onDelete : function () {
        var rec = this.getSelectionModel().getSelected();
        if (!rec) {
            return false;
        }
        this.store.remove(rec);
    }
});
