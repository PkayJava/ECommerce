//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.angkorteam.framework.wicket.markup.html.form;

import com.angkorteam.framework.wicket.resource.CodeMirrorResourceReference;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.model.IModel;

public class JavascriptTextArea extends TextArea<String> {

    public JavascriptTextArea(String id) {
        super(id);
    }

    public JavascriptTextArea(String id, IModel<String> model) {
        super(id, model);
    }

    public void renderHead(IHeaderResponse response) {
        super.renderHead(response);
        String markupId = this.getMarkupId(true);
        response.render(CssHeaderItem.forReference(CodeMirrorResourceReference.CODE_MIRROR_STYLE));
        response.render(CssHeaderItem.forReference(CodeMirrorResourceReference.SHOW_HINT_STYLE));
        response.render(CssHeaderItem.forReference(CodeMirrorResourceReference.FULLSCREEN_STYLE));
        response.render(CssHeaderItem.forReference(CodeMirrorResourceReference.THEME_NIGHT_STYLE));
        response.render(JavaScriptHeaderItem.forReference(CodeMirrorResourceReference.CODE_MIRROR_JAVASCRIPT));
        response.render(JavaScriptHeaderItem.forReference(CodeMirrorResourceReference.SHOW_HINT_JAVASCRIPT));
        response.render(JavaScriptHeaderItem.forReference(CodeMirrorResourceReference.SCRIPT_HINT_JAVASCRIPT));
        response.render(JavaScriptHeaderItem.forReference(CodeMirrorResourceReference.SCRIPT_JAVASCRIPT));
        response.render(JavaScriptHeaderItem.forReference(CodeMirrorResourceReference.FULLSCREEN_JAVASCRIPT));
        response.render(OnDomReadyHeaderItem.forScript("CodeMirror.fromTextArea(document.getElementById('" + markupId + "'), { indentUnit: 4, lineNumbers: true, theme: 'night', extraKeys: {'Ctrl-Space': 'autocomplete', 'F11': function(cm) { cm.setOption('fullScreen', !cm.getOption('fullScreen')); }, 'Esc': function(cm) { if (cm.getOption('fullScreen')) cm.setOption('fullScreen', false); }}, mode: {name: 'javascript', globalVars: true} })"));
    }

    protected void onInitialize() {
        super.onInitialize();
        this.setOutputMarkupId(true);
    }
}
