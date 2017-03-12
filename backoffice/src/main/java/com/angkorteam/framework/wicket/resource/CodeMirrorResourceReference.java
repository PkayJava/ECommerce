package com.angkorteam.framework.wicket.resource;

import com.angkorteam.framework.ResourceScope;
import org.apache.wicket.request.resource.PackageResourceReference;

/**
 * Created by Khauv Socheat on 4/14/2016.
 */
public class CodeMirrorResourceReference {

    public final static PackageResourceReference CODE_MIRROR_JAVASCRIPT = new PackageResourceReference(ResourceScope.class, "codemirror-5.16.0/lib/codemirror.js");
    public final static PackageResourceReference CODE_MIRROR_STYLE = new PackageResourceReference(ResourceScope.class, "codemirror-5.16.0/lib/codemirror.css");

    public final static PackageResourceReference FULLSCREEN_JAVASCRIPT = new PackageResourceReference(ResourceScope.class, "codemirror-5.16.0/addon/display/fullscreen.js");
    public final static PackageResourceReference FULLSCREEN_STYLE = new PackageResourceReference(ResourceScope.class, "codemirror-5.16.0/addon/display/fullscreen.css");

    public final static PackageResourceReference THEME_NIGHT_STYLE = new PackageResourceReference(ResourceScope.class, "codemirror-5.16.0/theme/night.css");

    public final static PackageResourceReference SHOW_HINT_JAVASCRIPT = new PackageResourceReference(ResourceScope.class, "codemirror-5.16.0/addon/hint/show-hint.js");
    public final static PackageResourceReference SHOW_HINT_STYLE = new PackageResourceReference(ResourceScope.class, "codemirror-5.16.0/addon/hint/show-hint.css");

    public final static PackageResourceReference SCRIPT_HINT_JAVASCRIPT = new PackageResourceReference(ResourceScope.class, "codemirror-5.16.0/addon/hint/javascript-hint.js");
    public final static PackageResourceReference SCRIPT_JAVASCRIPT = new PackageResourceReference(ResourceScope.class, "codemirror-5.16.0/mode/javascript/javascript.js");

    public final static PackageResourceReference CSS_JAVASCRIPT = new PackageResourceReference(ResourceScope.class, "codemirror-5.16.0/mode/css/css.js");
    public final static PackageResourceReference XML_JAVASCRIPT = new PackageResourceReference(ResourceScope.class, "codemirror-5.16.0/mode/xml/xml.js");
    public final static PackageResourceReference VB_JAVASCRIPT = new PackageResourceReference(ResourceScope.class, "codemirror-5.16.0/mode/vbscript/vbscript.js");

    public final static PackageResourceReference SQL_HINT_JAVASCRIPT = new PackageResourceReference(ResourceScope.class, "codemirror-5.16.0/addon/hint/sql-hint.js");
    public final static PackageResourceReference SQL_JAVASCRIPT = new PackageResourceReference(ResourceScope.class, "codemirror-5.16.0/mode/javascript/sql.js");

    public final static PackageResourceReference HTML_HINT_JAVASCRIPT = new PackageResourceReference(ResourceScope.class, "codemirror-5.16.0/addon/hint/html-hint.js");
    public final static PackageResourceReference CSS_HINT_JAVASCRIPT = new PackageResourceReference(ResourceScope.class, "codemirror-5.16.0/addon/hint/css-hint.js");
    public final static PackageResourceReference HTML_JAVASCRIPT = new PackageResourceReference(ResourceScope.class, "codemirror-5.16.0/mode/htmlmixed/htmlmixed.js");

}
