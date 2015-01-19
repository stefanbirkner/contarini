package com.github.stefanbirkner.contarini.render;

/**
 * The style of void elements like {@code meta} and {@code link}. See
 * <a href="http://stackoverflow.com/a/3558200/557091">http://stackoverflow.com/a/3558200/557091</a>
 * for more information about its usage.
 */
public enum VoidElementStyle {
    /**
     * Render a void tag without a slash. Example: {@code <foo>}
     */
    HTML_VOID(">"),
    /**
     * Render a self-closing tag with a whitespace before the slash.
     * Example: {@code <foo />}
     */
    XML_SELF_CLOSING_WITH_SPACE(" />"),
    /**
     * Render a self-closing tag without a whitespace before the slash.
     * Example: {@code <foo/>}
     */
    XML_SELF_CLOSING_WITHOUT_SPACE("/>");

    final String closingSuffix;

    private VoidElementStyle(String closingSuffix) {
        this.closingSuffix = closingSuffix;
    }
}
