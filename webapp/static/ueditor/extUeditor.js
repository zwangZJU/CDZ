Ext.define('App.ux.UEditor', {
    extend: 'Ext.form.field.Text',
    alias: ['widget.ueditor'],
    //alternateClassName: 'Ext.form.UEditor',
    fieldSubTpl: [
        '<textarea id="{id}" {inputAttrTpl}',
            '<tpl if="name"> name="{name}"</tpl>',
            '<tpl if="rows"> rows="{rows}" </tpl>',
            '<tpl if="cols"> cols="{cols}" </tpl>',
            '<tpl if="placeholder"> placeholder="{placeholder}"</tpl>',
            '<tpl if="size"> size="{size}"</tpl>',
            '<tpl if="maxLength !== undefined"> maxlength="{maxLength}"</tpl>',
            '<tpl if="readOnly"> readonly="readonly"</tpl>',
            '<tpl if="disabled"> disabled="disabled"</tpl>',
            '<tpl if="tabIdx"> tabIndex="{tabIdx}"</tpl>',
    //            ' class="{fieldCls} {inputCls}" ',
            '<tpl if="fieldStyle"> style="{fieldStyle}"</tpl>',
            ' autocomplete="off">\n',
            '<tpl if="value">{[Ext.util.Format.htmlEncode(values.value)]}</tpl>',
        '</textarea>',
        {
            disableFormats: true
        }
    ],
    ueditorConfig: {},
    initComponent: function () {
        var me = this;
        me.callParent(arguments);
    },
    afterRender: function () {
        var me = this;
        me.callParent(arguments);
        if (!me.ue) {
        	var heigth_=me.height-90;
            me.ue = UE.getEditor(me.getInputId(), Ext.apply(me.ueditorConfig, {
                initialFrameHeight:heigth_  || '200px',
                initialFrameWidth: '100%'
            }));
            me.ue.ready(function () {
                me.UEditorIsReady = true;
            });
             
　　　　　　//这块 组件的父容器关闭的时候 需要销毁编辑器 否则第二次渲染的时候会出问题 可根据具体布局调整
            var win = me.up('window');
            if (win && win.closeAction == "hide") {
                win.on('beforehide', function () {
                    me.onDestroy();
                });
            } else {
                var panel = me.up('panel');
                if (panel && panel.closeAction == "hide") {
                    panel.on('beforehide', function () {
                        me.onDestroy();
                    });
                }
            }
           
        } else {
            me.ue.setContent(me.getValue());
        }
    },
    setValue: function (value) {
        var me = this;
        if (!me.ue) {
            me.setRawValue(me.valueToRaw(value));
        } else {
            me.ue.ready(function () {
                me.ue.setContent(value);
            });
        }
        me.callParent(arguments);
        return me.mixins.field.setValue.call(me, value);
    },
    getRawValue: function () {
        var me = this;
        if (me.UEditorIsReady) {
            me.ue.sync(me.getInputId());
        }
        v = (me.inputEl ? me.inputEl.getValue() : Ext.value(me.rawValue, ''));
        me.rawValue = v;
        return v;
    },
    destroyUEditor: function () {
        var me = this;
        if (me.rendered) {
            try {
                me.ue.destroy();
                var dom = document.getElementById(me.id);
                if (dom) {
                    dom.parentNode.removeChild(dom);
                }
                me.ue = null;
            } catch (e) { }
        }
    },
    onDestroy: function () {
        var me = this;
        me.callParent();
        me.destroyUEditor();
    }
});

Ext.define('MDM.view.custom.MultiComboBox', {
    extend: 'Ext.form.ComboBox',
    alias: 'widget.multicombobox',
    xtype: 'multicombobox',
    initComponent: function() {
        this.multiSelect = true;
        this.listConfig = {
            itemTpl: Ext.create('Ext.XTemplate',
'<input type=checkbox>{' + this.displayField + '}'),
            onItemSelect: function(record) {
                var node = this.getNode(record);
                var count = this.getStore().data.length;
                if (node) {
                    Ext.fly(node).addCls(this.selectedItemCls);
                    var checkboxs = node.getElementsByTagName("input");
                    if (checkboxs != null) {
                        var checkbox = checkboxs[0];
                        checkbox.checked = true;
                    }
                }
            },
            onItemDeselect: function(record) {
            var node = this.getNode(record);
            var count = this.getStore().data.length;
            if (node) {
                Ext.fly(node).removeCls(this.selectedItemCls);
                var checkboxs = node.getElementsByTagName("input");
                if (checkboxs != null) {
                    var checkbox = checkboxs[0];
                    checkbox.checked = false;
                }
            }
            },
            listeners: {
                itemclick: function(view, record, item, index, e, eOpts) {
                    var isSelected = view.isSelected(item);
                    var checkboxs = item.getElementsByTagName("input");
                    if (checkboxs != null) {
                        var checkbox = checkboxs[0];
                        if (!isSelected) {
                            checkbox.checked = true;
                        } else {
                            checkbox.checked = false;
                        }
                    }
                }
            }
        }
        this.callParent();
    }
});